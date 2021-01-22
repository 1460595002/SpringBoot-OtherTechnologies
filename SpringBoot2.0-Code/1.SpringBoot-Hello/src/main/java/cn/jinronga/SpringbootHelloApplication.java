package cn.jinronga;

import ch.qos.logback.core.db.DBHelper;
import cn.jinronga.config.MyConfig;
import cn.jinronga.entity.Pet;
import cn.jinronga.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 主程序类
 */
@SpringBootApplication
public class SpringbootHelloApplication {

	public static void main(String[] args) {
		//1、返回我们的IDC容器
		ConfigurableApplicationContext run = SpringApplication.run(SpringbootHelloApplication.class, args);

		//2、查看容器里面组件的东西
		String[] beanDefinitionNames = run.getBeanDefinitionNames();

		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
		//3、从容器中获取组件

		Pet tom01 = run.getBean("tom", Pet.class);

		Pet tom02 = run.getBean("tom", Pet.class);

		System.out.println("组件："+(tom01 == tom02));
		//4、com.atguigu.boot.config.MyConfig$$EnhancerBySpringCGLIB$$51f1e1ca@1654a892
		MyConfig bean = run.getBean(MyConfig.class);
		System.out.println(bean);

		//如果@Configuration(proxyBeanMethods = true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。
		//保持组件单实例
		User user = bean.user1();
		User user1 = bean.user1();
		System.out.println(user == user1);


		User user01 = run.getBean("user01", User.class);
		Pet tom = run.getBean("tom", Pet.class);

		System.out.println("用户的宠物："+(user01.getPet()== tom));
		System.out.println("===获取user===类型的名字");
		String[] beanNamesForType = run.getBeanNamesForType(User.class);
		for (String s : beanNamesForType) {
			System.out.println(s);
		}
		String[] dbBeanNames = run.getBeanNamesForType(DBHelper.class);
		for (String dbBeanName : dbBeanNames) {
			System.out.println(dbBeanName);
		}
		System.out.println("========xml=Import======");
		User haha = run.getBean("haha",User.class);
		Pet hehe = run.getBean("hehe", Pet.class);
		System.out.println(haha);
		System.out.println(hehe);
	}
}
