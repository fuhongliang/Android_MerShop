package cn.ifhu.mershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.bean.WithDrawBean;

/**
 * @author fuhongliang
 */
public class SettledAdapter extends BaseAdapter {
    public List<WithDrawBean.ListBean> mDataList;
    public Context mContext;
    public int mCurPosition = 0;

    public SettledAdapter(List<WithDrawBean.ListBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    public void setmDataList(List<WithDrawBean.ListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_withdraw, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDate.setText(mDataList.get(position).getAdd_time()+"");
        viewHolder.tvAccountNumbe.setText(mDataList.get(position).getBank_no());
        viewHolder.tvMoney.setText("￥"+mDataList.get(position).getAmount());
        viewHolder.tvState.setText(mDataList.get(position).getPayment_state());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_account_numbe)
        TextView tvAccountNumbe;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
