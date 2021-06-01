package com.example.a9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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

public class MylistActivity3 extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
    Handler handler;
    private static final String TAG = "MylistActivity3";
    SimpleAdapter listItemAdapter;

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
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    ArrayList<HashMap<String, String>> listItem1 = (ArrayList<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MylistActivity3.this,
                            listItem1, R.layout.list3,
                            new String[]{"Detail", "Rate"},
                            new int[]{R.id.detail, R.id.rate});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);//配置单机事件接口
        getListView().setOnItemLongClickListener(this);
    }

    public void run() {
        try {
            Thread.sleep(2000);
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Element table = doc.getElementsByTag("table").get(1);
            Log.i(TAG, "run: " + table);
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements data = tr.getElementsByTag("td");
                if (data.size() > 0 && !data.get(1).text().isEmpty()) {//有tr且第二个td不为空则加入
                    HashMap<String, String> map = new HashMap<String, String>();//创建map来记录数据
                    map.put("Detail", data.get(0).text());//币种
                    map.put("Rate", data.get(1).text());//汇率
                    listItems.add(map);//将map保存到List列表中
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //线程完成任务
        //返回线程数据
        Message msg1 = handler.obtainMessage(7, listItems);//设置消息
        handler.sendMessage(msg1);//发送消息
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // listItems.remove(position);//删除数据
        // listItemAdapter.notifyDataSetChanged();//通知适配器已经删除了数据

        Object itemAtPosition = getListView().getItemAtPosition(position);
        HashMap<String, String> map = (HashMap<String, String>) itemAtPosition;
        String detail = map.get("Detail");
        String rate = map.get("Rate");
        Log.i(TAG, "detail: " + detail);
        Log.i(TAG, "Rate: " + rate);

        Intent intent = new Intent(this, RatelistActivity.class);
        intent.putExtra("detail", detail);
        intent.putExtra("rate", rate);
        startActivity(intent);
    }

    //长按
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按事件处理");
            //对话框提示
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("确认删除当前数据")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i(TAG, "onClick: 对话框事件处理");
                            listItems.remove(position);//删除数据
                            listItemAdapter.notifyDataSetChanged();//通知适配器已经删除了数据
                        }
                    })
                    .setNegativeButton("否", null);
            builder.create().show();

        return true;//是否屏蔽单击事件的产生
    }
}