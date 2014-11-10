package com.KeanuWeather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Keanu on 2014-11-10 .
 */
public class KeanuWeatherOpenHelper extends SQLiteOpenHelper {
    /**
     * province建表语句
     */
    public static final String CREATE_PROVINCE = "create table provinces("
            +"province_id int not null identity(1,1) primary key,"
            +"province_name char(100) not null,"
            +"province_code char(20) not null)";
    /**
     * city建表语句
     */
    public static final String CREATE_CITY = "create table cities("
            +"city_id int not null identity(1,1) primary key,"
            +"city_name char(100) not null,"
            +"city_code char(20) not null,"
            +"province_id int)";
    /**
     * county建表语句
     */
    public static final String CREATE_COUNTY = "create table counties("
            +"county_id int not null identity(1,1) primary key,"
            +"county_name char(100) not null,"
            +"county_code char(20) not null,"
            +"city_id int)";
    public KeanuWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
