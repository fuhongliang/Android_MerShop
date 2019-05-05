package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
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
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_y_jiesuan)
    TextView tvYJiesuan;
    int y_jiesuan = 0;//int类型数据返回(从上一个页面抛出、这个页面首先就得接收)
    String bank_type;//string类型
    String account_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        ButterKnife.bind(this);
        initData();                     //下面的方法调用上来
        tvTitle.setText("提现");
    }


    public void initData() {
        y_jiesuan = getIntent().getIntExtra("jiesuan", 0);//接口命名 = getIngent().getingExtra(ing类型)("自定义名称",默认为0)
        bank_type = getIntent().getStringExtra("bank_type");//接口命名 = getIntent().getStringExtra("自定义名称")
        account_number = getIntent().getStringExtra("account_number");
        tvYJiesuan.setText(y_jiesuan+"");//设置它显示出来、强制转换为String类型
        tvBankType.setText(bank_type);
        tvBankNumber.setText(account_number);
    }

    public void withdrawMoney() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).pdCashAdd(UserLogic.getUser().getStore_id() + "", etMoney.getText().toString())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                startActivity(new Intent(WithdrawActivity.this, WithdrawSuccessActivity.class));
            }

        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        withdrawMoney();
    }

    @OnClick(R.id.tv_money)
    public void onTvMoneyClicked() {
        etMoney.setText(y_jiesuan+"");
    }
}
