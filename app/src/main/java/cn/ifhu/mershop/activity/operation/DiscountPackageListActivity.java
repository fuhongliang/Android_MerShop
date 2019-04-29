package cn.ifhu.mershop.activity.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.DiscountPackageAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.DiscountBean;
import cn.ifhu.mershop.bean.DiscountPackageBean;
import cn.ifhu.mershop.dialog.nicedialog.BuyDiscountDialog;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DialogUtils;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class DiscountPackageListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    List<DiscountPackageBean> discountPackageBeans;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_discount_package)
    ListView lvDiscountPackage;
    @BindView(R.id.rl_add_discount_package)
    RelativeLayout rlAddDiscountPackage;
    DiscountPackageAdapter discountPackageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_package_list);
        ButterKnife.bind(this);
        tvTitle.setText("优惠套餐");
        discountPackageAdapter = new DiscountPackageAdapter(discountPackageBeans, this);
        discountPackageAdapter.setOnClickItem(new DiscountPackageAdapter.OnClickItem() {
            @Override
            public void editDiscountPackage(int position) {
                Intent intent = new Intent(DiscountPackageListActivity.this,AddDiscountPackageActivity.class);
                intent.putExtra("bundling_id",discountPackageBeans.get(position).getBl_id()+"");
                startActivity(intent);
            }

            @Override
            public void deleteDiscountPackage(int position) {
                deleteDiscountItem(position);
            }
        });
        lvDiscountPackage.setAdapter(discountPackageAdapter);
    }

    public void deleteDiscountItem(int position){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delDiscountPackage(discountPackageBeans.get(position).getBl_id()+"",UserLogic.getUser().getStore_id()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                discountPackageBeans.remove(position);
                discountPackageAdapter.setBeanList(discountPackageBeans);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDiscountListData();
    }

    public void getDiscountListData() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountPackageList(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<DiscountPackageBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<DiscountPackageBean>> t) throws Exception {
                discountPackageBeans = t.getData();
                discountPackageAdapter.setBeanList(discountPackageBeans);
                if (discountPackageAdapter.getCount() > 0) {
                    llEmpty.setVisibility(View.GONE);
                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onCodeError(BaseEntity<List<DiscountPackageBean>> t) throws Exception {
                super.onCodeError(t);
                if (t.getCode() == 2000) {
                    showBuyDiscountPackageQuanxianDialog();
                }
            }
        });
    }

    public void showBuyDiscountPackageQuanxianDialog() {
        View view = getLayoutInflater().inflate(R.layout.buy_discount_dialog, null);
        DialogUtils.showBuyDiscountDialog(getSupportFragmentManager(), new BuyDiscountDialog.ButtonOnclick() {
            @Override
            public void ok(String amount) {
                if (!StringUtils.isEmpty(amount)) {
                    buyDiscountPackageQuanxian(amount);
                }
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }

            @Override
            public void cancel() {
                finish();
            }
        });
    }

    public void buyDiscountPackageQuanxian(String month) {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).buy_bundling_quota(Integer.parseInt(month), UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage()).show();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_add_discount_package)
    public void onRlAddDiscountPackageClicked() {
        startActivity(new Intent(DiscountPackageListActivity.this, AddDiscountPackageActivity.class));
    }
}
