package com.KeanuWeather.app.util;

/**
 * Created by Keanu on 2014-11-10 .
 */
public interface HttpCallbackListener {
    public void onFinish(String response);
    public void onError(Exception e);
}
