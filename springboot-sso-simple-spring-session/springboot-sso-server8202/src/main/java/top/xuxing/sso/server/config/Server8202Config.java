package top.xuxing.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xuxing.sso.server.filter.Server8202AuthenticationFilter;

import javax.servlet.Filter;

/**
 * @description: TODO 
 * @author xu.h.b
 * @date 2020/8/27 14:03
 */
@Configuration
public class Server8202Config {

    @Autowired
    Server8202AuthenticationFilter server8202AuthenticationFilter;

    @Bean
    public FilterRegistrationBean authenticationFilter(){
        FilterRegistrationBean<Filter> fr = new FilterRegistrationBean();
        fr.setFilter(server8202AuthenticationFilter);
        fr.addUrlPatterns("/look/book");
        return fr;
    }
}
