package cn.ifhu.mershop.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class AccountAndSafeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_change_password)
    RelativeLayout rlChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountandsafe);
        ButterKnife.bind(this);
        tvTitle.setText("账号与安全");
        tvPhone.setText(UserLogic.getUser().getMember_mobile());
        tvName.setText(UserLogic.getUser().getMember_name());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_change_password)
    public void onRlChangePasswordClicked() {
        startActivity(new Intent(AccountAndSafeActivity.this,ChangePassWordActivity.class));
    }
}
