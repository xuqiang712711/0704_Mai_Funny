package com.example.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.content.DuanZI_UserInfo;
import com.example.fragment.content.DuanZi_More;
import com.example.listener.AnimateFirstDisplayListener;
import com.example.tab.R;
import com.example.util.CustomImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

//public class ImageAdapter extends BaseAdapter implements OnClickListener{
//	Holder holder = null;
//	private Context context;
//	private Tab_Image_Frag mcontext;
//	private List<Map<String, Object>> data ;
//	private static Map<Integer, Boolean> isChecked_bury;
//	private static Map<Integer, Boolean> isChecked_praise;
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	
//	public  ImageAdapter(Context context, List<Map<String, Object>> data){
//		this.context = context;
//		this.data = data;
//	}
//	
//	public ImageAdapter(Tab_Image_Frag mcontext, Context context, List<Map<String, Object>> data, DisplayImageOptions options){
//		this.context = context;
//		this.data = data;
//		this.mcontext = mcontext;
//		this.options = options;
//		
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.maimob)
//		.showImageForEmptyUri(R.drawable.maimob)
//		.showImageOnFail(R.drawable.maimob)
//		.cacheInMemory(true)
//		.cacheOnDisk(true)
//		.considerExifParams(true)
//		.displayer(new SimpleBitmapDisplayer())
//		.build();
//		
//		init();
//	}
//	
//	private void init(){
//		
//		isChecked_bury = new HashMap<Integer, Boolean>();
//		isChecked_praise = new HashMap<Integer, Boolean>();
//		for (int i = 0; i < data.size(); i++) {
//			isChecked_bury.put(i, false);
//			isChecked_praise.put(i, false);
//		}
//		
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
//		 TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		if (convertView == null) {
//			Log.i("FFF", convertView + "  convertView is  null " + position);
//			convertView = LayoutInflater.from(context).inflate(R.layout.duanzi_item, null);
//			holder = new Holder();
//			holder.layout_parise = (CustomImage)convertView.findViewById(R.id.duanzi_praise);
//			holder.layout_bury = (CustomImage)convertView.findViewById(R.id.duanzi_bury);
//			holder.layout_hot = (CustomImage)convertView.findViewById(R.id.duanzi_hot);
//			holder.more		= (ImageView)convertView.findViewById(R.id.duanzi_more);
//			
//			holder.user_icon = (ImageView)convertView.findViewById(R.id.duanzi_user_icon);
//			holder.user_name = (TextView)convertView.findViewById(R.id.duanzi_user_name);
//			
//			holder.content = (TextView)convertView.findViewById(R.id.duanzi_textview);
//			
//			holder.image = (ImageView)convertView.findViewById(R.id.duanzi_imageview);
//			holder.comment = (TextView)convertView.findViewById(R.id.duanzi_comment);
//			convertView.setTag(holder);
//		}else {
//			holder = (Holder) convertView.getTag();
//			Log.i("FFF",convertView +  "   convertView is not null " + position);
//		}
//		holder.layout_parise.setTag(position);
//		if (isChecked_praise.get(position) == true) {
//			holder.layout_parise.setImageResource(R.drawable.ic_digg_pressed);
//		}else {
//			holder.layout_parise.setImageResource(R.drawable.ic_digg_normal);
//		}
////		holder.layout_parise.setTextViewText(R.string.num);
//		
//		holder.layout_bury.setTag(position);
//		if (isChecked_bury.get(position) == true) {
//			holder.layout_bury.setImageResource(R.drawable.ic_bury_pressed);
//		}else {
//			holder.layout_bury.setImageResource(R.drawable.ic_bury_normal);
//		}
////		holder.layout_bury.setTextViewText(R.string.num);
//		
//		holder.layout_hot.setImageResource(R.drawable.hot_commenticon_textpage);
////		holder.layout_hot.setTextViewText(R.string.num);
//		holder.layout_hot.setTag(position);
//		
//		holder.more.setTag(position);
//		if (data.get(position).get("icon" + position) != null) {
//			holder.user_icon.setImageResource((Integer)data.get(position).get("icon"+position));
//			holder.user_icon.setTag(position);
//		}
//		holder.user_name.setText("Name " + (position +1));
//		holder.content.setText("content " + (position +1));
//		holder.content.setTag(position);
//		holder.comment.setText("comment " + (position +1));
//		holder.comment.setTag(position);
//		
//		if (data.get(position).get("image" + position) != null) {
////			holder.image.setImageResource((Integer)data.get(position).get("image" + position));
//			Log.i("FFF", "image ~~~~~" + data.get(position).get("image"+position));
//			ImageLoader imageLoader = ImageLoader.getInstance();
//			imageLoader.displayImage(data.get(position).get("image"+position).toString(), holder.image, options, animateFirstListener);
//		}
//		
//		addListenWidget(holder, position);
//		return convertView;
//	}
//	
//	public static class Holder{
//		CustomImage layout_parise, layout_bury, layout_hot;
//		ImageView user_icon, more, image;
//		TextView user_name, content, comment;
//	}
//
//	private void addListenWidget(Holder holder, int position){
//		holder.more.setOnClickListener(this);
//		holder.layout_bury.setOnClickListener(new myClick(position));
//		holder.layout_hot.setOnClickListener(this);
//		holder.layout_parise.setOnClickListener(new myClick(position));
//		holder.user_icon.setOnClickListener(this);
//		holder.content.setOnClickListener(this);
//		holder.comment.setOnClickListener(this);
//	}
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		int position = (Integer) v.getTag();
//		switch (v.getId()) {
//		
//		case R.id.duanzi_more:
//			DuanZi_More more = new DuanZi_More();
////			mcontext.switchFragment(more);
//			break;
//		case R.id.duanzi_hot:
//			DuanZi_Comment comment3 = new DuanZi_Comment();
////			mcontext.switchFragment(comment3);
//			Toast.makeText(context, "点击热门  +  " + position, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.duanzi_comment:
//			int comment_p = (Integer) v.getTag();
//			Toast.makeText(context, "点击评论  +  " + comment_p, Toast.LENGTH_SHORT).show();
//			DuanZi_Comment comment2 = new DuanZi_Comment();
////			mcontext.switchFragment(comment2);
//			break;
//
//		case R.id.duanzi_user_icon:
//			Toast.makeText(context, "点击用户头像  + " + position, Toast.LENGTH_SHORT).show();
//			DuanZI_UserInfo userInfo = new DuanZI_UserInfo();
////			mcontext.switchFragment(userInfo);
//			break;
//		case R.id.duanzi_textview:
//			Toast.makeText(context, "点击段子  + " +position, Toast.LENGTH_SHORT).show();
//			DuanZi_Comment comment = new DuanZi_Comment();
//			mcontext.switchFragment(new Tab_Image_Frag(), comment);
////			DuanZi_Hot duanZi_Hot = new DuanZi_Hot();
////			duanZi_Hot.switchFragment(comment);
//			break;
//		}
//	}
//	
//	public class myClick implements OnClickListener{
//		private int position;
//		public myClick(int position){
//			this.position = position;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.id.duanzi_bury:
//				if (isChecked_bury.get(position) == false) {
//					isChecked_bury.put(position, true);
//					Log.i("FFF", "pressed");
//					holder.layout_bury.setImageResource(R.drawable.ic_bury_pressed);
//				}else if (isChecked_bury.get(position) == true) {
//					isChecked_bury.put(position, false);
//					holder.layout_bury.setImageResource(R.drawable.ic_bury_normal);
//				}
//				notifyDataSetChanged();
//				break;
//
//			case R.id.duanzi_praise:
//				if (isChecked_praise.get(position) == false) {
//					isChecked_praise.put(position, true);
//					holder.layout_parise.setImageResource(R.drawable.ic_digg_pressed);
//				}else {
//					isChecked_praise.put(position, false);
//					holder.layout_parise.setImageResource(R.drawable.ic_digg_normal);
//				}
//				notifyDataSetChanged();
//				break;
//			}
//		}
//		
//	}
//	
//	
//}
