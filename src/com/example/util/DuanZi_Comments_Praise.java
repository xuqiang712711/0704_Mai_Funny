package com.example.util;

import com.example.tab.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DuanZi_Comments_Praise extends RelativeLayout{
	private ImageView imageView;
	private TextView textView;
	public DuanZi_Comments_Praise(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public DuanZi_Comments_Praise(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.duanzi_comments_praise, this);
		imageView = (ImageView)findViewById(R.id.duanzi_comments_icon);
		textView = (TextView)findViewById(R.id.duanzi_comments_praise_count);
	}
	
	public DuanZi_Comments_Praise(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public void setLocation(){
		imageView.setImageResource(R.drawable.ic_digg_pressed);
	}

	public void setText(String content){
		textView.setText(content);
	}
}
