package com.sinooceanland.roomhelper.control.util;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jackson on 2016/1/17.
 * Version : 1
 * Details :
 */
public class TimeUtil {
    public  interface BeijingTime{

        void  beijingtime(long lo);
    }

    public static void getBeijingTime(final BeijingTime time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long ld =  getBeijingTimeCon();
                    time.beijingtime(ld);
                }catch (Exception e){

                }
            }
        }).start();
    }


    private static long getBeijingTimeCon() throws Exception {
        URL url=new URL("http://www.bjtime.cn");//取得资源对象
        URLConnection uc=url.openConnection();//生成连接对象
        uc.connect(); //发出连接
        long ld=uc.getDate(); //取得网站日期时间
        return ld;
//        Date date=new Date(ld); //转换为标准时间对象
//        //分别取得时间中的小时，分钟和秒，并输出
//        System.out.print(date.getHours()+"时"+date.getMinutes()+"分"+date.getSeconds()+"秒");
    }
}
