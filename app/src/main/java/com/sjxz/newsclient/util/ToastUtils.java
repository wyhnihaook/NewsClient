package com.sjxz.newsclient.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/14.
 * Role:
 */
public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object synObj = new Object();
    private static Toast toastText;

    public ToastUtils() {
    }

    public static void showMessageCenter(Context context, String msg){
        showMsgLocation(context, msg, 0, Gravity.CENTER, 0, 0);
    }

    public static void showMessage(Context context, String msg) {
        showMsgLocation(context, msg, 0, 80, 0, 80);
    }

    public static void showMessage(Context context, int gravity, String msg, int duration) {
        showMsgLocation(context, msg, duration, gravity, 0, 0);
    }

    public static void showMessage(Context context, int msg) {
        showMsgLocation(context, context.getResources().getText(msg), 0, 80, 0, 0);
    }

    public static void cacleToast() {
        if(toast != null) {
            toast.cancel();
        }

        toast = null;
    }

    public static void showMessage(final Context context, final String msg, final int len) {
        (new Thread(new Runnable() {
            public void run() {
                ToastUtils.handler.post(new Runnable() {
                    public void run() {
                        synchronized(ToastUtils.synObj) {
                            if(ToastUtils.toast != null) {
                                ToastUtils.toast.cancel();
                                ToastUtils.toast.setText(msg);
                                ToastUtils.toast.setDuration(len);
                            } else {
                                ToastUtils.toast = Toast.makeText(context, msg, len);
                            }

                            ToastUtils.toast.show();
                        }
                    }
                });
            }
        })).start();
    }

    public static void showMessage(final Context context, final int msg, final int len) {
        (new Thread(new Runnable() {
            public void run() {
                ToastUtils.handler.post(new Runnable() {
                    public void run() {
                        synchronized(ToastUtils.synObj) {
                            if(ToastUtils.toast != null) {
                                ToastUtils.toast.cancel();
                                ToastUtils.toast.setText(msg);
                                ToastUtils.toast.setDuration(len);
                            } else {
                                ToastUtils.toast = Toast.makeText(context, msg, len);
                            }

                            ToastUtils.toast.show();
                        }
                    }
                });
            }
        })).start();
    }

    public static void showMsgLocation(Context context, CharSequence msg, int length_short, int gravity, int xOffset, int yOffset) {
        if(toast == null) {
            toast = Toast.makeText(context, msg, length_short);
            toast.setGravity(gravity, xOffset, yOffset);
        } else {
            toast.setText(msg);
        }

        toast.show();
    }

    public static void showMsg(Context context, String msg) {
        showMsgLocation(context, msg, 0, 80, 0, 0);
    }

    public static void showMsg(Context context, String msg, int xOffset, int yOffset) {
        showMsgLocation(context, msg, 0, 80, xOffset, yOffset);
    }

    public static void showToast(Context context, String msg, int duration, TextView textView) {
        if(toastText == null) {
            toastText = Toast.makeText(context, msg, duration);
            textView.setText(msg);
            toastText.setGravity(17, 0, 0);
            toastText.setView(textView);
        } else {
            toastText.setDuration(duration);
            textView.setText(msg);
        }

        toastText.show();
    }




}
