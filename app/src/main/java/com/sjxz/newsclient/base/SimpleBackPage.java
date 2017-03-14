package com.sjxz.newsclient.base;


import com.sjxz.newsclient.R;
import com.sjxz.newsclient.fragment.NewsFragment;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/15.
 * Role:
 */
public enum SimpleBackPage {


    CITY(1, R.string.news, NewsFragment.class),
    ;//选择城市


    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }
}
