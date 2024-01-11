package com.su.iot.crowdsensing.helper;


import org.json.JSONObject;

import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetTrainTask implements Callable<JSONObject> {

    private static final String URL = "https://iot-project-20240110.ew.r.appspot.com/nextTrain/";

    private final String inputParameter;

    public GetTrainTask(String inputParameter) {
        this.inputParameter = inputParameter;
    }

    @Override
    public JSONObject call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL + inputParameter + "/")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();

            return new JSONObject(result);
        } catch (Exception e) {
            throw e;
        }

    }
}
