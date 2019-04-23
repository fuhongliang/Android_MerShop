package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.ifhu.mershop.adapter.VouCherAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.DiscountBean;
import cn.ifhu.mershop.bean.VouCherBean;
import cn.ifhu.mershop.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DialogUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class VouCherListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_add_voucher)
    RelativeLayout rlAddVoucher;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_voucher)
    ListView lvVoucher;
    List<VouCherBean> vouCherBeanList;

    VouCherAdapter vouCherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_list);
        ButterKnife.bind(this);
        tvTitle.setText("代金券管理");
        vouCherBeanList = new ArrayList<>();
        vouCherAdapter = new VouCherAdapter(vouCherBeanList,this);
        lvVoucher.setAdapter(vouCherAdapter);
        vouCherAdapter.setOnClickItem(new VouCherAdapter.OnClickItem() {
            @Override
            public void editDiscount(int position) {
                Intent intent = new Intent(VouCherListActivity.this,AddVouCherActivity.class);
                intent.putExtra("voucher_id",vouCherBeanList.get(position).getVoucher_id()+"");
                startActivity(intent);
            }

            @Override
            public void deleteDiscount(int position) {
                DialogUtils.showConfirmDialog("提示","是否删除该代金券", getSupportFragmentManager(),new ConfirmDialog.ButtonOnclick() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        deleteDiscountItem(position);
                    }
                });
            }
        });
    }

    public void getData(){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getVoucherList(UserLogic.getUser().getStore_id()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<VouCherBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<VouCherBean>> t) throws Exception {
                vouCherBeanList = t.getData();
                vouCherAdapter.setvouCherBeanList(vouCherBeanList);
                if (vouCherAdapter.getCount()>0){
                    llEmpty.setVisibility(View.GONE);
                }else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void deleteDiscountItem(int position){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delVouCher(vouCherBeanList.get(position).getVoucher_id()+"",UserLogic.getUser().getStore_id()+"")
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                vouCherBeanList.remove(position);
                vouCherAdapter.setvouCherBeanList(vouCherBeanList);
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick(R.id.rl_add_voucher)
    public void onRlAddVoucherClicked() {
        startActivity(new Intent(VouCherListActivity.this, AddVouCherActivity.class));
    }
}
