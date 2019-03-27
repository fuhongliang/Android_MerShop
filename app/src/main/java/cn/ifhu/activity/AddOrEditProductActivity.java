package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.AddGoodsBean;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.CategoryWheelItem;
import cn.ifhu.bean.ProductManageBean;
import cn.ifhu.bean.SellingTime;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.ProductLogic;
import cn.ifhu.utils.StringUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;
import jsc.kit.wheel.dialog.ColumnWheelDialog;

/**
 * @author fuhongliang
 */
public class AddOrEditProductActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_product_name)
    EditText etProductName;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_original_price)
    EditText etOriginalPrice;
//    @BindView(R.id.tv_selling_time)
//    TextView tvSellingTime;

    @BindView(R.id.et_product_desr)
    EditText etProductDesr;
    @BindView(R.id.btn_save)
    Button btnSave;
    ColumnWheelDialog dialog = null;
    int categoryId;
    @BindView(R.id.swh_shock)
    Switch swhShock;

    List<SellingTime> sellingTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        int position = getIntent().getIntExtra("position",0);
        setTvCategory(position);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        goBack();
    }

    @OnClick(R.id.tv_category)
    public void onTvCategoryClicked() {
        if (dialog == null) {
            dialog = createDialog();
        } else {
            dialog.show();
        }
    }

    private ColumnWheelDialog createDialog() {
        ColumnWheelDialog<CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem> dialog = new ColumnWheelDialog<>(this);
        dialog.show();
        dialog.setTitle("");
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", new ColumnWheelDialog.OnClickCallBack<CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem>() {
            @Override
            public boolean callBack(View v, @Nullable CategoryWheelItem item0, @Nullable CategoryWheelItem item1, @Nullable CategoryWheelItem item2, @Nullable CategoryWheelItem item3, @Nullable CategoryWheelItem item4) {
                String result = "";
                if (item0 != null) {
                    result += item0.getShowText();
                    categoryId = item0.getId();
                }
                tvCategory.setText(result);
                return false;
            }
        });
        dialog.setItems(initItems(), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems() {
        final CategoryWheelItem[] items;
        try {
            List<ProductManageBean.ClassListBean> classListBeanList = ProductLogic.getClassList();
            items = new CategoryWheelItem[classListBeanList.size()];
            for (int i = 0; i < classListBeanList.size(); i++) {
                items[i] = new CategoryWheelItem(classListBeanList.get(i).getStc_name(), classListBeanList.get(i).getStc_id());
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        setLoadingMessageIndicator(true);
        if (checkContentEmpty()) {
            setLoadingMessageIndicator(false);
        } else {
            AddGoodsBean addGoodsBean = new AddGoodsBean();
            addGoodsBean.setGoods_name(etProductName.getText().toString());
            addGoodsBean.setGoods_price(Double.parseDouble(etPrice.getText().toString().trim()));
            addGoodsBean.setOrigin_price(Double.parseDouble(etOriginalPrice.getText().toString().trim()));
            addGoodsBean.setStore_id(UserLogic.getUser().getStore_id());
            addGoodsBean.setClass_id(categoryId);
            addGoodsBean.setGoods_desc(etProductDesr.getText().toString().trim());
            addGoodsBean.setGoods_storage(swhShock.isChecked() ? 999999999 : 10);
            addGoodsBean.setSell_time(new ArrayList<>());

            RetrofitAPIManager.create(OperationService.class).addGoods(addGoodsBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Object> t) throws Exception {
                    ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                    clearContent();
                }
            });
        }
    }


    public boolean checkContentEmpty() {
        if (StringUtils.isEmpty(etProductName.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品价格", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        if (StringUtils.isEmpty(etOriginalPrice.getText().toString().trim())) {
            ToastHelper.makeText("请输入商品原价", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return true;
        }
        return false;
    }

    public boolean clearContent() {
        etProductName.setText("");
        etPrice.setText("");
        etOriginalPrice.setText("");
        etProductDesr.setText("");
        return false;
    }

    @OnClick(R.id.ll_selling_time)
    public void onViewClicked() {
        startActivity(new Intent(AddOrEditProductActivity.this, SellingTimeActivity.class));
    }

    public void setTvCategory(int position) {
        try {
            tvCategory.setText(ProductLogic.getClassList().get(position).getStc_name());
            categoryId = ProductLogic.getClassList().get(position).getStc_id();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<SellingTime> sellingTimes = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            sellingTimes = ProductLogic.getSellingTime();
//            if (sellingTimes == null || sellingTimes.size() == 0) {
//                tvSellingTime.setText("时间无限");
//            } else {
//                String mSellingTime = "";
//                for (SellingTime sellingTime : sellingTimes) {
//                    mSellingTime =   sellingTime + sellingTime.getStart_time() + "~" + sellingTime.getEnd_time();
//                }
//                tvSellingTime.setText(mSellingTime);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
