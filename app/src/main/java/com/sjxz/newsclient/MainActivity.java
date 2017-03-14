package com.sjxz.newsclient;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.sjxz.newsclient.adapter.NewsListAdapter;
import com.sjxz.newsclient.base.BaseActivity;
import com.sjxz.newsclient.bean.news.JsonRootBean;
import com.sjxz.newsclient.bean.news.T1348647909107;
import com.sjxz.newsclient.helper.EventCenter;
import com.sjxz.newsclient.mvp.presenter.impl.NewsListPresenterImpl;
import com.sjxz.newsclient.mvp.view.NewsListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NewsListView {



    @Bind(R.id.xrefreshview)
    XRefreshView xrefreshview;

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;



    NewsListAdapter newsListAdapter;


    private NewsListPresenterImpl newsListPresenter;


    List<String> mPicList;

    boolean isFirstPic=true;


    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionType() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        newsListPresenter=new NewsListPresenterImpl();
        newsListPresenter.attach(this);
        newsListPresenter.loadNewsData(0);


        initData();
    }

    private void initData() {
        initialRecyclerViewLinearLayout(recycler_view);

        initialXRecyclerView(xrefreshview);

        if (null == newsListAdapter) {
            newsListAdapter = new NewsListAdapter(this);
        }

        recycler_view.setAdapter(newsListAdapter);

        newsListAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this) {
            @Override
            public void loadMoreAgain() {
                //就是上拉刷新,用于网络错误之后点击重新加载
                xrefreshview.notifyLoadMore();//正在加载布局显示
                page+=20;
                isLoadMore = true;
                newsListPresenter.loadNewsData(page);
            }
        });

        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                isLoadMore = false;
                newsListPresenter.loadNewsData(page);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                page+=20;
                isLoadMore = true;
                newsListPresenter.loadNewsData(page);
            }
        });


    }

    @Override
    protected View getLoadingTargetView() {
        return xrefreshview;
    }

    @Override
    public void getNewsListData(JsonRootBean list) {

    }

    @Override
    public void onSuccess(JsonRootBean date) {

        finishHttpCommon(xrefreshview);
        List<T1348647909107> data=new ArrayList<>();
        data.addAll(date.getT1348647909107());
        if (null == newsListAdapter) {
            newsListAdapter = new NewsListAdapter(this);
        }

        if (data.size() > 0) {
            if (isLoadMore) {
                newsListAdapter.getDatas().addAll(data);
            } else {
//                List<NewsSummary> dataList=new ArrayList<>();
//                NewsSummary newsBody=new NewsSummary();
//                dataList.add(newsBody);
//                dataList.addAll(data);
                newsListAdapter.setDatas(data);
            }

            if(isFirstPic){
                isFirstPic=!isFirstPic;
                initPicCarousel(data);
            }

            newsListAdapter.notifyDataSetChanged();
            xrefreshview.setPullLoadEnable(true);
            xrefreshview.setPullRefreshEnable(true);
        } else {
            xrefreshview.setPullLoadEnable(false);
            if (newsListAdapter.getDatas().size() > 0) {
                xrefreshview.setPullRefreshEnable(true);
            } else {
                xrefreshview.setPullRefreshEnable(false);
            }
        }
    }

    @Override
    public void onError() {
        finishHttpCommon(xrefreshview);
        if(newsListAdapter!=null&&newsListAdapter.getDatas().size()>0){
            Toast.makeText(this,"数据加载失败",Toast.LENGTH_SHORT).show();
        }else{
            showError("加载失败");
        }

    }



    /**
     * 轮播图显示
     */
    private List<String> initPicCarousel(List<T1348647909107> dataList) {

        mPicList=new ArrayList<>();

        for(T1348647909107 body :dataList){
            if(null!=body.getImgsrc()){
                mPicList.add(body.getImgsrc());
            }
            if(mPicList.size()>6){
                break;
            }
        }

        if(mPicList.size()==0){
            isFirstPic=!isFirstPic;
        }else{
            newsListAdapter.setNewADpagerList(mPicList);
        }

        return mPicList;
    }


}
