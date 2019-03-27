package cn.ifhu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.ifhu.R;
import cn.ifhu.bean.OrderBean;

/**
 * @author fuhongliang
 */
public class OnGoingOrdersAdapter extends RecyclerView.Adapter<OnGoingOrdersAdapter.MyViewHolder> {

    private List<OrderBean> mDatas;
    private Context mContext;

    public OnGoingOrdersAdapter(List<OrderBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    public void updateData(List<OrderBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OnGoingOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OnGoingOrdersAdapter.MyViewHolder holder = new OnGoingOrdersAdapter.MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_order, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(OnGoingOrdersAdapter.MyViewHolder holder, int position) {
        OrderBean orderBean = mDatas.get(position);
        holder.tvOrderNumber.setText("#" + orderBean.getOrder_id());
        holder.tvCustomerName.setText(orderBean.getExtend_order_common().getReciver_name() + "");
        holder.tvCustomerPhone.setText(orderBean.getExtend_order_common().getPhone() + "");
        holder.tvCustomerAdd.setText(orderBean.getExtend_order_common().getAddress() + "");
        holder.tvTotal.setText(orderBean.getTotal_price() + "");
        holder.tvServiceFee.setText(orderBean.getCommis_price() + "");
        holder.tvEarnMoney.setText(orderBean.getGoods_pay_price() + "");
        holder.llContent.removeAllViews();
        for (OrderBean.ExtendOrderGoodsBean extendOrderGoodsBean : orderBean.getExtend_order_goods()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
            TextView mProductName = view.findViewById(R.id.tv_product_name);
            TextView mPrice = view.findViewById(R.id.tv_price);
            TextView mNumber = view.findViewById(R.id.tv_number);
            mProductName.setText(extendOrderGoodsBean.getGoods_name());
            mPrice.setText(extendOrderGoodsBean.getGoods_price());
            mNumber.setText("x " + extendOrderGoodsBean.getGoods_num());
            holder.llContent.addView(view);
        }

        holder.tvOrderSn.setText("订单编号："+orderBean.getOrder_sn()+"");
        holder.tvOrderTime.setText("下单时间："+orderBean.getAdd_time()+"");


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNumber;
        TextView tvCustomerName;
        TextView tvCustomerPhone;
        TextView tvCustomerAdd;
        TextView tvTotal;
        TextView tvServiceFee;
        TextView tvEarnMoney;

        TextView tvOrderTime;
        TextView tvOrderSn;
        LinearLayout llContent;

        public MyViewHolder(View view) {
            super(view);
            tvOrderNumber = view.findViewById(R.id.tv_order_number);
            tvCustomerName = view.findViewById(R.id.tv_customer_name);
            tvCustomerPhone = view.findViewById(R.id.tv_customer_phone);
            tvCustomerAdd = view.findViewById(R.id.tv_customer_add);
            tvTotal = view.findViewById(R.id.tv_total);
            tvServiceFee = view.findViewById(R.id.tv_service_fee);
            tvEarnMoney = view.findViewById(R.id.tv_earn_money);
            llContent = view.findViewById(R.id.ll_content);

            tvOrderTime = view.findViewById(R.id.tv_order_time);
            tvOrderSn = view.findViewById(R.id.tv_order_sn);
        }
    }

}