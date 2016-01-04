package com.sinooceanland.roomhelper.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sinooceanland.roomhelper.control.constant.SpKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jackson on 2015/12/21.
 * Version : 1
 * Details :
 */
public class BitmapUtils {

    /*
        * Compute the sample size as a function of minSideLength
        * and maxNumOfPixels.
        * minSideLength is used to specify that minimal width or height of a
        * bitmap.
        * maxNumOfPixels is used to specify the maximal size in pixels that is
        * tolerable in terms of memory usage.
        *
        * The function returns a sample size based on the constraints.
        * Both size and minSideLength can be passed in as IImage.UNCONSTRAINED,
        * which indicates no care of the corresponding constraint.
        * The functions prefers returning a sample size that
        * generates a smaller bitmap, unless minSideLength = IImage.UNCONSTRAINED.
        *
        * Also, the function rounds up the sample size to a power of 2 or multiple
        * of 8 because BitmapFactory only honors sample size this way.
        * For example, BitmapFactory downsamples an image by 2 even though the
        * request is 3. So we round up the sample size to avoid OOM.
        */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                               int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     *
     * @param filePath 传入图片地址
     * @return 返回压缩后的Bitmap
     */
    public static Bitmap createBitmapThumbnail(String filePath){
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);//将图片的宽高放到opts中
       // opts.inSampleSize = computeSampleSize(opts, -1,(int)0.5*width*height );
        //opts.inSampleSize = computeSampleSize(opts, -1,width*height );
        opts.inSampleSize = computeSampleSize(opts, -1,1024*1024 );
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        }catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

      /*
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();

        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
//        try {
//            ExifInterface exif = new ExifInterface(url);
//            String model = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 按照固定宽高进行缩放
        // 这里希望知道照片是横屏拍摄还是竖屏拍摄
        // 因为两种方式宽高不同，缩放效果就会不同
        // 这里用了比较笨的方式
        if(mWidth <= mHeight) {
            scaleWidth = (float) (width/mWidth);
            scaleHeight = (float) (height/mHeight);
        } else {
            scaleWidth = (float) (height/mWidth);
            scaleHeight = (float) (width/mHeight);
        }
//        matrix.postRotate(90); *//* 翻转90度 *//*
        // 按照固定大小对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
        // 用完了记得回收
        bitmap.recycle();
        return newBitmap;*/


    /**
     * 将路径下的图片转成bitmap
     * @param filePath
     * @return
     */
    public static Bitmap createBitmap(String filePath){
       return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 存储缩放的图片
     */
    public static void saveScalePhoto(Bitmap bitmap,String name) {
        // 文件夹路径
        FileOutputStream fos = null;
        File file =  new File(SpKey.getSmallPictureAddress(),name);
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void saveScalePhoto(Bitmap bitmap,File file) {
        // 文件夹路径
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean  deleteBitmap(String filePath){
        File file = new File(filePath);
        return file.delete();
    }

}
