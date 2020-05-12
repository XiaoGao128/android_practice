package com.example.didida_corder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.didida_corder.ToolClass.CustomDialog;
import com.example.didida_corder.ToolClass.Login;
import com.example.didida_corder.ToolClass.SPUtils;
import com.example.didida_corder.ToolClass.SQLiteUserChager;

import java.util.zip.Inflater;

public class MyFragment extends Fragment implements View.OnClickListener {
    private TextView tv_like, tv_likeme, tv_depatch;
    private LinearLayout linearLayout;
    private int like, likeme, depatch;
    private Context context;
    private boolean flag = false;
    private TextView tv_name;
    private ImageView iv_head;
    private int type;
    private String nowusername="defult";
    private Button button_log;
    public MyFragment() {

    }
//    public static String getNowusername(){
//        return nowusername;
//    }
    //在activity中设置数字
    public void Set(int like, int likeme, int depatch) {
        this.like = like;
        this.likeme = likeme;
        this.depatch = depatch;
        flag = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_myfragment, null, false);
        tv_like = view.findViewById(R.id.myfrag_likeone);
        tv_likeme = view.findViewById(R.id.myfrag_likeme);
        tv_depatch = view.findViewById(R.id.myfrag_patch);
        iv_head=view.findViewById(R.id.myfrag_head);
        tv_name=view.findViewById(R.id.tv_name);
        button_log=view.findViewById(R.id.myfrag_log);
        linearLayout=view.findViewById(R.id.myfrag_linear_out);
        linearLayout.setOnClickListener(this);
        button_log.setOnClickListener(this);
        if (flag) {
            tv_like.setText(like);
            tv_likeme.setText(likeme);
            tv_depatch.setText(depatch);
        }
        return view;
    }

    public void sentenceAndSet(String username,int type){
        this.nowusername=username;
        this.type=type;
        if (type==0) {
            SQLiteUserChager sqLiteUserChager = new SQLiteUserChager(getContext(), "Userr", null, 1);
            SQLiteDatabase sqLiteDatabase = sqLiteUserChager.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.rawQuery("select * from user where username = ?", new String[]{nowusername});
            cursor.moveToNext();
            tv_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            button_log.setVisibility(View.INVISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            iv_head.setVisibility(View.VISIBLE);

        }else {
            tv_name.setVisibility(View.INVISIBLE);
            iv_head.setVisibility(View.INVISIBLE);
            button_log.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myfrag_linear_out:{
                CustomDialog dialog=new CustomDialog(getContext(),R.layout.layout_custom_dialog);
                dialog.setTitle("提示").setMessage("退出登录？").setConfirm("退出", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        tv_name.setVisibility(View.INVISIBLE);
                        iv_head.setVisibility(View.INVISIBLE);
                        button_log.setVisibility(View.VISIBLE);
                        nowusername="defult";
                        SPUtils.put(getContext(),"auto",0);
                        Intent intent=new Intent(getContext(),LoginActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                }).setCancel("算了", new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            }
            case R.id.myfrag_log:{
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                break;
            }

        }
    }


}
