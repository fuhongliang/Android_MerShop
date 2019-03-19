package cn.ifhu;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

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
        instance = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

}
