package com.android.layerselectview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.layerselectview.R;
import com.android.layerselectview.db.AddressLiteOrm;
import com.android.layerselectview.model.Area;
import com.android.layerselectview.model.City;
import com.android.layerselectview.model.Country;
import com.android.layerselectview.model.Province;
import com.android.layerselectview.model.Street;
import com.android.layerselectview.util.ToastMaster;
import com.android.layerselectview.widget.layerselectview.IOnListClickListener;
import com.android.layerselectview.widget.layerselectview.LayerSelectView;
import com.android.layerselectview.widget.layerselectview.OnListClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LayerSelectView mLayerSelectView;

    private AddressLiteOrm mDBHelper;
    private Country mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

    }

    @Override
    protected void onDestroy() {
        mDBHelper.closeDB();
        super.onDestroy();
    }

    private void initData() {
        mDBHelper = new AddressLiteOrm(this);
        mDBHelper.updateDB(this);

        final List<Province> provinceList = mDBHelper.getProvince();

        mCountry = new Country();
        mCountry.setId("1");
        mCountry.setName("中国");
        mCountry.setPid("0");
        if (provinceList != null && !provinceList.isEmpty()) {
            mCountry.setProvinceList(provinceList);
        }
    }

    private void initView() {
        mLayerSelectView = findViewById(R.id.layerSelectView);
        mLayerSelectView.addLayer(mCountry);
        mLayerSelectView.setContentListClick(mContentListClick);
    }

    private IOnListClickListener mContentListClick = new OnListClickListener() {
        @Override
        public void onItemClick(Object object) {
            if (object instanceof Province) {
                final Province province = (Province) object;
                final List<City> cityList = mDBHelper.getCity(province.getId());

                if (cityList != null && !cityList.isEmpty()) {
                    province.setCityList(cityList);
                    mLayerSelectView.addLayer(province);
                } else {
                    ToastMaster.toast(province.getName());
                }
            } else if (object instanceof City) {
                final City city = (City) object;
                final List<Area> areaList = mDBHelper.getArea(city.getId());

                if (areaList != null && !areaList.isEmpty()) {
                    city.setAreaList(areaList);
                    mLayerSelectView.addLayer(city);
                } else {
                    ToastMaster.toast(city.getName());
                }
            } else if (object instanceof Area) {
                final Area area = (Area) object;
                final List<Street> streetList = mDBHelper.getStreet(area.getId());

                if (streetList != null && !streetList.isEmpty()) {
                    area.setStreetList(streetList);
                    mLayerSelectView.addLayer(area);
                } else {
                    ToastMaster.toast(area.getName());
                }
            } else if (object instanceof Street) {
                final Street street = (Street) object;
                ToastMaster.toast(street.getName());
            }
        }
    };
}
