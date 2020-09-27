package cn.jinronga;

import cn.jinronga.pojo.CategoryEntity;
import cn.jinronga.pojo.User;
import cn.jinronga.service.CategoryService;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@MapperScan("cn.jinronga.dao")
@SpringBootTest
class SpringbootEsApplicationTests {

    //利用面向对象来操作
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;

    @Autowired
    CategoryService categoryService;



    //索引的创建  Request PUT jinronga
    @Test
    void contextLoads() throws IOException {
        //1、创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest("jinronga_index");
        //2、客户端执行的请求 IndecesClient，请求后获得响应
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }
    //判断索引是否存在
    @Test
    void testExistIndex() throws IOException {
        //创建获取索引请求
        GetIndexRequest request = new GetIndexRequest("jinronga_index");

        //客户端执行请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    //删除索引
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request=new DeleteIndexRequest("jinronga_index");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());//判断是否删除成功！
    }

    //添加文档
    @Test
    void testAddDocument() throws IOException {
       //创建对象
        User user = new User("金融啊", 20);

        //创建请求
        IndexRequest request = new IndexRequest("jinronga_index");

        //规则put jinronga_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        //将我的数据放入请求以json的方式
        request.source(JSON.toJSONString(user), XContentType.JSON);

        //客户端发送请求，响应结果
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());//对应我们命令返回状态CREATED

    }
    @Test
    void listTree() throws IOException {

        List<CategoryEntity> categoryEntities = categoryService.listTree();
        //创建请求

        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        //批处理请求
        for (int i=0;i<categoryEntities.size();i++){
            request.add(new IndexRequest("gulimall_product").id(""+(i+1))
                    .source(JSON.toJSONString(categoryEntities.get(i)),XContentType.JSON));

        }
        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());//是否成功失败
      /*    if (categoryEntities!=null){
              System.out.print("==="+categoryEntities);
          }
*/
    }
    //获取文档 判断文档是否存在 get /jinronga_index/doc/1
    @Test
    void testIsExists() throws IOException {
        GetRequest request = new GetRequest("jinronga_index", "1");
        //不获取赶回的_source的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档内容：
    @Test
    void testGetDocument() throws IOException {

        GetRequest request = new GetRequest("jinronga_index", "1");
        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);
        System.out.println("文档内容："+documentFields.getSourceAsString());//打印文档内容
        System.out.println(documentFields);//返回的全部内容和命令方式一样的

    }

    //更新文档的信息
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest=new UpdateRequest("jinronga_index","1");

        updateRequest.timeout("1s");
        User user = new User("金融啊一号", 20);
        updateRequest.doc(JSON.toJSONString(user),XContentType.JSON);

        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());//查看转态
    }

    //删除文档
    @Test
    void testDeleteRequest() throws IOException {
        DeleteRequest request = new DeleteRequest("jinronga_index", "1");
        request.timeout("1s");

        DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());//查看状态
    }

    //批量插入数据
    @Test
    void testBulkRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("10s");

        ArrayList<User> userList =new ArrayList<>();
       userList.add(new User("金融啊1号",20));
       userList.add(new User("金融啊2号",20));
       userList.add(new User("金融啊3号",20));
       userList.add(new User("金融啊4号",20));
       userList.add(new User("金融啊5号",20));
       userList.add(new User("金融啊5号",20));

       //批处理请求
        for (int i=0;i<userList.size();i++){
            request.add(new IndexRequest("jinronga_index").id(""+(i+1))
                    .source(JSON.toJSONString(userList.get(i)),XContentType.JSON));

        }


        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());//是否成功失败

    }
}
