package com.example.tmd_setting_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TimeSetting extends AppCompatActivity {

    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData/Configuration";
    final static String foldername_upper = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData";
    final static String filename = "TimeSetting.txt";
    //public String output = "";
    String [] txt_input_time = null;
    ArrayList<String> input_time = new ArrayList<String>();
    String temp_input = ""; // 한줄씩 읽기
    List<String> output_time = new ArrayList<String>();
    public int start_num = 0;

    public String time_t;
    public String inertial_t;
    public String location_t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);

        EditText time_type = (EditText) findViewById(R.id.time_type);
        EditText inertial_type = (EditText) findViewById(R.id.inertial_type);
        EditText location_type = (EditText) findViewById(R.id.location_type);

        time_type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    time_type.setHint("");
                else
                    time_type.setHint("Input Second");
            }
        });

        inertial_type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    inertial_type.setHint("");
                else
                    inertial_type.setHint("Input Hz");
            }
        });

        location_type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    location_type.setHint("");
                else
                    location_type.setHint("Input Second");
            }
        });
    }

    public void onTimeSaveClicked (View v){

        EditText time_type = (EditText) findViewById(R.id.time_type);
        EditText inertial_type = (EditText) findViewById(R.id.inertial_type);
        EditText location_type = (EditText) findViewById(R.id.location_type);

        time_t = time_type.getText().toString().trim();
        inertial_t = inertial_type.getText().toString().trim();
        location_t = location_type.getText().toString().trim();

        int time_temp = Integer.parseInt(time_t);
        int inertial_temp = Integer.parseInt(inertial_t);
        int location_temp = Integer.parseInt(location_t);

        if(time_temp<30 || time_temp>1800){
            if(time_temp<30){
                time_t = "30";
            }
            if(time_temp>1800){
                time_t = "1800";
            }
        }
        if(inertial_temp>60 || inertial_temp <=1){
            if(inertial_temp>60){
                inertial_t = "60";
            }
            if(inertial_temp<=1){
                inertial_t="1";
            }
        }
        if(location_temp>60 || location_temp <=1){
            if(inertial_temp>60){
                location_t = "60";
            }
            if(location_temp<=1){
                location_t="1";
            }
        }

        temp_input = time_t + "," + inertial_t + "," + location_t;
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
}