package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.adapter.CategoryAdapter;
import cn.ifhu.adapter.ProductAdapter;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class ProductActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.lv_product)
    ListView lvProduct;
    ArrayList<String> mDataArray = new ArrayList<>();
    ArrayList<String> mProductArray = new ArrayList<>();
    CategoryAdapter mCategoryAdapter;
    ProductAdapter mProductAdapter;
    @BindView(R.id.rl_manage_category)
    RelativeLayout rlManageCategory;
    @BindView(R.id.rl_add_product)
    RelativeLayout rlAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        mockData();
        View emptyView = LayoutInflater.from(this).inflate(R.layout.neworder_empty, null);
        lvProduct.setEmptyView(emptyView);
        mCategoryAdapter = new CategoryAdapter(mDataArray, this, new CategoryAdapter.ItemOnclick() {
            @Override
            public void onClickItem(int position) {
                mockProductData(mDataArray.get(position));
                mCategoryAdapter.notifyDataSetChanged();
                mProductAdapter.updateData(mProductArray);
                ToastHelper.makeText("点击的位置是：" + position, Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
        lvCategory.setAdapter(mCategoryAdapter);
        mProductAdapter = new ProductAdapter(mDataArray, this);
        lvProduct.setAdapter(mProductAdapter);
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

    public void mockProductData(String category) {
        mProductArray.clear();
        for (int i = 0; i < 10; i++) {
            mProductArray.add(category);
        }
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {
        startActivity(new Intent(ProductActivity.this,ManageProductCategoryActivity.class));

    }

    @OnClick(R.id.rl_add_product)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ProductActivity.this,AddOrEditProductActivity.class));
    }
}
