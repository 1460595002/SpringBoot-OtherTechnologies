package cn.jinronga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Redis02Application {

	public static void main(String[] args) {
		SpringApplication.run(Redis02Application.class, args);
	}

}
