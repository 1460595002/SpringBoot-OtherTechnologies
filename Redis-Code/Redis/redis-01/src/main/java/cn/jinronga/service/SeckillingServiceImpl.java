package cn.jinronga.service;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SeckillingService
 * @Author 郭金荣
 * @Date 2021/4/11 18:31
 * @Description SeckillingService
 * @Version 1.0
 */
@Service
public class SeckillingServiceImpl implements SeckillingService {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    public String seCkiLingInfo() {
        String lockKey = "lockKey";
        String clientId = UUID.randomUUID().toString();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(10, TimeUnit.SECONDS);
            //获取redis库存的信息
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock").toString());
            if (stock > 0) {
                int residueNum = stock - 1;

                stringRedisTemplate.opsForValue().set("stock", residueNum + "");

                System.out.println("扣除库存成功！剩余库存：" + residueNum);
                return residueNum + "";
            } else {
                System.out.println("扣减失败，库存不足！");
            }
        } finally {
            lock.unlock();
        }
        return "end";
    }
}
