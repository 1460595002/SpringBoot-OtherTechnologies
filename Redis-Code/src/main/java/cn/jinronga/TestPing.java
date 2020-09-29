package cn.jinronga;

import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/9 0009
 * Time: 10:46
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class TestPing {

    public static void main(String[] args) {

        //1、new Jedis对象即可
        Jedis jedis=new Jedis("127.0.0.1",6379);
        // jedis 所有的命令就是我们之前学习的所有指令！所以之前的指令学习很重要！

        System.out.println(jedis.ping());


    }
}
