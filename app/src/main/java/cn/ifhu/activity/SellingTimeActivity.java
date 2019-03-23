package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellging_time);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.tv_add)
    public void onTvAddClicked() {

    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
