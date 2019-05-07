package cn.ifhu.mershop.adapter;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

import cn.ifhu.mershop.BtService;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.me.PrintingSettingsActivity;
import cn.ifhu.mershop.activity.me.SearchBluetoothActivity;
import cn.ifhu.mershop.base.AppInfo;
import cn.ifhu.mershop.bean.OrderBean;
import cn.ifhu.mershop.print.PrintUtil;
import cn.ifhu.mershop.utils.OrderLogic;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class OnGoingOrdersAdapter extends RecyclerView.Adapter<OnGoingOrdersAdapter.MyViewHolder> {
    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    public static String unit = "￥";
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
        holder.tvCustomerAdd.setText(p.matcher(orderBean.getExtend_order_common().getAddress()).replaceAll("") + "");
        holder.tvTotal.setText(unit+orderBean.getTotal_price() + "");
        holder.tvServiceFee.setText(unit+orderBean.getCommis_price() + "");
        holder.tvEarnMoney.setText(unit+orderBean.getGoods_pay_price() + "");
        holder.llContent.removeAllViews();
        if (orderBean.isExpendOrder()){
            for (OrderBean.ExtendOrderGoodsBean extendOrderGoodsBean : orderBean.getExtend_order_goods()) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
                TextView mProductName = view.findViewById(R.id.tv_product_name);
                TextView mPrice = view.findViewById(R.id.tv_price);
                TextView mNumber = view.findViewById(R.id.tv_number);
                mProductName.setText(extendOrderGoodsBean.getGoods_name());
                mPrice.setText(unit+extendOrderGoodsBean.getGoods_price());
                mNumber.setText("x " + extendOrderGoodsBean.getGoods_num());
                holder.llContent.addView(view);
            }
            holder.tvExpend.setText("收起");
        }else {
            if (orderBean.getExtend_order_goods() != null&&orderBean.getExtend_order_goods().size()>0){
                OrderBean.ExtendOrderGoodsBean extendOrderGoodsBean = orderBean.getExtend_order_goods().get(0);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_product, null);
                TextView mProductName = view.findViewById(R.id.tv_product_name);
                TextView mPrice = view.findViewById(R.id.tv_price);
                TextView mNumber = view.findViewById(R.id.tv_number);
                mProductName.setText(extendOrderGoodsBean.getGoods_name());
                mPrice.setText(unit+extendOrderGoodsBean.getGoods_price());
                mNumber.setText("x " + extendOrderGoodsBean.getGoods_num());
                holder.llContent.addView(view);
            }
            holder.tvExpend.setText("展开");
        }

        if (StringUtils.isEmpty(orderBean.getOrder_state())){
            holder.tvOrderState.setVisibility(View.GONE);
        }else {
            holder.tvOrderState.setVisibility(View.VISIBLE);
            holder.tvOrderState.setText(orderBean.getOrder_state());
            if ("待配送".equals(orderBean.getOrder_state())){
                holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.daipeisong_text_color));
            }else if ("已取消".equals(orderBean.getOrder_state())){
                holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.yiquxiao_text_color));
            }else if ("已完成".equals(orderBean.getOrder_state())){
                holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.yiwangcheng_text_color));
            }else {
                holder.tvOrderState.setTextColor(mContext.getResources().getColor(R.color.peisongzhong_text_color));
            }
        }

        holder.tvOrderSn.setText("订单编号：" + orderBean.getOrder_sn() + "");
        holder.tvOrderTime.setText("下单时间：" + orderBean.getAdd_time() + "");
        holder.tvPrint.setOnClickListener(v -> {
            OrderLogic.savePrintingOrder(orderBean);
            printingOrder();
        });

        holder.tvExpend.setOnClickListener(v -> {
            mDatas.get(position).setExpendOrder(!mDatas.get(position).isExpendOrder());
            notifyDataSetChanged();
        });
    }

    public void printingOrder(){
        if (TextUtils.isEmpty(AppInfo.btAddress)) {
            ToastHelper.makeText("请连接蓝牙...", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            mContext.startActivity(new Intent(mContext, SearchBluetoothActivity.class));
        } else {
            if (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF) {
                //蓝牙被关闭时强制打开
                BluetoothAdapter.getDefaultAdapter().enable();
                ToastHelper.makeText("蓝牙被关闭请打开...", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            } else {
                ToastHelper.makeText("打印中...", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                Intent intent = new Intent(mContext.getApplicationContext(), BtService.class);
                intent.setAction(PrintUtil.ACTION_PRINT_TEST);
                mContext.startService(intent);
            }
        }
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
        TextView tvOrderState;
        TextView tvPrint;
        TextView tvExpend;
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
            tvOrderState = view.findViewById(R.id.tv_order_state);
            tvPrint = view.findViewById(R.id.tv_print);
            tvExpend = view.findViewById(R.id.tv_expend);

            tvOrderTime = view.findViewById(R.id.tv_order_time);
            tvOrderSn = view.findViewById(R.id.tv_order_sn);
        }
    }

}