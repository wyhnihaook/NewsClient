package com.sjxz.newsclient.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sjxz.newsclient.BaseApplication;
import com.sjxz.newsclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:
 */
public class ADPager extends HorizontalScrollView implements View.OnClickListener{

    private final int VELOCITY_SLOT = 1000;
    private final int DEFAULT_AUTOPLAY_DURATION = 5000;
    private List<String> noticeList;
    private LinearLayout container;
    private LinearLayout.LayoutParams linearLayoutParams;
    //    private DisplayImageOptions imageOptions;
    private VelocityTracker velocityTracker;
    private OnADPageClickListener mADPageClickListener;
    private int mCurrPage = 0;

    private long mDuration = DEFAULT_AUTOPLAY_DURATION;
    private boolean mIsAutoPlaying = false;
    private AutoPlayRunnable mAutoPlayRunnable = new AutoPlayRunnable();

    private int mMaximumVelocity;

    private float mCircleRadius;

    private Paint mStrokePaint;
    private Paint mFillPaint;

    public ADPager(Context context) {
        super(context);
        init();
    }

    public ADPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ADPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Context ctx = getContext();
        this.container = new LinearLayout(ctx);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.container.setOrientation(LinearLayout.HORIZONTAL);
        this.container.setLayoutParams(params);
        this.addView(this.container);
        this.linearLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        this.linearLayoutParams.weight = 1; // 平等分
        this.noticeList = new ArrayList<>();
        this.setHorizontalScrollBarEnabled(false);
//        this.imageLoader = ImageLoader.getInstance();


        this.setSmoothScrollingEnabled(true);

        final Resources res = getResources();
        this.mCircleRadius = 8;

        /** 默认图 **/
        ImageView imgView = makeImageView();
        LinearLayout linearLayout=makeLinearLayout(imgView);
        this.container.addView(linearLayout);
        /** 默认图结束 **/

        /**
         * 设置松手时velocity的追踪
         */
        final ViewConfiguration configuration = ViewConfiguration.get(ctx);
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

//        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
//        builder.cacheInMemory(true).cacheOnDisc(true)
//                .showImageForEmptyUri(R.mipmap.def_pic)
//                .showImageOnLoading(R.mipmap.def_pic)
//                .showImageOnFail(R.mipmap.def_pic);
//        imageOptions = builder.build();

        initPaint();
    }

    private void initPaint() {
        mStrokePaint = new Paint();
        mStrokePaint.setStrokeWidth(1.0f);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setAntiAlias(true);

        mFillPaint = new Paint();
        mFillPaint.setColor(Color.WHITE);
        mFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFillPaint.setAntiAlias(true);
    }

    private ImageView makeImageView() {
        Context ctx = getContext();
        ImageView imgView = new ImageView(ctx);
        imgView.setLayoutParams(linearLayoutParams);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);


        return imgView;
    }

    private LinearLayout makeLinearLayout(ImageView imgView){
        Context ctx = getContext();
        LinearLayout linearLayout=new LinearLayout(ctx);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.addView(imgView);

        return linearLayout;
    }

    /**
     * 设置 image的url
     *
     * @param noticeList
     */
    public void setImageUrl(List<String>  noticeList) {
        this.noticeList = noticeList;
        int size = noticeList.size();
        this.container.removeAllViews();
        ImageView imgView;
        LinearLayout linearLayout;
        int position;

        if (size == 0) {
            imgView = makeImageView();
            linearLayout = makeLinearLayout(imgView);

//            imgView.setImageResource(R.mipmap.def_pic);
            this.container.addView(linearLayout);
            return;
        }
        if (size > 1) {
            imgView = makeImageView();
            linearLayout = makeLinearLayout(imgView);
            position = size - 1;
            String lastUrl = noticeList.get(position);
            linearLayout.setTag(position);
            linearLayout.setOnClickListener(this);
            /**
             * 使用Volley框架加载图片更加快捷，使缓存在一处用于初始化显示
             * */
//            imageLoader.displayImage(lastUrl, imgView, imageOptions);
//            imgView.setImageUrl(lastUrl, mImageLoader);
            Glide.with(BaseApplication.getApplication()).load(lastUrl).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.homebg).into(imgView);

            this.container.addView(linearLayout);
        }
        position = 0;
        for (String notice : noticeList) {
            imgView = makeImageView();
            linearLayout = makeLinearLayout(imgView);
            linearLayout.setTag(position);
            linearLayout.setOnClickListener(this);
//            imageLoader.displayImage(notice.getPicUrl(), imgView, imageOptions);
//            imgView.setImageUrl(notice.getPicUrl(), mImageLoader);
            Glide.with(BaseApplication.getApplication()).load(notice).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.homebg).into(imgView);
            this.container.addView(linearLayout);
            position ++;
        }
        if (size > 1) {
            String firstUrl = noticeList.get(0);
            imgView = makeImageView();
            linearLayout = makeLinearLayout(imgView);
            linearLayout.setTag(0);
            linearLayout.setOnClickListener(this);
//            imageLoader.displayImage(firstUrl, imgView, imageOptions);
//            imgView.setImageUrl(firstUrl, mImageLoader);
            Glide.with(BaseApplication.getApplication()).load(firstUrl).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.homebg).into(imgView);

            this.container.addView(linearLayout);
        }
        this.requestLayout();
        this.scrollToPage(0, false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int size = this.noticeList.size();
        int imageLength;
        if (size > 1) {
            imageLength = size + 2;
        } else {
            imageLength = 1;
        }
        int containerSize = widthSize * imageLength;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY: {
                int childWidthSpec = MeasureSpec.makeMeasureSpec(containerSize, MeasureSpec.EXACTLY);
                this.container.measure(childWidthSpec, heightMeasureSpec);
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                throw new RuntimeException("Can not be unspecified");
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (mIsAutoPlaying) {
                    removeCallbacks(mAutoPlayRunnable);
                }
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        initVelocityTrackerIfNeed();
        velocityTracker.addMovement(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                float velocityX = velocityTracker.getXVelocity();
                int scrollX = this.getScrollX();
                int width = this.container.getChildAt(0).getWidth();
                //1080---1000

                int page;
                if (Math.abs(velocityX) > VELOCITY_SLOT) {
                    page = scrollX / width;
                    if (velocityX > 0) {
                        page = page - 1;
                    }
                } else {
                    page = (int)Math.round(scrollX * 1.0 / width) - 1;
                }
                this.scrollToPage(page, true);

                recycleVelocityTracker();
                if (mIsAutoPlaying) {
                    postDelayed(mAutoPlayRunnable, mDuration);
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void initVelocityTrackerIfNeed() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.scrollToPage(0, false);
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 滚动到某个页面
     *
     * @param page 页面第n页
     * @param smooth 滑动
     */
    public void scrollToPage(int page, boolean smooth) {
        int size = this.noticeList.size();
        if (page < 0) {
            page = size - 1;
        }
        if (size > 1) {
            int width = this.container.getChildAt(0).getWidth();
            mCurrPage = page;
            if (mCurrPage == size) {
                mCurrPage = 0;
            }
            if (!smooth) {
                this.scrollTo(width * (page + 1), 0);
            } else {
                this.smoothScrollTo(width * (page + 1), 0);
            }
        } else {
            mCurrPage = size - 1;
            this.scrollTo(0, 0);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        postInvalidate();
    }

    private class AutoPlayRunnable implements Runnable {
        @Override
        public void run() {
            if(mIsAutoPlaying){
                int size = noticeList.size();
                int targetPage = mCurrPage + 1;
                if (targetPage >= size) {
                    targetPage = 0;
                }
                scrollToPage(targetPage, true);
                postDelayed(mAutoPlayRunnable, mDuration);
            }

        }
    }

    public void setAutoPlay(boolean autoPlay) {
        this.setAutoPlay(autoPlay, DEFAULT_AUTOPLAY_DURATION);
    }

    public void setAutoPlay(boolean autoPlay, long duration) {
        mIsAutoPlaying = autoPlay;
        mDuration = duration;
        removeCallbacks(mAutoPlayRunnable);
        if (autoPlay) {
            postDelayed(mAutoPlayRunnable, duration);
        }
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        int width = this.container.getChildAt(0).getWidth();
        int size = this.noticeList.size();
        if (clampedX) {
            if (scrollX > 0) {
                mCurrPage = 0;
                scrollTo(width, 0);
            } else {
                mCurrPage = size - 1;
                scrollTo(width * size, 0);
            }
        }
    }

    public void setOnPageClickListener(OnADPageClickListener l) {
        this.mADPageClickListener = l;
    }

    public OnADPageClickListener getOnPageClickListener() {
        return this.mADPageClickListener;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        if (this.mADPageClickListener != null) {
            this.mADPageClickListener.onPageClick(position);
        }
    }

    public static interface OnADPageClickListener {
        public void onPageClick(int page);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();

        float threeRadius = 3 * mCircleRadius;

        int size = this.noticeList.size();
        int circleLayoutWidth = (int)(threeRadius * size - mCircleRadius);

        int offsetX = (int)((width - circleLayoutWidth) / 2 + mCircleRadius) + this.getScrollX();   // start pos
        int offsetY = (int)(height - 15 - mCircleRadius);                       // padding Bottom 10px

        int iLoop;
        for (iLoop = 0; iLoop < size; iLoop ++) {
            canvas.drawCircle(offsetX, offsetY, mCircleRadius, mStrokePaint);

            if (iLoop == mCurrPage) {
                canvas.drawCircle(offsetX, offsetY, mCircleRadius, mFillPaint);
            }

            offsetX += threeRadius;

        }
    }
}