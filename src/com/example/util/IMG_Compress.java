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
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 90;
		Log.i("FFF", "len  " + baos.toByteArray().length / 1024);
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			options -= 10;// 每次都减少10
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			Log.i("FFF", "len~~~~~  " + options);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	
	/**
	 * 
	 * @param b Bitmap
	 * @return 图片存储的位置
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
