package cn.ifhu.mershop.activity.operation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.FullCutAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.FullCutBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class FullCutActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_discount)
    ListView lvDiscount;
    List<FullCutBean> fullCutBeanList = new ArrayList<>();
    FullCutAdapter fullCutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_cut_list);
        ButterKnife.bind(this);
        tvTitle.setText("满立减");
        getFullCutListData();
        fullCutAdapter = new FullCutAdapter(fullCutBeanList, this);
        lvDiscount.setAdapter(fullCutAdapter);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_add_full_cut)
    public void onRlAddFullCutClicked() {

    }


    public void getFullCutListData() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).getFullCutList(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<FullCutBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<FullCutBean>> t) throws Exception {
                fullCutBeanList = t.getData();
                fullCutAdapter.setBeanList(fullCutBeanList);
                if (fullCutBeanList != null && fullCutBeanList.size() > 0) {
                    llEmpty.setVisibility(View.GONE);
                } else {
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
