package com.bxuan.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bxuan.springbootinit.common.BaseResponse;
import com.bxuan.springbootinit.common.ErrorCode;
import com.bxuan.springbootinit.common.ResultUtils;
import com.bxuan.springbootinit.exception.ThrowUtils;
import com.bxuan.springbootinit.model.dto.picture.PictureQueryRequest;
import com.bxuan.springbootinit.model.entity.Picture;
import com.bxuan.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                             HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPictures(searchText, current, size);
        return ResultUtils.success(picturePage);

    }
}
