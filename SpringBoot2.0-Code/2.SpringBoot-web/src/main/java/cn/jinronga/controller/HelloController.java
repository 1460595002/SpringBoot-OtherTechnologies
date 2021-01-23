package cn.jinronga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Author 郭金荣
 * @Date 2021/1/23 9:16
 * @Description HelloController
 * @Version 1.0
 */
@Controller
public class HelloController {
    @RequestMapping("/")
    public String hello() {
        return "res/index";
    }
}
