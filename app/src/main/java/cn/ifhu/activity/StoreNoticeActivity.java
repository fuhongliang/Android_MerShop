package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.net.MeService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.StringUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class StoreNoticeActivity extends BaseActivity {

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
        tvTitle.setText("餐厅公告");
        etNotice.setText(UserLogic.getUser().getStore_description()+"");
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        goBack();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (!StringUtils.isEmpty(etNotice.getText().toString().trim())) {
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(MeService.class).storeSetDesc(UserLogic.getUser().getStore_id(),etNotice.getText().toString().trim())
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Object> t) throws Exception {
                    ToastHelper.makeText(t.getMessage()+"", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                    UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
                    loginResponse.setStore_description(etNotice.getText().toString().trim());
                    UserLogic.saveUser(loginResponse);
                }
            });
        }
    }

}
