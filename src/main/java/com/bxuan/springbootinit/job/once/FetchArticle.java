package com.bxuan.springbootinit.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bxuan.springbootinit.model.entity.Post;
import com.bxuan.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 抓取帖子
 */
// 取消注释，每次执行 Spring 任务的时候就会进行一次任务
//@Component
@Slf4j
public class FetchArticle implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
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
        boolean b = postService.saveBatch(postList);
        if (b) {
            log.info("启动抓取数据成功，共有{}条数据", postList.size());
        }else {
            log.info("启动抓取数据失败！");
        }
    }
}
