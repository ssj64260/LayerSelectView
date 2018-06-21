package com.android.layerselectview.widget.layerselectview;

import java.util.List;

/**
 * 层级信息
 */
public abstract class LayerInfo {

    private boolean isSelect = false;//是否被选择

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    abstract public String getLayerId();//获取ID

    abstract public String getFatherId();//获取父级ID

    abstract public String getLayerName();//获取层名称

    abstract public List<LayerInfo> getLayerChildList();//获取子列表
}
