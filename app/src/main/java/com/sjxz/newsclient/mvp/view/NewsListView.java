package com.sjxz.newsclient.mvp.view;

import com.sjxz.newsclient.bean.news.JsonRootBean;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:1.获取新闻客户新闻信息
 */
public interface NewsListView {

    void getNewsListData(JsonRootBean list);

    void onSuccess(JsonRootBean showApiNews);

    void onError();
}
