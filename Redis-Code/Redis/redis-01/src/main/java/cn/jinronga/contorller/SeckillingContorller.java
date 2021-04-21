package cn.jinronga.contorller;

import cn.jinronga.service.SeckillingService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SeckillingContorller
 * @Author 郭金荣
 * @Date 2021/4/11 18:31
 * @Description SeckillingContorller
 * @Version 1.0
 */
@RestController
public class SeckillingContorller {

    @Autowired
    private SeckillingService seckillingService;


    @RequestMapping("/secking")
    public String seCkLing() {
        seckillingService.seCkiLingInfo();

        return seckillingService.seCkiLingInfo();
    }
}
