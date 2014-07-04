package com.example.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.TextView;

public class EditUtil {
	private EditText editText;
	private Context context;
	private TextView textView;
	private static final int MAX_NUM = 140;
	public EditUtil(Context context,EditText editText,TextView textView){
		this.context = context;
		this.editText = editText;
		this.textView = textView;
		this.editText.addTextChangedListener(MyWatcher);
		this.editText.setSelection(editText.length());
		setLeftCount();
	}
	TextWatcher MyWatcher = new TextWatcher() {
		private int Start;
		private int End;
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			Start = editText.getSelectionStart();
			End = editText.getSelectionEnd();
			editText.removeTextChangedListener(MyWatcher);
			while (calculateLength(s.toString()) > MAX_NUM) {
				s.delete(Start - 1, End);
				Start--;
				End--;
			}
			editText.setText(s);
			editText.setSelection(Start);
			editText.addTextChangedListener(MyWatcher);
			setLeftCount();
		}
	};
	
	/**
	 * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
	 * 
	 * @param c
	 * @return
	 */
	private long calculateLength(CharSequence c) {
		double len = 0;
		for (int i = 0; i < c.length(); i++) {
			int tmp = (int) c.charAt(i);
			if (tmp > 0 && tmp < 127) {
				len += 0.5;
			} else {
				len++;
			}
		}
		return Math.round(len);
	}
	
	/**
	 * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
	 */
	private void setLeftCount() {
		textView.setText(String.valueOf((MAX_NUM - getInputCount())));
	}
	
	/**
	 * 获取用户输入的分享内容字数
	 * 
	 * @return
	 */
	private long getInputCount() {
		return calculateLength(editText.getText().toString());
	}
}
