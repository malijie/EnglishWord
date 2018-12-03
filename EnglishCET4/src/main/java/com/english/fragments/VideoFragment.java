package com.english.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.lib.ui.CustomDialog;
import com.common.lib.util.PermissionController;
import com.common.lib.util.ToastManager;
import com.dl7.player.activity.IjkFullscreenActivity;
import com.dl7.player.entity.VideoInfo;
import com.english.activity.SearchDetailActivity;
import com.english.activity.WordsDetailActivity;
import com.english.adapter.SearchAdapter;
import com.english.cet4.R;
import com.english.config.Const;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.inter.IDialogOnClickListener;
import com.english.model.WordInfo;
import com.english.util.SharedPreferenceUtil;
import com.english.util.Util;
import com.english.util.Utils;
import com.english.video.VideoManager;
import com.pay.lib.pay.PayBaseInfo;
import com.pay.lib.pay.PayManager;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
	private View viewVideo = null;
	private ListView mListView = null;
	private PayManager mPayManager = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		viewVideo = inflater.inflate(R.layout.video_layout, container, false);
		initView(viewVideo);
		initData();
		
		return viewVideo;
	}

	private void initData() {
		mPayManager = PayManager.getInstance(getActivity());
	}

	private void initView(View viewVideo) {
		mListView = (ListView) viewVideo.findViewById(R.id.listview_video);
		mListView.setAdapter(new VideoAdapter(Const.VIDEO_NAME,Const.VIDEO_URL,Const.VIDEO_LENGTH));
	}


	private class VideoAdapter extends BaseAdapter{

		private String[] videoItem = null;
		private String[] videoUrl = null;
		private int[] videoLength = null;

		VideoAdapter(String[] videoItem, String[] videoUrl, int[] videoLength) {
			this.videoItem = videoItem;
			this.videoUrl = videoUrl;
			this.videoLength = videoLength;
		}

		@Override
		public int getCount() {
			return videoItem.length;
		}

		@Override
		public Object getItem(int i) {
			return videoItem[i];
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(final int i, View view, ViewGroup viewGroup) {
			ViewHolder holder = null;
			if (view == null) {
				holder = new ViewHolder();
				view = Utils.getView(R.layout.video_expand_item_view);
				holder.textName = (TextView) view.findViewById(R.id.id_video_expand_item_text_title);
				holder.textTime = (TextView) view.findViewById(R.id.id_video_expand_item_text_length);
				holder.mLayout = (RelativeLayout) view.findViewById(R.id.id_video_expand_item_layout);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}


			holder.textName.setText(videoItem[i]);
			int min = videoLength[i] / 60;
			int sec = videoLength[i] % 60;
			String strMin = min < 10 ? "0" + min : min + "";
			String strSec = sec < 10 ? "0" + sec : sec + "";
			holder.textTime.setText(strMin + ":" + strSec);


			holder.mLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					VideoInfo videoInfo  = new VideoInfo();
					videoInfo.setUrl(videoUrl[i]);
					videoInfo.setName(videoItem[i]);

					if (i >= 1) {
						if (!mPayManager.hasPayedEnglishVideo()) {
							Util.showAlertDialog(getActivity(),
									PayBaseInfo.ITEM_ENGLISH_VIDEO, PayBaseInfo.ITEM_ENGLISH_VIDEO_DESCR,"", new IDialogOnClickListener() {
										@Override
										public void onClick() {
											PayManager.getInstance(getActivity()).payForEnglishVideo();
										}
									});
						} else {
							if (PermissionController.checkPermission(getActivity())) {
								startGoPlay(i);
								VideoManager.saveCurrentInfo(videoInfo);
							}

						}

					}else{
						startGoPlay(i);
						VideoManager.saveCurrentInfo(videoInfo);
					}
				}

			});


			return view;
		}

		private void startGoPlay(int index){
			VideoInfo videoInfo = new VideoInfo();
			videoInfo.setName(videoItem[index]);
			videoInfo.setUrl(videoUrl[index]);
			Intent i = new Intent(getActivity(), IjkFullscreenActivity.class);
			i.putExtra("videoInfo", videoInfo);
			startActivity(i);
		}


		class ViewHolder {
			public TextView textName;
			public TextView textTime;
			public RelativeLayout mLayout;

		}

	}

	private void showPayTip() {
//		final CustomDialog dialog = new CustomDialog(getActivity(), R.layout.dialog_layout, PayBaseInfo.ITEM_ENGLISH_VIDEO,PayBaseInfo.ITEM_ENGLISH_VIDEO_DESCR);
//		dialog.setButtonClickListener(new CustomDialog.DialogButtonListener() {
//			@Override
//			public void onConfirm() {
//
//				if(PermissionController.checkPermission(getActivity())){
//					mPayManager.hasPayedEnglishVideo();
//					dialog.dissmiss();
//				}else{
//					ToastManager.showLongMsg("未打开权限，请到设置-应用中打开相关权限后完成支付");
//					dialog.dissmiss();
//				}
//
//			}
//
//			@Override
//			public void onCancel() {
//				dialog.dissmiss();
//			}
//		});
//		dialog.show();


	}

}
