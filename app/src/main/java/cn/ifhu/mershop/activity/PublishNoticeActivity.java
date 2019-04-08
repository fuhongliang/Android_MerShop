package cn.ifhu.mershop.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.UserServiceBean;
import cn.ifhu.mershop.net.MeService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class PublishNoticeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_notice)
    EditText etNotice;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_announcement);
        ButterKnife.bind(this);
        setStoreNotice();
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    public void setStoreNotice(){
        etNotice.setText(UserLogic.getUser().getStore_description());
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (!isNoticeEmpty()){
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(MeService.class).storeSetDesc(UserLogic.getUser().getStore_id(),etNotice.getText().toString().trim())
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
                    loginResponse.setStore_description(etNotice.getText().toString().trim());
                    UserLogic.saveUser(loginResponse);
                    setStoreNotice();
                    ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                }
            });
        }

    }

    public boolean isNoticeEmpty(){
        return StringUtils.isEmpty(etNotice.getText().toString().trim());
    }
}
