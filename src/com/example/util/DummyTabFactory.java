package com.example.util;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class DummyTabFactory implements TabContentFactory{
	private Context context;
	public DummyTabFactory(Context context){
		this.context = context;
	}
	@Override
	public View createTabContent(String tag) {
		// TODO Auto-generated method stub
		View view = new View(context);
		return view;
	}

}
