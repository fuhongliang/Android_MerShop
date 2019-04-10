package cn.ifhu.mershop.activity.operation;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.CategoryAdapter;
import cn.ifhu.mershop.adapter.ProductAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DialogUtils;
import cn.ifhu.mershop.utils.ProductLogic;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class ProductManageActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_category)
    ListView lvCategory;
    @BindView(R.id.lv_product)
    ListView lvProduct;
    List<ProductManageBean.ClassListBean> mDataArray = new ArrayList<>();
    List<ProductManageBean.GoodsListBean> mProductArray = new ArrayList<>();
    CategoryAdapter mCategoryAdapter;
    ProductAdapter mProductAdapter;
    @BindView(R.id.rl_manage_category)
    RelativeLayout rlManageCategory;
    @BindView(R.id.rl_add_product)
    RelativeLayout rlAddProduct;
    public int mCurId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.neworder_empty, null);
        lvProduct.setEmptyView(emptyView);
        mCategoryAdapter = new CategoryAdapter(mDataArray, this, new CategoryAdapter.ItemOnclick() {
            @Override
            public void onClickItem(int position) {
                mProductData(mDataArray.get(position).getStc_id());
                mCurId = mDataArray.get(position).getStc_id();
            }
        });
        lvCategory.setAdapter(mCategoryAdapter);

        mProductAdapter = new ProductAdapter(mProductArray, this);
        lvProduct.setAdapter(mProductAdapter);
        tvTitle.setText("商品管理");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(mCurId);
    }

    public void getData(int class_id) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).goodsList(UserLogic.getUser().getStore_id(), class_id)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ProductManageBean>(true) {

            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ProductManageBean> t) throws Exception {
                if (t.getData() == null || t.getData().getClass_list() == null || t.getData().getClass_list().size() == 0) {
                    DialogUtils.showConfirmDialog("温馨提示", "您的店铺还没有商品分类 \n 是否新建商品分类？", "否", "是", getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
                        @Override
                        public void cancel() {
                            finish();
                        }

                        @Override
                        public void ok() {
                            startActivity(new Intent(ProductManageActivity.this, AddOrEditCategoryActivity.class));
                        }
                    });
                } else {
                    mDataArray = t.getData().getClass_list();
                    mCategoryAdapter.setmDataList(mDataArray);
                    mProductArray = t.getData().getGoods_list();
                    mProductAdapter.setmDataList(mProductArray);
                    ProductLogic.saveClass(mDataArray);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastHelper.makeText(e.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                finish();
            }
        });
    }


    public void mProductData(int class_id) {
        getData(class_id);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_manage_category)
    public void onRlManageCategoryClicked() {
        startActivity(new Intent(ProductManageActivity.this, ManageCategoryActivity.class));
    }

    @OnClick(R.id.rl_add_product)
    public void onRlAddProductClicked() {
        startActivity(new Intent(ProductManageActivity.this, AddOrEditProductActivity.class));
    }
}
