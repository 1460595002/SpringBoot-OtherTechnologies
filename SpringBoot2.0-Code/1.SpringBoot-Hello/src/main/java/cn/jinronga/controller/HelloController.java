package cn.jinronga.controller;

import cn.jinronga.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class HelloController {
    @Autowired
    Car car;
    @RequestMapping("/hello")
    public String hello(){
        return "Hello,SpringBoot2.0!";
    }

    @RequestMapping("/car")
    public Car car1(){
        return car;
    }
}
