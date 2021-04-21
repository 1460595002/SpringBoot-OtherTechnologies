package cn.jinronga.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.asm.Type;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;


/**
 * @ClassName RedisDelayingQueue
 * @Author 郭金荣
 * @Date 2021/4/14 19:40
 * @Description RedisDelayingQueue
 * @Version 1.0
 */
public class RedisDelayingQueue<T> {

    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    //TypeReference<T> type, Feature... features
    private TypeReference<TaskItem> TaskType = (TypeReference<TaskItem>) new TypeReference<TaskItem<T>>() {
    }.getType();
    private Jedis jedis;
    private String queueKey;


    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    private void delay(T msg) {
        TaskItem taskItem = new TaskItem();
        taskItem.id = UUID.randomUUID().toString();
        taskItem.msg = msg;
        String s = JSON.toJSONString(taskItem);
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, s);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            //只取一条
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }
            String s = values.iterator().next();
            if (jedis.zrem(queueKey, s) > 0) {//抢到了
                TaskItem taskItem = JSON.parseObject(s, TaskType);// fastjson 反序列化
                handleMsg((T) taskItem.msg);
            }

        }
    }

    public void handleMsg(T msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        RedisDelayingQueue<Object> queue = new RedisDelayingQueue<>(jedis, "q-demo");

        Thread producer = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    queue.delay("codehole" + i);
                }
            }
        };
        Thread consumer = new Thread() {
            @Override
            public void run() {
                queue.loop();
            }
        };
        producer.start();
        consumer.start();
        try {
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {

        }
    }
}
