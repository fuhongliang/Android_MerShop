package cn.ifhu.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.R;

/**
 * @author fuhongliang
 */
public class ProductAdapter extends BaseAdapter {
    public ArrayList<String> mDataList;
    public Context mContext;

    public void updateData(ArrayList<String> mDataList){
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public ProductAdapter(ArrayList<String> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
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
            convertView = inflater.inflate(R.layout.item_product, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
            viewHolder.tvOriginalPrice = convertView.findViewById(R.id.tv_original_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvPrice.setText(mDataList.get(position));
        viewHolder.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        ImageView ivProductImage;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_original_price)
        TextView tvOriginalPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
