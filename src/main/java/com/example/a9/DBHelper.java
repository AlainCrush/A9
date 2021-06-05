package com.example.a9;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int Version = 1;
    public static final String DB_NAME ="Myrates.db";//数据库名
    public static final String TB_NAME = "Table_rates";//表名

    public DBHelper(@Nullable Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DB_NAME , null, Version);
    }

    public DBHelper(Context context) {
        super(context,DB_NAME , null, Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE "+ TB_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME TEXT,CURRATE TEXT)");//创建数据库
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //更新数据库了
      /* if(oldVersion==1&&newVersion==2){
            db.execSQL("");
        }*/
    }
}
