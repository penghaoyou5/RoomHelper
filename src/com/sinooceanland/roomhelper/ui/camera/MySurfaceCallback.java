package com.sinooceanland.roomhelper.ui.camera;

import java.io.IOException;
import java.util.List;

import com.sinooceanland.roomhelper.ui.camera.util.CamParaUtil;
import com.sinooceanland.roomhelper.ui.camera.util.FileUtil;
import com.sinooceanland.roomhelper.ui.camera.util.ImageUtil;
import com.sinooceanland.roomhelper.ui.utils.BitmapUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;


public class MySurfaceCallback implements android.view.SurfaceHolder.Callback, PictureCallback {

    CameraSurfaceView surfaceView = null;
    private Camera mCamera;
    private static final String TAG = "xiao";
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private float mPreviwRate = -1f;
    private ICameraFinish iCameraFinish;
    SurfaceHolder mSurfaceHolder;

    public MySurfaceCallback(CameraSurfaceView surfaceView, ICameraFinish iCameraFinish) {
        this.surfaceView = surfaceView;
        this.iCameraFinish = iCameraFinish;
        mSurfaceHolder = surfaceView.getSurfaceHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i(TAG, "doStartPreview...");
        try {
            mCamera = Camera.open(); // 打开摄像头
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        mParams = mCamera.getParameters();
        mParams.setPictureFormat(ImageFormat.JPEG);//设置拍照后存储的图片格式
       // CamParaUtil.getInstance().printSupportPictureSize(mParams);
       // CamParaUtil.getInstance().printSupportPreviewSize(mParams);
        //设置PreviewSize和PictureSize
        Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
                mParams.getSupportedPictureSizes(),
                surfaceView.previewRate,
                800);
        mParams.setPictureSize(pictureSize.width, pictureSize.height);


        Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
                mParams.getSupportedPreviewSizes(),
                surfaceView.previewRate,
                800);
        mParams.setPreviewSize(previewSize.width, previewSize.height);

        mCamera.setDisplayOrientation(0);
       // mParams.setRotation(90);

        //-----------
        CamParaUtil.getInstance().printSupportFocusMode(mParams);
        List<String> focusModes = mParams.getSupportedFocusModes();
        if (focusModes.contains("continuous-video")) {
            mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        if (focusModes.contains("auto")) {
            mCamera.cancelAutoFocus();
        }
        mCamera.setParameters(mParams);

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();//开启预览
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        isPreviewing = true;
        mPreviwRate = surfaceView.previewRate;

        mParams = mCamera.getParameters(); //重新get一次
        Log.i(TAG, "最终设置:PreviewSize--With = " + mParams.getPreviewSize().width
                + "Height = " + mParams.getPreviewSize().height);
        Log.i(TAG, "最终设置:PictureSize--With = " + mParams.getPictureSize().width
                + "Height = " + mParams.getPictureSize().height);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mPreviwRate = -1f;
            mCamera.release();
            mCamera = null;
        }
    }

    //========================进行拍照的回调
    private String picName;

    public void doTakePicture(String picName) {
        this.picName = picName;
        if (isPreviewing && (mCamera != null)) {
            mCamera.takePicture(null, null, this);
        }
    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (null != data) {
            mCamera.stopPreview();
            isPreviewing = false;
            BitmapUtils.saveBitmap(data, picName);
            iCameraFinish.cameraFinish();
            mCamera.startPreview();
            isPreviewing = true;
        }
		/*//保存图片到sdcard
		if(null != b)
		{
			设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
			图片竟然不能旋转了，故这里要旋转下
			Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);
			FileUtil.saveBitmap(rotaBitmap,path);

		}*/
        //再次进入预览
    }

    public Camera getCamera(){
        return  mCamera;
    }


}
