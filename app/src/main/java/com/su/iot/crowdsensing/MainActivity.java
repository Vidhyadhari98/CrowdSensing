package com.su.iot.crowdsensing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.su.iot.crowdsensing.domain.Platform;
import com.su.iot.crowdsensing.domain.Sensor;
import com.su.iot.crowdsensing.helper.GetPlatformSensorsTask;
import com.su.iot.crowdsensing.helper.GetPlatformTask;
import com.su.iot.crowdsensing.helper.TaskRunner;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RangedBeacon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private boolean switchingMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskRunner taskRunner = new TaskRunner();

        taskRunner.executeAsync(new GetPlatformTask("kista"), result -> {
            try {
                String name = result.getString("name");
                double length = result.getDouble("length");

                JSONArray sensors = new GetPlatformSensorsTask().getPlatformSensors(name);

                Platform platform = new Platform(name, length);
                for (int i = 0; i < sensors.length(); i++) {
                    JSONObject instance = sensors.getJSONObject(i);
                    Sensor sensor = new Sensor(instance.getString("uuid"), instance.getDouble("position"), instance.getDouble("height"));
                    platform.addSensor(sensor);
                }

                switchPlatformActivity(platform);
            } catch (JSONException e) {
                logger.error("Error parsing JSON", e);
            }
        });
    }

    private void switchPlatformActivity(Platform platform) {
        if (switchingMode) {
            return;
        }
        switchingMode = true;
        Intent moveScreenIntent = new Intent(MainActivity.this, PlatformActivity.class);
        moveScreenIntent.putExtra("platform", platform);
        startActivity(moveScreenIntent);
    }
}