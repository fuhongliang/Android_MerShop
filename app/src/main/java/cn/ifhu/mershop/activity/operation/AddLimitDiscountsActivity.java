package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.SelectedDiscountGoodsAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.DiscountGoods;
import cn.ifhu.mershop.bean.DiscountInfoBean;
import cn.ifhu.mershop.bean.DiscountPostBean;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DateUtil;
import cn.ifhu.mershop.utils.GsonUtils;
import cn.ifhu.mershop.utils.ProductLogic;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;
import io.reactivex.Observer;
import jsc.kit.wheel.dialog.DateTimeWheelDialog;

/**
 * @author fuhongliang
 */
public class AddLimitDiscountsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_discount_name)
    EditText etDiscountName;
    @BindView(R.id.et_tag)
    EditText etTag;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.iv_less)
    ImageView ivLess;
    @BindView(R.id.tv_limit_less)
    TextView tvLimitLess;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_add_goods)
    LinearLayout llAddGoods;
    @BindView(R.id.btn_save)
    Button btnSave;
    int limitNumber = 1;
    List<ProductManageBean.GoodsListBean> listBeans = new ArrayList<>();
    @BindView(R.id.lv_discount_goods)
    ListView lvDiscountGoods;
    SelectedDiscountGoodsAdapter selectedDiscountGoodsAdapter;
    String xianshi_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_limit_discounts);
        ButterKnife.bind(this);
        xianshi_id = getIntent().getStringExtra("xianshi_id");
        if (StringUtils.isEmpty(xianshi_id)){
            tvTitle.setText("添加活动");
        }else {
            tvTitle.setText("编辑活动");
            getDiscountInfo();
        }
        selectedDiscountGoodsAdapter  = new SelectedDiscountGoodsAdapter(listBeans,this);
        lvDiscountGoods.setAdapter(selectedDiscountGoodsAdapter);

    }

    public void getDiscountInfo(){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountInfo(xianshi_id,UserLogic.getUser().getStore_id()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<DiscountInfoBean>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<DiscountInfoBean> t) throws Exception {
                initData(t.getData());
            }
        });
    }

    public void initData(DiscountInfoBean discountInfoBean){
        etDiscountName.setText(discountInfoBean.getXianshi_name());
        etTag.setText(discountInfoBean.getXianshi_title());
        etDescription.setText(discountInfoBean.getXianshi_explain());
        tvStartTime.setText(discountInfoBean.getStart_time()+"");
        tvEndTime.setText(discountInfoBean.getEnd_time()+"");
        tvLimitLess.setText(discountInfoBean.getLower_limit()+"");
        limitNumber = discountInfoBean.getLower_limit();
        for (DiscountInfoBean.GoodsListBean goodsListBean :discountInfoBean.getGoods_list()){
            ProductManageBean.GoodsListBean goodsbean = new ProductManageBean.GoodsListBean();
            goodsbean.setGoods_id(goodsListBean.getGoods_id());
            goodsbean.setGoods_name(goodsListBean.getGoods_name());
            goodsbean.setGoods_dicountprice(goodsListBean.getXianshi_price());
            goodsbean.setGoods_price(goodsListBean.getGoods_price());
            goodsbean.setImg_name("");
            goodsbean.setImg_path(discountInfoBean.getImg_path());
            listBeans.add(goodsbean);
        }
        selectedDiscountGoodsAdapter.setmDataList(listBeans);
        ProductLogic.saveDiscountGoods(listBeans);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_start_time)
    public void onTvStartTimeClicked() {
        createDialog(0).show();
    }

    @OnClick(R.id.tv_end_time)
    public void onTvEndTimeClicked() {
        createDialog(1).show();
    }

    private DateTimeWheelDialog createDialog(int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date startDate = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2035);
        Date endDate = calendar.getTime();

        DateTimeWheelDialog dialog = new DateTimeWheelDialog(this);
        dialog.show();
        dialog.setTitle("选择时间");
        int config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;
        dialog.configShowUI(config);
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", (v, selectedDate) -> {
            if (type == 0) {
                tvStartTime.setText(DateUtil.getDateToString(selectedDate));
            } else {
                tvEndTime.setText(DateUtil.getDateToString(selectedDate));
            }
            return false;
        });
        dialog.setDateArea(startDate, endDate, true);
        dialog.updateSelectedDate(new Date());
        return dialog;
    }

    @OnClick(R.id.iv_less)
    public void onIvLessClicked() {
        if (limitNumber != 1) {
            limitNumber = limitNumber - 1;
            tvLimitLess.setText(limitNumber + "");
        }
    }

    @OnClick(R.id.iv_more)
    public void onIvMoreClicked() {
        limitNumber = limitNumber + 1;
        tvLimitLess.setText(limitNumber + "");
    }

    @OnClick(R.id.ll_add_goods)
    public void onLlAddGoodsClicked() {
        startActivity(new Intent(AddLimitDiscountsActivity.this, DiscountAddProductActivity.class));
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()){
            setLoadingMessageIndicator(true);
            DiscountPostBean discountPostBean = new DiscountPostBean();
            discountPostBean.setXianshi_name(etDiscountName.getText().toString().trim());
            discountPostBean.setXianshi_title(etTag.getText().toString().trim());
            discountPostBean.setStore_id(UserLogic.getUser().getStore_id()+"");
            discountPostBean.setXianshi_explain(etDescription.getText().toString().trim());
            discountPostBean.setStart_time(tvStartTime.getText().toString());
            discountPostBean.setEnd_time(tvEndTime.getText().toString());
            discountPostBean.setLower_limit(tvLimitLess.getText().toString().trim());
            List<DiscountGoods> goodsList = new ArrayList<>();
            for (ProductManageBean.GoodsListBean bean:listBeans){
                DiscountGoods discountGoods = new DiscountGoods();
                discountGoods.setGoods_id(bean.getGoods_id()+"");
                discountGoods.setXianshi_price(bean.getGoods_dicountprice());
                goodsList.add(discountGoods);
            }
            discountPostBean.setGoods_list(goodsList);
            RetrofitAPIManager.create(OperationService.class).xianshiAddOrEdit(discountPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Observer> t) throws Exception {
                    ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                    ProductLogic.clearDiscountGoods();
                }
            });
        }
    }

    public boolean checkContent(){
        if (StringUtils.isEmpty(etDiscountName.getText().toString().trim())){
            ToastHelper.makeText("请输入活动名称", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvStartTime.getText().toString().trim())){
            ToastHelper.makeText("请选择开始时间", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvEndTime.getText().toString().trim())){
            ToastHelper.makeText("请选择结束时间", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            return false;
        }


        if (listBeans == null || listBeans.size()<1){
            ToastHelper.makeText("请添加商品", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            listBeans = ProductLogic.getDiscountGoods();
            selectedDiscountGoodsAdapter.setmDataList(listBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
