package cn.jinronga.springwebflux.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName User
 * @Author 郭金荣
 * @Date 2021/6/26 19:32
 * @Description User
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private String gender;
    private Integer age;
}
