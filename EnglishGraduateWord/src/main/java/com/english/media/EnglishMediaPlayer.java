package com.english.media;

import android.content.Context;
import android.media.MediaPlayer;

import com.english.config.Config;
import com.english.config.Const;

import java.io.IOException;

/**
 * Created by vic_ma on 15/10/8.
 * Ӣ����Ƶ������
 */
public class EnglishMediaPlayer {
    private static final Object mObject = new Object();

    public static EnglishMediaPlayer mEnglishMediaPlayer = null;
    //media����
    private MediaPlayer mMediaPlayer = null;

    private Context mContext = null;

    public static EnglishMediaPlayer getInstance(Context context){
        if(mEnglishMediaPlayer == null){
            synchronized (mObject){
                if(mEnglishMediaPlayer == null){
                    mEnglishMediaPlayer = new EnglishMediaPlayer(context);
                }
            }
        }
        return  mEnglishMediaPlayer;
    }

    private EnglishMediaPlayer(Context context){
        mMediaPlayer = new MediaPlayer();
        mContext = context;
    }

    /**
     * ���ŵ�����Ƶ
     */
    public void playTheWordTune(String wordName){
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(Config.PLAY_WORDS_VOLUME_PATH + wordName + Const.WORDS_VOICE_SUFFIX);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �����Ķ����������Ƶ
     * @param passage
     */
    public void playThePassage(String passage){
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(passage);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ֹͣ����
     */
    public void stopPlay(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
        }
    }
}
