package com.android.layerselectview.db;

import android.content.Context;

import com.android.layerselectview.model.Area;
import com.android.layerselectview.model.City;
import com.android.layerselectview.model.Province;
import com.android.layerselectview.model.Street;
import com.android.layerselectview.util.AssetsUtil;
import com.android.layerselectview.util.FileUtil;
import com.android.layerselectview.util.SDCardUtil;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 地址数据库管理
 */

public class AddressLiteOrm {

    private final String DB_NAME = "region.db";

    private final boolean DEBUGGABLE = true; // 是否输出log

    private LiteOrm liteOrm;

    public AddressLiteOrm(Context context) {
        liteOrm = LiteOrm.newSingleInstance(context, DB_NAME);
        liteOrm.setDebugged(DEBUGGABLE);
    }

    public boolean updateDB(Context context) {
        File dbFile = new File(SDCardUtil.getDataBaseDir(context, DB_NAME));
        InputStream inputStream = AssetsUtil.getInputStream(context, DB_NAME);

        if (inputStream != null) {
            try {
                long streamSize = inputStream.available();
                long dbFileSize = FileUtil.getFileSize(dbFile);
                if (streamSize != dbFileSize) {
                    FileUtil.copyFile(inputStream, dbFile.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dbFile.exists();
    }

    public List<Province> getProvince()  {
        return liteOrm.query(Province.class);
    }

    public List<City> getCity(String provinceId) {
        return liteOrm.query(new QueryBuilder<>(City.class)
                .where("pid = ?", provinceId));
    }

    public List<Area> getArea(String cityId) {
        return liteOrm.query(new QueryBuilder<>(Area.class)
                .where("pid = ?", cityId));
    }

    public List<Street> getStreet(String areaId) {
        return liteOrm.query(new QueryBuilder<>(Street.class)
                .where("pid = ?", areaId));
    }

    public void closeDB() {
        if (liteOrm != null) {
            liteOrm.close();
        }
    }
}
