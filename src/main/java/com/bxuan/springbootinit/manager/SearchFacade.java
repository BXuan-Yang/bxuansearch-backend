package com.bxuan.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bxuan.springbootinit.common.ErrorCode;
import com.bxuan.springbootinit.datasource.*;
import com.bxuan.springbootinit.exception.ThrowUtils;
import com.bxuan.springbootinit.model.dto.search.SearchRequest;
import com.bxuan.springbootinit.model.entity.Picture;
import com.bxuan.springbootinit.model.enums.SearchTypeEnum;
import com.bxuan.springbootinit.model.vo.PostVO;
import com.bxuan.springbootinit.model.vo.SearchVO;
import com.bxuan.springbootinit.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 23:59
 * @description 服务适配器
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostDataSource postDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        String type = searchRequest.getType();
        SearchTypeEnum value = SearchTypeEnum.getEnumByValue(type);

        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);

        if (value == null) {
            // 文章
            Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
            // 图片
            Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
            // 用户
            Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);

            SearchVO searchVO = new SearchVO();
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            searchVO.setUserList(userVOPage.getRecords());

            return searchVO;
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource source = dataSourceRegistry.getDataSourceByType(type);
            Page page = source.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}
