package cn.jinronga.gmall;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/8/7 0007
 * Time: 22:39
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
