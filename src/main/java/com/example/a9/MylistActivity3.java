package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MylistActivity3 extends ListActivity implements Runnable, AdapterView.OnItemClickListener {
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
    Handler handler;
    private static final String TAG = "MylistActivity3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_mylist3);
       // ListView list3 = findViewById(R.id.list3);

        //data
        /*listItems = new ArrayList<HashMap<String,String>>();
        for (int i = 0;i<=10;i++){
            HashMap<String ,String> map = new HashMap<String,String>();
            map.put("Rate","Rate" + i);//汇率
            map.put("Detail","Detail" + i);//详情
            listItems.add(map);
    }*/

    //adapter

        Thread thread = new Thread(this);
        thread.start();
        handler =new Handler() {
        @Override
        public void handleMessage (@NonNull Message msg){
            if (msg.what == 7) {
                ArrayList<HashMap<String, String>> listItem1 = (ArrayList<HashMap<String, String>>) msg.obj;
                SimpleAdapter listItemAdapter = new SimpleAdapter(MylistActivity3.this,
                        listItem1, R.layout.list3,
                        new String[]{"Detail","Rate"},
                        new int[]{R.id.detail, R.id.rate});
                setListAdapter(listItemAdapter);
            }
            super.handleMessage(msg);
        }
    };
        getListView().setOnItemClickListener(this);
}
    public void run() {
        try {
            Thread.sleep(2000);
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Element table = doc.getElementsByTag("table").get(1);
            Log.i(TAG, "run: "+table);
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements data = tr.getElementsByTag("td");
                if (data.size() > 0&&!data.get(1).text().isEmpty()){//有tr且第二个td不为空则加入
                    HashMap<String ,String> map = new HashMap<String,String>();
                        map.put("Detail",data.get(0).text());//币种
                        map.put("Rate",data.get(1).text());//汇率
                        listItems.add(map);

                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //线程完成任务
        //返回线程数据
        Message msg1 = handler.obtainMessage(7,listItems);
        // msg.obj = "From message";
        handler.sendMessage(msg1);//发送消息
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object itemAtPosition = getListView().getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String,String>)itemAtPosition;
        String  detail = map.get("Detail");
        String rate  = map.get("Rate");
        Log.i(TAG, "detail: "+detail);
        Log.i(TAG, "Rate: "+rate);

        Intent intent = new Intent(this,RatelistActivity.class);
        intent.putExtra("detail",detail);
        intent.putExtra("rate",rate);
        startActivity(intent);
    }
}