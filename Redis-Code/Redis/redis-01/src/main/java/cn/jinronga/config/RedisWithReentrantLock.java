package cn.jinronga.config;

import com.sun.org.apache.bcel.internal.generic.NEW;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RedisWithReentrantLock
 * @Author 郭金荣
 * @Date 2021/4/13 22:13
 * @Description RedisWithReentrantLock
 * @Version 1.0
 */
public class RedisWithReentrantLock {
    private ThreadLocal<Map> lockers = new ThreadLocal<Map>();
    private Jedis jedis;

    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 加锁
     *
     * @param key
     * @return
     */
    private boolean _lock(String key) {
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.ex(5);
        return jedis.set(key, "", setParams) != null;
    }

    /**
     * 解锁
     *
     * @param key
     */
    public void _unlock(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }
        boolean ok = _lock(key);
        if (!ok) {
            return false;
        }
        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer reCnt = refs.get(key);
        reCnt -= 1;
        if (reCnt != null) {
            refs.put(key, reCnt);
            return true;
        } else {
            refs.remove(key);
            _unlock(key);
            return false;
        }
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.unlock("codehole"));
        System.out.println(redis.unlock("codehole"));
    }
}
