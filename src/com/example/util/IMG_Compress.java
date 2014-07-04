package com.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

public class IMG_Compress {
	public static String Compress(String imgPath, int reqWidth) throws Exception{
		Bitmap bitmap = BitmapOptions.decodeSampledBitmapFromFile(imgPath, reqWidth, 0);
		int reqHeight = BitmapOptions.getWH(imgPath, reqWidth);
		Log.i("FFF", reqHeight + "  IMG  " + reqWidth);
		Bitmap currBmp = compressImage(Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, false));
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
			System.gc();
		}
		return saveImg(currBmp, MD5Utils.md5(timeStamp));
	}
	
	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 90;
		Log.i("FFF", "len  " + baos.toByteArray().length / 1024);
		while (baos.toByteArray().length / 1024 > 100) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			options -= 10;// ÿ�ζ�����10
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			Log.i("FFF", "len~~~~~  " + options);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}
	
	/**
	 * 
	 * @param b Bitmap
	 * @return ͼƬ�洢��λ��
	 * @throws FileNotFoundException 
	 */
	public static String saveImg(Bitmap b,String name) throws Exception{
		String path = Environment.getExternalStorageDirectory().getPath()+File.separator+"Mai/";
		File mediaFile = new File(path + File.separator + name + ".jpg");
		if(mediaFile.exists()){
			mediaFile.delete();
			
		}
		if(!new File(path).exists()){
			new File(path).mkdirs();
		}
		mediaFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(mediaFile);
		b.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
		b.recycle();
		b = null;
		System.gc();
		return mediaFile.getPath();
	}
}
