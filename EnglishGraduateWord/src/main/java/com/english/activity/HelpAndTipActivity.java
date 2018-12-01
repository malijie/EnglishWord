package com.english.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.cet6.activity.R;


public class HelpAndTipActivity extends Activity {
	private TextView textHelp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.help_or_tip_layout);
		textHelp = (TextView) super.findViewById(R.id.text_help);
		String strHelp = "<P>这是一款专门为备考考研英语学子量身定做的英语APP。其主要功能包括：</P>"
				+ "<P>1. 单词。 提供包含最新大纲内所有单词，让您万无一失！</P>"
				+ "<P>2. 读音。 真人发音。最新录入标准美式发音，让您背单词更轻松</P>"
				+ "<P>3. 搜词。 收录最新考研英语大纲词汇。</P>"
				+ "<P>4. 生词本。将您在所学过程中所不认识或者记忆错误的单词统一归类到生词本中，提升您的复习效率，强化您的记忆！让您不必再为生词担心。</P>"
				+ "<P align=center>Copyright malijie </P>"
				+ "<P>All Rights Reserved.  </P>"
				+ "<P>联系方式：190223629@qq.com </P>";
		textHelp.setText(Html.fromHtml(strHelp));
		textHelp.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	
}
