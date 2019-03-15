package cn.ifhu.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.fragments.operation.SortCategoryListFragment;

/**
 * @author fuhongliang
 */
public class SortCategoryActivity extends BaseActivity {

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
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
