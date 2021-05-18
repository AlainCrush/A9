package com.example.a9;

import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MyTask implements Runnable{
    private static final String TAG = "MyTask";
    private Handler handler;

    public  void setHandler(Handler h){
        this.handler = h;
    }
       @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
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


       /* Message msg = handler.obtainMessage(7,list);
        // msg.obj = "From message";
        handler.sendMessage(msg);//发送消息*/
    }
}
