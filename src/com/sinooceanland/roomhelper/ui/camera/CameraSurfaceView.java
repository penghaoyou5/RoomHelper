package com.sinooceanland.roomhelper.ui.camera;


import com.sinooceanland.roomhelper.ui.camera.util.DisplayUtil;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;

public class CameraSurfaceView extends SurfaceView {
	CameraSurfaceView surfaceView = null;
	float previewRate = -1f;
	private Context context;
	private MySurfaceCallback mySurfaceCallback;

	public CameraSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context =context;
		init();
	}

	public CameraSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context =context;
		init();
	}
	SurfaceHolder mSurfaceHolder;


	public Camera getCamera(){
		return  mySurfaceCallback.getCamera();
	}

	public SurfaceHolder getSurfaceHolder(){
		return mSurfaceHolder;
	}
	private void init(){
		mSurfaceHolder = getHolder();
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}


	public void initConfig(ICameraFinish iCameraFinish) {
		initDate(iCameraFinish);
		initViewParams();
	}
	
	private void initDate(ICameraFinish iCameraFinish) {
		surfaceView = this;
		mySurfaceCallback = new MySurfaceCallback(this,iCameraFinish);
		surfaceView.getHolder().addCallback(mySurfaceCallback);
	}
	
	private void initViewParams(){
		LayoutParams params = surfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(context);
		params.width = p.x;
		params.height = p.y;
		previewRate = DisplayUtil.getScreenRate(context); //默认全屏的比例预览
		surfaceView.setLayoutParams(params);
	}

	/*
	 * 拍照的方法
	 */
	public void doTakePicture(String picName){
		mySurfaceCallback.doTakePicture( picName);
	}
}
