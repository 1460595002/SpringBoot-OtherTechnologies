package cn.jinronga.gmall;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MainApplication {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
		
		OrderService orderService = applicationContext.getBean(OrderService.class);

		List<UserAddress> userAddresses = orderService.initOrder("1");
		Iterator<UserAddress> iterator = userAddresses.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}

		System.out.println("调用完成....");
		System.in.read();
	}

}
