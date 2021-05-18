package com.example.a9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RatelistActivity extends AppCompatActivity {
    private static final String TAG = "RatelistActivity";
    Float rate = 0f;
    TextView tw1;
    TextView tw2;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratelist);
        String detail = getIntent().getStringExtra("detail");
        String srate =getIntent().getStringExtra("rate");
        rate=Float.parseFloat(srate);
        tw1 = (TextView)findViewById(R.id.detail1);
        tw2 = (TextView)findViewById(R.id.show1);
        et = findViewById(R.id.input1);
        tw1.setText(detail);
        //tw2.setText(String.valueOf(100/rate*rmb));
    }
    private void show(float s){
        tw2.setText(String.valueOf(s));
    }
    public void caculate(View v){
        Float rmb = Float.parseFloat(et.getText().toString());
        show(100/rate*rmb);
    }

}