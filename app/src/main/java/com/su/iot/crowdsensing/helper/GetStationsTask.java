package com.su.iot.crowdsensing.helper;


import android.util.Log;

import org.json.JSONArray;

import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetStationsTask implements Callable<JSONArray> {

    private static final String URL = "https://iot-project-20240110.ew.r.appspot.com/station/";

    @Override
    public JSONArray call() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();

            return new JSONArray(result);

        } catch (Exception e) {
            Log.e(getClass().getName(), "Error getting stations", e);
        }

        return null;
    }
}
