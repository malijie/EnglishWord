package com.english;

import android.app.Application;
import android.content.Context;

import com.common.lib.base.CommonBase;
import com.english.util.Logger;

/**
 * Created by vic_ma on 15/12/3.
 */
public class English extends Application {
    public static Context mContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CommonBase.init(mContext);
        Logger.d("MLJ", "mContext onCreate=" + mContext);
    }
}
