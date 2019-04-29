package cn.ifhu.mershop.activity.operation;

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
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.rl_financial_bill)
    RelativeLayout rlFinancialBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_settlement);
        ButterKnife.bind(this);
        tvTitle.setText("财务结算");

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick(R.id.rl_header)
    public void onRlHeaderClicked() {
    }

    @OnClick(R.id.tv_settlement)
    public void onTvSettlementClicked() {
    }

    @OnClick(R.id.tv_withdraw)
    public void onTvWithdrawClicked() {

    }

    @OnClick(R.id.rl_add_bank)
    public void onRlAddBankClicked() {
        startActivity(new Intent(FinanceActivity.this,ManageBankActivity.class));
    }

    @OnClick(R.id.tv_not_settlement)
    public void onTvNotSettlementClicked() {
    }

    @OnClick(R.id.tv_bill)
    public void onTvBillClicked() {
    }

    @OnClick(R.id.rl_financial_bill)
    public void onRlFinancialBillClicked() {
    }
}
