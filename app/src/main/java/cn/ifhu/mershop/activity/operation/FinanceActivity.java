package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.FinanceBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class FinanceActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.iv_tongzhi)
    ImageView ivTongzhi;
    @BindView(R.id.rl_add_bank)
    RelativeLayout rlAddBank;
    @BindView(R.id.tv_not_settlement)
    TextView tvNotSettlement;
    @BindView(R.id.rl_financial_bill)
    RelativeLayout rlFinancialBill;
    @BindView(R.id.lv_finance)
    ListView lvFinance;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_settlement);
        ButterKnife.bind(this);
        tvTitle.setText("财务结算");
    }


    public void bankAccount() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).storeJiesuan(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<FinanceBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<FinanceBean> t) throws Exception {
                tvSettlement.setText(t.getData().getY_jiesuan() + "");
                tvNotSettlement.setText(t.getData().getD_jiesuan() + "");
                tvBankNumber.setText(t.getData().getAccount().getAccount_number());
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_withdraw)
    public void onTvWithdrawClicked() {
    }

    @OnClick(R.id.rl_add_bank)
    public void onRlAddBankClicked() {
        startActivity(new Intent(FinanceActivity.this, ManageBankActivity.class));
    }

    @OnClick(R.id.rl_financial_bill)
    public void onRlFinancialBillClicked() {
    }
}
