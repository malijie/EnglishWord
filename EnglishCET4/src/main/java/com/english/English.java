package com.english;

import android.app.Application;
import android.content.Context;

import com.english.ad.AdUtil;
import com.english.pay.PayManager;
import com.english.util.Logger;
import com.wanpu.pay.PayConnect;

/**
 * Created by vic_ma on 15/12/3.
 */
public class English extends Application {
    public static Context mContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Logger.d("MLJ", "mContext onCreate=" + mContext);
//        AdUtil.init(mContext);
        PayManager.getInstance(mContext);
    }
}
