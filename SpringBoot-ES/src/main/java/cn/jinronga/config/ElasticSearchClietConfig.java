package cn.jinronga.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/13 0013
 * Time: 15:39
 * E-mail:1460595002@qq.com
 * 类说明:
 */

//1、找对象
//3、放到spring容器中待用
@Configuration
public class ElasticSearchClietConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
           return client;
    }
}
