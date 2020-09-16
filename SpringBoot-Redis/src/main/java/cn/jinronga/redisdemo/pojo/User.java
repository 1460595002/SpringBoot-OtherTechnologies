package cn.jinronga.redisdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/8/13 0013
 * Time: 13:33
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private int age;
}