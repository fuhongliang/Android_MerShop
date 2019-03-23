package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.AddGoodsBean;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;

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
    @BindView(R.id.tv_selling_time)
    TextView tvSellingTime;
    @BindView(R.id.et_unit)
    EditText etUnit;
    @BindView(R.id.et_product_desr)
    EditText etProductDesr;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_category)
    public void onTvCategoryClicked() {

    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        setLoadingMessageIndicator(true);
        AddGoodsBean addGoodsBean = new AddGoodsBean();
        RetrofitAPIManager.create(OperationService.class).addGoods(addGoodsBean)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {

            }
        });
    }

    @OnClick(R.id.ll_selling_time)
    public void onViewClicked() {
        startActivity(new Intent(AddOrEditProductActivity.this,SellingTimeActivity.class));
    }
}
