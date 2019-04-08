package cn.ifhu.mershop.fragments.reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.AllReviewsAdapter;
import cn.ifhu.mershop.base.BaseFragment;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.ReviewBean;
import cn.ifhu.mershop.dialog.nicedialog.NiceDialog;
import cn.ifhu.mershop.dialog.nicedialog.ViewConvertListener;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class NeedReplyReviewFragment extends BaseFragment {

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;

    AllReviewsAdapter allReviewsAdapter;

    public static NeedReplyReviewFragment newInstance() {
        return new NeedReplyReviewFragment();
    }


    public NeedReplyReviewFragment() {
    }


    public void getAllReviews() {
        layoutSwipeRefresh.setRefreshing(true);
        RetrofitAPIManager.create(OperationService.class).getNoReplyReviews(UserLogic.getUser().getStore_id(),1)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ReviewBean>(true) {
            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ReviewBean> t) throws Exception {
                allReviewsAdapter.updateData(t.getData());
            }
        });
    }

    public void ReViewReply(String content,int reviewId) {
        layoutSwipeRefresh.setRefreshing(true);
        RetrofitAPIManager.create(OperationService.class).storeFeedback(UserLogic.getUser().getStore_id(),content,reviewId)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        allReviewsAdapter = new AllReviewsAdapter(new ReviewBean(), getActivity(), new AllReviewsAdapter.OnclickButton() {
            @Override
            public void reply(int position) {
                showAddNoteDialog(position);
            }
        });
        recyclerList.setAdapter(allReviewsAdapter);
        setRefreshLayout();
        getAllReviews();
    }

    public void showAddNoteDialog(int position) {

        final NiceDialog niceDialog = NiceDialog.init().setLayoutId(R.layout.add_notes_layout);
        niceDialog.setConvertListener((ViewConvertListener) (holder, dialog) -> {
            final EditText editText = holder.getView(R.id.edit_input);
            final TextView mTvAdd = holder.getView(R.id.tv_add);
            final TextView mTvCancel = holder.getView(R.id.tv_cancel);
            editText.postDelayed(() -> {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
            }, 500);

            mTvAdd.setOnClickListener(view -> {
                if (!StringUtils.isEmpty(editText.getText().toString().trim())){
                    ReViewReply(editText.getText().toString(),allReviewsAdapter.getReviewBean().getCom_list().get(position).getCom_id());
                    niceDialog.dismiss();
                }
            });

            mTvCancel.setOnClickListener(view -> niceDialog.dismiss());

        }).setShowBottom(true).show(getActivity().getSupportFragmentManager());
    }


    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeColors(R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            getAllReviews();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
