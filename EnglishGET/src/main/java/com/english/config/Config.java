package com.english.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by vic_ma on 15/10/8.
 */
    public class Config {

    //���ʽ�ѹ·��
    public static final String UNZIP_WORDS_FILE_PATH = Environment.getExternalStorageDirectory()
                                                        .getAbsolutePath() + File.separator + "EnglishGET"+ File.separator;

    //������Ƶ����·��
    public static final String PLAY_WORDS_VOLUME_PATH = UNZIP_WORDS_FILE_PATH + "words" + File.separator;

    //�Ķ���Ƶ����·��
    public static final String PLAY_READING_VOLUME_PATH = UNZIP_WORDS_FILE_PATH + "reading" + File.separator;

    //��ʽ�ߣ�����������
    public static boolean DEBUG_MODE = true;

}
