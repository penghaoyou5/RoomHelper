package com.sinooceanland.roomhelper.control.util;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageLoaderUtil {
	
	/**
	 * 获取imageLoader
	 * 
	 * @return
	 */
	public static ImageLoader getImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPoolSize(6)
				// 线程池内加载的数�?
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(8 * 1024 * 1024))
				.memoryCacheSize(8 * 1024 * 1024).build();// �?��构建
		ImageLoader loader = ImageLoader.getInstance();
		loader.init(config);
		return loader;
	}

	

	/**
	 * R.drawable.avatar_default
	 * R.drawable.default_pic
	 * @param type
	 * @return
	 */
	public static DisplayImageOptions getDisplayImageOptions(int type) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(type)
					// 设置图片在下载期间显示的图片
					.showImageForEmptyUri(type)
					// 设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(type)
					// 设置图片加载/解码过程中错误时候显示的图片
					.cacheInMemory(true)
					// 设置下载的图片是否缓存在内存�?
					.cacheOnDisc(true)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
					// 设置图片以如何的编码方式显示
					.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类�?/
					.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位 �?
					.build();// 构建完成
		return options;
	}


}
