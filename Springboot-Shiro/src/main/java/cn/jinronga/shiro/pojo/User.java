package cn.jinronga.shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2019/12/31 0031
 * Time: 11:14
 * E-mail:1460595002@qq.com
 * 类说明:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private  int id;
    private  String name;

    private  String password;

    private  String perms;//用户权限




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
