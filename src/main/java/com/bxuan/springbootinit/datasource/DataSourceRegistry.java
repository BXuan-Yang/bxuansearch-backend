package com.bxuan.springbootinit.datasource;

import com.bxuan.springbootinit.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: BXuan
 * @Date: 2023/03/30/ 0:28
 * @description 数据源注册类
 */
@Component
public class DataSourceRegistry {

    @Resource
    private PostDataSource postDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;

    Map<String, DataSource> typeDataSourceMap;

    @PostConstruct
    public void doInit(){
        typeDataSourceMap = new HashMap<String, DataSource>() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
        }};
    }

    public DataSource getDataSourceByType(String type){
        return typeDataSourceMap.get(type);
    }
}
