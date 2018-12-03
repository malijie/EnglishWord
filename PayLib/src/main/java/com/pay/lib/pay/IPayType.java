package com.pay.lib.pay;

/**
 * Created by malijie on 2018/11/3.
 */

public interface IPayType {
    int POLITICS_QUESTION = 1;
    int POLITICS_VIDEO = 2;
    int MATH1_VIDEO = 3;
    int MATH2_VIDEO = 4;
    int MATH3_VIDEO = 5;
    int ENGLISH_VOICE = 6;
    int ENGLISH_VIDEO = 7;
    void payForPoliticsVideo();
    void payForPoliticsQuestions();
    void payForMathVideo(int type);
    void payForEnglishVoice();
    void payForEnglishVideo();
}
