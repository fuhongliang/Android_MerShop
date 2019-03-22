package cn.ifhu.activity;

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

    @OnClick(R.id.tv_selling_time)
    public void onTvSellingTimeClicked() {
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
    }
}
