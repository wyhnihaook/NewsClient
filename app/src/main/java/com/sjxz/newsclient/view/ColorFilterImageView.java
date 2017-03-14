package com.sjxz.newsclient.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/13.
 * Role:
 */
public class ColorFilterImageView extends ImageView implements View.OnTouchListener {
    public ColorFilterImageView(Context context) {
        this(context, null, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  // 按下时图像变灰
                setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                break;
            case MotionEvent.ACTION_UP:   // 手指离开或取消操作时恢复原色
            case MotionEvent.ACTION_CANCEL:
                setColorFilter(Color.TRANSPARENT);
                break;
            default:
                break;
        }
        return false;
    }
}