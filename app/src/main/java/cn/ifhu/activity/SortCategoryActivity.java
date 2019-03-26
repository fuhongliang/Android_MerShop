package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.fragments.operation.SortCategoryListFragment;

/**
 * @author fuhongliang
 */
public class SortCategoryActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_category);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            SortCategoryListFragment fragment = new SortCategoryListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }
        tvTitle.setText("排序/批量操作");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
