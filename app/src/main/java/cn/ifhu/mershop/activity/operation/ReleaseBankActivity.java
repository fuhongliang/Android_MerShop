package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.ReleaseBankBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;
import io.reactivex.Observer;

/**
 * @author fuhongliang
 */
public class ReleaseBankActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_bank_information)
    RelativeLayout rlBankInformation;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_bank);
        ButterKnife.bind(this);
        tvTitle.setText("银行卡管理");
        RekeaseBankinformation();
    }

    public void RekeaseBankinformation() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).bankAccountList(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ReleaseBankBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ReleaseBankBean> t) throws Exception {
                tvBank.setText(t.getData().getBank_type());
                tvBankNumber.setText(t.getData().getAccount_number().replaceAll("\\d{4}(?!$)", "$0 "));
                tvName.setText(t.getData().getAccount_name());
            }
        });
    }

    //解除绑定银行卡
    public void UntiedBank() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delBankAccount(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);

            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                startActivity(new Intent(ReleaseBankActivity.this, FinanceActivity.class));
            }


        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_bank_information)
    public void onRlBankInformationClicked() {
        startActivity(new Intent(ReleaseBankActivity.this, AccoutInformationActivity.class));

    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        UntiedBank();
    }
}
