/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ifhu.fragments.operation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.ifhu.R;
import cn.ifhu.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.utils.DialogUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.view.ItemTouchHelper.ItemTouchHelperAdapter;
import cn.ifhu.view.ItemTouchHelper.ItemTouchHelperViewHolder;
import cn.ifhu.view.ItemTouchHelper.OnStartDragListener;


/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final List<String> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;
    public Context context;
    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener) {
        this.context = context;
        mDragStartListener = dragStartListener;
        mockData();
        mItems.addAll(mDataArray);
    }
    ArrayList<String> mDataArray = new ArrayList<>();
    public void mockData() {
        mDataArray.add("热销");
        mDataArray.add("特殊套餐");
        mDataArray.add("单人套餐");
        mDataArray.add("米饭");
        mDataArray.add("主食类");
        mDataArray.add("精美小吃");
        mDataArray.add("汤类");
        mDataArray.add("饮料");
        mDataArray.add("必买");
        mDataArray.add("点心");
        mDataArray.add("其他");
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.textView.setText(mItems.get(position));

        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemMove(holder.getAdapterPosition(),0);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

    }

    public void deleteItem(int position){
        DialogUtils.showConfirmDialog("是否删除此分类","该分类所有商品将会被删除", ((FragmentActivity)context).getSupportFragmentManager(),new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
                ToastHelper.makeText("点击了取消按钮", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }

            @Override
            public void ok() {
                mItems.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                ToastHelper.makeText("点击了确定按钮",Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView textView;
        public final ImageView handleView;
        public final ImageView ivDelete;
        public final ImageView ivTop;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            ivTop = (ImageView) itemView.findViewById(R.id.iv_top);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
