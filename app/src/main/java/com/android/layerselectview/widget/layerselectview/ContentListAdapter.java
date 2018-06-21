package com.android.layerselectview.widget.layerselectview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.layerselectview.R;

import java.util.List;

/**
 * 当层内容列表
 */

public class ContentListAdapter extends RecyclerView.Adapter {

    private List<LayerInfo> mDatas;
    private LayoutInflater mInflater;
    private IOnListClickListener mListClick;

    public ContentListAdapter(Context context, List<LayerInfo> datas) {
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_content_list, parent, false));
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

        holder.itemView.setTag(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(mClick);
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        private BaseViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setListClick(IOnListClickListener listClick) {
        this.mListClick = listClick;
    }

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListClick != null) {
                final int position = (int) v.getTag();
                final LayerInfo layer = mDatas.get(position);
                mListClick.onItemClick(layer);
            }
        }
    };
}
