package com.bxuan.springbootinit.model.vo;

import com.bxuan.springbootinit.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 20:43
 * @description 聚合搜索
 */
@Data
public class SearchVO implements Serializable {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<Object> dataList;

    private static final long serialVersionUID = 1L;

}
