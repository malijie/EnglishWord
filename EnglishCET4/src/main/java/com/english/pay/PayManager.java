package com.english.pay;

import android.content.Context;
import android.content.res.ObbInfo;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.english.English;
import com.english.ad.AdUtil;
import com.english.util.Logger;
import com.english.util.SharedPreferenceUtil;
import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PayManager {
    private static final String TAG = PayManager.class.getSimpleName();
    //App ID
    public static final String APP_ID = "b04f2b0f3f9fcd5c5acece6ac1f0ce9f";
    public static final int PAY_RESULT_MSG_SUCCESS = 1;
    public static final int PAY_RESULT_MSG_FAILED = 0;

    private static PayManager mPayManager = null;
    private static Object sObject = new Object();

    private PayManager(Context context) {
        PayConnect.getInstance(APP_ID,AdUtil.APP_PID, context);
    }

    public static PayManager getInstance(Context context){
        if(mPayManager == null){
            synchronized (sObject){
                if(mPayManager == null){
                    mPayManager = new PayManager(context);
                }
            }
        }
        return mPayManager;
    }

    public void pay(Context context){

        String device = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Logger.d("MLJ","device=" + device);
        PayConnect.getInstance(context).pay(context,
                System.currentTimeMillis() + "",
                device,
                (float) 4.99,
                "标准读音",
                "购买美式标准读音",
                "",
                new MyPayResultListener());
    }


    private Handler mPayResultHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PAY_RESULT_MSG_SUCCESS:
                    //保存支付状态
                    SharedPreferenceUtil.savePayResult(English.mContext,true);

                    break;
                case PAY_RESULT_MSG_FAILED:
                    SharedPreferenceUtil.savePayResult(English.mContext,false);
                    break;
            }
        }
    };

    public class MyPayResultListener implements PayResultListener {
        @Override
        public void onPayFinish(Context payViewContext, String order_id, int resultCode,
                                String resultString, int payType,
                                float amount, String goods_name) {
//            mPayResultCode = resultCode;
            if (resultCode == 0) { // 支付成功
                Log.d(TAG, "order_id=" + order_id +
                        "resultCode=" + resultCode +
                        "payType=" + payType +
                        "resultString=" + resultString + ":" + amount + "元" +
                        "goods_name=" + goods_name);
                // 支付成功时关闭当前支付界面
                PayConnect.getInstance(payViewContext).closePayView(payViewContext);

                // 未指定 notifyUrl 的情况下,交易成功后,必须发送回执
                PayConnect.getInstance(payViewContext).confirm(order_id, payType);
                //处理支付成功操作
                mPayResultHandler.sendEmptyMessage(PAY_RESULT_MSG_SUCCESS);

            }else{// 支付失败
                mPayResultHandler.sendEmptyMessage(PAY_RESULT_MSG_FAILED);
            }
        }
    }
}
