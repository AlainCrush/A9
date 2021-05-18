package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity implements Runnable {//两种列表方法，继承父类或者ListvView控件，可以放字符串数组（长度固定）或者一个列表
    private static final String TAG = "MyListActivity";
    List<String> list = new ArrayList<String>();
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list);
        Thread thread = new Thread(this);
        thread.start();

        handler =new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    ArrayList list1 =(ArrayList<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1, list1);
                    setListAdapter(adapter);
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