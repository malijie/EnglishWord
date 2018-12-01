package com.english;

import android.app.Application;
import android.content.Context;

import com.english.util.Logger;

/**
 * Created by vic_ma on 15/12/3.
 */
public class English2 extends Application {
    public static Context mContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Logger.d("MLJ", "mContext onCreate=" + mContext);
    }
}
