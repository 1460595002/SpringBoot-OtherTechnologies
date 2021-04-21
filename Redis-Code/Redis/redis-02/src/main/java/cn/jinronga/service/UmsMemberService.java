package cn.jinronga.service;

import cn.jinronga.common.R;

/**
 * @ClassName UmsMemberService
 * @Author 郭金荣
 * @Date 2021/4/20 20:22
 * @Description UmsMemberService
 * @Version 1.0
 */
public interface UmsMemberService {
    /**
     * 通过手机获取验证码，
     * 生成验证码时，将自定义的Redis键值加上手机号
     * 生成一个Redis的密钥，以验证码为值存入到Redis中
     * 并设置时间为自己配置的时间（此处为120s）
     *
     * @param telephone
     * @return
     */
    R generateAuthCode(String telephone);

    /**
     *通过手机和验证码验证
     *  验证码时根据手机号码来获取Redis内部存储的验证码
     * 并与初始化的验证码进行比对。
     * @param telephone 手机号
     * @param authCode  验证码
     * @return
     */
    R verifyAuthCode(String telephone,String authCode);

}
