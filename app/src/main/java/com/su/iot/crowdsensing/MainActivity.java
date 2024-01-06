package com.su.iot.crowdsensing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.su.iot.crowdsensing.helper.GetPlatformsTask;
import com.su.iot.crowdsensing.helper.TaskRunner;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new GetPlatformsTask(), result -> {
            try {
                String[] platforms = new String[result.length()];
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObject = result.getJSONObject(i);
                    platforms[i] = jsonObject.getString("name");
                }

                switchToSelectPlatformsActivity(platforms);
            } catch (Exception e) {
                Log.e(getClass().getName(), "Error parsing JSON", e);
            }
        });
    }

    private void switchToSelectPlatformsActivity(String[] items) {
        Intent moveScreenIntent = new Intent(MainActivity.this, SelectPlatformsActivity.class);
        moveScreenIntent.putExtra("stations", items);
        startActivity(moveScreenIntent);
    }
}