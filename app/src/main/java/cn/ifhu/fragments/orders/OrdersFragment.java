package cn.ifhu.fragments.orders;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ifhu.R;
import cn.ifhu.base.BaseFragment;

/**
 * @author tony
 */
public class OrdersFragment extends BaseFragment {

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }


    public OrdersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_managment, container, false);
    }


}
