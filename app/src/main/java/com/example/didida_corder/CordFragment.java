package com.example.didida_corder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didida_corder.Adapter.CordAdapter;
import com.example.didida_corder.R;
import com.example.didida_corder.ToolClass.CustomDialog;
import com.example.didida_corder.ToolClass.SQLiteUserChager;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CordFragment extends Fragment implements CordAdapter.Clickiii, CordSelectAdapter.OnItemClicked {
    private RecyclerView recyclerView;
    private String[] strings;
    private int count;
    private String nowusername = "defult";
    private int i = 0;
    private boolean isCreat = false;
    private ArrayList<String[]> arr = new ArrayList<>();
    private String group = "", item = "";
    private boolean clickflag = false;
    private CordSelectAdapter cordSelectAdapter, cordSelectAdapter1, cordSelectAdapter2;
    private ExpandableListView expandableListView, expandableListView1, expandableListView2;
    private TextView tv_recover,tv_sum;
    private String[][] select = new String[3][11];
    private double number=0;
    public CordFragment() {

    }

    public static CordFragment newInstance(String nowusername) {

        Bundle args = new Bundle();
        args.putString("username", nowusername);
        CordFragment fragment = new CordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        nowusername = getArguments().getString("username");
        Log.d("attach------", "");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resume------", "");
        if (!clickflag)
            recyclerView.setAdapter(new CordAdapter(getView().getContext(), readCord().length, readCord(), this));
        else {
            arr = new ArrayList<>();
            clickflag = false;
            recyclerView.setAdapter(new CordAdapter(getView().getContext(), readCord_select().length, readCord_select(), this));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_cordfragment, null, false);
        recyclerView = view.findViewById(R.id.crod_recy);
        tv_sum=view.findViewById(R.id.cord_money);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new CordAdapter(view.getContext(), count, readCord(), this));
        isCreat = true;
        bindExpand(view);
        Log.d("------gupitem", group + "+" + item);
        final CordAdapter.Clickiii clickiii=this;
        tv_recover = view.findViewById(R.id.cord_btn_select);
        tv_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new CordAdapter(getView().getContext(), readCord().length, readCord(),clickiii));
            }
        });

        return view;
    }


    public String[] readCord() {
        SQLiteUserChager sqLiteUserChager = new SQLiteUserChager(getContext(), "Userr", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLiteUserChager.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Cord where username = ?", new String[]{nowusername});
        String[] strings = new String[count = cursor.getCount()];
        number=0;
        int i = 0;
        if (cursor.moveToNext())
            do {
                number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                String[] strings1 = new String[7];
                strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                arr.add(strings1);
                strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                        " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                        "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                        "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                i++;
            } while (cursor.moveToNext());
        cursor.close();
        sqLiteDatabase.close();
        sqLiteUserChager.close();
        tv_sum.setText("总收支："+number);
        return strings;
    }

    public String[] readCord_select() {
        SQLiteUserChager sqLiteUserChager = new SQLiteUserChager(getContext(), "Userr", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLiteUserChager.getReadableDatabase();
        number=0;
        if (group.length() < 3) {
            Cursor cursor;
            if (Integer.parseInt(group) == 1) {
                cursor = sqLiteDatabase.rawQuery("select * from Cord where inout=? and username=?", new String[]{select[1][Integer.parseInt(item)],nowusername});
            } else {
                cursor = sqLiteDatabase.rawQuery("select * from Cord where type=? and username=?", new String[]{select[2][Integer.parseInt(item)],nowusername});
            }
            String[] strings = new String[count = cursor.getCount()];
            int i = 0;
            if (cursor.moveToNext())
                do {
                    number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                    String[] strings1 = new String[7];
                    strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                    strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                    strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                    strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                    strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                    strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                    strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                    arr.add(strings1);
                    strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                            " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                            "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                            "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                    i++;
                } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
            sqLiteUserChager.close();
            tv_sum.setText("总收支："+number);
            return strings;
        } else {                                                                                     //dateselect
            String[] min = group.split("/");
            String[] max = item.split("/");
            Log.d("----max", max[0] + "/" + max[1] + "/" + max[2]);
            Log.d("----min", min[0] + "/" + min[1] + "/" + min[2]);
            Cursor cursor = sqLiteDatabase.rawQuery("select * from Cord where username=?", new String[]{nowusername});
            String[] strings = new String[cursor.getCount() + 8000];
            while (cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String[] dats = date.split("/");
                if (Integer.parseInt(dats[0]) > Integer.parseInt(min[0]) && Integer.parseInt(dats[0]) < Integer.parseInt(max[0])) {
                    String[] strings1 = new String[7];
                    number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                    strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                    strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                    strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                    strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                    strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                    strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                    strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                    arr.add(strings1);
                    strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                            " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                            "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                            "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                    i++;
                } else if (Integer.parseInt(dats[0]) == Integer.parseInt(min[0])) {
                    if (Integer.parseInt(dats[1]) > Integer.parseInt(min[1]) && Integer.parseInt(dats[1]) < Integer.parseInt(max[1])) {
                        String[] strings1 = new String[7];
                        number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                        strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                        strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                        strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                        strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                        strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                        strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                        strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                        arr.add(strings1);
                        strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                                " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                                "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                                "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                        i++;
                    } else if (Integer.parseInt(dats[1]) == Integer.parseInt(min[1])) {
                        if ((Integer.parseInt(dats[2]) >= Integer.parseInt(min[2]) && Integer.parseInt(dats[1]) < Integer.parseInt(max[1])) || (Integer.parseInt(dats[2]) >= Integer.parseInt(min[2]) && Integer.parseInt(dats[2]) <= Integer.parseInt(max[2]))) {
                            String[] strings1 = new String[7];
                            number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                            strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                            strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                            strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                            strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                            strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                            strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                            strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                            arr.add(strings1);
                            strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                                    " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                                    "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                                    "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                            i++;
                        }
                    } else if (Integer.parseInt(dats[1]) == Integer.parseInt(max[1])) {
                        if ((Integer.parseInt(dats[2]) <= Integer.parseInt(max[2]) && Integer.parseInt(dats[1]) > Integer.parseInt(min[1])) || (Integer.parseInt(dats[2]) >= Integer.parseInt(min[2]) && Integer.parseInt(dats[2]) <= Integer.parseInt(max[2]))) {
                            String[] strings1 = new String[7];
                            number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                            strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                            strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                            strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                            strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                            strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                            strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                            strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                            arr.add(strings1);
                            strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                                    " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                                    "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                                    "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                            i++;
                        }
                    }
                } else if (Integer.parseInt(dats[0]) == Integer.parseInt(max[0])) {
                    if (Integer.parseInt(dats[1]) < Integer.parseInt(max[1])) {
                        String[] strings1 = new String[7];
                        number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                        strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                        strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                        strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                        strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                        strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                        strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                        strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                        arr.add(strings1);
                        strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                                " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                                "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                                "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                        i++;
                    } else if (Integer.parseInt(dats[1]) == Integer.parseInt(max[1])) {
                        if (Integer.parseInt(dats[2]) <= Integer.parseInt(max[2])) {
                            String[] strings1 = new String[7];
                            number+=Double.parseDouble(cursor.getString(cursor.getColumnIndex("number")));
                            strings1[0] = cursor.getString(cursor.getColumnIndex("date"));
                            strings1[1] = cursor.getString(cursor.getColumnIndex("type"));
                            strings1[2] = cursor.getString(cursor.getColumnIndex("inout"));
                            strings1[3] = cursor.getString(cursor.getColumnIndex("number"));
                            strings1[4] = cursor.getString(cursor.getColumnIndex("info"));
                            strings1[5] = cursor.getString(cursor.getColumnIndex("username"));
                            strings1[6] = cursor.getString(cursor.getColumnIndex("id"));
                            arr.add(strings1);
                            strings[i] = "我" + cursor.getString(cursor.getColumnIndex("date")) +
                                    " 在" + cursor.getString(cursor.getColumnIndex("type")) +
                                    "上" + cursor.getString(cursor.getColumnIndex("inout")) +
                                    "了" + cursor.getString(cursor.getColumnIndex("number")) + "元";
                            i++;
                        }
                    }
                }
            }
            cursor.close();
            sqLiteDatabase.close();
            sqLiteUserChager.close();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < strings.length; i++) {
                if (strings[i] != null) {
                    arrayList.add(strings[i]);
                }
            }
            String[] strings2 = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                strings2[i] = arrayList.get(i);
            }
            Log.d("-----gropuitem", group + "+" + item);
            tv_sum.setText("总收支："+number);
            return strings2;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void clik(int position) {
        final String[] strings = arr.get(position);
        final AlertDialog.Builder builder4 = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_dialog_info, null);
        TextView textView = view.findViewById(R.id.info_tx_2);
        TextView textView2 = view.findViewById(R.id.info_tx_3);
        TextView textView3 = view.findViewById(R.id.info_tx_4);
        TextView textView4 = view.findViewById(R.id.info_tx_5);
        TextView textView5 = view.findViewById(R.id.info_tx_6);
        Button button = view.findViewById(R.id.btn_ok);
        Button button1 = view.findViewById(R.id.btn_delete);
        textView.setText(strings[0]);
        textView2.setText(strings[2]);
        textView3.setText(strings[1]);
        textView4.setText(strings[3]);
        textView5.setText(strings[4]);
        final AlertDialog alertDialog = builder4.setView(view).show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog customDialog = new CustomDialog(getContext());
                customDialog.setMessage("确定删除？");
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                customDialog.setConfirm("确定", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        SQLiteUserChager sqLiteUserChager = new SQLiteUserChager(getContext(), "Userr", null, 1);
                        SQLiteDatabase sqLiteDatabase = sqLiteUserChager.getWritableDatabase();
                        sqLiteDatabase.execSQL("delete from Cord where id = ?", new String[]{strings[6]});
                        sqLiteDatabase.close();
                        sqLiteUserChager.close();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("refresh", 1);
                        bundle.putString("username", nowusername);
                        Log.d("--------cord-log", nowusername);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }).setCancel("取消", new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        customDialog.dismiss();
                    }
                }).show();

            }
        });
    }

    public void bindExpand(View view) {
        ArrayList<String> gData = new ArrayList<>();
        ArrayList<String[]> iData = new ArrayList<>();
        String[] siData;
        expandableListView = view.findViewById(R.id.cord_date);
        gData.add("日期");
        siData = new String[2];
        siData[0] = "从";
        siData[1] = "至";
        iData.add(siData);
        cordSelectAdapter = new CordSelectAdapter(gData, iData, getContext(), 0, this);
        expandableListView.setAdapter(cordSelectAdapter);
        gData = new ArrayList<>();
        gData.add("类型");
        siData = new String[2];
        iData = new ArrayList<>();
        siData[0] = "收入";
        siData[1] = "支出";
        select[1][0] = "收入";
        select[1][1] = "支出";
        iData.add(siData);
        expandableListView1 = view.findViewById(R.id.cord_inout);
        cordSelectAdapter1 = new CordSelectAdapter(gData, iData, getContext(), 1, this);
        expandableListView1.setAdapter(cordSelectAdapter1);
        gData = new ArrayList<>();
        gData.add("收支");
        iData = new ArrayList<>();
        siData = new String[11];
        siData[0] = "工资收入";
        siData[1] = "捡钱收入";
        siData[2] = "兼职收入";
        siData[3] = "红包收入";
        siData[4] = "水电支出";
        siData[5] = "餐饮支出";
        siData[6] = "衣物支出";
        siData[7] = "出行支出";
        siData[8] = "电子产品支出";
        siData[9] = "娱乐支出";
        siData[10] = "其他支出";
        select[2][0] = "工资";
        select[2][1] = "捡钱";
        select[2][2] = "兼职";
        select[2][3] = "红包";
        select[2][4] = "水电";
        select[2][5] = "餐饮";
        select[2][6] = "衣物";
        select[2][7] = "出行";
        select[2][8] = "电子产品";
        select[2][9] = "娱乐";
        select[2][10] = "其他";
        iData.add(siData);
        expandableListView2 = view.findViewById(R.id.cord_type);
        cordSelectAdapter2 = new CordSelectAdapter(gData, iData, getContext(), 2, this);
        expandableListView2.setAdapter(cordSelectAdapter2);
    }

    @Override
    public void send(String group, String item) {
        this.group = group;
        this.item = item;
        clickflag = true;
    }

    public void set(String group, String item) {
        this.group = group;
        this.item = item;
        arr = new ArrayList<>();
        recyclerView.setAdapter(new CordAdapter(getView().getContext(), readCord_select().length, readCord_select(), this));

    }
}
