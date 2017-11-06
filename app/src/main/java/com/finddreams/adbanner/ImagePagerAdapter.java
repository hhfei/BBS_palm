/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.finddreams.adbanner;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hh.bbs_palm.R;

//import com.example.palm_bbs.R;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ImagePagerAdapter extends BaseAdapter {

	private Context context;
	private List<Integer> imageID;
	//	private List<String> linkUrlArray;
	private int size;
	private boolean isInfiniteLoop;
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;

	public ImagePagerAdapter(Context context, List<Integer> imageID) {
		this.context = context;
		this.imageID = imageID;
		if (imageID != null) {
			this.size = imageID.size();
		}
//		this.linkUrlArray = urllist;
//		this.urlTitlesList = urlTitlesList;
		isInfiniteLoop = false;
		// 鍒濆鍖杋mageLoader 鍚﹀垯浼氭姤閿�//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//		options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.ic_launcher) // 璁剧疆鍥剧墖涓嬭浇鏈熼棿鏄剧ず鐨勫浘鐗�//				.showImageForEmptyUri(R.drawable.meinv) // 璁剧疆鍥剧墖Uri涓虹┖鎴栨槸閿欒鐨勬椂鍊欐樉绀虹殑鍥剧墖
//				.showImageOnFail(R.drawable.meinv) // 璁剧疆鍥剧墖鍔犺浇鎴栬В鐮佽繃绋嬩腑鍙戠敓閿欒鏄剧ず鐨勫浘鐗�//				.cacheInMemory(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪鍐呭瓨涓�////				.cacheOnDisc(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪SD鍗′腑
//				.build();

	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : imageID.size();
	}

	/**
	 * get really position
	 *
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		return isInfiniteLoop ? position % size : position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container) {
		final ViewHolder holder;
		if (view == null) {
//			holder = new ViewHolder();
//			view = holder.imageView = new ImageView(context);
//			holder.imageView
//					.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
//			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//			view.setTag(holder);

			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.viewpaper_item ,null);
			holder.imageView=(ImageView)view.findViewById(R.id.iv_1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.imageView.setImageResource(imageID.get(getPosition(position)));
//		holder.imageView.setBackgroundResource(imageID.get(getPosition(position)));

//		holder.imageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				String url = linkUrlArray.get(ImagePagerAdapter.this
//						.getPosition(position));
//				String title = urlTitlesList.get(ImagePagerAdapter.this
//						.getPosition(position));
				/*
				 * if (TextUtils.isEmpty(url)) {
				 * holder.imageView.setEnabled(false); return; }
				 */
//				Bundle bundle = new Bundle();
//
//				bundle.putString("url", url);
//				bundle.putString("title", title);
//				Intent intent = new Intent(context, BaseWebActivity.class);
//				intent.putExtras(bundle);
//
//				context.startActivity(intent);
//				Toast.makeText(context, "鐐瑰嚮浜嗙" + getPosition(position) + "缇庡コ",
//						0).show();
//
//			}
//		});

		return view;
	}

	private static class ViewHolder {

		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
