package com.su.iot.crowdsensing.helper;


import java.io.IOException;
import java.util.concurrent.Callable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetTrainTask implements Callable<JSONObject> {

  private final Logger logger = LoggerFactory.getLogger(GetTrainTask.class);

  private static final String URL = "https://iot-project-408501.ew.r.appspot.com/nextTrain/";

  private final String inputParameter;

  public GetTrainTask(String inputParameter) {
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

    } catch (IOException | JSONException e) {
      logger.error("Error getting next train", e);
    }

    return null;
  }
}
