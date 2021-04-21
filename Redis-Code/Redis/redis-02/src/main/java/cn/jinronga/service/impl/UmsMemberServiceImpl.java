package cn.jinronga.service.impl;

import cn.jinronga.common.R;
import cn.jinronga.service.UmsMemberService;
import cn.jinronga.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @ClassName UmsMemberServiceImpl
 * @Author 郭金荣
 * @Date 2021/4/20 20:29
 * @Description UmsMemberServiceImpl
 * @Version 1.0
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {


    @Value("${myRedis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${myRedis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Autowired
    private RedisCache redisCache;

    @Override
    public R generateAuthCode(String telephone) {

        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        //生成6位数验证码
        for (int i = 0; i < 6; i++) {
            sb.append(rand.nextInt(10));
        }
        //生成的验证放进redis，命名key号码结尾
        redisCache.setCacheObject(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        //设置验证码的过期时间
        redisCache.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return R.ok().data("获取验证码成功！", sb.toString());
    }

    @Override
    public R verifyAuthCode(String telephone, String authCode) {
        if (StringUtils.isEmpty(authCode)) {
            return R.error().data("请输入验证码！", null);
        }
        Object redisAuthCode = redisCache.getCacheObject(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        String moDieCode = String.valueOf(redisAuthCode);
        if (authCode.equals(moDieCode)) {
            return R.ok().data("验证码校验成功！", 200);
        } else {
            return R.error().message("验证码校验失败了！");
        }
    }
}
