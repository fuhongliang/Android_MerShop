package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
public class VouCherListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_add_voucher)
    RelativeLayout rlAddVoucher;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_voucher)
    ListView lvVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_list);
        ButterKnife.bind(this);
        tvTitle.setText("代金券管理");

    }




    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @OnClick(R.id.rl_add_voucher)
    public void onRlAddVoucherClicked() {
        startActivity(new Intent(VouCherListActivity.this, AddVouCherActivity.class));
    }
}
