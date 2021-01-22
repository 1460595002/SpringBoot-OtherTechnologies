package cn.jinronga.config;

import ch.qos.logback.core.db.DBHelper;
import cn.jinronga.entity.Pet;
import cn.jinronga.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.server.handler.WebHandlerDecorator;

/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proxyBeanMethods：代理bean的方法
 *      Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 *      Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 *      组件依赖必须使用Full模式默认。其他默认是否Lite模式
 * 4、@import(iUser.class,DBHeLper.class})
 * 给容器中自动创建出这两个类型的组件
 */
@Import({User.class, DBHelper.class})
@Configuration(proxyBeanMethods = true)
//@ConditionalOnBean(name ="tom")//当有tom组件的时候就注入
//@ConditionalOnMissingBean(name = "tom")//当没有tom组件的时候注入
@ImportResource("classpath:bean.xml")
public class MyConfig {
    /**
     * Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     * @return
     */
   // @ConditionalOnBean(name ="tom")//当有tom组件的时候就注入user01组件
    @Bean("user01")
    public User user1(){
        return new User("jinronga",14);
    }
    @Bean("tom")
    public Pet pet(){
        return new Pet("tom");
    }
}
