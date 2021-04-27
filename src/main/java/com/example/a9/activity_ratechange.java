package com.example.a9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
        float dollar2 = con.getFloatExtra("dollarRate", 0);
        float euro2 = con.getFloatExtra("euroRate", 0);
        float won2 = con.getFloatExtra("wonRate", 0);
        dollar.setText(String.valueOf(dollar2));
        euro.setText(String.valueOf(euro2));
        won.setText(String.valueOf(won2));

    }
    public void save(View v){
        //获得输入的数据
        float dollar1 =Float.parseFloat(dollar.getText().toString());
        float euro1 =Float.parseFloat(euro.getText().toString());
        float won1 =Float.parseFloat(won.getText().toString());

        //返回数据
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);//创建SharePreferences对象用以保存数据
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        //在SharePreferences中上传数据
        editor.putFloat("key_dollarRate",dollar1);
        editor.putFloat("key_euroRate", euro1);
        editor.putFloat("key_wonRate", won1);
        editor.apply();//提交
        Intent intent =getIntent();
        /*Bundle bd = new Bundle();//新建一个Bundle存放数据
        bd.putFloat("key_dollarRate",dollar1);
        bd.putFloat("key_euroRate",euro1);
        bd.putFloat("key_wonRate",won1);
        intent.putExtras(bd);*/
        setResult(2,intent);//设置resultCode并带回数据
        //返回调用页面
        finish();

    }
}