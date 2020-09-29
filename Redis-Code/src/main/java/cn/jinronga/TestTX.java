package cn.jinronga;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/9 0009
 * Time: 11:10
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class TestTX {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);

        jedis.flushDB();//清空
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("hello","world");
        jsonObject.put("name","jinronga");

        //开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();

        try {
//            jedis.watch(result);//监听数据
            multi.set("user1",result);
            multi.set("user2",result);
               int i=1/0;
            multi.exec();
        }catch (Exception e){
            multi.discard();//放弃事务
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();//关闭连接
        }
    }
}
