package cn.jinronga.service;

import cn.jinronga.pojo.Contert;
import cn.jinronga.utils.HtmlParseUtil;
import com.alibaba.fastjson.JSON;
import org.apache.lucene.index.Term;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/6/14 0014
 * Time: 17:33
 * E-mail:1460595002@qq.com
 * 类说明:
 */

//业务编写
@Service
public class ContertService {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 如果需要查询中文
     * 手动用PostMan建立了一下中文分词ES索引，http://127.0.0.1:9200/jd_goods   PUT
     * {
     * "settings":{
     * "number_of_shards":"5",
     * "number_of_replicas":"1"
     * },
     * "mappings":{
     * "properties":{
     * "img":{
     * "type":"text",
     * "analyzer": "ik_max_word"
     * },
     * "title":{
     * "type":"text",
     * "analyzer": "ik_max_word"
     * },
     * "price":{
     * "type":"text",
     * "analyzer": "ik_max_word"
     * },
     * "shop":{
     * "type":"text",
     * "analyzer": "ik_max_word"
     * }
     * <p>
     * }
     * <p>
     * }
     * }
     */

    //批量插入数据
    public Boolean parseContent(String keyword) throws IOException {

        //查出来的数据放进集合中去
        List<Contert> contertList = new HtmlParseUtil().parseID(keyword);
        //创建插入数据请求
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        //移除对象字段为：""
        Contert contert = new Contert();
        contert.setImg("");

        contert.setName("");
        contert.setPrice("");
        contert.setShop("");

        List<Contert> list2 = new ArrayList<>();
        list2.add(contert);

        contertList.removeAll(list2);

//        contert.setImg("http");
        /**
         * 将查出来的数据序列化成json批量放进es索引中
         */

        for (int i = 0; i < contertList.size(); i++) {
//            System.out.println(JSON.toJSONString(contertList.get(i)));
            //批量更新和批量
            bulkRequest.add(new IndexRequest("jd_goods")
                    .source(JSON.toJSONString(contertList.get(i)), XContentType.JSON));
        }

        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);


        return !bulk.hasFailures();
    }

    //获取数据实现搜索功能
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {

        if (pageNo <= 1) {
            pageNo = 1;
        }

        SearchRequest searchRequest = new SearchRequest("jd_goods");

        //条件搜索
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(pageNo);//开始索引
        searchSourceBuilder.size(pageSize);//每页显示多少数据

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        //执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit documentFields : search.getHits().getHits()) {

            System.out.println(Arrays.asList(documentFields));
        }
        //解析结果
        ArrayList<Map<String, Object>> lists = new ArrayList<>();
        for (SearchHit documentFields : search.getHits().getHits()) {

            lists.add(documentFields.getSourceAsMap());
        }


        return lists;
    }

    //获取数据实现高亮功能
//获取数据实现高亮功能
    public List<Map<String, Object>> searchPageHighlightBuilder(String keyword, int pageNo, int pageSize) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }

        keyword = URLDecoder.decode(keyword, "UTF-8");

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        //构建DSL语句
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);
        //精准匹配
        if (!StringUtils.isEmpty(keyword)) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
            searchSourceBuilder.query(termQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        }


        //高亮
        if (!StringUtils.isEmpty(keyword)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.requireFieldMatch(true);//多个高亮显示
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        //执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit documentFields : search.getHits().getHits()) {

            //解析高亮的字段
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments) {
                    n_title += text;
                }
                sourceAsMap.put("title", n_title);
            }
            list.add(sourceAsMap);
        }
        return list;
    }

}
