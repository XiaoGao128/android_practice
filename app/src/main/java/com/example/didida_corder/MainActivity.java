package com.example.didida_corder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.didida_corder.ToolClass.SQLiteUserChager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener,
        View.OnClickListener, InOutFragment.IOinput,CalculFragment.changeAdater{

    private ViewPager viewPager;
    private TextView textView;
    private ImageView my_right_menu;
    private RadioGroup radioGroup;
    private DrawerLayout drawerLayout;
    private RadioButton radioButton_crod;
    private RadioButton radioButton_inout;
    private RadioButton radioButton_myitem;
    private int PAGE_NUM = 3,ii=0;
    private final int PAGE_CORD = 0;
    private final int PAGE_INOUT = 1;
    private final int PAGE_MY = 2;
    private MyFragPageAdapter myFragPageAdapter;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private String nowusername="defult";
    private int type;
    private boolean isrefresh=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //从login传入参数
        Bundle bundle=getIntent().getExtras();
        nowusername=bundle.getString("username");
        type=bundle.getInt("type");
        fragmentManager = getSupportFragmentManager();
        myFragPageAdapter = new MyFragPageAdapter(getSupportFragmentManager(),nowusername);
        myFragment = (MyFragment) fragmentManager.findFragmentById(R.id.fg_left_menu);
        myFragment.sentenceAndSet(nowusername,type);
        bindViews();
        if (bundle.getInt("refresh")==1){
            radioButton_crod.setChecked(true);
            radioButton_crod.setBackground(getDrawable(R.drawable.hostory));

        }else {
            radioButton_inout.setChecked(true);
            radioButton_inout.setBackground(getDrawable(R.drawable.money_2));
        }
        initLeftMenu();
    }

    private void bindViews() {
        radioButton_crod = findViewById(R.id.main_rd_cord);
        radioButton_inout = findViewById(R.id.main_rd_inout);
        radioButton_myitem = findViewById(R.id.main_rd_my);
        radioGroup = findViewById(R.id.main_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setAdapter(myFragPageAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(PAGE_INOUT);
        drawerLayout = findViewById(R.id.main_drawlayout);
    }

    public void initLeftMenu() {
        my_right_menu = findViewById(R.id.main_leftmenu);
        my_right_menu.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_CORD:
                    radioButton_crod.setChecked(true);
                    radioButton_inout.setBackground(getDrawable(R.drawable.money));
                    radioButton_crod.setBackground(getDrawable(R.drawable.hostory_1));
                    radioButton_myitem.setBackground(getDrawable(R.drawable.society));
                    //new 可解决翻页时 No adapter attached; skipping layout
//                    viewPager.setAdapter(new MyFragPageAdapter(getSupportFragmentManager(),nowusername));
                    break;
                case PAGE_INOUT:
                    radioButton_inout.setChecked(true);
                    radioButton_inout.setBackground(getDrawable(R.drawable.money_2));
                    radioButton_crod.setBackground(getDrawable(R.drawable.hostory));
                    radioButton_myitem.setBackground(getDrawable(R.drawable.society));
                    break;
                case PAGE_MY:
                    radioButton_myitem.setChecked(true);
                    radioButton_inout.setBackground(getDrawable(R.drawable.money));
                    radioButton_crod.setBackground(getDrawable(R.drawable.hostory));
                    radioButton_myitem.setBackground(getDrawable(R.drawable.society_1));
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rd_cord: {
                viewPager.setCurrentItem(PAGE_CORD);
                //new 可解决翻页时 No adapter attached; skipping layout
                radioButton_inout.setBackground(getDrawable(R.drawable.money));
                radioButton_crod.setBackground(getDrawable(R.drawable.hostory_1));
                radioButton_myitem.setBackground(getDrawable(R.drawable.society));
                Log.d("mainact------username",nowusername);
//                viewPager.setAdapter(new MyFragPageAdapter(getSupportFragmentManager(),nowusername));
                break;
            }
            case R.id.main_rd_inout: {
                viewPager.setCurrentItem(PAGE_INOUT);
                radioButton_inout.setBackground(getDrawable(R.drawable.money_2));
                radioButton_crod.setBackground(getDrawable(R.drawable.hostory));
                radioButton_myitem.setBackground(getDrawable(R.drawable.society));
                break;
            }
            case R.id.main_rd_my: {
                viewPager.setCurrentItem(PAGE_MY);
                radioButton_inout.setBackground(getDrawable(R.drawable.money));
                radioButton_crod.setBackground(getDrawable(R.drawable.hostory));
                radioButton_myitem.setBackground(getDrawable(R.drawable.society_1));
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void send(String s1, String s2) {
        Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.layout_fragment_calcul, null);
        CalculFragment calculFragment = new CalculFragment(root,MainActivity.this,s1,s2,nowusername,mCameraDialog,this);
        root = calculFragment.bindandset(root);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogAnimation); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
//        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();


    }


    @Override
    public void change() {
        viewPager.setAdapter(new MyFragPageAdapter(getSupportFragmentManager(),nowusername));
        viewPager.setCurrentItem(PAGE_INOUT);
    }
}
