package com.bxuan.springbootinit.model.entity;

import lombok.Data;

/**
 * @Author: BXuan
 * @Date: 2023/03/28/ 17:22
 * @description 图片对象
 */
@Data
public class Picture {
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片 url
     */
    private String url;
}
