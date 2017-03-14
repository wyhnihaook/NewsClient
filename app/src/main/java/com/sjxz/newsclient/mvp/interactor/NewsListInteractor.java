package com.sjxz.newsclient.mvp.interactor;

import com.sjxz.newsclient.bean.news.JsonRootBean;

import rx.Observable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:数据请求
 */
public interface NewsListInteractor {

    Observable<JsonRootBean> getNewsListData(int page);
}
