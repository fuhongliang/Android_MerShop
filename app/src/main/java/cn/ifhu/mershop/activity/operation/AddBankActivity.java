package cn.ifhu.mershop.activity.operation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class AddBankActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_bank_number)
    EditText etBankNumber;
    @BindView(R.id.et_bank_account)
    EditText etBankAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        tvTitle.setText("添加银行卡");

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.et_name)
    public void onEtNameClicked() {
    }

    @OnClick(R.id.et_bank_number)
    public void onEtBankNumberClicked() {

    }

    @OnClick(R.id.et_bank_account)
    public void onEtBankAccountClicked() {
    }

}
