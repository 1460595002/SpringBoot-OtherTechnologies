package cn.jinronga.contorller;

import cn.jinronga.common.R;
import cn.jinronga.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName UmsMemberController
 * @Author 郭金荣
 * @Date 2021/4/20 21:49
 * @Description UmsMemberController
 * @Version 1.0
 */
@Api(tags = "UmsMemberController", value = "会员登录注册管理")
@Controller
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public R getAuthCode(@RequestParam("telephone") String telephone) {
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public R updatePassword(@RequestParam("telephone") String telephone, @RequestParam("authCode") String authCode) {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }
}
