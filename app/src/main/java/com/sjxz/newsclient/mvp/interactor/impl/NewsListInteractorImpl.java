package com.sjxz.newsclient.mvp.interactor.impl;

import com.sjxz.newsclient.bean.news.JsonRootBean;
import com.sjxz.newsclient.mvp.interactor.NewsListInteractor;
import com.sjxz.newsclient.rx.api.ApiInterface;
import com.sjxz.newsclient.rx.api.NewsListService;
import com.sjxz.newsclient.rx.net.NetManager;

import rx.Observable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:
 */
public class NewsListInteractorImpl implements NewsListInteractor {
    @Override
    public Observable<JsonRootBean> getNewsListData(int page) {
        NewsListService newsService= NetManager.getInstance().create(NewsListService.class);
        return newsService.getNewsList(ApiInterface.CACHE_CONTROL_AGE,ApiInterface.HEADLINE_TYPE,ApiInterface.HEADLINE_ID,page);
    }
}
