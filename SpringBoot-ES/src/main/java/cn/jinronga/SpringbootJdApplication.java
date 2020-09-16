package cn.jinronga;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootJdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJdApplication.class, args);

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("127.0.0.1", 9200, "http"),
						new HttpHost("localhost", 9201, "http")));
	}
}
