package com.example.tmd_setting_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SensorSetting extends AppCompatActivity {

    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData/Configuration";
    final static String foldername_upper = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData";
    final static String filename = "SensorSetting.txt";
    String [] txt_input_sensor = null;
    Integer [] int_input_sensor = null;
    String temp_input = ""; // 한줄씩 읽기
    public int start_num = 0;
    private static final String TAG = "SensorSetting";



    int gra_check = 0;
    int gyro_check = 0;
    int Lacc_check = 0;
    int acc_check = 0;
    int light_check = 0;
    int mag_check = 0;
    int baro_check = 0;
    int rot_check = 0;
    int ori_check = 0;
    int prox_check = 0;
    int step_check = 0;
    int temper_check = 0;
    int press_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_setting);
        Switch gra = (Switch)findViewById(R.id.Gravity);
        Switch gyro = (Switch)findViewById(R.id.Gyroscope);
        Switch Lacc = (Switch)findViewById(R.id.LinearAcceleration);
        Switch acc = (Switch)findViewById(R.id.Acceleration);
        Switch light = (Switch)findViewById(R.id.Light);
        Switch mag = (Switch)findViewById(R.id.MagneticField);
        Switch baro = (Switch)findViewById(R.id.Barometer);
        Switch rot = (Switch)findViewById(R.id.Rotation);
        Switch ori = (Switch)findViewById(R.id.Orientation);
        Switch prox = (Switch)findViewById(R.id.Proximity);
        Switch step = (Switch)findViewById(R.id.Step);
        Switch temper = (Switch)findViewById(R.id.Temperature);
        Switch press = (Switch)findViewById(R.id.GameRot);

        ///////////////////////////////////////////////////////////////////////
        if(start_num == 0){

            //저장된 mode 읽기
            temp_input = ReadTextFile(foldername+"/"+filename);

            if(TextUtils.isEmpty(temp_input)){

            }
            else{
                temp_input = temp_input.replaceAll("\n", "");
                txt_input_sensor = temp_input.split(",");
                int_input_sensor = new Integer[txt_input_sensor.length];


                for(int i = 0; i<txt_input_sensor.length; i++){
                    switch (i){
                        case 0:
                            gra_check = CheckSetting(gra, txt_input_sensor[i]);
                            break;
                        case 1:
                            gyro_check = CheckSetting(gyro, txt_input_sensor[i]);
                            break;
                        case 2:
                            Lacc_check = CheckSetting(Lacc, txt_input_sensor[i]);
                            break;
                        case 3:
                            acc_check = CheckSetting(acc, txt_input_sensor[i]);
                            break;
                        case 4:
                            light_check = CheckSetting(light, txt_input_sensor[i]);
                            break;
                        case 5:
                            mag_check = CheckSetting(mag, txt_input_sensor[i]);
                            break;
                        case 6:
                            baro_check = CheckSetting(baro, txt_input_sensor[i]);
                            break;
                        case 7:
                            rot_check = CheckSetting(rot, txt_input_sensor[i]);
                            break;
                        case 8:
                            ori_check = CheckSetting(ori, txt_input_sensor[i]);
                            break;
                        case 9:
                            prox_check = CheckSetting(prox, txt_input_sensor[i]);
                            break;
                        case 10:
                            step_check = CheckSetting(step, txt_input_sensor[i]);
                            break;
                        case 11:
                            temper_check = CheckSetting(temper, txt_input_sensor[i]);
                            break;
                        case 12:
                            press_check = CheckSetting(press, txt_input_sensor[i]);
                            break;
                    }
                }
            }
            start_num = start_num + 1;
        }


        /////////////////////////////////////////////////////////////////////////////////////////////


        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acc_check = CheckState(acc);
            }
        });

        gra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gra_check = CheckState(gra);
            }
        });

        gyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyro_check = CheckState(gyro);
            }
        });

        Lacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lacc_check = CheckState(Lacc);
            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light_check = CheckState(light);
            }
        });

        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mag_check = CheckState(mag);
            }
        });

        baro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baro_check = CheckState(baro);
            }
        });

        rot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rot_check = CheckState(rot);
            }
        });

        ori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ori_check = CheckState(ori);
            }
        });

        prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prox_check = CheckState(prox);
            }
        });

        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step_check = CheckState(step);
            }
        });

        temper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temper_check = CheckState(temper);
            }
        });

        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                press_check = CheckState(press);
            }
        });
    }

    public int CheckState(Switch temp_switch){
        int temp_num = 0;
        if(temp_switch.isChecked()){
           temp_num= 1;
        }
        else{
            temp_num = 0;
        }
        return  temp_num;
    }

    public int CheckSetting(Switch temp_switch, String temp_string){
        int temp_int = Integer.parseInt(temp_string);
        int temp_check;
        boolean temp_bool = (temp_int != 0);
        temp_switch.setChecked(temp_bool);
        temp_check = CheckState(temp_switch);
        return temp_check;
    }


    public void onLocationClicked(View v){

        temp_input = Integer.toString(gra_check) + "," + Integer.toString(gyro_check) + "," + Integer.toString(Lacc_check)
                + "," + Integer.toString(acc_check) + "," +  Integer.toString(light_check) + "," + Integer.toString(mag_check)
                + "," + Integer.toString(baro_check) + "," + Integer.toString(rot_check) + "," + Integer.toString(ori_check)
                + "," + Integer.toString(prox_check) + "," + Integer.toString(step_check) + "," + Integer.toString(temper_check)
                + "," + Integer.toString(press_check);
        WriteTextFile(foldername, filename, temp_input);

        Intent intent = new Intent(this, SensorSetting2.class);
        startActivity(intent);
    }

    public void WriteTextFile(String foldername, String filename, String contents){
        try{
            File dir = new File (foldername);
            File f = new File(foldername+"/"+filename);
            //디렉토리 폴더가 없으면 생성함
            File dir_upper = new File (foldername_upper);
            if(!dir_upper.exists()){
                dir_upper.mkdir();
            }
            if(!dir.exists()){
                dir.mkdir();
            }

            //저장할 string이 empty 면 해당 파일 삭제
            if(TextUtils.isEmpty(contents)){
                f.delete();
            }
            else{
                //파일 output stream 생성
                FileOutputStream fos = new FileOutputStream(foldername+"/"+filename, false);
                //파일쓰기
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                writer.write(contents);
                writer.flush();
                writer.close();
                fos.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String ReadTextFile(String path){
        StringBuffer strBuffer = new StringBuffer();
        try{
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                strBuffer.append(line+"\n");
            }
            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
    }
}