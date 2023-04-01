package com.bxuan.springbootinit;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bxuan.springbootinit.model.entity.Picture;
import com.bxuan.springbootinit.model.entity.Post;
import com.bxuan.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: BXuan
 * @Date: 2023/03/28/ 15:56
 * @description 抓取帖子测试类
 */
@SpringBootTest
public class FetchArticleTest {

    @Autowired
    private PostService postService;

    public void fetchPicture() throws IOException {
        String first = "1";
        String url = String.format("https://www.bing.com/images/search?q=小黑子&first=%s", first);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            Picture picture = new Picture();
            // 取图片地址 murl
            String pictureUrl = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(pictureUrl, Map.class);
            String pictureUrlRes = (String) map.get("murl");
            // 取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            picture.setTitle(title);
            picture.setUrl(pictureUrlRes);
            pictureList.add(picture);
        }
    }

    @Test
    public void fetchArticle(){
        // 1、获取数据源
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String body = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
        System.out.println(body);
        // 2、解析数据源 = json转对象
        Map<String, Object> map = JSONUtil.toBean(body, Map.class);
        System.out.println(map);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object o : records) {
            Post post = new Post();
            JSONObject object = (JSONObject) o;
            post.setTitle(object.getStr("title"));
            post.setContent(object.getStr("content"));
            JSONArray tags = (JSONArray) object.get("tags");
            List<String> list = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(list));
            // 设置管理员id
            post.setUserId(1640573242712092673L);
            postList.add(post);
        }
        // 3、存储数据库
        postService.saveBatch(postList);
    }
}
