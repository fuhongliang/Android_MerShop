package cn.ifhu.mershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.bean.DiscountBean;

/**
 * @author fuhongliang
 */
public class DiscountAdapter extends BaseAdapter {

    List<DiscountBean> discountBeanList;
    Context mContext;

    public DiscountAdapter(List<DiscountBean> discountBeanList, Context mContext) {
        this.discountBeanList = discountBeanList;
        this.mContext = mContext;
    }

    public void setDiscountBeanList(List<DiscountBean> discountBeanList) {
        this.discountBeanList = discountBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return discountBeanList == null?0:discountBeanList.size();
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
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_limit_discounts, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDiscountName.setText(discountBeanList.get(position).getXianshi_name());
//        viewHolder.tvAtLess.setText();
        viewHolder.tvTime.setText(discountBeanList.get(position).getStart_time() + "-" + discountBeanList.get(position).getEnd_time());
        if (discountBeanList.get(position).getState() == 1) {
            viewHolder.ivDiscount.setBackgroundResource(R.drawable.yhq_bnt_xszk);
        } else {
            viewHolder.ivDiscount.setBackgroundResource(R.drawable.yhq_bnt_xszk01);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_discount)
        ImageView ivDiscount;
        @BindView(R.id.tv_discount_name)
        TextView tvDiscountName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_at_less)
        TextView tvAtLess;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
