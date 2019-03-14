package cn.ifhu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.R;

/**
 * @author fuhongliang
 */
public class ManageCategoryAdapter extends BaseAdapter {
    public ArrayList<String> mDataList;
    public Context mContext;
    public int mCurPosition = 0;
    ItemOnclick itemOnclick;

    public ManageCategoryAdapter(ArrayList<String> mDataList, Context mContext, ItemOnclick itemOnclick) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.itemOnclick = itemOnclick;
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
            convertView = inflater.inflate(R.layout.item_manage_category, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvCategroy.setText(mDataList.get(position));
        viewHolder.tvEdit.setOnClickListener(v -> {
            itemOnclick.onClickEditItem(position);
        });
        viewHolder.tvAddProduct.setOnClickListener(v -> {
            itemOnclick.onClickAddItem(position);
        });
        return convertView;
    }


    public interface ItemOnclick {
        void onClickEditItem(int position);

        void onClickAddItem(int position);
    }

    static class ViewHolder {
        @BindView(R.id.tv_categroy)
        TextView tvCategroy;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_add_product)
        TextView tvAddProduct;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
