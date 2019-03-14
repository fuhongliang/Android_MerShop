package cn.ifhu.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class AddOrEditProductActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
