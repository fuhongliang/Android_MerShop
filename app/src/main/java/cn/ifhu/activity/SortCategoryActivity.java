package cn.ifhu.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import cn.ifhu.fragments.operation.RecyclerListAdapter;
import cn.ifhu.fragments.operation.SortCategoryListFragment;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.GsonUtils;
import cn.ifhu.utils.ProductLogic;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;
import cn.ifhu.view.ItemTouchHelper.OnStartDragListener;
import cn.ifhu.view.ItemTouchHelper.SimpleItemTouchHelperCallback;

/**
 * @author fuhongliang
 */
public class SortCategoryActivity extends BaseActivity  implements OnStartDragListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    SortCategoryListFragment fragment;

    @BindView(R.id.recycler_list)
    RecyclerView recyclerView;


    private ItemTouchHelper mItemTouchHelper;
    RecyclerListAdapter adapter = new RecyclerListAdapter(this,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_category);
        ButterKnife.bind(this);
        tvTitle.setText("排序/批量操作");
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        setLoadingMessageIndicator(true);
        List<Integer> classIds = new ArrayList<>();
        for (ProductManageBean.ClassListBean classListBean : getmDataArray()) {
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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public List<ProductManageBean.ClassListBean> getmDataArray() {
        return adapter.getmDataArray();
    }

}
