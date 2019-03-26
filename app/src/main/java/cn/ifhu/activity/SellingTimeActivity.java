package cn.ifhu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.dialog.DialogWheelFragment;
import cn.ifhu.net.MeService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class SellingTimeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_select)
    ImageView ivSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellging_time);
        ButterKnife.bind(this);
        tvTitle.setText("可售时间");
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.iv_select)
    public void onIvSelectClicked() {
        if (ivSelect.isSelected()) {
            ivSelect.setSelected(false);
        } else {
            ivSelect.setSelected(true);
        }
    }

    @OnClick(R.id.tv_add)
    public void onTvAddClicked() {
        if (ivSelect.isSelected()){
            ToastHelper.makeText("已勾选无限时间",Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("startTime", "07:00");
            bundle.putString("endTime", "09:00");
            DialogWheelFragment.showOperateDialog(getSupportFragmentManager(), bundle, (beginTime, endTime) -> {
                setStoreTime(beginTime, endTime);
            });
        }
    }

    public void setStoreTime(String beginTime, String endTime) {

        View view = getLayoutInflater().inflate(R.layout.item_time, null);
        TextView mTvStartTime = view.findViewById(R.id.tv_start_time);
        TextView mTvEndTime = view.findViewById(R.id.tv_end_time);
        ImageView mIvDelete = view.findViewById(R.id.iv_delete);
        mTvStartTime.setText(beginTime);
        mTvEndTime.setText(endTime);
        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContent.removeView(view);
            }
        });
        llContent.addView(view);
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {

    }
}
