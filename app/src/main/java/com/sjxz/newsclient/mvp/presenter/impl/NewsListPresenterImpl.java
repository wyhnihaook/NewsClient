package com.sjxz.newsclient.mvp.presenter.impl;

import com.andview.refreshview.utils.LogUtils;
import com.sjxz.newsclient.bean.news.JsonRootBean;
import com.sjxz.newsclient.mvp.interactor.impl.NewsListInteractorImpl;
import com.sjxz.newsclient.mvp.presenter.BasePresenter;
import com.sjxz.newsclient.mvp.view.NewsListView;
import com.sjxz.newsclient.rx.RxManager;
import com.sjxz.newsclient.rx.RxSubscriber;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:
 */
public class NewsListPresenterImpl extends BasePresenter<NewsListView> {

    private NewsListInteractorImpl newsListInteractor;


    public NewsListPresenterImpl() {
        newsListInteractor=new NewsListInteractorImpl();
    }

    public void loadNewsData(int page) {
        mSubscription= RxManager.getInstance().doSubscribe(newsListInteractor.getNewsListData(page), new RxSubscriber<JsonRootBean>(true) {
            @Override
            protected void _onNext(JsonRootBean showApiNews) {
                LogUtils.i("走这里_onNext");
                if(showApiNews!=null){
                    mView.onSuccess(showApiNews);
                }

            }

            @Override
            protected void _onError() {
                LogUtils.i("走这里_onError");
                mView.onError();
            }
        });
    }


}
