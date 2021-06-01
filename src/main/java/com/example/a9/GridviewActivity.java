package com.example.a9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        GridView gridView = findViewById(R.id.mygridview);
        //ListView listView = findViewById(R.id.mylist0);
        //TextView textView = findViewById(R.id.nodata);

        List<String> list = new ArrayList<String >();
        /*for(int i =1;i<=100;i++){
            list.add("Item-" + i);
        }*/
        ListAdapter adapter =new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        gridView.setAdapter(adapter);
        gridView.setEmptyView(findViewById(R.id.nodata));//空数据
        //listView.setAdapter(adapter);
        //listView.setEmptyView(textView);//空数据
    }
}