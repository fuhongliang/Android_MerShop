package cn.ifhu.mershop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.ifhu.R;
import cn.ifhu.mershop.bean.ProductManageBean;

/**
 * @author fuhongliang
 */
public class CategoryAdapter extends BaseAdapter {
    public List<ProductManageBean.ClassListBean> mDataList;
    public Context mContext;
    public int mCurPosition = 0;
    ItemOnclick itemOnclick;

    public CategoryAdapter(List<ProductManageBean.ClassListBean> mDataList, Context mContext,  ItemOnclick itemOnclick) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.itemOnclick = itemOnclick;
    }

    public void setmDataList(List<ProductManageBean.ClassListBean> mDataList) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv_line);
            viewHolder.textView = convertView.findViewById(R.id.tv_categroy);
            viewHolder.linearLayout = convertView.findViewById(R.id.ll_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (mCurPosition == position) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.selected_black));
            viewHolder.linearLayout.setBackgroundColor(Color.WHITE);
        } else {
            viewHolder.linearLayout.setBackgroundColor(mContext.getColor(R.color.category_color));
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.unselect_grey));
        }
        viewHolder.textView.setText(mDataList.get(position).getStc_name());
        viewHolder.linearLayout.setOnClickListener(v -> {
            mCurPosition = position;
            itemOnclick.onClickItem(position);
        });

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;
    }

    public interface ItemOnclick {
        /**
         * 点击事件
         * @param position 位置
         */
        void onClickItem(int position);
    }
}
