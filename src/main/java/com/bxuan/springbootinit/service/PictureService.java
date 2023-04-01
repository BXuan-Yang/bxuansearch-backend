package com.bxuan.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bxuan.springbootinit.model.entity.Picture;

public interface PictureService {

    Page<Picture> searchPictures(String searchText, long pageNum, long pageSize);

}
