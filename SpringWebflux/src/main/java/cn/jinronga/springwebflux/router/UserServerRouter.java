package cn.jinronga.springwebflux.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import cn.jinronga.springwebflux.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @ClassName UserServerRouter
 * @Author 郭金荣
 * @Date 2021/6/26 23:23
 * @Description UserServerRouter
 * @Version 1.0
 */
@Configuration
public class UserServerRouter {
    @Autowired
    private UserHandler userHandler;

    /**
     * )
     * 创建Router路由
     */
    @Bean
    public RouterFunction<ServerResponse> responseRouterFunction() {

        //设置路由
        return RouterFunctions.route(GET("/fluxuser/{id}")
                .and(accept(MediaType.APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(GET("/fluxusers/list"),  userHandler::getAllUser)
                .andRoute(GET("/fluxuser/add").and(contentType(MediaType.APPLICATION_JSON)), userHandler::saveUser);
//                .andRoute(GET("/user/update").and(contentType(MediaType.APPLICATION_JSON)),userHandler::update);
//                .andRoute(GET("/user/delete/{id}"),userHandler::deleteId);
    }


}
