package com.english.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.english.English;
import com.english.config.Const;
import com.english.inter.IDialogOnClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ������,�������ļ��Ĳ��� 
 * @author malijie
 *
 */
public class Util {
	private static final String TAG = Util.class.getSimpleName();

	private final static String DATABASEPATH = "/data/data/com.english.phone/databases/";
	private final static String DATABASENAME = "english.db";

	 
	public static boolean isDBExist(){
		File dbFile = new File(DATABASEPATH + DATABASENAME);
		if(!dbFile.exists()){
			dbFile.getParentFile().mkdirs();
		}
		return true;
	}
	
	/**
	 * ��assets������ݿ��ļ�������ϵͳ��
	 */
	public static void copyDB2Phone(Context context){
		String dbFileName = DATABASEPATH + DATABASENAME;
    	File dbFile = new File(dbFileName);
    	InputStream is = null;
    	FileOutputStream os = null;
  		Logger.d(TAG, "dbFile.exists()=" + dbFile.exists());
    	if(!dbFile.exists()){//�ж��ļ����Ƿ���ڣ������ھ��½�һ��
    	Toast.makeText(context, "�״...", Toast.LENGTH_LONG).show();
    		dbFile.getParentFile().mkdirs();
	    	try{
	    		os = new FileOutputStream(dbFileName);//�õ����ݿ��ļ���д����
	    		is = context.getResources().getAssets().open(DATABASENAME);//�õ����ݿ��ļ���������
	    		  byte[] buffer = new byte[1024];
	    	        int count = 0; 
	    	        	while((count=is.read(buffer))>0){
	    	        		os.write(buffer, 0, count);
	    	        		os.flush(); 
	    	        	}
	    	}catch(FileNotFoundException e){
	    		e.printStackTrace();
	    	} catch (IOException e) {
				e.printStackTrace();
			}finally{   
	    		try { 
					is.close();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
    	}
   }

	/**
	 * �����Ƿ�ǰ��ѹ״̬
	 * @param status
	 */
	public static void saveUnZipStatus(String type, boolean status){
		SharedPreferenceUtil.saveUnzipWordsStatus(English.mContext, status);
	}

	/**
	 * ��ѹ�ļ���sd��
	 */
	public static void unZipFile2SdCard(final Context context, final String assetName, final String outputDirectory){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Logger.d(TAG, "start unzip file to sd card");

					File file = new File(outputDirectory);
					if (!file.exists()) {
						file.mkdirs();
					}
					InputStream inputStream = null;
					inputStream = context.getAssets().open(assetName);
					ZipInputStream zipInputStream = new ZipInputStream(inputStream);
					ZipEntry zipEntry = zipInputStream.getNextEntry();

					//ʹ��1M buffer
					byte[] buffer = new byte[1024 * 1024];
					//��ѹʱ�ֽڼ���
					int count = 0;

					while (zipEntry != null) {
						if (zipEntry.isDirectory()) {
							file = new File(outputDirectory + File.separator + zipEntry.getName());
							file.mkdir();
						} else {
							file = new File(outputDirectory + File.separator
									+ zipEntry.getName());
							file.createNewFile();
							FileOutputStream fileOutputStream = new FileOutputStream(file);
							while ((count = zipInputStream.read(buffer)) > 0) {
								fileOutputStream.write(buffer, 0, count);
							}
							fileOutputStream.close();
						}
						zipEntry = zipInputStream.getNextEntry();
					}
					zipInputStream.close();
					Logger.d(TAG, "complete unzip to sd card");

					saveUnZipStatusEvent(assetName);

				}catch(IOException e){
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 *�����ļ����Ʊ�����Ӧ�Ľ�ѹ״̬
	 * @param assetName
	 */
	private static void saveUnZipStatusEvent(String assetName) {
		if(assetName.equals(Const.UNZIP_WORDS_FILE_NAME)){
			//�����ļ��ѽ�ѹ����
			SharedPreferenceUtil.saveUnzipWordsStatus(English.mContext, true);
		}
	}


//	/**
//	 * ��AndroidManifest.xml�ж�ȡmete-data������
//	 * @return
//	 */
//	public static String getAppID(){
//		return "469378b902a681c98f5a095ddccf8223";
//	}
//
//	/**
//	 * ����������
//	 * @return
//	 */
//	public static String generateOrderId(){
//		return String.valueOf(System.currentTimeMillis());
//	}
//
//	/**
//	 * �����û�id
//	 * @return
//	 */
//	public static String generateUserId(){
//		return PayConnect.getInstance(English.mContext)
//				         .getDeviceId(English.mContext);
//	}
//
	/**
	 * ������Դ�ļ���ȡview
	 * @param res
	 * @return
	 */
	public static View getView(int res){
		LayoutInflater inflater = LayoutInflater.from(English.mContext);
		View v = inflater.inflate(res,null);
		return  v;
	}

	/**
	 * �����Ի���
	 * @param title	�Ի������
	 * @param msg �Ի�������
	 * @param listener ���ȷ���¼�
	 */
	public static void showAlertDialog(Context context, String title, String msg, final IDialogOnClickListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context, com.english.cet4.R.style.Base_Theme_AppCompat_Dialog_Alert);

		View v = Util.getView(com.english.cet4.R.layout.alert_dialog_layout);
		builder.setView(v);

		Button buttonConfirm = (Button) v.findViewById(com.english.cet4.R.id.dialog_button_confirm);
		Button buttonCancel = (Button) v.findViewById(com.english.cet4.R.id.dialog_button_cancel);
		TextView textTitle = (TextView) v.findViewById(com.english.cet4.R.id.dialog_text_title);
		TextView textMsg = (TextView) v.findViewById(com.english.cet4.R.id.dialog_text_content);

		textTitle.setText(title);
		textMsg.setText(msg);

		final AlertDialog dialog = builder.create();
		//�˳�
		buttonConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(listener != null){
					listener.onClick();
					dialog.dismiss();
				}
			}
		});
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
