package cn.ifhu.mershop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.ifhu.mershop.R;
import cn.ifhu.mershop.bean.NewOrderBean;
import cn.ifhu.mershop.bean.OrderBean;
import cn.ifhu.mershop.utils.Constants;

/**
 * @author fuhongliang
 */
public class NewOrdersAdapter extends RecyclerView.Adapter<NewOrdersAdapter.MyViewHolder> {

    private List<NewOrderBean.ListBean> mDatas;
    private Context mContext;
    public OnclickButton onclickButton;

    public NewOrdersAdapter(List<NewOrderBean.ListBean> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void updateData(List<NewOrderBean.ListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_neworder, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewOrderBean.ListBean orderBean = mDatas.get(position);
        holder.tvOrderNumber.setText("#" + orderBean.getOrder_id());
        holder.tvCustomerName.setText(orderBean.getExtend_order_common().getReciver_name() + "");
        holder.tvCustomerPhone.setText(orderBean.getExtend_order_common().getPhone() + "");
        holder.tvCustomerAdd.setText(orderBean.getExtend_order_common().getAddress() + "");
        holder.tvTotal.setText(Constants.unit + orderBean.getTotal_price() + "");
        holder.tvServiceFee.setText(Constants.unit + orderBean.getCommis_price() + "");
        holder.tvEarnMoney.setText(Constants.unit + orderBean.getGoods_pay_price() + "");
        holder.llContent.removeAllViews();

        holder.tvOrderSn.setText("订单编号：" + orderBean.getOrder_sn() + "");
        holder.tvOrderTime.setText("下单时间：" + orderBean.getAdd_time() + "");

        holder.btn_refuse.setOnClickListener(v -> onclickButton.refuse(position));
        holder.btn_accept.setOnClickListener(v -> onclickButton.accept(position));

        if (orderBean.isExpendOrder()) {
            for (NewOrderBean.ListBean.ExtendOrderGoodsBean extendOrderGoodsBean : orderBean.getExtend_order_goods()) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
                TextView mProductName = view.findViewById(R.id.tv_product_name);
                TextView mPrice = view.findViewById(R.id.tv_price);
                TextView mNumber = view.findViewById(R.id.tv_number);
                mProductName.setText(extendOrderGoodsBean.getGoods_name());
                mPrice.setText(Constants.unit + extendOrderGoodsBean.getGoods_price());
                mNumber.setText("x " + extendOrderGoodsBean.getGoods_num());
                holder.llContent.addView(view);
            }
            holder.tvExpend.setText("收起");
            holder.ivExpend.setBackgroundResource(R.drawable.icon_zhankai);
        } else {
            if (orderBean.getExtend_order_goods() != null && orderBean.getExtend_order_goods().size() > 0) {
                NewOrderBean.ListBean.ExtendOrderGoodsBean extendOrderGoodsBean = orderBean.getExtend_order_goods().get(0);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
                TextView mProductName = view.findViewById(R.id.tv_product_name);
                TextView mPrice = view.findViewById(R.id.tv_price);
                TextView mNumber = view.findViewById(R.id.tv_number);
                mProductName.setText(extendOrderGoodsBean.getGoods_name());
                mPrice.setText(Constants.unit + extendOrderGoodsBean.getGoods_price());
                mNumber.setText("x " + extendOrderGoodsBean.getGoods_num());
                holder.llContent.addView(view);
            }
            holder.tvExpend.setText("展开");
            holder.ivExpend.setBackgroundResource(R.drawable.icon_zhankai);
        }

        holder.tvExpend.setOnClickListener(v -> {
            mDatas.get(position).setExpendOrder(!mDatas.get(position).isExpendOrder());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView btn_refuse;
        TextView btn_accept;
        TextView tvOrderNumber;
        TextView tvCustomerName;
        TextView tvCustomerPhone;
        TextView tvCustomerAdd;
        TextView tvTotal;
        TextView tvServiceFee;
        TextView tvEarnMoney;
        TextView tvExpend;
        ImageView ivExpend;

        TextView tvOrderTime;
        TextView tvOrderSn;
        LinearLayout llContent;

        public MyViewHolder(View view) {
            super(view);
            ivExpend = view.findViewById(R.id.iv_ic_zhankai);
            tvExpend = view.findViewById(R.id.tv_expend);
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

            btn_refuse = view.findViewById(R.id.btn_refuse);
            btn_accept = view.findViewById(R.id.btn_accept);
        }
    }

    public interface OnclickButton {
        void refuse(int position);

        void accept(int position);
    }
}