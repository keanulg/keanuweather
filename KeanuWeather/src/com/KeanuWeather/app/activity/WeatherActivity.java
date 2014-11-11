package com.KeanuWeather.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.KeanuWeather.app.R;
import com.KeanuWeather.app.util.HttpCallbackListener;
import com.KeanuWeather.app.util.HttpUtil;
import com.KeanuWeather.app.util.Utility;

/**
 * Created by Keanu on 2014-11-11 .
 */
public class WeatherActivity extends Activity {
    private TextView weather_areaName;
    private TextView publishTime;
    private TextView currentDate;
    private TextView weatherDesp;
    private TextView temp1;
    private TextView temp2;
    private Button changeCityButton;
    private Button refreshButton;
    private LinearLayout weather_info_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        weather_areaName = (TextView) findViewById(R.id.weather_areaName);
        publishTime = (TextView) findViewById(R.id.publishTime);
        currentDate = (TextView) findViewById(R.id.currentDate);
        weatherDesp = (TextView) findViewById(R.id.weatherDesp);
        temp1 = (TextView) findViewById(R.id.temp1);
        temp2 = (TextView) findViewById(R.id.temp2);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        changeCityButton = (Button) findViewById(R.id.changeCityButton);
        weather_info_layout = (LinearLayout) findViewById(R.id.weather_info_layout);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)){
            publishTime.setText("同步中.....");
            weather_info_layout.setVisibility(View.INVISIBLE);
            weather_areaName.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else {
            //加载本地天气信息
            showWeather();
        }
        switchCity();
        refreshWeather();
    }
    /**
     * 查询县级代号对应的天气代号
     */
    private void queryWeatherCode(String countyCode){
        String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        queryFromServer(address,"countyCode");
    }
    /**
     * 查询天气代号对应的天气
     */
    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address,"weatherCode");
    }

    private void queryFromServer(final String address,final String type){
        HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (type.equals("countyCode")){
                    if (!TextUtils.isEmpty(response)){
                        String[] array = response.split("\\|");
                        if (array!=null&&array.length==2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }else if (type.equals("weatherCode")){
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTime.setText("同步失败！");
                    }
                });
            }
        });

    }
    /**
     * 读取xml文件存储的天气信息
     */
    private void showWeather(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        weather_areaName.setText(preferences.getString("city_name",""));
        publishTime.setText("今天"+preferences.getString("publish_time","")+"发布");
        currentDate.setText(preferences.getString("curremt_data",""));
        weatherDesp.setText(preferences.getString("weather_desp",""));
        temp1.setText(preferences.getString("temp1",""));
        temp2.setText(preferences.getString("temp2",""));
        weather_info_layout.setVisibility(View.VISIBLE);
        weather_areaName.setVisibility(View.VISIBLE);
    }
    /**
     * 切换城市
     */
    private void switchCity(){
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this,ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * 更新天气
     */
    private void refreshWeather(){
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishTime.setText("同步中...");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
                String weatherCode = preferences.getString("weather_code","");
                if (!TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }
            }
        });

    }
}
