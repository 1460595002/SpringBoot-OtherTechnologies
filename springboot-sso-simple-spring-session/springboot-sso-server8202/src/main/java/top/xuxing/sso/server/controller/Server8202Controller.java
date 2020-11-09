package top.xuxing.sso.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xuxing.sso.server.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * @description: TODO 
 * @author xu.h.b
 * @date 2020/8/27 14:20
 */
@Controller
public class Server8202Controller {

    private Logger logger = LoggerFactory.getLogger(Server8202Controller.class);

    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/dologin")
    public void doLogin(User user, HttpServletRequest request, HttpServletResponse response){

        String token = UUID.randomUUID().toString().replace("-", "");

        // 假设登陆成功,将用户信息保存到redis
        HttpSession session = request.getSession();
        // 脱敏
        user.setPassword(null);
        session.setAttribute(token,user);
        response.addCookie(new Cookie("token",token));
        // 转发到目标地址
        String redirectUri = (String) session.getAttribute("targetURI");
        String requestPath = redirectUri == null ? "main":redirectUri;
        try {
            response.sendRedirect(requestPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/look/book")
    public String lookBook(){
        logger.info("当前接口地址:{}","/look/book");
        return "books";
    }

    @RequestMapping("/tobooks")
    public String toBooks(){
        logger.info("当前接口地址:{}","/look/book");
        return "books";
    }
}
