package com.english.ad;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.english.util.Logger;

import cn.waps.AppConnect;

//import cn.waps.AppConnect;

public class AdUtil {
 
	public static final String APP_ID = "b04f2b0f3f9fcd5c5acece6ac1f0ce9f";
	public static String APP_PID = "default";
	private static AppConnect mAppConnect = null;
	
	public static void init(Context context){
		mAppConnect = AppConnect.getInstance(context);
		mAppConnect.initUninstallAd(context);
		mAppConnect.setCrashReport(true);
	}
		public static void showFeedback(Context context){
			if(mAppConnect != null){
				mAppConnect.showFeedback(context);
			}else{
                Toast.makeText(context,"暂时无法连接服务器，请稍后再试",Toast.LENGTH_SHORT).show();
            }
		}

	public static void checkUpdate(Context context){
			if(mAppConnect != null){
				mAppConnect.checkUpdate(context);
			}else{
                Toast.makeText(context,"暂时无法连接服务器，请稍后再试",Toast.LENGTH_SHORT).show();
            }
	}

}
