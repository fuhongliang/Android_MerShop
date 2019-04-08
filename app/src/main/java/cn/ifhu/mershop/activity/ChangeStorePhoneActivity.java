package cn.ifhu.mershop.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
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
public class ChangeStorePhoneActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_phone);
        ButterKnife.bind(this);
        tvTitle.setText("餐厅电话");
        etPhone.setText(UserLogic.getUser().getStore_phone()+"");
    }

    public void changeStorePhone() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(MeService.class).storeSetPhone(UserLogic.getUser().getStore_id(),etPhone.getText().toString().trim())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {


            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage()+"", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
                loginResponse.setStore_phone(etPhone.getText().toString().trim());
                UserLogic.saveUser(loginResponse);
            }
        });
    }

    public void clearEdit(){
        etPhone.setText("");
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        goBack();
    }


    @OnClick(R.id.btn_feedback)
    public void onBtnFeedbackClicked() {
        if (checkContent()){
            changeStorePhone();
        }
    }

    public boolean checkContent(){
        if (StringUtils.isEmpty(etPhone.getText().toString().trim())){
            return false;
        }
        if (etPhone.getText().toString().trim().equals(UserLogic.getUser().getStore_phone())){
            return false;
        }
        return true;
    }
}
