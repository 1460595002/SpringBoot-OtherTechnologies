package cn.jinronga.shiro.mapper;

import cn.jinronga.shiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2019/12/31 0031
 * Time: 11:17
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Repository
@Mapper
public interface UserMapper {

    public User queyUserByName(String name);
}
