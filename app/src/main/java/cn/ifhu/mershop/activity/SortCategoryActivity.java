package cn.ifhu.mershop.activity;

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
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.adapter.RecyclerListAdapter;
import cn.ifhu.mershop.fragments.operation.SortCategoryListFragment;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.GsonUtils;
import cn.ifhu.mershop.utils.ProductLogic;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;
import cn.ifhu.mershop.view.ItemTouchHelper.OnStartDragListener;
import cn.ifhu.mershop.view.ItemTouchHelper.SimpleItemTouchHelperCallback;

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
        adapter.setDeleteitemInterface(new RecyclerListAdapter.DeleteitemInterface() {
            @Override
            public void deleteItem(int class_id, int position) {
                deleteGoodsClass(class_id,position);
            }
        });
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

    public void deleteGoodsClass(int class_id,int position){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(OperationService.class).delGoodsClass(class_id, UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<Object>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<Object>> t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                adapter.ItemRemoved(position);
                ProductLogic.saveClass(getmDataArray());
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
