package com.android.layerselectview.widget.layerselectview;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * adapter 点击回调接口
 */

public interface IOnListClickListener {

    @IntDef({ITEM_TAG0, ITEM_TAG1, ITEM_TAG2, ITEM_TAG3})
    @Retention(RetentionPolicy.SOURCE)
    @interface ItemView {
    }

    int ITEM_TAG0 = 0;
    int ITEM_TAG1 = 1;
    int ITEM_TAG2 = 2;
    int ITEM_TAG3 = 3;

    //item点击事件
    void onItemClick(int position);

    //item点击事件，返回model
    void onItemClick(Object object);

    //可根据tag来区分点击的是item内部哪个控件
    void onTagClick(@ItemView int tag, int position);
}
