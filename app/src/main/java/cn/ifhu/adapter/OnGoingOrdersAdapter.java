package cn.ifhu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.ifhu.R;

/**
 * @author fuhongliang
 */
public class OnGoingOrdersAdapter extends RecyclerView.Adapter<OnGoingOrdersAdapter.MyViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    public OnclickButton onclickButton;
    public OnGoingOrdersAdapter(List<String> mDatas, Context mContext, OnclickButton onclickButton) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.onclickButton = onclickButton;
    }

    public void updateData(List<String> mDatas){
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_order, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        holder.btn_print.setOnClickListener(v -> onclickButton.print(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        Button btn_print;
        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.tv_order_number);
            btn_print = view.findViewById(R.id.btn_print);
        }
    }

    public interface OnclickButton{
        void print(int position);
    }
}