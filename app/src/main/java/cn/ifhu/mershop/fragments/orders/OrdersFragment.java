package cn.ifhu.mershop.fragments.orders;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.notice.NoticeListActivity;
import cn.ifhu.mershop.base.BaseFragment;
import cn.ifhu.mershop.bean.MessageEvent;
import cn.ifhu.mershop.utils.Constants;
import cn.ifhu.mershop.view.RVPIndicator;

import static cn.ifhu.mershop.utils.Constants.LOGOUT;

/**
 * @author tony
 */
public class OrdersFragment extends BaseFragment {

    @BindView(R.id.rvp_indicator)
    RVPIndicator rvpIndicator;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    Unbinder unbinder;
    @BindView(R.id.simpleMarqueeView)
    SimpleMarqueeView<String> simpleMarqueeView;
    @BindView(R.id.rl_marquee_view)
    RelativeLayout rlMarqueeView;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    private List<String> mList = Arrays.asList("进行中", "已完成", "已取消");
    FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragmentArrayList = new ArrayList<Fragment>();


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_managment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFragmentArrayList.add(OngoingOrderFragment.newInstance());
        mFragmentArrayList.add(FinishedOrderFragment.newInstance());
        mFragmentArrayList.add(CancelOrderFragment.newInstance());
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        setSimpleMarqueeView();
    }

    public void setSimpleMarqueeView() {
        final List<String> datas = Arrays.asList("您有一笔新订单，系统已自动接单~", "您有一笔新订单，系统已自动接单~");
        //SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
        SimpleMF<String> marqueeFactory = new SimpleMF(getContext());
        marqueeFactory.setData(datas);
        simpleMarqueeView.setMarqueeFactory(marqueeFactory);
        simpleMarqueeView.startFlipping();
    }

    public void initViewPager() {
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragmentArrayList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentArrayList.get(position);
            }
        };
        rvpIndicator.setTitleList(mList);
        vpContent.setOffscreenPageLimit(5);
        vpContent.setAdapter(mAdapter);
        rvpIndicator.setViewPager(vpContent, 0);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //发通知更新对应的页面
                switch (i) {
                    case 0:
                        EventBus.getDefault().post(new MessageEvent(Constants.ORDERGOING));
                        break;
                    case 1:
                        EventBus.getDefault().post(new MessageEvent(Constants.ORDERFINISH));
                        break;
                    case 2:
                        EventBus.getDefault().post(new MessageEvent(Constants.ORDERCANCELED));
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        simpleMarqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        simpleMarqueeView.stopFlipping();
    }

    @OnClick(R.id.iv_notice)
    public void onIvNoticeClicked() {
        startActivity(new Intent(getContext(), NoticeListActivity.class));
    }

    @OnClick(R.id.iv_close)
    public void onIvCloseClicked() {
        simpleMarqueeView.stopFlipping();
        rlMarqueeView.setVisibility(View.GONE);
    }
}
