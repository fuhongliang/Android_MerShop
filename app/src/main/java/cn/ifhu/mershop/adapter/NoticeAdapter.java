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
import cn.ifhu.mershop.bean.NoticeBean;

/**
 * @author fuhongliang
 */
public class NoticeAdapter extends BaseAdapter {

    List<NoticeBean> noticeBeanList;
    Context mContext;

    public NoticeAdapter(List<NoticeBean> noticeBeanList, Context mContext) {
        this.noticeBeanList = noticeBeanList;
        this.mContext = mContext;
    }

    public void setvouCherBeanList(List<NoticeBean> noticeBeanList) {
        this.noticeBeanList = noticeBeanList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return noticeBeanList == null ? 0 : noticeBeanList.size();
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
            convertView = layoutInflater.inflate(R.layout.item_system_notification, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNoticeContent.setText(noticeBeanList.get(position).getSm_content());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_notice_title)
        TextView tvNoticeTitle;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.tv_notice_content)
        TextView tvNoticeContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
