package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity implements Runnable {
    EditText et;
    TextView tw;
    String edit;
    float dollarRate = 0.15f;
    float euroRate =0.13f;
    float wonRate = 171.9f;
    float i = 0;
    Handler handler;

    private static final String TAG = "RateActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        et = (EditText) findViewById(R.id.Et2);
        tw = findViewById(R.id.textView6);
        Thread t = new Thread(this);
        t.start();

        handler =new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: get str" + str);
                    tw.setText(str);
                }
                super.handleMessage(msg);
            }
        };

    }

    private void show(double s){
        tw.setText(String.valueOf(s));
    }

    public void dollar(View v){
        edit = et.getText().toString();
         i =Float.parseFloat(edit);
        i*=dollarRate;
        show(i);
    }
    public void won(View v){
        edit = et.getText().toString();
         i =Float.parseFloat(edit);
        i*=wonRate;
        show(i);
    }
    public void euro(View v){
        edit = et.getText().toString();
         i =Float.parseFloat(edit);
        i*=euroRate;
        show(i);
    }
    public void config(View v){
        Intent config =new Intent(this,activity_ratechange.class);
        //放参数
        config.putExtra("dollarRate",dollarRate);
        config.putExtra("euroRate",euroRate);
        config.putExtra("wonRate",wonRate);
        //startActivity(config);
        startActivityForResult(config,5);//有返回的启动
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==5 && resultCode==2){
           /* Bundle bd1 =data.getExtras();
            dollarRate = bd1.getFloat("key_dollarRate", 0.1528);
            euroRate = bd1.getFloat("key_euroRate", 0.1284);
            wonRate = bd1.getFloat("key_wonRate", 171.8839);*/
            //获取SharePreferences数据
            SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            dollarRate = sp.getFloat("key_dollarRate",0.0f);
            euroRate = sp.getFloat("key_euroRate",0.0f);
            wonRate = sp.getFloat("key_wonRate",0.0f);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settings){
           View v = null;
            config(v);


        }
        return super.onOptionsItemSelected(item);
    }
    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    @Override
    public void run() {
        URL url =null;
        try {
            url =new URL("https://www.usd-cny.com/");
            HttpURLConnection http =(HttpURLConnection)url.openConnection();
            InputStream in =http.getInputStream();

            String html =inputStream2String(in);
            Log.i(TAG, "html="+html);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.i(TAG, "run: ............");
        //线程完成任务
        //返回线程数据
        Message msg = handler.obtainMessage(7);
        msg.obj = "From message";
        handler.sendMessage(msg);//发送消息

    }
}