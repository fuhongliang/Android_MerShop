package cn.ifhu.mershop.activity.me;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
