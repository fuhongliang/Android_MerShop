package cn.ifhu.mershop.activity;

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
import cn.ifhu.mershop.adapter.ManageCategoryAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.utils.ProductLogic;

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
    ManageCategoryAdapter manageCategoryAdapter;
    List<ProductManageBean.ClassListBean> mDataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct_category);
        ButterKnife.bind(this);
        tvTitle.setText("管理分类");
        manageCategoryAdapter = new ManageCategoryAdapter(mDataArray, this, new ManageCategoryAdapter.ItemOnclick() {
            @Override
            public void onClickEditItem(int position) {
                Intent intent = new Intent(ManageCategoryActivity.this,AddOrEditCategoryActivity.class);
                intent.putExtra("ClassId",mDataArray.get(position).getStc_id());
                intent.putExtra("ClassName",mDataArray.get(position).getStc_name());
                startActivity(intent);
            }

            @Override
            public void onClickAddItem(int position) {
                Intent intent = new Intent(ManageCategoryActivity.this,AddOrEditProductActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        manageCategoryAdapter.setmDataList(mDataArray);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {
        startActivity(new Intent(ManageCategoryActivity.this,SortCategoryActivity.class));
    }

    @OnClick(R.id.rl_add_category)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ManageCategoryActivity.this, AddOrEditCategoryActivity.class));
    }
}
