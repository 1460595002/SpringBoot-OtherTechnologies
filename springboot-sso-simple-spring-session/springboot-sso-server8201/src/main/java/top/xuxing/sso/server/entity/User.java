package top.xuxing.sso.server.entity;

import java.io.Serializable;

/**
 * @description: TODO 
 * @author xu.h.b
 * @date 2020/8/27 11:43
 */
public class User implements Serializable {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
