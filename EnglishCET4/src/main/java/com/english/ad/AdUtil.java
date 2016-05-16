package com.english.ad;

import android.content.Context;
import android.widget.LinearLayout;

import com.english.util.Logger;

import cn.waps.AppConnect;

//import cn.waps.AppConnect;

public class AdUtil {
 
	public static final String APP_ID = "b04f2b0f3f9fcd5c5acece6ac1f0ce9f";
	public static String APP_PID = "default";
	
	public static void init(Context context){
    		AppConnect.getInstance(APP_ID, APP_PID, context);
//    		AppConnect.getInstance(context);
        	AppConnect.getInstance(context).initUninstallAd(context);
        	AppConnect.getInstance(context).setCrashReport(true);
	}
//
//	public static void showMiniAd(Context context, LinearLayout adLayout, int time){
//			AppConnect.getInstance(context).initAdInfo();
//			AppConnect.getInstance(context).showMiniAd(context, adLayout, time);
//	}
//
//
//	public static void showOffersAd(Context context){
//			AppConnect.getInstance(context).showOffers(context);
//	}
//
//	public static void closeAd(Context context){
//			AppConnect.getInstance(context).close();
//	}
//
		public static void showFeedback(Context context){
			AppConnect.getInstance(context).showFeedback(context);
		}

	public static void checkUpdate(Context context){
		Logger.d("MLJ","AppConnect=" + AppConnect.getInstance(context) + ",context=" + context);
			AppConnect.getInstance(context).checkUpdate(context);
	}
//
//	public static void showPopAd(Context context){
//			AppConnect.getInstance(context).showPopAd(context);
//	}

}
