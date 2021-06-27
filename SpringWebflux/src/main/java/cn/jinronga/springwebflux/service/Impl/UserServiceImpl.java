package cn.jinronga.springwebflux.service.Impl;

import cn.jinronga.springwebflux.pojo.User;
import cn.jinronga.springwebflux.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Author 郭金荣
 * @Date 2021/6/26 19:42
 * @Description UserServiceImpl
 * @Version 1.0
 */
@Repository
public class UserServiceImpl implements UserService {
    /**
     * 创建map集合存储数据
     */
    private final Map<Integer, User> userMap = new HashMap<>();

    public UserServiceImpl() {
        this.userMap.put(1, new User("lucy", "nan", 20));
        this.userMap.put(2, new User("mary", "nv", 30));
        this.userMap.put(3, new User("jack", "nan", 40));
        this.userMap.put(4, new User("WeiSanJin", "nan", 50));
    }

    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(userMap.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(userMap.values());
    }

    @Override
    public Mono<Void> saveUserInfo(Mono<User> user) {
        return user.doOnNext(person -> {
            int id = userMap.size() + 1;
            userMap.put(id,person);
        }).thenEmpty(Mono.empty());
    }
}
