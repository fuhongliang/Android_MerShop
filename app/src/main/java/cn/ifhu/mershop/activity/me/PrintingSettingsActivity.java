package cn.ifhu.mershop.activity.me;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.WebViewActivity;
import cn.ifhu.mershop.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class PrintingSettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printing_settings);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_help)
    public void onTvHelpClicked() {
        WebViewActivity.startLoadAssetsHtml(PrintingSettingsActivity.this, "bluetooth_help.html", "帮助");
    }
}
