package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import java.util.List;

import cn.ifhu.R;
import cn.ifhu.activity.login.LoginActivity;
import cn.ifhu.adapter.FragmentAdapter;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseFragment;
import cn.ifhu.base.ViewManager;
import cn.ifhu.fragments.me.MeFragment;
import cn.ifhu.fragments.neworder.NewOrderFragment;
import cn.ifhu.fragments.operation.OperationFragment;
import cn.ifhu.fragments.orders.OrdersFragment;
import cn.ifhu.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class MainActivity extends BaseActivity {

    private ViewPager mPager;
    private List<BaseFragment> mFragments;
    private FragmentAdapter mAdapter;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
            if (i == R.id.navigation_data) {
                mPager.setCurrentItem(0);
                return true;
            } else if (i == R.id.navigation_meetings) {
                mPager.setCurrentItem(1);
                return true;
            } else if (i == R.id.navigation_investors) {
                mPager.setCurrentItem(2);
                return true;
            } else if (i == R.id.navigation_me) {
                mPager.setCurrentItem(3);
                return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initViewPager();
    }

    private void initViewPager() {
        ViewManager.getInstance().addFragment(0, NewOrderFragment.newInstance());
        ViewManager.getInstance().addFragment(1, OrdersFragment.newInstance());
        ViewManager.getInstance().addFragment(2, OperationFragment.newInstance());
        ViewManager.getInstance().addFragment(3, MeFragment.newInstance());
        mFragments = ViewManager.getInstance().getAllFragment();
        mPager = findViewById(R.id.container_pager);
        mPager.setOffscreenPageLimit(8);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                navigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void logout(){
        UserLogic.isLogin();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
