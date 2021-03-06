package com.english.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.english.English;
import com.english.ad.WapManager;
import com.english.cet6.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.inter.IDialogOnClickListener;
import com.english.media.EnglishMediaPlayer;
import com.english.model.WordInfo;
import com.english.pay.PayManager;
import com.english.util.PermissionController;
import com.english.util.SharedPreferenceUtil;
import com.english.util.Util;
import com.wanpu.pay.PayConnect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WordsDetailActivity extends Activity implements OnClickListener {
	private TextView txtWord = null;
	private TextView txtSymbols = null;
	private TextView txtContent = null;
	private TextView txtExample1 = null;
	private TextView txtExample2 = null;
	private TextView txtExample = null;
	private TextView txtProgress = null;
	 
	private LinearLayout layoutContent = null;
	private RelativeLayout layoutExample1 = null;
	private RelativeLayout layoutExample2 = null;
	private LinearLayout layoutAd = null;
	private LinearLayout layoutRightOrWrong = null;
	private LinearLayout layoutKnownOrNot = null;
	
	private Button butNext = null;
	private Button butKnown = null;
	private Button butNotKnown = null;
	private Button butRight = null;
	private Button butWrong = null;
	private ImageButton butSound = null;
    private ImageButton butGoHead = null;

	private int progress;
	private int lessonNum;
    private boolean mIsGoHead = false;
	private List<WordInfo> lessonWords = null;
	private WordInfo mWordInfo = null;
	private static SimpleDateFormat sDateFormat = null;
	
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;

	private EnglishMediaPlayer mEnglishMediaPlayer = null;
	private PayManager mPayManager = null;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.words_detail_layout);

		initData();
		initDatabase();
		initView();
		showWordsKnownOrNotUI();
		setWordData(progress);
		
	}
	
	
	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(),this);
	}

	private void initActionBar() {
		ActionBar mainActionBar = getActionBar();
		mainActionBar.show();
	}
	

	private void initData() {
        mIsGoHead = true;
		lessonNum = getIntent().getIntExtra("lesson_num", 0);
		lessonWords = (List<WordInfo>) getIntent().getSerializableExtra("lesson_words");
//		index = getIntent().getIntExtra("index", 0);

		sDateFormat = getSimpleDateFormatInstance();
        progress = SharedPreferenceUtil.loadLessonProgress(WordsDetailActivity.this, lessonNum);

        mEnglishMediaPlayer = EnglishMediaPlayer.getInstance(this);
		mPayManager = PayManager.getInstance(this);

	}
	
	private SimpleDateFormat getSimpleDateFormatInstance(){
		if(sDateFormat == null){
			sDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		}
		return sDateFormat;
	}
	
	private void initView() {
		txtWord = (TextView) super.findViewById(R.id.word_detail_word);
		txtSymbols = (TextView) super.findViewById(R.id.word_detail_symbol);
		txtContent = (TextView) super.findViewById(R.id.word_detail_content);
		txtExample1 = (TextView) super.findViewById(R.id.word_detail_example1);
		txtExample2 = (TextView) super.findViewById(R.id.word_detail_example2);
		txtExample = (TextView) super.findViewById(R.id.word_detail_text_example);
		txtProgress = (TextView) super.findViewById(R.id.word_detail_text_progress);
		
		layoutContent = (LinearLayout) super.findViewById(R.id.word_detail_content_layout);
		layoutExample1 = (RelativeLayout) super.findViewById(R.id.word_detail_example1_layout);
		layoutExample2 = (RelativeLayout) super.findViewById(R.id.word_detail_example2_layout);
		layoutAd = (LinearLayout) super.findViewById(R.id.word_detail_ad_layout);
		layoutRightOrWrong = (LinearLayout) super.findViewById(R.id.word_detail_layout_rightorwrong);
		layoutKnownOrNot = (LinearLayout) super.findViewById(R.id.word_detail_layout_knownornot);
		
		butNext = (Button) super.findViewById(R.id.word_detail_button_next);
		butKnown = (Button) super.findViewById(R.id.word_detail_button_known);
		butNotKnown = (Button) super.findViewById(R.id.word_detail_button_notknown);
		butRight = (Button) super.findViewById(R.id.word_detail_button_right);
		butWrong = (Button) super.findViewById(R.id.word_detail_button_wrong);
		butSound = (ImageButton) super.findViewById(R.id.word_detail_button_volume);
        butGoHead = (ImageButton) super.findViewById(R.id.word_detail_button_gohaed);


		butNext.setOnClickListener(this);
		txtExample1.setOnClickListener(this);
		txtExample2.setOnClickListener(this); 
		butKnown.setOnClickListener(this);
		butNotKnown.setOnClickListener(this);
		butRight.setOnClickListener(this);
		butWrong.setOnClickListener(this);
        butSound.setOnClickListener(this);
        butGoHead.setOnClickListener(this);

		SharedPreferenceUtil spUtil = new SharedPreferenceUtil(this);
		txtWord.setTextSize(spUtil.getFontSize("word_size"));
		
		//AdUtil.showMiniAd(this, layoutAd,30);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.word_detail_button_next:
			showWordsKnownOrNotUI();
			showNextWord();
			break;
		case R.id.word_detail_example1:
			showExampleDetail();
			break;
		case R.id.word_detail_example2:
			showExampleDetail();
			break;
		case R.id.word_detail_button_known:
			showRightOrWrongUI();
			break;
		case R.id.word_detail_button_notknown:
			eOperate.updateWordIsKnownById(false, mWordInfo.getId());
			showNextWordUI();
			setWordData(progress);
			break;
		case R.id.word_detail_button_right:
			eOperate.updateWordIsKnownById(true, mWordInfo.getId());
			showWordsKnownOrNotUI();
			showNextWord();
			break;
		case R.id.word_detail_button_wrong:
			eOperate.updateWordIsKnownById(false, mWordInfo.getId());
			showWordsKnownOrNotUI(); 
			showNextWord(); 
			break;
		case R.id.word_detail_button_volume:
				//播放单词音频
				if(SharedPreferenceUtil.getPayResult(getApplicationContext())){
					mEnglishMediaPlayer.playTheWordTune(mWordInfo.getWord());
				}else{
					if(PermissionController.checkPermission(this)){
						WapManager.getInstance(this);
						PayConnect.getInstance(this);
						Util.showAlertDialog(WordsDetailActivity.this,
								PayManager.PAY_DIALOG_TITLE, PayManager.PAY_DIALOG_CONTENT,"", new IDialogOnClickListener() {
									@Override
									public void onClick() {
										mPayManager.pay(WordsDetailActivity.this);
									}
								});

					}

				}
			break;
        case R.id.word_detail_button_gohaed:
            //从头开始学习
            Util.showAlertDialog(WordsDetailActivity.this,
                    "从头开始学习", "当前学习进度将不会保存，确定这么做吗？","", new IDialogOnClickListener() {
                        @Override
                        public void onClick() {
                            mIsGoHead = true;
                            //当前课程正确率清0
                            eOperate.resumeAccuracyCount(lessonNum);
                            //将当前课程的进度置为0
                            SharedPreferenceUtil.saveLessonProgress(English.mContext, lessonNum, 0);
                            //关闭当前Activity
                            WordsDetailActivity.this.finish();
                        }
                    });

            break;
            case R.id.word_detail_add:
			
			break;
			
		}
	}
	
	private void showNextWord(){
        progress ++;
		setWordData(progress);
	}
	
	/**
	 * 显示下一个单词界面
	 */
	private void showNextWordUI() {
		layoutRightOrWrong.setVisibility(View.GONE);
		layoutKnownOrNot.setVisibility(View.VISIBLE);
		layoutContent.setVisibility(View.VISIBLE);
		txtExample.setVisibility(View.VISIBLE);
		layoutExample1.setVisibility(View.VISIBLE);
		layoutExample2.setVisibility(View.VISIBLE);
		txtExample.setVisibility(View.INVISIBLE);
		butNext.setVisibility(View.VISIBLE);
		butKnown.setVisibility(View.GONE);
		butNotKnown.setVisibility(View.GONE);
	}

	/**
	 *显示认识/不认识单词界面
	 */
	private void showWordsKnownOrNotUI(){
		layoutRightOrWrong.setVisibility(View.GONE);
		layoutKnownOrNot.setVisibility(View.VISIBLE);
		layoutContent.setVisibility(View.INVISIBLE);
		layoutExample1.setVisibility(View.INVISIBLE);
		layoutExample2.setVisibility(View.INVISIBLE);
		txtExample.setVisibility(View.INVISIBLE);
		butNext.setVisibility(View.GONE);
		butKnown.setVisibility(View.VISIBLE);
		butNotKnown.setVisibility(View.VISIBLE);
	}
	
	/** 
	 * 显示正确或是错误界面
	 */
	private void showRightOrWrongUI(){
		layoutKnownOrNot.setVisibility(View.GONE);
		butNext.setVisibility(View.GONE);
		layoutRightOrWrong.setVisibility(View.VISIBLE);
		layoutContent.setVisibility(View.VISIBLE);
		txtExample.setVisibility(View.VISIBLE);
		layoutExample1.setVisibility(View.VISIBLE);
		layoutExample2.setVisibility(View.VISIBLE);
		 
	}
	
	private void showExampleDetail(){
		Intent it = new Intent(WordsDetailActivity.this, WordExampleDetailActivity.class);
		it.putExtra("id", lessonWords.get(progress).getId());
		it.putExtra("symbols", lessonWords.get(progress).getSymbols());
		it.putExtra("word", lessonWords.get(progress).getWord());
		it.putExtra("content", lessonWords.get(progress).getContent());
		it.putExtra("example", lessonWords.get(progress).getExample());
		startActivity(it);
	}

    /**
     * 为控件设置数据
     * @param mIndex
     */
    private void setWordData(int mIndex){
        if(mIndex > lessonWords.size()-1){
            Toast.makeText(WordsDetailActivity.this, "本课单词已学习结束，请选择下一课。", Toast.LENGTH_SHORT).show();
            return;
        }
        mWordInfo = lessonWords.get(mIndex);
        txtSymbols.setText(lessonWords.get(mIndex).getSymbols());
        txtWord.setText(lessonWords.get(mIndex).getWord());
        txtContent.setText(lessonWords.get(mIndex).getContent());
        txtProgress.setText("Lesson " + (lessonNum + 1) + "  (" + (progress + 1) + "/" + lessonWords.size() + ")");
        setExampleData(lessonWords.get(mIndex).getExample());
        updateLastVisitDate(mWordInfo.getId());
    }

	private void updateLastVisitDate(int id) {
		String date = sDateFormat.format(new Date());
		eOperate.updateLastVisitDateById(date, id);
	}


	/**
	 *设置例句数据
	 */
	private void setExampleData(String example){
	   String examples[] = example.split("2.");
	   if(examples.length <= 1){
		   txtExample1.setText(Html.fromHtml(examples[0]));
		   return;
	   } 
	   txtExample1.setText(Html.fromHtml(examples[0]));
	   txtExample2.setText(Html.fromHtml("2." + examples[1]));
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
					saveWordProgress();
					WordsDetailActivity.this.finish();

			break;
		}
		
		return super.onKeyDown(keyCode, event);
	}


	public void closeDB(EnglishDatabaseHelper eHelper){
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
	}

	/**
	 * 保存单词进度
	 */
	private void saveWordProgress(){
		SharedPreferenceUtil.saveLessonProgress(English.mContext,lessonNum, progress);
	}

	@Override
	protected void onPause() {
        if(!mIsGoHead){
            saveWordProgress();
        }
		super.onPause();
	}

    @Override
    protected void onStop() {
        super.onStop();
        mEnglishMediaPlayer.stopPlay();
        mIsGoHead = false;
    }
}
