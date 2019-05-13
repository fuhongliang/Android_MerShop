package cn.ifhu.mershop.activity.me;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.WebViewActivity;
import cn.ifhu.mershop.activity.operation.WithdrawActivity;
import cn.ifhu.mershop.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        tvTitle.setText("关于我们");
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_protocol)
    public void onTvProtocolClicked() {
        WebViewActivity.startLoadAssetsHtml(AboutUsActivity.this, "protocol.html", "服务协议");

    }
}
