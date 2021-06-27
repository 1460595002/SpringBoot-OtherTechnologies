package cn.jinronga.springwebflux.service;

import cn.jinronga.springwebflux.pojo.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserService
 * @Author 郭金荣
 * @Date 2021/6/26 19:33
 * @Description UserService
 * @Version 1.0
 */
public interface UserService {
    /**
     * 根据id查询用户
     */
    Mono<User> getUserById(int id);

    /**
     * 查询所有用户
     */
    Flux<User> getAllUser();

    /**
     * 添加用户
     */
    Mono<Void> saveUserInfo(Mono<User> user);
}
