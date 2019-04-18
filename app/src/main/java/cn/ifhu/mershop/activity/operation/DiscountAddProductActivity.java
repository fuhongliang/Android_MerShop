package cn.ifhu.mershop.activity.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.CategoryAdapter;
import cn.ifhu.mershop.adapter.DiscountProductAdapter;
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
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class DiscountAddProductActivity extends BaseActivity {

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
    DiscountProductAdapter mProductAdapter;

    public int mCurId = 0;
    public int classPosition = 0;
    List<ProductManageBean.GoodsListBean> mSelectedGoodsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_add_product);
        ButterKnife.bind(this);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.neworder_empty, null);
        lvProduct.setEmptyView(emptyView);
        mCategoryAdapter = new CategoryAdapter(mDataArray, this, new CategoryAdapter.ItemOnclick() {
            @Override
            public void onClickItem(int position) {
                mProductData(mDataArray.get(position).getStc_id());
                mCurId = mDataArray.get(position).getStc_id();
                classPosition = position;
            }
        });
        lvCategory.setAdapter(mCategoryAdapter);
        mProductAdapter = new DiscountProductAdapter(mProductArray, this);
        mProductAdapter.setOnClickItem(new DiscountProductAdapter.onClickItem() {
            @Override
            public void setDiscountPrice(int position) {
                View view = getLayoutInflater().inflate(R.layout.dialog_input, null);
                final EditText editText = view.findViewById(R.id.dialog_edit);
                final TextView textView = view.findViewById(R.id.tv_price);
                textView.setText(mProductArray.get(position).getGoods_price());
                AlertDialog dialog = new AlertDialog.Builder(DiscountAddProductActivity.this)
                        .setTitle("修改价格")
                        .setView(view)
                        .setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss())
                        .setPositiveButton("确定", (dialog12, which) -> {
                            String content = editText.getText().toString();
                            if (!StringUtils.isEmpty(content)) {
                                ProductManageBean.GoodsListBean goodsListBean = mProductArray.get(position);
                                goodsListBean.setGoods_dicountprice(content);
                                addSelectedGoods(goodsListBean);
                            }

                            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            dialog12.dismiss();
                        }).create();
                dialog.show();
            }

            @Override
            public void unselectedGoods(int position) {
                removeSelectedGoods(mProductArray.get(position).getGoods_id());
            }
        });
        lvProduct.setAdapter(mProductAdapter);
        tvTitle.setText("添加商品");
    }

    public void addSelectedGoods(ProductManageBean.GoodsListBean goodsListBean) {
        try {

            mSelectedGoodsBeans = ProductLogic.getDiscountGoods();
            if (mSelectedGoodsBeans == null){
                mSelectedGoodsBeans = new ArrayList<>();
            }
            mSelectedGoodsBeans.add(goodsListBean);
            ProductLogic.saveDiscountGoods(mSelectedGoodsBeans);
            mProductAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeSelectedGoods(int goodId) {
        try {
            mSelectedGoodsBeans = ProductLogic.getDiscountGoods();
            for (ProductManageBean.GoodsListBean goodsListBean : mSelectedGoodsBeans) {
                if (goodsListBean.getGoods_id() == goodId) {
                    mSelectedGoodsBeans.remove(goodsListBean);
                }
            }
            ProductLogic.saveDiscountGoods(mSelectedGoodsBeans);
            mProductAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            startActivity(new Intent(DiscountAddProductActivity.this, AddOrEditCategoryActivity.class));
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
                ToastHelper.makeText(e.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
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


    @OnClick(R.id.tv_finish)
    public void onTvFinishClicked() {
        ProductLogic.saveDiscountGoods(mSelectedGoodsBeans);
        finish();
    }
}
