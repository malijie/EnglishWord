package com.english.util;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * ������,�������ļ��Ĳ��� 
 * @author malijie
 *
 */
public class FileUtil {
	private final static String DATABASEPATH = "/data/data/com.cet6.activity/databases/";
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
   
    	if(!dbFile.exists()){//
    	Toast.makeText(context, "首次加载，数据初始化中...", Toast.LENGTH_LONG).show();
    		dbFile.getParentFile().mkdirs();
	    	try{
	    		os = new FileOutputStream(dbFileName);//
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
					if(os!= null){
						os.close();
						os=null;
					}
					if(is!=null){
						is.close();
						is = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
    	}  
   }
    	
} 
