package cn.jinronga.shiro.service.impl;

import cn.jinronga.shiro.mapper.UserMapper;
import cn.jinronga.shiro.pojo.User;
import cn.jinronga.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2019/12/31 0031
 * Time: 11:52
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User queUserByName(String name) {
        return userMapper.queyUserByName(name);
    }
}
