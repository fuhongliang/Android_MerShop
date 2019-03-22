package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.utils.Constants;
import cn.ifhu.utils.IrReference;

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
        initSwitch();
        settingSwitch();
    }

    public void settingSwitch(){
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

    public void initSwitch(){
        boolean isShake = IrReference.getInstance().getBoolean(Constants.Shake,false);
        swhShock.setChecked(isShake);

        boolean isRingMost = IrReference.getInstance().getBoolean(Constants.RINGMOST,false);
        swhStatus.setChecked(isRingMost);

    }

}