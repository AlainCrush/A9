package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MylistActivity2 extends AppCompatActivity implements Runnable{
    Handler handler;
    List<String> list = new ArrayList<String>();
    private static final String TAG = "MylistActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist2);

        //获得控件
        ListView listView = findViewById(R.id.mylist2);
        ProgressBar progressBar=findViewById(R.id.progressBar);//获得进度条控件

        //开启子线程
        Thread thread = new Thread(this);
        thread.start();

        handler =new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    ArrayList list1 =(ArrayList<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MylistActivity2.this,
                            android.R.layout.simple_list_item_1, list1);
                    listView.setAdapter(adapter);
                    //设置控件
                    progressBar.setVisibility(View.GONE);//设置进度条可视化
                    listView.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void run() {
        try {
            Thread.sleep(3000);
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Element table = doc.getElementsByTag("table").get(1);
            Log.i(TAG, "run: "+table);
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements data = tr.getElementsByTag("td");
                if (data.size() > 0&&!data.get(1).text().isEmpty()){//有tr且第二个td不为空则加入
                    list.add(data.get(0).text() + "------>" + data.get(1).text());
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //线程完成任务
        //返回线程数据
        Message msg = handler.obtainMessage(7,list);
        // msg.obj = "From message";
        handler.sendMessage(msg);//发送消息
    }
}