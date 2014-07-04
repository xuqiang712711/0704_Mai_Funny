package com.example.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.listener.AnimateFirstDisplayListener;
import com.example.tab.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//public class MyAdapter extends BaseAdapter{
//	private List<Map<String, Object>> data;
//	private Context context;
//	private Holder holder;
//	private Handler handler;
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	
//	public MyAdapter (List<Map<String, Object>> data, Context convertView , DisplayImageOptions options){
//		this.options = options;
//		this.data = data;
//		this.context = convertView;
////		map = new HashMap<Integer, View>();
//	}
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return data.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return data.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		MyClick click = null;
//		if (convertView ==null) {
//			convertView = LayoutInflater.from(context).inflate(R.layout.image_item, null);
//			holder = new Holder();
//			holder.textView = (TextView)convertView.findViewById(R.id.Image_text);
//			holder.imageView = (ImageView)convertView.findViewById(R.id.Image_image);
//			click = new MyClick();
//			convertView.setTag(holder);
//			convertView.setTag(holder.textView.getId(), click);
////			map.put(position, holder.textView);
//		}else {
//			holder = (Holder) convertView.getTag();
//			click = (MyClick) convertView.getTag(holder.textView.getId());
//		}
//		click.setPosition(position);
//		holder.textView.setText("Item  " +(position +1));
//		holder.textView.setOnClickListener(click);
//		Log.i("FFF", position + "image  position  " + data.get(position).get("image"+position));
//		Log.i("XXX", (String)data.get(position).get("image"+position));
//		if (data.get(position).get("image"+position) != null) {
////			holder.imageView.setImageResource((Integer)data.get(position).get("image" + position));
//			ImageLoader imageLoader = ImageLoader.getInstance();
//			imageLoader.displayImage(data.get(position).get("image"+position).toString(), holder.imageView, options, animateFirstListener);
//		}
////		holder.textView.setTag(position);
////		convertView.setTag(position);
////		initData();
//		return convertView;
//	}
//	
//
//	public static class Holder{
//		TextView textView;
//		ImageView imageView;
//	}
//	
//
//	class MyClick implements OnClickListener{
//		private int position;
//		public void setPosition(int position){
//			this.position = position;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Log.i("FFF", "OnClick");
//			switch (v.getId()) {
//			case R.id.my_text:
//				Message message = Message.obtain();
//				message.what = position;
//				handler.sendMessage(message);
//				break;
//
//			default:
//				break;
//			}
//		}
//		
//	}
//	
//}
