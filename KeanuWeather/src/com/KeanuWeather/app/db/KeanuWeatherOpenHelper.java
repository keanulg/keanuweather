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
            +"province_id integer primary key autoincrement,"
            +"province_name char(100),"
            +"province_code char(20))";
    /**
     * city建表语句
     */
    public static final String CREATE_CITY = "create table cities("
            +"city_id integer primary key autoincrement,"
            +"city_name char(100),"
            +"city_code char(20),"
            +"province_id integer)";
    /**
     * county建表语句
     */
    public static final String CREATE_COUNTY = "create table counties("
            +"county_id integer primary key autoincrement,"
            +"county_name char(100) not null,"
            +"county_code char(20) not null,"
            +"city_id integer)";
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
