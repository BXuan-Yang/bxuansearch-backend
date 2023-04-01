package com.bxuan.springbootinit.model.dto.search;

import com.bxuan.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 20:40
 * @description 搜索类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;
    /**
     * 类型
     */
    private String type;

    private static final long serialVersionUID = 1L;
}
