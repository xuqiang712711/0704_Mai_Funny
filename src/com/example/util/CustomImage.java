package com.example.util;

import com.example.tab.R;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomImage extends LinearLayout{
	private ImageView imageView;
	private TextView textView;
	private LinearLayout layout;
	public CustomImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.my_check_image, this);
		layout = (LinearLayout)findViewById(R.id.custom_image);
		imageView = (ImageView)findViewById(R.id.my_check_bottom_image);
		textView = (TextView)findViewById(R.id.my_check_bottom_text);
	}
	public void setLayoutOrientation(int orientation){
		layout.setOrientation(orientation);
	}
	
	public void setImageResource(int resID){
		imageView.setImageResource(resID);
	}
	
	public void setTextView_int(int resID){
		textView.setText(resID);
	}
	
	public void setTextView_String(String content){
		textView.setText(content);
	}

}
