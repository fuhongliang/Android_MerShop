package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.adapter.ManageCategoryAdapter;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.bean.ProductManageBean;
import cn.ifhu.utils.ProductLogic;

/**
 * @author fuhongliang
 */
public class ManageCategoryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.rl_manage_category)
    RelativeLayout rlManageCategory;
    @BindView(R.id.rl_add_product)
    RelativeLayout rlAddProduct;
    ManageCategoryAdapter manageCategoryAdapter;
    List<ProductManageBean.ClassListBean> mDataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct_category);
        ButterKnife.bind(this);
        tvTitle.setText("管理分类");
        initData();
        manageCategoryAdapter = new ManageCategoryAdapter(mDataArray, this, new ManageCategoryAdapter.ItemOnclick() {
            @Override
            public void onClickEditItem(int position) {

            }

            @Override
            public void onClickAddItem(int position) {

            }
        });
        lvCategory.setAdapter(manageCategoryAdapter);
    }

    public void initData() {
        try {
            mDataArray = ProductLogic.getClassList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {
        startActivity(new Intent(ManageCategoryActivity.this,SortCategoryActivity.class));
    }

    @OnClick(R.id.rl_add_product)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ManageCategoryActivity.this, CategoryManagementActivity.class));
    }
}
