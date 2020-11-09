package top.xuxing.sso.server.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.xuxing.sso.server.entity.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: TODO 
 * @author xu.h.b
 * @date 2020/8/27 14:22
 */
@Component
public class Server8202AuthenticationFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(Server8202AuthenticationFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取到token
        Cookie[] cookies = request.getCookies() == null ? new Cookie[]{}:request.getCookies();
        List<Cookie> cookiesArray = Arrays.asList(cookies);
        List<Cookie> tokens = cookiesArray.stream().filter((item) -> {
            return item != null && item.getName().equalsIgnoreCase("token") && item.getValue().length() == 32;
        }).collect(Collectors.toList());

        logger.info("判断用户是否登陆...");
        String requestURI = request.getRequestURI();
        logger.info("目标地址URI:{}", requestURI);
        HttpSession session = request.getSession();
        session.setAttribute("targetURI", requestURI);

        // 判断请求是否携带token
        if (tokens.size() == 0) {
            logger.info("用户未登录...");
            response.sendRedirect("/login");
            return;
        } else {
            User user = (User) session.getAttribute(tokens.get(0).getValue());
            if (user == null) {
                response.sendRedirect("/login");
                return;
            }
        }
        logger.info("过滤器放行:{}", requestURI);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
