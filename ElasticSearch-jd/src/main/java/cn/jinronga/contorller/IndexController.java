package cn.jinronga.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/14 0014
 * Time: 11:08
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Controller
public class IndexController {

     @GetMapping({"/","/index"})
    public String index(){

        return "index";
    }
}
