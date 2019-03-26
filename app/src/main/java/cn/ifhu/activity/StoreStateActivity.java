package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.Button;
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
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class StoreStateActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_store_state)
    TextView tvStoreState;
    @BindView(R.id.tv_store_time)
    TextView tvStoreTime;
    @BindView(R.id.btn_change_state)
    Button btnChangeState;
    @BindView(R.id.iv_state)
    ImageView ivState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_status);
        ButterKnife.bind(this);
        setBtnChangeState(UserLogic.getUser().getStore_state());
        tvTitle.setText("营业状态");
        tvStoreTime.setText("营业时间:" + UserLogic.getUser().getWork_start_time() + "~" + UserLogic.getUser().getWork_end_time());
    }

    public void setBtnChangeState(int state) {
        if (state == 0) {
            btnChangeState.setText("开始营业");
            tvStoreState.setText("已停止营业");
            ivState.setBackgroundResource(R.drawable.yingye_ic_weiyingye);
        } else {
            btnChangeState.setText("停止营业");
            tvStoreState.setText("正常开业中");
            ivState.setBackgroundResource(R.drawable.personal_ic_yyzt);
        }
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.btn_change_state)
    public void onBtnChangeStateClicked() {
        changeState();
    }

    public void changeState() {
        setLoadingMessageIndicator(true);
        int state = UserLogic.getUser().getStore_state() == 0 ? 1 : 0;
        RetrofitAPIManager.create(MeService.class).storeSetWorkstate(UserLogic.getUser().getStore_id(), state)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
                loginResponse.setStore_state(state);
                UserLogic.saveUser(loginResponse);
                setBtnChangeState(state);
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }
}
