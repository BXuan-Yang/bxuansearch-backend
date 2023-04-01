package com.bxuan.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 23:46
 * @description 数据源接口
 */
public interface DataSource <T> {

    Page<T> doSearch(String searchText, long current, long pageSize);

}
