package cn.ifhu.mershop;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.ifhu.mershop.base.AppInfo;
import cn.ifhu.mershop.utils.IrReference;
import cn.ifhu.mershop.utils.ProductLogic;

import static cn.ifhu.mershop.utils.Constants.TAGBUGKEY;


/**
 * @author fuhongliang
 */
public class MyApplication extends Application {
    public static MyApplication instance;

    public static MyApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppInfo.init(getApplicationContext());
        instance = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        IrReference.getInstance();
        //在这里初始化
        Bugtags.start(TAGBUGKEY, this, Bugtags.BTGInvocationEventBubble);
        ProductLogic.clearDiscountGoods();

    }

}
