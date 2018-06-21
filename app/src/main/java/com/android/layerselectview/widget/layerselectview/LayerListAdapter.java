package com.android.layerselectview.widget.layerselectview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.layerselectview.R;

import java.util.List;

/**
 * 层级列表
 */

public class LayerListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<LayerInfo> mDatas;
    private LayoutInflater mInflater;
    private IOnListClickListener mListClick;

    private int mSelectedColor = Color.BLACK;
    private int mUnselectedColor = Color.BLACK;

    public LayerListAdapter(Context context, List<LayerInfo> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_layer_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindItem((BaseViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void bindItem(BaseViewHolder holder, final int position) {
        final LayerInfo data = mDatas.get(position);
        final String name = data.getLayerName();

        holder.tvName.setText(name);
        if (position == mDatas.size() - 1) {
            holder.tvName.setTextColor(mSelectedColor);
        } else {
            holder.tvName.setTextColor(mUnselectedColor);
        }

        holder.ivArrow.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);

        holder.itemView.setTag(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(mClick);

    }

    private class BaseViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivArrow;
        private TextView tvName;

        private BaseViewHolder(View itemView) {
            super(itemView);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setListClick(IOnListClickListener listClick) {
        this.mListClick = listClick;
    }

    public void setColor(int selectedColor, int unselectedColor) {
        this.mSelectedColor = selectedColor;
        this.mUnselectedColor = unselectedColor;
    }

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListClick != null) {
                final int position = (int) v.getTag();
                mListClick.onItemClick(position);
            }
        }
    };
}
