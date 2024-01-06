package com.su.iot.crowdsensing;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.su.iot.crowdsensing.domain.Train;
import com.su.iot.crowdsensing.view.PlatformView;
import com.su.iot.crowdsensing.view.TrainView;

public class PlatformActivity extends AppCompatActivity {

    private TrainView trainView;
    private PlatformView platformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);

        String platformName = getIntent().getSerializableExtra("platform", String.class);
        Train train = getIntent().getSerializableExtra("train", Train.class);

        TextView topText = findViewById(R.id.topText);
        topText.setText(platformName);

        platformView = findViewById(R.id.platformView);
        trainView = findViewById(R.id.trainView);
        trainView.setTrain(train);
    }
}