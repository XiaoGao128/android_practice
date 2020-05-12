package com.example.didida_corder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.ChineseCalendar;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CordSelectAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> gData;
    private ArrayList<String[]> iData;
    private Context mContext;
    private OnItemClicked onItemClicked;
    private boolean firstdate=false,seconddate=false;
    private String first="",second="";
    private int groupid;
    private CordFragment cordFragment;
    public CordSelectAdapter(ArrayList<String> gData, ArrayList<String[]> iData,Context mContext,int groupid,CordFragment cordFragment){
        this.gData=gData;
        this.iData=iData;
        this.mContext=mContext;
        this.groupid=groupid;
        this.cordFragment=cordFragment;
    }
    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d("--------------","111111111");
        return iData.get(groupPosition).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.d("--gDt(groupPosition)","+"+gData.get(groupPosition));
        return gData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("--------------","111111111");
        return iData.get(groupPosition)[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.d("--------------","111111111");
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Log.d("--------------","111111111");
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_cordselect_group,parent,false);
            viewHolderGroup=new ViewHolderGroup();
            viewHolderGroup.btn_name=convertView.findViewById(R.id.cordselect_group);
            convertView.setTag(viewHolderGroup);
        }else {
            viewHolderGroup= (ViewHolderGroup) convertView.getTag();
        }
        viewHolderGroup.btn_name.setText(gData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolderItem viewHolderItem;
        if (convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.layout_cordselect_item,parent,false);
            viewHolderItem=new ViewHolderItem();
            viewHolderItem.btn_item=convertView.findViewById(R.id.cordselect_item);
            convertView.setTag(viewHolderItem);
        }else {
            viewHolderItem= (ViewHolderItem) convertView.getTag();
        }
        String[] strings=iData.get(groupPosition);
        viewHolderItem.btn_item.setText(strings[childPosition]);
        if (groupid>0){
        viewHolderItem.btn_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onItemClicked.send(""+groupid,""+childPosition);
                cordFragment.set(""+groupid,""+childPosition);
            }
        });}
        else {
            final View finalConvertView = convertView;

            viewHolderItem.btn_item.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    Calendar calendar1= ChineseCalendar.getInstance();
                    DatePickerDialog datePickerDialog=new DatePickerDialog(finalConvertView.getContext(), AlertDialog.THEME_HOLO_LIGHT,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    viewHolderItem.btn_item.setText(""+year+"/"+(month+1)+"/"+dayOfMonth);
                                    if (childPosition==0) {
                                        firstdate=true;
                                        first=viewHolderItem.btn_item.getText().toString();
                                    }
                                    if (childPosition==1){
                                        seconddate=true;
                                        second=viewHolderItem.btn_item.getText().toString();
                                    }
                                    if (firstdate&&seconddate){
                                        firstdate=false;
                                        seconddate=false;
                                        cordFragment.set(""+first,""+second);

                                    }
                                }
                            }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();

                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private static class ViewHolderGroup{
        private TextView btn_name;
    }
    private static class ViewHolderItem{
       private TextView btn_item;
    }
    public interface OnItemClicked{
        public void send(String group,String item);
    }
}
