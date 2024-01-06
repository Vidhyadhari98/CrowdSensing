package com.su.iot.crowdsensing.helper;


import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPlatformTask implements Callable<JSONObject> {

  private static final String URL = "https://iot-project-408501.ew.r.appspot.com/platform/";

  private final String inputParameter;

  public GetPlatformTask(String inputParameter) {
    this.inputParameter = inputParameter;
  }

  @Override
  public JSONObject call() {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(URL + inputParameter + "/")
        .build();

    try (Response response = client.newCall(request).execute()) {
      String result = response.body().string();

      return new JSONObject(result);

    } catch (Exception e) {
      Log.e(getClass().getName(), "Error getting platform", e);
    }

    return null;
  }
}
