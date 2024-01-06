package com.su.iot.crowdsensing.helper;


import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPlatformSensorsTask {

  private final Logger logger = LoggerFactory.getLogger(GetPlatformSensorsTask.class);

  private static final String URL = "https://iot-project-408501.ew.r.appspot.com/platform/";

  public JSONArray getPlatformSensors(String platformName) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(URL + platformName + "/sensor/")
        .build();

    try (Response response = client.newCall(request).execute()) {
      String result = response.body().string();

      return new JSONArray(result);

    } catch (IOException | JSONException e) {
      logger.error("Error getting platform", e);
    }

    return null;
  }
}
