package com.example.a9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

public class RateActivity extends AppCompatActivity {
    EditText et;
    TextView tw;
    String edit;
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
        double i =Double.parseDouble(edit);
        i*=0.1528;
        show(i);
    }
    public void won(View v){
        edit = et.getText().toString();
        double i =Double.parseDouble(edit);
        i*=171.8839;
        show(i);
    }
    public void euro(View v){
        edit = et.getText().toString();
        double i =Double.parseDouble(edit);
        i*=0.1284;
        show(i);
    }
}