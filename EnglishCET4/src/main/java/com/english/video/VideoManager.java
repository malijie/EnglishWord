package com.english.video;

import com.common.lib.util.SharedPreferenceUtil;
import com.common.lib.util.SpKey;
import com.dl7.player.entity.VideoInfo;

/**
 * Created by malijie on 2018/12/1.
 */

public class VideoManager {
    /**
     * 保存当前视频信息
     *
     * @param videoInfo
     */
    public static void saveCurrentInfo(VideoInfo videoInfo) {
        SharedPreferenceUtil.saveStringVideoInfo(SpKey.VIDEO_NAME, videoInfo.getName());
        SharedPreferenceUtil.saveStringVideoInfo(SpKey.VIDEO_URL, videoInfo.getUrl());

    }

    public static String getCurrentName() {
        return SharedPreferenceUtil.getStringVideoInfo(SpKey.VIDEO_NAME);
    }

    public static String getCurrentUrl() {
        return SharedPreferenceUtil.getStringVideoInfo(SpKey.VIDEO_URL);
    }
}
