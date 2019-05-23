package cn.ifhu.mershop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.TestBean;
import cn.ifhu.mershop.net.MeService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.GsonUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_phone);

        setLoadingMessageIndicator(true);
        TestBean testBean = new TestBean();
        testBean.setUserid("111111");
        testBean.setInvite_code("code");
        testBean.setCompany("company");
        testBean.setPassword("111111");
        testBean.setName("111111");

        RetrofitAPIManager.create(MeService.class).register(RequestBody.create(MediaType.parse("application/json"), GsonUtils.convertObject2Json(testBean)))
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                Logger.d(t);
            }
        });
    }


}
