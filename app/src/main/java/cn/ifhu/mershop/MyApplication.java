package cn.ifhu.mershop;

import android.app.Application;
import android.util.Log;

import com.bugtags.library.Bugtags;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

import cn.ifhu.mershop.base.AppInfo;
import cn.ifhu.mershop.utils.IrReference;
import cn.ifhu.mershop.utils.ProductLogic;

import static cn.ifhu.mershop.utils.Constants.DEVICETOKEN;
import static cn.ifhu.mershop.utils.Constants.TAGBUGKEY;
import static cn.ifhu.mershop.utils.Constants.USER;


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
        initUmeng();
    }


    public void initUmeng() {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5cc026e64ca357afec000039", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "c3ec2468e9b0f55a1c49a92d73cbb31d");

        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //服务端控制声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("mPushAgent", "注册成功：deviceToken：-------->  " + deviceToken);
                IrReference.getInstance().saveString(DEVICETOKEN, deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("mPushAgent", "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

    }
}
