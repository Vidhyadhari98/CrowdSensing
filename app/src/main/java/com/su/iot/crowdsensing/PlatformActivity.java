package com.su.iot.crowdsensing;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.su.iot.crowdsensing.domain.Platform;
import com.su.iot.crowdsensing.domain.Train;
import com.su.iot.crowdsensing.domain.TrainCoach;
import com.su.iot.crowdsensing.helper.GetTrainTask;
import com.su.iot.crowdsensing.helper.TaskRunner;
import com.su.iot.crowdsensing.view.TrainView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlatformActivity extends AppCompatActivity {

  private final Logger logger = LoggerFactory.getLogger(PlatformActivity.class);

  private TrainView trainView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_platform);

    Platform platform = getIntent().getSerializableExtra("platform", Platform.class);

    TextView topText = findViewById(R.id.topText);
    topText.setText(platform.getName());

    findViewById(R.id.platformView);
    trainView = findViewById(R.id.trainView);

    loadNextTrainOnPlatform(platform);
  }

  private void loadNextTrainOnPlatform(Platform platform) {
    TaskRunner taskRunner = new TaskRunner();

    taskRunner.executeAsync(new GetTrainTask(platform.getName()), result -> {
      try {
        JSONObject trainJson = result.getJSONObject("train");
        JSONArray trainCoachesJson = result.getJSONArray("carriages");
        Train train = new Train(trainJson.getInt("id"));

        for (int i = 0; i < trainCoachesJson.length(); i++) {
          JSONObject instance = trainCoachesJson.getJSONObject(i);
          TrainCoach trainCoach = new TrainCoach(instance.getInt("id"),
              instance.getInt("position"));
          train.addTrainCoach(trainCoach);
        }

        trainView.setTrain(train);
      } catch (JSONException e) {
        logger.error("Error parsing JSON", e);
      }
    });
  }
}