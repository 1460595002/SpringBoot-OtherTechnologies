package cn.jinronga.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2019/12/29 0029
 * Time: 17:07
 * E-mail:1460595002@qq.com
 * 类说明:shiro配置类
 */
@Configuration//这是配置类
public class ShiroConfig {

    //shiroFilterFactoryBean工程对象
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean =new ShiroFilterFactoryBean();
      //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /**
         * anon:匿名拦截器无需认证也能访问
         * authc:必须认证才能访问
         * user:必须拥有记住我才能访问
         * perms：拥有某个资源权限才能访问
         * role：拥有某个角色权限才能进行访问
         */
         /* filterMap.put("/add","authc");
       filterMap.put("/update","authc");*/
         //拦截
        Map<String, String> filterMap=new LinkedHashMap<>();

        //授权 正常跳转401页面表示没有授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/user/*","authc");

        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录请求
        bean.setLoginUrl("/Tologin");

        //设置没有授权的页面
    bean.setUnauthorizedUrl("/unauthorized");

        return bean;
    }


    //DefaultWebSecurityManager

    //与自定义realm对象关联起来
    @Bean(name ="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        // 关联UserRealm对象
        securityManager.setRealm(userRealm);

         return securityManager;

    }




   //创建realm对象   1:需要自定义对象
    @Bean(name="userRealm")
    public UserRealm userRealm(){

        return new UserRealm();
    }


    //整合shiroDialect：用来整合shiro thymleay

    @Bean
    public ShiroDialect getShiroDialect(){

        return  new ShiroDialect();

    }


}
