package cn.jinronga.springwebflux.handler;

import cn.jinronga.springwebflux.pojo.User;
import cn.jinronga.springwebflux.service.UserService;
import org.reactivestreams.Subscriber;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserHandler
 * @Author 郭金荣
 * @Date 2021/6/26 22:58
 * @Description UserHandler
 * @Version 1.0
 */
@Component
public class UserHandler {

    private final UserService userService;


    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    /**
     * 根据id查询
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        /**
         * 获取id
         */
        Integer id = Integer.valueOf(request.pathVariable("id"));
        /**
         * 空值处理
         */
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//调用service方法获取数据
        Mono<User> userMono = userService.getUserById(id);

        /* userMono进行转换返回*/
        /* 使用Reactor操作符fluxMap*/
        return userMono.flatMap(person -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(person)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        Flux<User> userFlux = userService.getAllUser();


        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userFlux, User.class);
    }

    /**
     * 添加
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);

        return ServerResponse.ok().build(userService.saveUserInfo(userMono));
    }

}
