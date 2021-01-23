package cn.jinronga.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author 郭金荣
 * @Date 2021/1/23 11:02
 * @Description UserController
 * @Version 1.0
 */
@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser() {
        return "查询！！！";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String addUser() {
        return "添加！！！";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String updateUser() {
        return "修改！！！";
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteUser() {
        return "删除！！！";
    }
}
