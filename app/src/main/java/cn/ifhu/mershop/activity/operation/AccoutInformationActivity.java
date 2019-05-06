package cn.ifhu.mershop.activity.operation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.AccoutInformationBean;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class AccoutInformationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;

    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        ButterKnife.bind(this);
        tvTitle.setText("账户信息");
        bankInInformation();
    }

    public void bankInInformation() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).bankAccountInfo(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<AccoutInformationBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<AccoutInformationBean> t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
                tvAccountName.setText(t.getData().getAccount_name());
                tvBankNumber.setText(t.getData().getAccount_number());
                tvBankType.setText(t.getData().getBank_type());
                tvBranch.setText(t.getData().getBank_name());
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBankClicked() {
        finish();
    }
}
