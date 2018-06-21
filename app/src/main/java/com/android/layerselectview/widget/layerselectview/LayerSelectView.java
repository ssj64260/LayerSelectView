package com.android.layerselectview.widget.layerselectview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Space;

import com.android.layerselectview.R;

import java.util.ArrayList;
import java.util.List;

public class LayerSelectView extends LinearLayoutCompat {

    private RecyclerView rvLayerList;//选择的层
    private RecyclerView rvContentList;//当前层的内容

    private List<LayerInfo> mLayerList;
    private LayerListAdapter mLayerAdapter;
    private List<LayerInfo> mContentList;
    private ContentListAdapter mContentAdapter;

    private DividerItemDecoration mDivider;
    private int mContentMarginTop;

    private int mSelectedColor;
    private int mUnselectedColor;

    public LayerSelectView(Context context) {
        this(context, null, 0);
    }

    public LayerSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayerSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayerSelectView);
        final int layerHeight =
                typedArray.getDimensionPixelSize(R.styleable.LayerSelectView_layer_height, 40);
        final boolean isMatchParent =
                typedArray.getBoolean(R.styleable.LayerSelectView_content_height_match_parent, false);
        mContentMarginTop = typedArray.getDimensionPixelSize(R.styleable.LayerSelectView_content_margin_top, 0);
        mSelectedColor = typedArray.getColor(R.styleable.LayerSelectView_layer_selected_color, Color.GRAY);
        mUnselectedColor = typedArray.getColor(R.styleable.LayerSelectView_layer_unselected_color, Color.GRAY);
        typedArray.recycle();

        initData(context);
        initView(context, layerHeight, isMatchParent);

    }

    private void initData(Context context) {
        mDivider = new DividerItemDecoration(context, 1, R.color.color_F5F5F5);
        mDivider.setPaddingLeft(16);

        mLayerList = new ArrayList<>();
        mLayerAdapter = new LayerListAdapter(context, mLayerList);
        mLayerAdapter.setColor(mSelectedColor, mUnselectedColor);
        mLayerAdapter.setListClick(mLayerListClick);

        mContentList = new ArrayList<>();
        mContentAdapter = new ContentListAdapter(context, mContentList);
    }

    private void initView(Context context, int layerHeight, boolean isMatchParent) {
        final LayoutParams layerLayoutParams
                = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layerHeight);
        final LayoutParams contentLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                isMatchParent ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT);

        rvLayerList = new RecyclerView(context);
        rvLayerList.setLayoutParams(layerLayoutParams);
        rvLayerList.setScrollBarSize(0);
        rvLayerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvLayerList.setAdapter(mLayerAdapter);

        final Space space = new Space(context);
        space.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContentMarginTop));

        rvContentList = new RecyclerView(context);
        rvContentList.setLayoutParams(contentLayoutParams);
        rvContentList.setLayoutManager(new LinearLayoutManager(context));
        rvContentList.addItemDecoration(mDivider);
        rvContentList.setAdapter(mContentAdapter);

        addView(rvLayerList);
        addView(space);
        addView(rvContentList);
    }

    public void addLayer(LayerInfo layer) {
        if (layer != null) {
            mLayerList.add(layer);
        }
        mLayerAdapter.notifyDataSetChanged();

        final List<LayerInfo> childList = layer.getLayerChildList();
        mContentList.clear();
        if (childList != null && !childList.isEmpty()) {
            mContentList.addAll(childList);
        }
        mContentAdapter.notifyDataSetChanged();
    }

    public void setContentListClick(IOnListClickListener listClick) {
        if (mContentAdapter != null) {
            mContentAdapter.setListClick(listClick);
        }
    }

    private IOnListClickListener mLayerListClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            if (position < mLayerList.size() - 1) {
                final LayerInfo layer = mLayerList.get(position);

                final List<LayerInfo> layerList = new ArrayList<>(mLayerList.subList(0, position));
                mLayerList.clear();
                mLayerList.addAll(layerList);

                addLayer(layer);
            }
        }
    };
}
