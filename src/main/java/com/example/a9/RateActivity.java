package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

public class RateActivity extends AppCompatActivity {
    EditText et;
    TextView tw;
    String edit;
    double dollarRate = 0.1528;
    double euroRate =0.1284;
    double wonRate = 171.8839;
    double i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        et = (EditText) findViewById(R.id.Et2);
        tw = findViewById(R.id.textView6);

    }
    private void show(double s){
        tw.setText(String.valueOf(s));
    }
    public void dollar(View v){
        edit = et.getText().toString();
         i =Double.parseDouble(edit);
        i*=dollarRate;
        show(i);
    }
    public void won(View v){
        edit = et.getText().toString();
         i =Double.parseDouble(edit);
        i*=wonRate;
        show(i);
    }
    public void euro(View v){
        edit = et.getText().toString();
         i =Double.parseDouble(edit);
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
            Bundle bd1 =data.getExtras();
            dollarRate = bd1.getDouble("key_dollarRate", 0.1528);
            euroRate = bd1.getDouble("key_euroRate", 0.1284);
            wonRate = bd1.getDouble("key_wonRate", 171.8839);

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
}