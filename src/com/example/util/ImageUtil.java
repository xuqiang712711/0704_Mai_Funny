package com.example.util;

import java.io.IOException;
import java.io.InputStream;

import com.example.tab.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class ImageUtil {
	private static AssetManager assetManager;
	public static final  String file = "images";
	public static DisplayImageOptions options;
	//����ԭͼ��������ѹ��
	public static Bitmap decodeBitmap(Context context,String ImagePath){
		InputStream is =null;
		try {
			assetManager = context.getAssets();
			is = assetManager.open(file  +"/"+ ImagePath);
			BitmapFactory.Options op = new BitmapFactory.Options();
			BitmapFactory.decodeStream(is, null, op);
			Bitmap bitmap =BitmapFactory.decodeStream(is, null, op);
			return bitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	/**
	 * ͨ�������ҵ�����,˳����������Ĵ�С��ͼƬ��С�����¸ı䡣��ֹ�ڴ������
	 * @param context ������
	 * @param ImagePath ͼƬ��ַ
	 * @param HopeWidth ������
	 * @param HopeHeight ������
	 * @return
	 */
	public static Bitmap decodeBitmap(Context context,String ImagePath,int HopeWidth, int HopeHeight){
		InputStream is =null;
		try {
			assetManager = context.getAssets();
			
			is = assetManager.open(file  +"/"+ ImagePath);
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, op);
			op.inSampleSize = ComputeInSampleSize(op, HopeWidth, HopeHeight);
			op.inJustDecodeBounds = false;
			Bitmap bitmap =BitmapFactory.decodeStream(is, null, op);
			return bitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	/**
	 * �������ű�����
	 * @param op 
	 * @param HopeW 
	 * @param HopeH
	 * @return
	 */
	public static int ComputeInSampleSize(BitmapFactory.Options op,int HopeW,int HopeH){
		final int RealW = op.outWidth;
		int RealH = op.outHeight;
		int inSampleSize = 1;
		if (RealH > HopeH || RealW > HopeW) {
			int scaleH = Math.round((float)RealH / (float)HopeH);
			int scaleW = Math.round((float)RealW / (float)HopeW);
			inSampleSize =  scaleH < scaleW ? scaleW : scaleH;
		}
		return inSampleSize;
	}

	public static DisplayImageOptions getOption(){
		options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.showImageOnLoading(R.drawable.maimob)
		.showImageForEmptyUri(R.drawable.maimob)
		.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		return options;
	}
}
