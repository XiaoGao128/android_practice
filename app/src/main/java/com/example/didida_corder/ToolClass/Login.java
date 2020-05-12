package com.example.didida_corder.ToolClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Login {
    private String username, password;
    private Context context;

    public Login(Context context) {
        this.context = context;
    }

    public Login(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }

    public Boolean sentenceLog(String username, String password) {
        boolean flag = false;
        SQLiteUserChager sq = new SQLiteUserChager(context, "Userr", null, 1);
        SQLiteDatabase sqLiteDatabase = sq.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                if (password.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                    flag = true;
                }
                ;
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        sq.close();
        return flag;
    }
    public Boolean isInstance(String username){
        boolean flag=false;
        SQLiteUserChager sq = new SQLiteUserChager(context, "Userr", null, 1);
        SQLiteDatabase sqLiteDatabase = sq.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) flag=true;
        cursor.close();
        sqLiteDatabase.close();
        sq.close();
            return flag;
    }
    public void dataIn(String username,String inout,String type,String number,String date,String info){
        SQLiteUserChager sq = new SQLiteUserChager(context, "Userr", null, 1);
        SQLiteDatabase sqLiteDatabase = sq.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Cord",new String[]{});
        int i=0;
        if (cursor.moveToNext()) i=cursor.getCount();
        sqLiteDatabase.execSQL("insert into Cord values(?,?,?,?,?,?,?)",new String[]{""+i,username,inout,type,number,date,info});
       cursor.close();
        sqLiteDatabase.close();
        sq.close();
    }
}
