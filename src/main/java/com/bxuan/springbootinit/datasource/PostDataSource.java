package com.bxuan.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bxuan.springbootinit.model.dto.post.PostQueryRequest;
import com.bxuan.springbootinit.model.entity.Post;
import com.bxuan.springbootinit.model.vo.PostVO;
import com.bxuan.springbootinit.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 23:47
 * @description 文章数据源
 */
@Service
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;
    @Resource
    private HttpServletRequest request;

    @Override
    public Page<PostVO> doSearch(String searchText, long current, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        // 调用使用 ES 接口
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        // 将 Post 转换为 PostVO
        Page<PostVO> postVOPage = postService.getPostVOPage(postPage, request);
        return postVOPage;
    }
}
