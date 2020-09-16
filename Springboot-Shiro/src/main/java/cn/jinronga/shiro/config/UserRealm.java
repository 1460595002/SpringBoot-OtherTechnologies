package cn.jinronga.shiro.config;

import cn.jinronga.shiro.pojo.User;
import cn.jinronga.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2019/12/29 0029
 * Time: 17:12
 * E-mail:1460595002@qq.com
 * 类说明:想要自定义的Realm继承extends AuthorizingRealm就可以 权限认证
 */
public class UserRealm extends AuthorizingRealm {


    //需要连接数据库装备进来
    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权+doGetAuthorizationInfo");

        //授权 SimpleAuthorizationInfo
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
       //经过user:add就加个权限
        info.addStringPermission("user:add");
        //经过user:update就加个权限
        info.addStringPermission("user:update");
        //拿到当前登录的这个对象
        Subject subject= SecurityUtils.getSubject();
        User  currentUser= (User)subject.getPrincipal();//拿到user对象了

        //给数据库查取的user权限  getPerms数据库中拿到的
        info.addStringPermission(currentUser.getPerms());

        return info;

    }
   //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证+doGetAuthorizationInfo");




        UsernamePasswordToken userToken=(UsernamePasswordToken )token;
        //连接真实的数据库
         User user=userService.queUserByName(userToken.getUsername());
        if(null==user){//没有这个人
            return null;//抛出异常未知道用户名

        }

         //密码认证shiro来做
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
