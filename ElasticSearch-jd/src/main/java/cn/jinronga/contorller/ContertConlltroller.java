package cn.jinronga.contorller;

import cn.jinronga.service.ContertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/14 0014
 * Time: 17:33
 * E-mail:1460595002@qq.com
 * 类说明:
 */
//请求编写
@RestController
public class ContertConlltroller {

    @Autowired
    private ContertService contertService;

    //添加数据到es
    @RequestMapping("parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword) throws IOException {

      return contertService.parseContent(keyword);

    }
    //获取数据实现搜索功能
    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> search(@PathVariable ("keyword")String keyword,
                                           @PathVariable ("pageNo")int pageNo,
                                           @PathVariable("pageSize") int pageSize) throws Exception {
        List<Map<String, Object>> maps = contertService.searchPage(keyword, pageNo, pageSize);

        //插入数据
        contertService.parseContent(keyword);
//        maps.stream().collect(Collectors.toList()).forEach(System.out::println);

//        return contertService.searchPage(keyword,pageNo,pageSize);
        //实现高亮搜索
        return contertService.searchPageHighlightBuilder(keyword,pageNo,pageSize);
    }


}
