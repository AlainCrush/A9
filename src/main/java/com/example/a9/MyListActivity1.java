package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity1 extends AppCompatActivity implements Runnable{
    Handler handler;
    List<String> list = new ArrayList<String>();
    private static final String TAG = "MyListActivity1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list1);
        ListView listview = findViewById(R.id.mylist);
        //创建并开启子线程
        Thread thread = new Thread(this);
        thread.start();

        handler =new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    ArrayList list1 =(ArrayList<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity1.this, android.R.layout.simple_list_item_1, list1);
                    listview.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void run() {
        try {
            Document doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");

            for (Element tr : trs) {
                Elements data = tr.getElementsByTag("td");
                if (data.size() > 0) {
                    list.add(data.get(0).text() + "---->" + data.get(1).text());
                    Log.i(TAG, "onCreate: run......." + data.get(0).text() + data.get(1).text());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //线程完成任务
        //返回线程数据
        Message msg = handler.obtainMessage(7,list);
        // msg.obj = "From message";
        handler.sendMessage(msg);//发送消息
    }
}