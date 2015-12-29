package com.sinooceanland.roomhelper.control.net;
import com.sinooceanland.roomhelper.control.base.BaseNet.BaseCallBack;
import com.sinooceanland.roomhelper.control.base.BaseNet.RequestType;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.net.RequestNet;

import android.test.AndroidTestCase;
import android.widget.Toast;


public class testTTT extends AndroidTestCase {

		public void testLogin() throws Exception{
			System.out.println("ggggggggfffui");
			 RequestNet requestNet = new RequestNet(mContext);
			 requestNet.login("v-gouying", "", new BaseCallBack<LoginBean>() {
				
				@Override
				public void messageResponse(RequestType requestType, LoginBean bean,
						String message) {
					System.out.println("gggggg"+bean.userID);
					Toast.makeText(mContext, bean.userID, 1).show();
				}
			});
		}
	
	
	
//	 public void testAdd() throws Exception {  
//	       RequestNet requestNet = new RequestNet(mContext);
//	       requestNet.downTask(mContext, null, new BaseCallBack<String>(){
//
//			@Override
//			public void messageResponse(RequestType requestType, String bean,
//					String message) {
//				System.out.println("sunn");
//			}
//	    	   
//	       }, null);
//	    }  
}
