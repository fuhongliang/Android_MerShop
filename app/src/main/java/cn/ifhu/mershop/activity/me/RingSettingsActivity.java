package cn.ifhu.mershop.activity.me;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.utils.Constants;
import cn.ifhu.mershop.utils.IrReference;

/**
 * @author fuhongliang
 */
public class RingSettingsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.swh_shock)
    Switch swhShock;
    @BindView(R.id.swh_status)
    Switch swhStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_settings);
        ButterKnife.bind(this);
        tvTitle.setText("消息与铃声设置");
        initSwitch();
        settingSwitch();
    }

    public void settingSwitch() {
        // 添加监听
        swhShock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                IrReference.getInstance().setBoolean(Constants.Shake, true);
            } else {
                IrReference.getInstance().setBoolean(Constants.Shake, false);
            }
        });

        swhStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                IrReference.getInstance().setBoolean(Constants.RINGMOST, true);
            } else {
                IrReference.getInstance().setBoolean(Constants.RINGMOST, false);
            }
        });
    }

    public void initSwitch() {
        boolean isShake = IrReference.getInstance().getBoolean(Constants.Shake, false);
        swhShock.setChecked(isShake);

        boolean isRingMost = IrReference.getInstance().getBoolean(Constants.RINGMOST, false);
        swhStatus.setChecked(isRingMost);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}