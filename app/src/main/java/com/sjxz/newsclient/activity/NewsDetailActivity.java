package com.sjxz.newsclient.activity;

import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rey.material.widget.ProgressView;
import com.sjxz.newsclient.BaseApplication;
import com.sjxz.newsclient.R;
import com.sjxz.newsclient.base.BaseActivity;
import com.sjxz.newsclient.helper.EventCenter;
import com.sjxz.newsclient.util.ToastUtils;
import com.sjxz.newsclient.view.MyBitmapImageViewTarget;

import butterknife.Bind;

public class NewsDetailActivity  extends BaseActivity {

    /**
     * 需要点击列表传递过来的新闻详情链接
     */
    public static final String ARG_NEWS_URL = "arg_news_url";
    /**
     * 需要传递过来的新闻图片
     */
    public static final String ARG_NEWS_PIC = "arg_news_pic";
    /**
     * 需要传递过来的新闻标题
     */
    public static final String ARG_NEWS_TITLE = "arg_news_title";

    private String mUrl = "";
    private String mPic = "";
    private String mTitle = "";


    @Bind(R.id.progress)
    ProgressView mProgressView;
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.iv_detail)
    ImageView mImageView;

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionType() {
        return TransitionMode.RIGHT;
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
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initViewsAndEvents() {

        if(getIntent().getExtras() != null){
            mUrl = getIntent().getStringExtra(ARG_NEWS_URL);
            mPic = getIntent().getStringExtra(ARG_NEWS_PIC);
            mTitle = getIntent().getStringExtra(ARG_NEWS_TITLE);
        }else {
            finish();
            ToastUtils.showMessage(this,"参数有误");
        }

        if(null!=toolbar){
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        initWebView();

        mCollapsingToolbarLayout.setTitle(mTitle);
        Glide.with(BaseApplication.getApplication()).load(mPic).asBitmap().
                diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(mImageView));
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式(true);
        ws.setAppCacheEnabled(true);
        ws.setSupportZoom(false);
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setJavaScriptCanOpenWindowsAutomatically(true);//js支持
        ws.setDomStorageEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(null!=mProgressView) {
                    mProgressView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(null!=mProgressView){
                    mProgressView.setVisibility(View.GONE);
                }

            }

        });
    }

}
