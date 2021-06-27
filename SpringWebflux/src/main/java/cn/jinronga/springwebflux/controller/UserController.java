package cn.jinronga.springwebflux.controller;

import cn.jinronga.springwebflux.pojo.User;
import cn.jinronga.springwebflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserController
 * @Author 郭金荣
 * @Date 2021/6/26 20:00
 * @Description UserController
 * @Version 1.0
 */
@RestController
public class UserController {
/*    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Mono<User> getUserId(@PathVariable int id) {
        return userService.getUserById(id);
    }

    *//**
     * 查询所有
     *//*
    @GetMapping("/users")
    public Flux<User> getUsers() {
        return userService.getAllUser();
    }

    *//**
     * 添加
     *//*
    @PostMapping("/saveUser")
    public Mono<Void> saveUser(@RequestBody User user) {
        Mono<User> userMono = Mono.just(user);
        return userService.saveUserInfo(userMono);
    }*/
}
