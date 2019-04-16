package cn.ifhu.mershop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.Constants;
import cn.ifhu.mershop.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class ProductAdapter extends BaseAdapter {
    public List<ProductManageBean.GoodsListBean> mDataList;
    public Context mContext;
    onClickItem onClickItem;
    public void setmDataList(List<ProductManageBean.GoodsListBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public ProductAdapter(List<ProductManageBean.GoodsListBean> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    public void changeProductState(int position){
        ProductManageBean.GoodsListBean goodsListBean = mDataList.get(position);
        mDataList.get(position).setGoods_state(goodsListBean.getGoods_state() == 0?1:0);
        notifyDataSetChanged();
    }

    public void setOnClickItem(ProductAdapter.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvPrice.setText(Constants.unit + mDataList.get(position).getGoods_price());
        viewHolder.tvOriginalPrice.setText(Constants.unit + mDataList.get(position).getGoods_marketprice());
        viewHolder.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tvName.setText(mDataList.get(position).getGoods_name());
        viewHolder.tvReserve.setText("当前库存：" + mDataList.get(position).getGoods_storage());
        if (mDataList.get(position).getGoods_state() == 0) {
            viewHolder.tvChangeState.setText("上架");
            viewHolder.tvStateTip.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvChangeState.setText("下架");
            viewHolder.tvStateTip.setVisibility(View.INVISIBLE);
        }
      viewHolder.ivProductImage.load(mDataList.get(position).getGoods_image());
        if (onClickItem != null){
            viewHolder.tvChangeState.setOnClickListener(v -> onClickItem.changeState(position));

            viewHolder.tvEdit.setOnClickListener(v -> onClickItem.editProduct(position));

            viewHolder.tvDelete.setOnClickListener(v -> onClickItem.deleteProduct(position));
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_product_image)
        GlideImageView ivProductImage;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_original_price)
        TextView tvOriginalPrice;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_reserve)
        TextView tvReserve;
        @BindView(R.id.tv_change_state)
        TextView tvChangeState;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_state_tip)
        TextView tvStateTip;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickItem{
        void changeState(int position);
        void editProduct(int position);
        void deleteProduct(int position);
    }

}
