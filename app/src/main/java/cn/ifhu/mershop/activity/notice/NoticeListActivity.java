package cn.ifhu.mershop.activity.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.NoticeDetailActivity;
import cn.ifhu.mershop.adapter.NoticeAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.NoticeBean;
import cn.ifhu.mershop.net.NoticeService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class NoticeListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_notice)
    ListView lvNotice;
    NoticeAdapter noticeAdapter;
    List<NoticeBean> noticeBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notification);
        ButterKnife.bind(this);
        tvTitle.setText("系统通知");
        noticeAdapter = new NoticeAdapter(noticeBeanList,this);
        lvNotice.setAdapter(noticeAdapter);
        lvNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoticeListActivity.this, NoticeDetailActivity.class);
                intent.putExtra("smId",noticeBeanList.get(position).getSm_id());
                startActivity(intent);
            }
        });
    }


    public void getData(){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(NoticeService.class).getMsgList(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<NoticeBean>>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<List<NoticeBean>> t) throws Exception {
                noticeBeanList = t.getData();
                noticeAdapter.setvouCherBeanList(noticeBeanList);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
