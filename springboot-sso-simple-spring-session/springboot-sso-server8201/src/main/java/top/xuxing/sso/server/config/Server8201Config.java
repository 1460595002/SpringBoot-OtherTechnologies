package top.xuxing.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xuxing.sso.server.filter.Server8201AuthenticationFilter;

import javax.servlet.Filter;
import java.util.ArrayList;

/**
 * @description: TODO 
 * @author xu.h.b
 * @date 2020/8/27 14:03
 */
@Configuration
public class Server8201Config {

    @Autowired
    Server8201AuthenticationFilter server8201AuthenticationFilter;

    @Bean
    public FilterRegistrationBean authenticationFilter(){
        FilterRegistrationBean<Filter> fr = new FilterRegistrationBean();
        fr.setFilter(server8201AuthenticationFilter);
        fr.addUrlPatterns("/look/book");
        return fr;
    }
}
