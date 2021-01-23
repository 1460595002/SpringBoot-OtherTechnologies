package cn.jinronga.controller;

import cn.jinronga.entity.Person;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ParameterTestController
 * @Author 郭金荣
 * @Date 2021/1/23 14:22
 * @Description ParameterTestController
 * @Version 1.0
 */
@RestController
public class ParameterTestController {
    /**
     * 数据绑定：页面提交的请求数据（GET、POST）都可以和对象属性进行绑定
     *
     * @param person
     * @return
     */

    @PostMapping("saveuser")
    public Person saveuser(Person person) {
        return person;
    }

    @GetMapping("/car/{id}/owner/{username}")
    public Map<String, Object> getCar(
            @PathVariable("id") Integer id,
            @PathVariable("username") String username,
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader Map<String, String> header,
            @RequestParam("age") Integer age,
            @RequestParam("inters") List<String> inters,
            @RequestParam Map<String, String> params,
            @CookieValue("_ga") String _ga,
            @CookieValue("_ga") Session.Cookie cookie) {

        Map<String, Object> map = new HashMap<>();

//        map.put("id",id);
//        map.put("name",name);
//        map.put("pv",pv);
//        map.put("userAgent",userAgent);
//        map.put("headers",header);
        map.put("age", age);
        map.put("inters", inters);
        map.put("params", params);
        map.put("_ga", _ga);
        System.out.println(cookie.getName() + "===>" + cookie.getPath());
        return map;
    }

    @PostMapping("/save")
    public Map postMethod(@RequestBody String content) {
        HashMap<String, Object> map = new HashMap<>();
        return map;
    }

    //矩阵变量的写法，springboot默认禁用矩阵变量的，所以需要在config中自定义
    //1、语法： 请求路径：/cars/sell;low=34;brand=byd,audi,yd
    //2、SpringBoot默认是禁用了矩阵变量的功能
    //      手动开启：原理。对于路径的处理。UrlPathHelper进行解析。
    //              removeSemicolonContent（移除分号内容）支持矩阵变量的
    //3、矩阵变量必须有url路径变量才能被解析
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brand,
                        @PathVariable("path") String path) {
        Map<String, Object> map = new HashMap<>();

        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }

    // /boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age", pathVar = "empId") Integer empAge) {
        Map<String, Object> map = new HashMap<>();

        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }
}
