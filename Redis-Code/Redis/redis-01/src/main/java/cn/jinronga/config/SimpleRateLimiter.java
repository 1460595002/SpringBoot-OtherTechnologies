package cn.jinronga.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @ClassName SimpleRateLimiter
 * @Author 郭金荣
 * @Date 2021/4/17 0:06
 * @Description SimpleRateLimiter
 * @Version 1.0
 */
public class SimpleRateLimiter {
    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long newTs = System.currentTimeMillis();//时间戳
        Pipeline pipe = jedis.pipelined();
        pipe.multi();
        System.out.println(newTs+"newTs:");
        //记录行为
        pipe.zadd(key, newTs, "" + newTs);
        long l = newTs - period * 1000;
        System.out.println(l+"l:");
        //移除时间窗口之前的行为记录，剩下的都是时间窗口内的
        pipe.zremrangeByScore(key, 0, newTs - period * 1000);
        // 获取窗口内的行为数量
        Response<Long> count = pipe.zcard(key);
        pipe.exec();
        pipe.close();
        // 比较数量是否超标
        return count.get() <= maxCount;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);
        for (int i = 0; i < 20; i++) {
            System.out.println(limiter.isActionAllowed("laoqian", "reply", 60, 5));
        }
    }
}
