package com.english.pay;

import android.content.Context;

import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PayManager {
    //App ID
    public static final String APP_ID = "469378b902a681c98f5a095ddccf8223";
    public static void pay(Context context){
        PayConnect.getInstance(context).pay(context, "1", "1", (float) 1.0,"haha","desc", "", new MyPayResultListener());
    }

    private static class MyPayResultListener implements PayResultListener {

        @Override
        public void onPayFinish(Context context, String s, int i, String s1, int i1, float v, String s2) {

        }
    }
}
