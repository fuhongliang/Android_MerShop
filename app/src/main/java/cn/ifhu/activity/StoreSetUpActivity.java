package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.dialog.DialogListFragment;
import cn.ifhu.dialog.DialogWheelFragment;

/**
 * @author fuhongliang
 */
public class StoreSetUpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_store_state)
    LinearLayout llStoreState;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rl_notice)
    RelativeLayout rlNotice;
    @BindView(R.id.tv_store_phone)
    TextView tvStorePhone;
    @BindView(R.id.ll_store_phone)
    LinearLayout llStorePhone;
    @BindView(R.id.tv_store_add)
    TextView tvStoreAdd;
    @BindView(R.id.tv_store_time)
    TextView tvStoreTime;
    @BindView(R.id.rl_store_license)
    RelativeLayout rlStoreLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setup);
        ButterKnife.bind(this);
        tvTitle.setText("门店设置");
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.ll_store_state)
    public void onLlStoreStateClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, StoreStateActivity.class));
    }

    @OnClick(R.id.rl_notice)
    public void onRlNoticeClicked() {
    }

    @OnClick(R.id.ll_store_phone)
    public void onLlStorePhoneClicked() {
    }

    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> mins = new ArrayList<>();

    private void initOptionData() {
        for (int i=0;i<24;i++){
            if (i<10){
                hours.add("0"+i);
            }else {
                hours.add(i+"");
            }
        }

        for (int i=0;i<60;i++){
            if (i<10){
                mins.add("0"+i);
            }else {
                mins.add(i+"");
            }
        }
    }



    @OnClick(R.id.tv_store_time)
    public void onTvStoreTimeClicked() {
        Bundle bundlePhone = new Bundle();
        bundlePhone.putString("title", "请选择时间");
        bundlePhone.putStringArrayList("stringList", hours);

        DialogWheelFragment.showOperateDialog(getSupportFragmentManager(), bundlePhone, new DialogWheelFragment.OperateDialogConfirmListner() {
            @Override
            public void onClickTextView(String string) {

            }
        });
    }

    @OnClick(R.id.rl_store_license)
    public void onRlStoreLicenseClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, BusinessQualificationActivity.class));
    }
}
