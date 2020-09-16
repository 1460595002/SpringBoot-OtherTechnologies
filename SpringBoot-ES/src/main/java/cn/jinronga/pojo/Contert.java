package cn.jinronga.pojo;

import lombok.Data;
import org.jsoup.select.Elements;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/14 0014
 * Time: 15:38
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Data
public class Contert {
    private String name;//商品名称
    private String price;//价格
    private  String img;//图片地址
    private  String shop;//店铺
}
