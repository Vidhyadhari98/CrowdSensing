package com.su.iot.crowdsensing;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.su.iot.crowdsensing.domain.Train;
import com.su.iot.crowdsensing.view.TrainView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.json.JSONArray;
import org.json.JSONObject;

public class StationActivity extends AppCompatActivity {

    private TrainView trainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        String platformName = getIntent().getSerializableExtra("station", String.class);
        Train train = getIntent().getSerializableExtra("train", Train.class);

        TextView topText = findViewById(R.id.topText);
        topText.setText(platformName);

        findViewById(R.id.platformView);
        trainView = findViewById(R.id.trainView);
        trainView.setTrain(train);

        connect();
    }

    public void connect() {
        String brokerUrl = "tcp://test.mosquitto.org:1883";

        try (MqttClient mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), null)) {
            mqttClient.connect(getMqttConnectOptions());
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(getClass().getName(), "Connection lost: ", cause);
                }

                @Override
                public void messageArrived(String topic, org.eclipse.paho.client.mqttv3.MqttMessage message) throws Exception {
                    Log.d(getClass().getName(), "Message received: " + new String(message.getPayload()));

                    JSONObject event = new JSONObject(message.toString());
                    JSONArray coaches = event.getJSONArray("coaches");
                    for (int i = 0; i < coaches.length(); i++) {
                        JSONObject instance = (JSONObject) coaches.get(i);
                        int index = instance.getInt("position") - 1;
                        double crowdedness = instance.getDouble("crowdedness");
                        trainView.getTrain().updateCrowdedValue(index, crowdedness);
                    }

                    trainView.invalidate();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used in this example
                }
            });

            subscribeToTopic(mqttClient, "0207/train/" + trainView.getTrain().getTrainId());

        } catch (Exception e) {
            Log.e(getClass().getName(), "Error connecting to MQTT broker: ", e);
        }
    }

    @NonNull
    private static MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        return options;
    }

    private void subscribeToTopic(MqttClient mqttClient, String topic) {
        try {
            mqttClient.subscribe(topic, 1);
            Log.d(getClass().getName(), "Subscribed to topic: " + topic);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error subscribing to topic: ", e);
        }
    }
}