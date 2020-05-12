package com.example.didida_corder.ToolClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteUserChager extends SQLiteOpenHelper {

    public SQLiteUserChager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context,"Userr",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table user(name varchar(50),username varchar(50),password varchar(50))";
        db.execSQL(sql);
        sql="create table Cord(id varchar(50),username varchar(50),inout varchar(50),type varchar(50),number varchar(50),date varchar(50),info varchar(50))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
