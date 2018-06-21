package com.android.layerselectview.model;

import com.android.layerselectview.widget.layerselectview.LayerInfo;

import java.util.ArrayList;
import java.util.List;

public class Country extends LayerInfo {

    private String id;//地区Id
    private String pid;//父级ID
    private String name;//名称

    private List<LayerInfo> layerChildList;

    @Override
    public String getLayerId() {
        return id;
    }

    @Override
    public String getFatherId() {
        return pid;
    }

    @Override
    public String getLayerName() {
        return name;
    }

    @Override
    public List<LayerInfo> getLayerChildList() {
        return layerChildList;
    }

    public void setProvinceList(List<Province> provinceList) {
        if (layerChildList == null) {
            layerChildList = new ArrayList<>();
        } else {
            layerChildList.clear();
        }
        layerChildList.addAll(provinceList);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
