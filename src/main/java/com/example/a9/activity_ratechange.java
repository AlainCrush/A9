package com.example.a9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class activity_ratechange extends AppCompatActivity {
    EditText dollar, euro, won;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratechange);
        //获得控件
        dollar = findViewById(R.id.T1);
        euro = findViewById(R.id.T2);
        won = findViewById(R.id.T3);
        Intent con = getIntent();
        double dollar2 = con.getDoubleExtra("dollarRate", 0);
        double euro2 = con.getDoubleExtra("euroRate", 0);
        double won2 = con.getDoubleExtra("wonRate", 0);
        dollar.setText(String.valueOf(dollar2));
        euro.setText(String.valueOf(euro2));
        won.setText(String.valueOf(won2));

    }
    public void save(View v){
        //获得输入的数据
        double dollar1 =Double.parseDouble(dollar.getText().toString());
        double euro1 =Double.parseDouble(euro.getText().toString());
        double won1 =Double.parseDouble(won.getText().toString());

        //返回数据
        Intent intent =getIntent();
        Bundle bd = new Bundle();//新建一个Bundle存放数据
        bd.putDouble("key_dollarRate",dollar1);
        bd.putDouble("key_euroRate",euro1);
        bd.putDouble("key_wonRate",won1);
        intent.putExtras(bd);
        setResult(2,intent);//设置resultCode并带回数据
        //返回调用页面
        finish();

    }
}