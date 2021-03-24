package com.example.tmd_setting_app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class ModeSetting extends AppCompatActivity {
    public String inputMode = "";
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData/Configuration";
    final static String foldername_upper = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMDData";
    final static String filename = "ModeSetting.txt";
    //public String output = "";
    List<String> output_mode = new ArrayList<String>();
    String [] txt_input_mode = null;
    String temp_input = ""; // 한줄씩 읽기
    public int start_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_setting);
        final ArrayList<String> items = new ArrayList<String>() ;
        checkPermission();
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, items) ;

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter);

        //처음실행할때만


        if(start_num == 0){

            //저장된 mode 읽기
            temp_input = temp_input.replaceAll("\n", "");
            temp_input = ReadTextFile(foldername+"/"+filename);
            if(TextUtils.isEmpty(temp_input)){

            }
            else{
                txt_input_mode = temp_input.split(",");
                for(int i = 0; i<txt_input_mode.length; i++){
                    items.add(txt_input_mode[i]);
                }

            }
            start_num = start_num + 1;
            adapter.notifyDataSetChanged();
        }


        // add button에 대한 이벤트 처리.
        Button addButton = (Button)findViewById(R.id.add) ;
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count;
                count = adapter.getCount();

                AlertDialog.Builder alert = new AlertDialog.Builder(ModeSetting.this);
                alert.setTitle("Input transportation mode");
                alert.setMessage("ex)bike, car, subway");
                final EditText name = new EditText(ModeSetting.this);
                alert.setView(name);
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputMode = name.getText().toString();
                        items.add(inputMode);
                    }
                });

                alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();

                adapter.notifyDataSetChanged();

            }
        }) ;

        // delete button에 대한 이벤트 처리.
        Button deleteButton = (Button)findViewById(R.id.delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();

                int count = adapter.getCount() ;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        items.remove(i) ;
                        //output_mode.remove(output_mode.size()-(count-1));
                    }
                }

                // 모든 선택 상태 초기화.
                listview.clearChoices() ;

                adapter.notifyDataSetChanged();
            }
        }) ;

        Button selectAllButton = (Button)findViewById(R.id.selectAll) ;
        selectAllButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = 0 ;
                count = adapter.getCount() ;

                for (int i=0; i<count; i++) {
                    listview.setItemChecked(i, true) ;
                }
            }
        });

        // save button에 대한 이벤트 처리.


        Button saveButton = (Button)findViewById(R.id.pos_save) ;
        saveButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();

                int count = adapter.getCount() ;

                for (int i = 0; i < count; i++) {
                    if (checkedItems.get(i)) {
                        String vo = (String)items.get(i);
                        output_mode.add(vo);
                    }
                }

                String[] tempstr = output_mode.toArray(new String[output_mode.size()]);
                String temp_output = "";
                for (int i=0; i<tempstr.length; i++){
                    if(i==(tempstr.length-1)){
                        temp_output = temp_output + tempstr[i];
                    }
                    else{
                        temp_output = temp_output + tempstr[i] + ",";
                    }
                }
                String contents = temp_output;

                WriteTextFile(foldername, filename, contents);
                Intent intent = new Intent(ModeSetting.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void checkPermission(){
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
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