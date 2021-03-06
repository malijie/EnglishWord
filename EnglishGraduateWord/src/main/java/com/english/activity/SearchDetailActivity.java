package com.english.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cet6.activity.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.inter.IDialogOnClickListener;
import com.english.media.EnglishMediaPlayer;
import com.english.model.WordInfo;
import com.english.pay.PayManager;
import com.english.util.SharedPreferenceUtil;
import com.english.util.Util;

import static com.english.media.EnglishMediaPlayer.mEnglishMediaPlayer;

public class SearchDetailActivity extends Activity implements OnClickListener {
	private TextView textWord = null;
	private TextView textContent = null;
	private TextView textSymbols = null;
	private TextView textExample1 = null;
	private LinearLayout ad1Layout = null;
	private LinearLayout ad2Layout = null;
	private ImageButton buttonAdd = null;
    private ImageButton buttonPlay = null;
	private WordInfo wordInfo = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
    private EnglishMediaPlayer mPlayer = null;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.search_detail_layout);
		initData();
		initView();
		setData();
		initDatabase();
	}

	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(), this);
	}

	private void setData() {
		textWord.setText(wordInfo.getWord());
		textSymbols.setText(wordInfo.getSymbols());
		textContent.setText(wordInfo.getContent());
		textExample1.setText(Html.fromHtml(wordInfo.getExample()));
		//AdUtil.showMiniAd(this, ad1Layout,15);
	}

	private void initView() {
		textWord = (TextView) super.findViewById(R.id.search_detail_text_word);
		textContent = (TextView) super.findViewById(R.id.search_detail_text_content);
		textSymbols = (TextView) super.findViewById(R.id.search_detail_text_symbols);
		textExample1 = (TextView) super.findViewById(R.id.search_detail_text_example1);
		ad1Layout = (LinearLayout) super.findViewById(R.id.search_detail_layout_ad1);
		ad2Layout = (LinearLayout) super.findViewById(R.id.search_detail_layout_ad2);
		buttonAdd = (ImageButton) super.findViewById(R.id.search_detail_button_add);
		textExample1.setMovementMethod(ScrollingMovementMethod.getInstance());
		textContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        buttonPlay = (ImageButton)findViewById(R.id.search_detail_button_volume);

        buttonPlay.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
	}
	
	private void initData(){
		wordInfo = (WordInfo) getIntent().getSerializableExtra("word_info");
        mPlayer = EnglishMediaPlayer.getInstance(this);

    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.search_detail_button_add:
			eOperate.updateWordIsKnownById(false, wordInfo.getId());
			Toast.makeText(SearchDetailActivity.this, "已添加到生词库", Toast.LENGTH_SHORT).show();
			break;
        case R.id.search_detail_button_volume:
			if(SharedPreferenceUtil.getPayResult(getApplicationContext())){
				mPlayer.playTheWordTune(wordInfo.getWord());
			}else{
				Util.showAlertDialog(this,
						PayManager.PAY_DIALOG_TITLE, PayManager.PAY_DIALOG_CONTENT,"", new IDialogOnClickListener() {
							@Override
							public void onClick() {
								PayManager.getInstance(SearchDetailActivity.this).pay(SearchDetailActivity.this);
							}
						});

			}
		}
	}

	@Override
	protected void onDestroy() {
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
        mPlayer.stopPlay();
        super.onDestroy();
	}
	
	
	
}
