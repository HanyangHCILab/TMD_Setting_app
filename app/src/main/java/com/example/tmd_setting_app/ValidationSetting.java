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

public class ValidationSetting extends AppCompatActivity {
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData/Configuration";
    final static String foldername_upper = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData";
    final static String filename = "ValidationSetting.txt";
    String [] txt_input_val = null;
    Integer [] int_input_val = null;
    String temp_input = ""; // 한줄씩 읽기
    public int start_num = 0;

    int photo_check = 0;
    int ques_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_setting);

        Switch photo = (Switch)findViewById(R.id.Photo);
        Switch ques = (Switch)findViewById(R.id.Ques);

        if(start_num == 0){

            //저장된 mode 읽기
            temp_input = ReadTextFile(foldername+"/"+filename);

            if(TextUtils.isEmpty(temp_input)){

            }
            else{
                temp_input = temp_input.replaceAll("\n", "");
                txt_input_val = temp_input.split(",");
                int_input_val = new Integer[txt_input_val.length];


                for(int i = 0; i<txt_input_val.length; i++){
                    switch (i){
                        case 0:
                            photo_check = CheckSetting(photo, txt_input_val[i]);
                            break;
                        case 1:
                            ques_check = CheckSetting(ques, txt_input_val[i]);
                            break;
                    }
                }
            }
            start_num = start_num + 1;
        }



        /////////////////////////////////////////////////////////////////////////////////////////////

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo_check = CheckState(photo);
            }
        });

        ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ques_check = CheckState(ques);
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


    public void OnValSaveClicked(View v){

        temp_input = Integer.toString(photo_check) + "," + Integer.toString(ques_check);
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