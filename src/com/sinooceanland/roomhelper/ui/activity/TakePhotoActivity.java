package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.support.v4.view.ViewPager;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData.*;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.ui.UIContacts;
import com.sinooceanland.roomhelper.ui.adapter.MyViewPagerAdapter;
import com.sinooceanland.roomhelper.ui.camera.CameraSurfaceView;
import com.sinooceanland.roomhelper.ui.camera.ICameraFinish;
import com.sinooceanland.roomhelper.ui.utils.BitmapUtils;
import com.sinooceanland.roomhelper.ui.adapter.MyViewPagerAdapter.ViewHolder;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/18.
 * Version : 1
 * Details :
 */
public class TakePhotoActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener,ICameraFinish {


    private CameraSurfaceView mSurface;

    public void test(){
//获得布局列表
        HouseMessageData houseMessageData = HouseMessageData.getInstance();
        SpaceLayoutListHelper spaceHelper = houseMessageData.getSpaceLayoutList();
        List<SpaceLayoutList> spaceLayoutList = spaceHelper.getSpaceLayoutList();
        SpaceLayoutList layout = spaceLayoutList.get(0);

        //添加问题
        ProbleamInfo probleamInfo = new ProbleamInfo();
        probleamInfo.EnginTypeCode = "第一层的问题编码";
        probleamInfo.ProblemDescriptionCode = "第三层问题编码";
        probleamInfo.ProblemDescriptionName = "问题描述";

        //获取图片名字 项目名称_房间全名_布局code
        String pictureName =  HouseMessageData.getPictrueName(String.valueOf(layout.getSpaceLayoutCode()),true);
        //添加照片
        //spaceHelper.newInstancePictureInfo(1,);

        PictureInfo pictureInfo1 = spaceHelper.addPictureInfoOrModify(1, pictureName, true, probleamInfo);
        //获取照片目录
        SpKey.getBigPictureAddress();
        SpKey.getSmallPictureAddress();

        //通过布局获取图片bean
        int position = 0;//当前布局位置
        List<PictureInfo> pictureInfos = spaceHelper.getPotion(position);
        PictureInfo pictureInfo = pictureInfos.get(0);
        pictureInfo.getBigPictureUri();
        pictureInfo.getSmallPictureUri();
        boolean isSure = pictureInfo.isSure();


        //删除图片

    }

    private TextView tv_center;
    private TextView tv_right;
    private TextView tv_delete;
    private TextView tv_left;
    private ViewPager vp_content;
    private TextView tv_title_complete;
    private TextView tv_title_left;
    private TextView tv_title_right;

    private MyViewPagerAdapter mAdapter;
    private static boolean isChongpai = false;

    public String getPictureName(boolean isPreUrl){
        return HouseMessageData.getPictrueName(String.valueOf(mLayoutList.get(mCurrentLayoutPosition).getSpaceLayoutCode()),isPreUrl);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
        initCamera();
        setViewPagerState();
    }

    private void initCamera() {
        mSurface = (CameraSurfaceView)findViewById(R.id.surface);
        mSurface.initConfig(this);
    }


    @Override
    public void cameraFinish() {
        vp_content.setVisibility(View.VISIBLE);
        if(isChongpai){
            isChongpai = false;
          //  PictureInfo pictureInfo = mPictureList.get(mCurrentPicPosition);
           /* Bitmap bitmap = getBitmapFromUrl(pictureInfo.getBigPictureUri());
            BitmapUtils.saveScalePhoto(bitmap,new File(pictureInfo.getSmallPictureUri()));*/
            mAdapter = new MyViewPagerAdapter(mPictureList,this);
            vp_content.setAdapter(mAdapter);
            vp_content.setCurrentItem(mCurrentPicPosition);
        }else {
            //1将数据压缩后保存下来 2将ui的data数据进行更新
            PictureInfo pictureInfo = mSpaceHelper.newInstancePictureInfo(mCurrentLayoutPosition, getPictureName(true));
            //Bitmap bitmap = BitmapUtils.getSmallBitmap(getPictureName(true));

            //BitmapUtils.saveScalePhoto(bitmap,getPictureName(true));
            //bitmap.recycle();

            mPictureList.add(pictureInfo);
            mAdapter.setDatas(mPictureList);
            vp_content.setCurrentItem(mPictureList.size() - 1);
            setBottomState(pictureInfo);
        }
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(300);
                isPhotoing = false;
            }
        }.start();
    }

    private SpaceLayoutListHelper mSpaceHelper;
    private List<SpaceLayoutList> mLayoutList;
    private int mCurrentLayoutPosition = 0;
    ArrayList<PictureInfo> mPictureList;
    private void initData() {
        //得到所有的布局
        HouseMessageData houseMessageData = HouseMessageData.getInstance();
        mSpaceHelper = houseMessageData.getSpaceLayoutList();
        mLayoutList = mSpaceHelper.getSpaceLayoutList();
        //获得第一个布局的照片列表
        mPictureList = getPictrueList(mCurrentLayoutPosition);
        mAdapter = new MyViewPagerAdapter(mPictureList, this);

    }

    private void initView() {
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        vp_content.setAdapter(mAdapter);
        vp_content.setOnPageChangeListener(this);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_title_complete = (TextView) findViewById(R.id.tv_title_complete);
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
       // initBottom();
        initTitleView();
    }


    private void initBottom() {
        List<PictureInfo> datas = mAdapter.getDatas();
        boolean hasPicture = datas.size()!=0;
        if(hasPicture){
            PictureInfo pictureInfo = datas.get(0);
            setBottomState(pictureInfo);
        }else {
            setBottomState(BottomState.miss);
        }
    }

    private void setBottomState(PictureInfo currentPicture){
        findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
        if(currentPicture .isSure()){
            setBottomState(BottomState.delete);
        }else if(currentPicture.getProblem()!=null){
            setBottomState(BottomState.update);
        }else {
            setBottomState(BottomState.normal);
        }
    }

    private void setBottomState(BottomState state){
        switch (state){
            case update:
                findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.GONE);
                tv_center.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("修改问题");
                tv_delete.setVisibility(View.GONE);
                break;
            case normal:
                findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.VISIBLE);
                tv_center.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("添加问题");
                tv_delete.setVisibility(View.GONE);
                break;
            case delete:
                findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.GONE);
                tv_center.setVisibility(View.GONE);
                tv_right.setVisibility(View.GONE);
                tv_delete.setVisibility(View.VISIBLE);
                break;
            case miss:
                findViewById(R.id.rl_bottom).setVisibility(View.GONE);
                break;
        }
    }

    private void setViewPagerState(){
        List<PictureInfo> list = mAdapter.getDatas();
        if(list==null || list.size()==0){
            vp_content.setVisibility(View.INVISIBLE);
            setBottomState(BottomState.miss);
        }else {
            vp_content.setVisibility(View.VISIBLE);
            if(list.size()>1){
                vp_content.setCurrentItem(list.size()-1);
            }
            setBottomState(mPictureList.get(mCurrentPicPosition));
        }
    }

    private enum BottomState{
        update,delete,normal,miss
    }

    /**
     * 获取布局里的图片集合
     * @param position
     * @return
     */
    public ArrayList<PictureInfo> getPictrueList(int position){
        return (ArrayList<PictureInfo>) mSpaceHelper.getPotion(position);
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView titleTv = (TextView) findViewById(R.id.tv_title_name);
        titleTv.setText(title);
    }

    private void initListener() {
        findViewById(R.id.tv_left).setOnClickListener(this);
        findViewById(R.id.tv_center).setOnClickListener(this);
        findViewById(R.id.tv_right).setOnClickListener(this);
        findViewById(R.id.ib_back).setOnClickListener(this);
        findViewById(R.id.ib_take_photo).setOnClickListener(this);
        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.tv_title_left).setOnClickListener(this);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        findViewById(R.id.tv_delete).setOnClickListener(this);
        findViewById(R.id.tv_title_complete).setOnClickListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_take_photo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_left://上一布局
                mCurrentLayoutPosition--;
                mCurrentPicPosition=0;
                mPictureList = getPictrueList(mCurrentLayoutPosition);
                mAdapter = new MyViewPagerAdapter(mPictureList,this);
                vp_content.setAdapter(mAdapter);

                initTitleView();
                setViewPagerState();
                break;

            case R.id.tv_title_right://下一布局
                //TODO 遍历所有图片的确定状态和添加问题状态
                if(!isCurrentLayoutSure())return;
                mCurrentLayoutPosition++;
                mCurrentPicPosition=0;
                mPictureList = getPictrueList(mCurrentLayoutPosition);
                mAdapter = new MyViewPagerAdapter(mPictureList,this);
                vp_content.setAdapter(mAdapter);
                initTitleView();
                setViewPagerState();
                break;

            case R.id.tv_left://重拍
                isChongpai = true;
                vp_content.setVisibility(View.INVISIBLE);
                setBottomState(BottomState.miss);
                break;
            case R.id.tv_center://确定
                setBottomState(BottomState.delete);
                mPictureList.get(mCurrentPicPosition).setSure(true);
                vp_content.setVisibility(View.INVISIBLE);
                setBottomState(BottomState.miss);
                isChongpai = false;
                break;
            case R.id.tv_right://添加问题
                startActivityForResult(new Intent(this, TreeActivity.class), UIContacts.REQUEST_CODE_QUESTION);
                break;
            case R.id.ib_back:
                startActivity(new Intent(this, ChooseBuildingActivity.class));
                finish();
                break;
            case R.id.ib_take_photo://拍照
                if(mAdapter.getDatas().size()!=0 &&  vp_content.getVisibility() == View.VISIBLE){
                    if(!currentPicIsSure())return;
                    vp_content.setVisibility(View.INVISIBLE);
                    setBottomState(BottomState.miss);
                    return;
                }
                if(isChongpai){
                    PictureInfo pictureInfo = mPictureList.get(mCurrentPicPosition);
                    takePhoto(pictureInfo.getPictureUri());
                    setBottomState(pictureInfo);
                }else {
                    if(!currentPicIsSure())return;
                    takePhoto(getPictureName(false));
                }
                break;
            case R.id.tv_add://查看布局照片
                vp_content.setVisibility(View.VISIBLE);
                if(mPictureList==null || mPictureList.size()==0){
                    setBottomState(BottomState.miss);
                }else {
                    setBottomState(mPictureList.get(mCurrentPicPosition));
                }
                /*if(!currentPicIsSure())return;
                takePhoto(getPictureName(false));*/
                break;
            case R.id.tv_delete://删除当前照片， 删除bean里数据 删除本地文件
                mSpaceHelper.deletePicture(mCurrentLayoutPosition, mPictureList.get(mCurrentPicPosition));
                mPictureList.remove(mCurrentPicPosition);
                mAdapter = new MyViewPagerAdapter(mPictureList,this);
                vp_content.setAdapter(mAdapter);
                mCurrentPicPosition = 0;
                initBottom();
                break;
            case R.id.tv_title_complete:
                if(!isCurrentLayoutSure())return;
                HouseMessageData houseMessageData = HouseMessageData.getInstance();
                houseMessageData.setCheckStautsSure();
                startActivity(new Intent(this,ChooseBuildingActivity.class));
                finish();
                break;
        }
    }

    private void initTitleView(){
        setTitle(mLayoutList.get(mCurrentLayoutPosition).getSpaceLayoutName());
        if(mLayoutList.size()==1){
            tv_title_left.setVisibility(View.GONE);
            tv_title_complete.setVisibility(View.VISIBLE);
            tv_title_right.setVisibility(View.GONE);
        }else {
            if(mCurrentLayoutPosition==0){  //在第一个
                tv_title_left.setVisibility(View.GONE);
                tv_title_complete.setVisibility(View.GONE);
                tv_title_right.setVisibility(View.VISIBLE);
            }else if(mCurrentLayoutPosition==mLayoutList.size()-1){//在最后
                tv_title_left.setVisibility(View.VISIBLE);
                tv_title_complete.setVisibility(View.VISIBLE);
                tv_title_right.setVisibility(View.GONE);
            }else {//在中间
                tv_title_left.setVisibility(View.VISIBLE);
                tv_title_complete.setVisibility(View.GONE);
                tv_title_right.setVisibility(View.VISIBLE);
            }

        }
    }

    private boolean isCurrentLayoutSure() {
        int i = 0;//用来记录没有添加问题照片的个数
        //1 必须有照片
        if(mPictureList==null ||mPictureList.size()==0){
            showToast("请添加照片");
            return false;
        }
        for(PictureInfo pic:mPictureList){
            //2 照片必须都是确定的
            if(!pic.isSure()){
                showToast("请先确定布局内照片");
                return false;
            }
            if(pic.getProblem()==null){
                i++;
            }
        }
        //3有且只有一张没有问题的照片
        if(i==0){
            showToast("必须有一张没有问题照片");
            return false;
        }
        if(i>1){
            showToast("只能有一张没有问题照片");
            return false;
        }
        if(i!=1){
            showToast("有且只有一张没有问题照片");
            return false;
        }else {
            return true;
        }

    }

    private boolean currentPicIsSure(){
        if(mPictureList.size()==0)return true;
        for(PictureInfo pic:mPictureList){
            if(!pic.isSure()){
                showToast("请确认照片,再进行拍照");
                return false;
            }
        }
        return true;
    }
    private boolean isPhotoing = false;
    public void takePhoto(String picName) {//拍照
        if(isPhotoing)return;
        isPhotoing = true;
        mSurface.doTakePicture(picName);
       /*
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//要打开的应用
        File out = new File(path,fileName);
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,//设置拍照存储路径
                uri);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, UIContacts.REQUEST_CODE_CARMER);//回调里拿数据*/
    }

  /*  public void takePhoto(String path) {//重拍
        mSurface.doTakePicture(path);
      *//*  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//要打开的应用
        File out = new File(path);
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,//设置拍照存储路径
                uri);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, UIContacts.REQUEST_CODE_CARMER);//回调里拿数据*//*
    }*/

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = BitmapUtils.createBitmapThumbnail(url);
        return bitmap;
    }


//-----------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case UIContacts.REQUEST_CODE_CARMER://拍照后的回调
                    if (RESULT_OK == resultCode) {
                        if(isChongpai){
                            isChongpai = false;
                            PictureInfo pictureInfo = mPictureList.get(mCurrentPicPosition);
                            Bitmap bitmap = getBitmapFromUrl(pictureInfo.getBigPictureUri());
                            BitmapUtils.saveScalePhoto(bitmap,new File(pictureInfo.getSmallPictureUri()));
                            mAdapter = new MyViewPagerAdapter(mPictureList,this);
                            vp_content.setAdapter(mAdapter);
                            vp_content.setCurrentItem(mCurrentPicPosition);
                        }else {
                            //1将数据压缩后保存下来 2将ui的data数据进行更新
                            PictureInfo pictureInfo = mSpaceHelper.newInstancePictureInfo(mCurrentLayoutPosition,getPictureName(true));
                            Bitmap bitmap = getBitmapFromUrl(SpKey.getBigPictureAddress()+getPictureName(true));

                            BitmapUtils.saveScalePhoto(bitmap,getPictureName(true));
                            bitmap.recycle();

                            mPictureList.add(pictureInfo);
                            mAdapter.setDatas(mPictureList);
                            vp_content.setCurrentItem(mPictureList.size() - 1);
                            setBottomState(pictureInfo);
                        }

                    }
                    break;
                case (UIContacts.REQUEST_CODE_QUESTION)://选择问题回调
                    if (RESULT_OK == resultCode) {
                        String question3 = data.getStringExtra("question3");
                        String questionCode3 = data.getStringExtra("questionCode3");
                        String question1 = data.getStringExtra("question1");
                        //String questionCode1 = data.getStringExtra("questionCode1");
                        ProbleamInfo probleamInfo = new ProbleamInfo();
                        probleamInfo.ProblemDescriptionName = question3;
                        probleamInfo.ProblemDescriptionCode = questionCode3;
                        probleamInfo.EnginTypeCode = question1;

                        PictureInfo pictureInfo = mPictureList.get(getCurrentPosition());
                        pictureInfo.setProblem(probleamInfo);
                        mAdapter.setDatas(mPictureList);
                        setText(question3);
                        setBottomState(pictureInfo);
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, ChooseBuildingActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //----------滑动监听
    private int mCurrentPicPosition = 0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPicPosition = position;
       setBottomState( mPictureList.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void finish() {
        super.finish();
        TaskMyssageData.saveModifyBigJson();
        vp_content.setAdapter(null);
        mAdapter = null;
    }

    public void setText(String question){
        int currentPosition = getCurrentPosition();
        Log.e("test", "currentPosition" + currentPosition);

        ViewHolder holder = (ViewHolder) getCurrentView().getTag();
        holder.tv.setText(question);
        holder.tv.setVisibility(View.VISIBLE);
    }

    /**
     * 获取当前Viewpanger的View
     * @return
     */
    private View getCurrentView() {
        return vp_content.findViewById(getCurrentPosition());
    }

    public int getCurrentPosition(){
       return   vp_content.getCurrentItem();
    }
}