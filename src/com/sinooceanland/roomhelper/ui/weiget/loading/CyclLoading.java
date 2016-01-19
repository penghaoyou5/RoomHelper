<<<<<<< HEAD
package com.sinooceanland.roomhelper.ui.weiget.loading;

import android.content.Context;

/**
 * Created by Jackson on 2016/1/14.
 * Version : 1
 * Details :
 */
public class CyclLoading implements InterLoading {

    private Context context;
    public CyclLoading(Context context){
        this.context =context;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
=======
package com.sinooceanland.roomhelper.ui.weiget.loading;

import android.content.Context;
import android.widget.PopupWindow;

import com.victor.loading.rotate.RotateLoading;

/**
 * Created by Jackson on 2016/1/14.
 * Version : 1
 * Details :
 */
public class CyclLoading implements InterLoading {

    private final RotateLoading mLoading;
    private Context context;
    public CyclLoading(Context context){
        this.context =context;
        mLoading = new RotateLoading(context);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
>>>>>>> bd7db19437929ed8e3a979a4de8392288bb9ee94
