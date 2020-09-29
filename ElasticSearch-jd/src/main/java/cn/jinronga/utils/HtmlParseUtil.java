package cn.jinronga.utils;

import cn.jinronga.pojo.Contert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/14 0014
 * Time: 14:46
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Component
public class HtmlParseUtil {

    public static void main(String[] args) throws IOException {

           new HtmlParseUtil().parseID("vue").forEach(System.out::println);

    }

    public List<Contert> parseID(String keywords) throws IOException {
        //获取请求 https://search.jd.com/Search?keyword=???
        //需要联网：
        //如果中文搜索不了就用：
        //如果中文搜索不了就用
//        String url = "https://search.jd.com/Search?keyword=" + keyWords +"&enc=utf-8";
        String url="https://search.jd.com/Search?keyword="+keywords;
        //解析网页(Jsoup返回Document就是document对象)
        Document parse = Jsoup.parse(new URL(url), 30000);
        //所有js的方法这里都能使用
        //获取页面中标签id为：shortcut-2014页面信息
        Element elementById = parse.getElementById("shortcut-2014");
        //打印标签的id为shortcut-2014内容
//        System.out.println(elementById.html());

        Elements li = parse.getElementsByTag("li");
        //获取页面里的信息

        ArrayList<Contert> products=new ArrayList();


        for (Element en: li) {

            String img = en.getElementsByTag("img").eq(0).attr("src");
        //  String price = en.getElementsByClass("p-price").eq(0).text();//bug 同时会取出两个价格 正常价格和会员价格
            String price = en.select("div.p-price> strong").eq(0).text();
            String name = en.getElementsByClass("p-name").eq(0).text();
            String shop = en.getElementsByClass("p-shop").eq(0).text();

            Contert contert=new Contert();
            //因为有些标签店铺的class不为p-shop
            if (shop.isEmpty()){
                shop=en.getElementsByClass("p-shopnum").eq(0).text();
            }
            contert.setImg(img);
            contert.setName(name);
            contert.setPrice(price);
            contert.setShop(shop);
           products.add(contert);
        }
       return products;
    }
}
