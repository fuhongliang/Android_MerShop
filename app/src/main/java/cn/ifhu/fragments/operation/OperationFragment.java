package cn.ifhu.fragments.operation;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.activity.ProductActivity;
import cn.ifhu.base.BaseFragment;

/**
 * @author tony
 */
public class OperationFragment extends BaseFragment {

    LinearLayout llOperationProduct;
    LinearLayout llReviews;
    LinearLayout llOperationdata;

    public static OperationFragment newInstance() {
        return new OperationFragment();
    }


    public OperationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        llOperationProduct = view.findViewById(R.id.ll_OperationProduct);
        llOperationProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductActivity.class));
            }
        });
        return view;
    }


}
