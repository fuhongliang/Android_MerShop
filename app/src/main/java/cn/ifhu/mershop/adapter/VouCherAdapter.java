package cn.ifhu.mershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.bean.VouCherBean;

/**
 * @author fuhongliang
 */
public class VouCherAdapter extends BaseAdapter {

    List<VouCherBean> vouCherBeanList;
    Context mContext;
    OnClickItem onClickItem;

    public VouCherAdapter(List<VouCherBean> vouCherBeanList, Context mContext) {
        this.vouCherBeanList = vouCherBeanList;
        this.mContext = mContext;
    }

    public void setvouCherBeanList(List<VouCherBean> vouCherBeanList) {
        this.vouCherBeanList = vouCherBeanList;
        notifyDataSetChanged();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public int getCount() {
        return vouCherBeanList == null ? 0 : vouCherBeanList.size();
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
            convertView = layoutInflater.inflate(R.layout.item_voucher, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


    public interface OnClickItem {
        void editDiscount(int position);

        void deleteDiscount(int position);
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_voucher)
        ImageView ivVoucher;
        @BindView(R.id.tv_voucher_name)
        TextView tvVoucherName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.management)
        LinearLayout management;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
