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

public class SensorSetting2 extends AppCompatActivity {
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData/Configuration";
    final static String foldername_upper = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData";
    final static String filename = "SensorSetting2.txt";
    String [] txt_input_sensor2 = null;
    Integer [] int_input_sensor2 = null;
    String temp_input = ""; // 한줄씩 읽기
    public int start_num = 0;

    int lat_check = 0;
    int lng_check = 0;
    int alt_check = 0;
    int accuracy_check = 0;
    int provider_check = 0;
    int bear_check = 0;
    int satel_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_setting2);

        Switch lat = (Switch)findViewById(R.id.Latitude);
        Switch lng = (Switch)findViewById(R.id.Longitude);
        Switch alt = (Switch)findViewById(R.id.Altitude);
        Switch accuracy = (Switch)findViewById(R.id.Accuracy);
        Switch provider = (Switch)findViewById(R.id.Provider);
        Switch bear = (Switch)findViewById(R.id.Bearing);
        Switch satel = (Switch)findViewById(R.id.Satellites);

        if(start_num == 0){

            //저장된 mode 읽기
            temp_input = ReadTextFile(foldername+"/"+filename);

            if(TextUtils.isEmpty(temp_input)){

            }
            else{
                temp_input = temp_input.replaceAll("\n", "");
                txt_input_sensor2 = temp_input.split(",");
                int_input_sensor2 = new Integer[txt_input_sensor2.length];


                for(int i = 0; i<txt_input_sensor2.length; i++){
                    switch (i){
                        case 0:
                            lat_check = CheckSetting(lat, txt_input_sensor2[i]);
                            break;
                        case 1:
                            lng_check = CheckSetting(lng, txt_input_sensor2[i]);
                            break;
                        case 2:
                            alt_check = CheckSetting(alt, txt_input_sensor2[i]);
                            break;
                        case 3:
                            accuracy_check = CheckSetting(accuracy, txt_input_sensor2[i]);
                            break;
                        case 4:
                            provider_check = CheckSetting(provider, txt_input_sensor2[i]);
                            break;
                        case 5:
                            bear_check = CheckSetting(bear, txt_input_sensor2[i]);
                            break;
                        case 6:
                            satel_check = CheckSetting(satel, txt_input_sensor2[i]);
                            break;
                    }
                }
            }
            start_num = start_num + 1;
        }

        lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lat_check = CheckState(lat);
            }
        });

        lng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lng_check = CheckState(lng);
            }
        });

        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alt_check = CheckState(alt);
            }
        });

        accuracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accuracy_check = CheckState(accuracy);
            }
        });

        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider_check = CheckState(provider);
            }
        });

        bear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bear_check = CheckState(bear);
            }
        });

        satel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                satel_check = CheckState(satel);
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

    public void onSaveSensorClicked(View v){

        temp_input = Integer.toString(lat_check) + "," + Integer.toString(lng_check) + "," + Integer.toString(alt_check)
                + "," + Integer.toString(accuracy_check) + "," + Integer.toString(provider_check) + "," + Integer.toString(bear_check)
                + "," + Integer.toString(satel_check);
        WriteTextFile(foldername, filename, temp_input);
        Intent intent = new Intent(this, MainActivity.class);
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