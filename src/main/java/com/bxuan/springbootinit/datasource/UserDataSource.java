package com.bxuan.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bxuan.springbootinit.model.dto.user.UserQueryRequest;
import com.bxuan.springbootinit.model.vo.UserVO;
import com.bxuan.springbootinit.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 23:54
 * @description 用户数据源
 */
@Service
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long current, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(current);
        userQueryRequest.setPageSize(pageSize);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest, null);
        return userVOPage;
    }
}
