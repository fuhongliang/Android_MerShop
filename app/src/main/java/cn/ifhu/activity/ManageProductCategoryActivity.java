package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.adapter.ManageCategoryAdapter;
import cn.ifhu.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class ManageProductCategoryActivity extends BaseActivity {

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
    ArrayList<String> mDataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct_category);
        ButterKnife.bind(this);
        mockData();
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

    public void mockData() {
        mDataArray.add("热销");
        mDataArray.add("特殊套餐");
        mDataArray.add("单人套餐");
        mDataArray.add("米饭");
        mDataArray.add("主食类");
        mDataArray.add("精美小吃");
        mDataArray.add("汤类");
        mDataArray.add("饮料");
        mDataArray.add("必买");
        mDataArray.add("点心");
        mDataArray.add("其他");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {

    }

    @OnClick(R.id.rl_add_product)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ManageProductCategoryActivity.this,AddOrEditProductActivity.class));
    }
}
