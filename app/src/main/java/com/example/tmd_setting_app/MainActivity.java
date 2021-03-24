package com.example.tmd_setting_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    public void onModeClicked(View v){
        Intent intent = new Intent(this, ModeSetting.class);
        startActivity(intent);
    }

    public void onSensorClicked(View v){
        Intent intent = new Intent(this, SensorSetting.class);
        startActivity(intent);
    }

    public void onPosClicked(View v){
        Intent intent = new Intent(this, PositionSetting.class);
        startActivity(intent);
    }

    public void onTimeClicked(View v){
        Intent intent = new Intent(this, TimeSetting.class);
        startActivity(intent);
    }

    public void onValClicked(View v){
        Intent intent = new Intent(this, ValidationSetting.class);
        startActivity(intent);
    }

    public void checkPermission(){
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


}