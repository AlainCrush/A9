package com.example.a9;

import android.app.LauncherActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateManager {

    private DBHelper dbHelper;
    private String TBNAME;
    String curname = "CURNAME";
    String currate = "CURRATE";

    public RateManager(Context context){

        this.dbHelper = new DBHelper(context);
        TBNAME =DBHelper.TB_NAME;
    }

    public  void add (RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //放数据
        ContentValues contentValues = new ContentValues();
        contentValues.put(curname,item.getCurname());
        contentValues.put(currate,item.getCurrate());

        db.insert(TBNAME,null,contentValues);
        db.close();
    }

    public void addAll(ArrayList<HashMap<String, String>> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(HashMap<String, String> item : list){
            ContentValues values = new ContentValues();
            values.put(curname,item.get("Detail"));
            values.put(currate,item.get("Rate"));
            db.insert(TBNAME,null,values);
        }
        db.close();
    }

    public RateItem FindByID(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},null,null,null);
        RateItem rateItem =null;
        if (cursor!=null&&cursor.moveToFirst()){
            rateItem = new RateItem();

            //通过游标查询索引来查询值
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurname(cursor.getString(cursor.getColumnIndex(curname)));
            rateItem.setCurrate(cursor.getString(cursor.getColumnIndex(currate)));
            cursor.close();///关闭游标
        }
        db.close();
        return rateItem;
    }


    public ArrayList<HashMap<String,String>> listAll(){
        ArrayList<HashMap<String,String>> ratelist =null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);

        if (cursor!=null){
            ratelist = new ArrayList<HashMap<String,String>>();
            while (cursor.moveToNext()){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("Detail",cursor.getString(cursor.getColumnIndex(curname)));
                map.put("Rate",cursor.getString(cursor.getColumnIndex(currate)));
                ratelist.add(map);
            }
            cursor.close();///关闭游标
        }
        db.close();
        return  ratelist;
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
