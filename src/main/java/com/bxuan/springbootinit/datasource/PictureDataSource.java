package com.bxuan.springbootinit.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bxuan.springbootinit.common.ErrorCode;
import com.bxuan.springbootinit.exception.BusinessException;
import com.bxuan.springbootinit.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: BXuan
 * @Date: 2023/03/29/ 23:57
 * @description 图片数据源
 */
@Service
public class PictureDataSource implements DataSource<Picture> {
    @Override
    public Page<Picture> doSearch(String searchText, long current, long pageSize) {
        long first = (current - 1) * pageSize;
        String url = String.format("https://www.bing.com/images/search?q=%s&first=%s", searchText, first);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            Picture picture = new Picture();
            // 取图片地址 murl
            String pictureUrl = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(pictureUrl, Map.class);
            String pictureUrlRes = (String) map.get("murl");
            // 取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            picture.setTitle(title);
            picture.setUrl(pictureUrlRes);
            pictureList.add(picture);
            if (pictureList.size() >= pageSize){
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(current, pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
