package cn.ifhu.mershop.activity.operation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.DiscountAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.DiscountBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class DiscountListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_discount)
    ListView lvDiscount;
    @BindView(R.id.rl_add_discount)
    RelativeLayout rlAddDiscount;
    DiscountAdapter discountAdapter;
    List<DiscountBean> discountBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_list);
        ButterKnife.bind(this);
        discountAdapter = new DiscountAdapter(discountBeanList,this);
        lvDiscount.setAdapter(discountAdapter);
        getDiscountListData();
    }

    public void getDiscountListData(){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getDiscountList(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<DiscountBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<DiscountBean>> t) throws Exception {
                discountBeanList = t.getData();
                discountAdapter.setDiscountBeanList(discountBeanList);
                if (discountAdapter.getCount()>0){
                    llEmpty.setVisibility(View.GONE);
                }else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_add_discount)
    public void onRlAddDiscountClicked() {

    }
}
