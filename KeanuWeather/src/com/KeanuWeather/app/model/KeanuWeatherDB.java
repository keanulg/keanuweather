package com.KeanuWeather.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.KeanuWeather.app.db.KeanuWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keanu on 2014-11-10 .
 */
public class KeanuWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "keanu_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static KeanuWeatherDB keanuWeatherDB;
    private SQLiteDatabase db;
    /**
     * 将构造方法私有化
     */
    private KeanuWeatherDB(Context context){
        KeanuWeatherOpenHelper dbHelper = new KeanuWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 获取KeanuWeatherDB的实例
     */
    public synchronized static KeanuWeatherDB getInstence(Context context){
        if (keanuWeatherDB==null){
            keanuWeatherDB = new KeanuWeatherDB(context);
        }
        return keanuWeatherDB;
    }
    /**
     * 将province实例存储到数据库
     */
    public void saveProvince(Province province){
        if (province!=null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("provinces",null,values);
        }
    }
    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        String sql = "select * from  provinces";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int provinceId = cursor.getInt(cursor.getColumnIndex("province_id"));
                String provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
                String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
                Province province = new Province();
                province.setProvinceId(provinceId);
                province.setProvinceCode(provinceCode);
                province.setProvinceName(provinceName);
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }
    /**
     * 将city实例存储到数据库
     */
    public void saveCity(City city){
        ContentValues values = new ContentValues();
        values.put("city_name",city.getCityName());
        values.put("city_code",city.getCityCode());
        values.put("province_id",city.getProvinceId());
        db.insert("cities",null,values);
    }

    /**
     * 从数据库读取全国所有的城市信息
     */
    public List<City> loadCities(){
        List<City> list = new ArrayList<City>();
        String sql = "select * from  cities";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int cityId = cursor.getInt(cursor.getColumnIndex("city_id"));
                String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
                String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
                int provinceId = cursor.getInt(cursor.getColumnIndex("province_id"));
                City city = new City();
                city.setCityId(cityId);
                city.setCityName(cityName);
                city.setCityCode(cityCode);
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }
    /**
     * 将county实例存储到数据库
     */
    public void saveCounty(County county){
        ContentValues values = new ContentValues();
        values.put("county_name",county.getCountyId());
        values.put("county_code",county.getCountyCode());
        values.put("city_id",county.getCityId());
        db.insert("counties",null,values);
    }
    /**
     * 从数据库读取全国所有的县信息
     */
    public List<County> loadcounties(){
        List<County> list = new ArrayList<County>();
        String sql = "select * from  counties";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int countyId = cursor.getInt(cursor.getColumnIndex("county_id"));
                String countyName = cursor.getString(cursor.getColumnIndex("county_name"));
                String countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
                int cityId = cursor.getInt(cursor.getColumnIndex("city_id"));
                County county = new County();
                county.setCountyId(countyId);
                county.setCountyName(countyName);
                county.setCountyCode(countyCode);
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
