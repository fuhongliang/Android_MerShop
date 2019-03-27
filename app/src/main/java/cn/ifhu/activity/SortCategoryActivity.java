package cn.ifhu.activity;

import android.os.Bundle;
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
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.ProductManageBean;
import cn.ifhu.bean.SortCategoryBean;
import cn.ifhu.fragments.operation.SortCategoryListFragment;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.GsonUtils;
import cn.ifhu.utils.ProductLogic;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class SortCategoryActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    SortCategoryListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_category);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            fragment = new SortCategoryListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }
        tvTitle.setText("排序/批量操作");
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        setLoadingMessageIndicator(true);
        List<Integer> classIds = new ArrayList<>();
        for (ProductManageBean.ClassListBean classListBean : fragment.getmDataArray()) {
            classIds.add(classListBean.getStc_id());
        }

        RetrofitAPIManager.create(OperationService.class).sortGoodsClass(GsonUtils.convertObject2Json(classIds), UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<ProductManageBean.ClassListBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<ProductManageBean.ClassListBean>> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                ProductLogic.saveClass(t.getData());
                finish();
            }
        });
    }
}
