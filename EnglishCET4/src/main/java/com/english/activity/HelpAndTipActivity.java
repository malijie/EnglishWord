package com.english.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


public class HelpAndTipActivity extends Activity {
	private TextView textHelp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(com.english.cet4.R.layout.help_or_tip_layout);
		textHelp = (TextView) super.findViewById(com.english.cet4.R.id.text_help);
		String strHelp = "";
		textHelp.setText(Html.fromHtml(strHelp));
		textHelp.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	
}
