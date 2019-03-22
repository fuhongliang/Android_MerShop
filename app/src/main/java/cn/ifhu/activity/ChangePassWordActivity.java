package cn.ifhu.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

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
public class ChangePassWordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private Timer mTimer;
    private int mOriSecond = 5;
    private int mCurSecond;
    private int mCodeLength = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        tvTitle.setText("修改密码");
        tvPhone.setText(UserLogic.getUser().getMember_mobile());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        setLoadingMessageIndicator(true);
        UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
        RetrofitAPIManager.create(MeService.class).getSms(loginResponse.getMember_mobile()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                showCountDown();
            }
        });
    }

    private void showCountDown() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mCurSecond--;
                if (mCurSecond < 1) {
                    this.cancel();
                    runOnUiThread(() -> {
                        tvGetCode.setText("获取验证码");
                    });
                    mCurSecond = mOriSecond;
                } else {
                    String sencond = mCurSecond + "重新获取";
                    runOnUiThread(() -> {
                        tvGetCode.setText(sencond);
                    });
                }
            }
        }, 0, 1000);
    }

    @OnClick(R.id.btn_ok)
    public void onBtnOkClicked() {
        if (checkPassWord()) {
            setLoadingMessageIndicator(true);
            UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
            RetrofitAPIManager.create(MeService.class).editPasswd(loginResponse.getMember_id()+"", loginResponse.getMember_mobile(), code, newPassWord, newPassWordAgain)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                }
            });
        }
    }

    String newPassWord = "";
    String newPassWordAgain = "";
    String code = "";

    public boolean checkPassWord() {
        newPassWord = etNewPassword.getText().toString().trim();
        newPassWordAgain = etPasswordAgain.getText().toString().trim();
        code = etCode.getText().toString().trim();

        if (StringUtils.isEmpty(code)) {
            return false;
        }

        if (StringUtils.isEmpty(newPassWord) || newPassWord.length() < mCodeLength) {
            return false;
        }

        if (!newPassWord.equals(newPassWordAgain)) {
            return false;
        }
        return true;
    }

}
