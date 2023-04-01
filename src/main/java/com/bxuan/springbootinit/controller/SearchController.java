package com.bxuan.springbootinit.controller;

import com.bxuan.springbootinit.common.BaseResponse;
import com.bxuan.springbootinit.common.ResultUtils;
import com.bxuan.springbootinit.manager.SearchFacade;
import com.bxuan.springbootinit.model.dto.search.SearchRequest;
import com.bxuan.springbootinit.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 20:37
 * @description 搜索接口
 */
@RequestMapping("/search")
@RestController
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}
