package com.su.iot.crowdsensing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.su.iot.crowdsensing.domain.Train;
import com.su.iot.crowdsensing.domain.TrainCoach;
import com.su.iot.crowdsensing.helper.GetTrainTask;
import com.su.iot.crowdsensing.helper.TaskRunner;

import org.json.JSONArray;
import org.json.JSONObject;


public class SelectPlatformsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_platform);

        String[] items = getIntent().getSerializableExtra("stations", String[].class);

        Spinner dynamicSpinner = findViewById(R.id.dynamic_spinner);
        Button button = findViewById(R.id.button);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items);
        dynamicSpinner.setAdapter(adapter);

        button.setOnClickListener(v -> loadNextTrainOnPlatformAndSwitch(String.valueOf(dynamicSpinner.getSelectedItem())));
    }

    private void loadNextTrainOnPlatformAndSwitch(String platformName) {
        TaskRunner taskRunner = new TaskRunner();

        taskRunner.executeAsync(new GetTrainTask(platformName), result -> {
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

                switchToPlatformActivity(platformName, train);
            } catch (Exception e) {
                Log.e(getClass().getName(), "Error loading next train platform", e);
            }
        });
    }

    private void switchToPlatformActivity(String selectedItem, Train train) {
        Intent intent= new Intent(SelectPlatformsActivity.this, PlatformActivity.class);
        intent.putExtra("platform", selectedItem);
        intent.putExtra("train", train);
        startActivity(intent);
    }
}