package cn.jinronga.shiro.controller;




import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2019/12/29 0029
 * Time: 16:26
 * E-mail:1460595002@qq.com
 * 类说明:Controller
 */
@Controller
public class Mycontroller {


    @RequestMapping({"/","/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg","hello shiro");

        return "index";

    }

    @RequestMapping("/user/update")
    public  String update(){

        return "/user/update";
    }

    @RequestMapping("/user/add")
    public  String add(){

        return "/user/add";
    }

    @RequestMapping("/Tologin")
   public String toLogin(){

        return "login";
   }

   @RequestMapping("/login")
   public String login(String username,String password,Model model) {

       //获取当前用户
       Subject subject = SecurityUtils.getSubject();

       //封装用户的登录数据
       UsernamePasswordToken token = new UsernamePasswordToken(username, password);

       try {
           subject.login(token);//执行登录的方法，如果没有异常说明OK了

           return "index";//登录成功返回首页
       } catch (UnknownAccountException e) {//用户名不存在
           model.addAttribute("msg", "用户名不存在");
           return "login";


       } catch (IncorrectCredentialsException e) {//密码错误
           model.addAttribute("msg", "密码错误");
           return "login";

       }


   }

   //没有权限的页面
   @ResponseBody
   @RequestMapping("/unauthorized")
   public  String unauthorized(){

        return "你没有权限！";
   }
}
