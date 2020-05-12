package com.example.didida_corder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InOutFragment extends Fragment implements View.OnClickListener {
    private ImageView in, out, water, eat, cloth, go, happy, sentic, other, page, jianqian, jop, soket;
    private IOinput iOinput;
    private boolean incan = false, outcan = false;
    private String inout,type;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_inout, null, false);
        bindView(view);
        return view;
    }

    public void bindView(View view) {
        in = view.findViewById(R.id.inout_in);
        out = view.findViewById(R.id.inout_out);
        eat = view.findViewById(R.id.inout_canyin);
        water = view.findViewById(R.id.inout_shuidian);
        cloth = view.findViewById(R.id.inout_yiwu);
        go = view.findViewById(R.id.inout_chuxing);
        happy = view.findViewById(R.id.inout_yule);
        sentic = view.findViewById(R.id.inout_dianzi);
        other = view.findViewById(R.id.inout_qita);
        page = view.findViewById(R.id.inout_gongzi);
        jianqian = view.findViewById(R.id.inout_jianqian);
        jop = view.findViewById(R.id.inout_jianzhi);
        soket = view.findViewById(R.id.inout_hongbao);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColorToBlack();
                if (!incan) {
                    incan = true;
                    eat.setVisibility(View.VISIBLE);
                    water.setVisibility(View.VISIBLE);
                    go.setVisibility(View.VISIBLE);
                    cloth.setVisibility(View.VISIBLE);
                    sentic.setVisibility(View.VISIBLE);
                    other.setVisibility(View.VISIBLE);
                    happy.setVisibility(View.VISIBLE);
                    in.setImageResource(R.drawable.zhichu_hong);
                } else {
                    incan = false;
                    eat.setVisibility(View.INVISIBLE);
                    water.setVisibility(View.INVISIBLE);
                    go.setVisibility(View.INVISIBLE);
                    cloth.setVisibility(View.INVISIBLE);
                    sentic.setVisibility(View.INVISIBLE);
                    other.setVisibility(View.INVISIBLE);
                    happy.setVisibility(View.INVISIBLE);
                    in.setImageResource(R.drawable.zhichu_hei);
                }
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColorToBlack();
                if (!outcan) {
                    outcan = true;
                    page.setVisibility(View.VISIBLE);
                    soket.setVisibility(View.VISIBLE);
                    jianqian.setVisibility(View.VISIBLE);
                    jop.setVisibility(View.VISIBLE);
                    out.setImageResource(R.drawable.shouru_hong);
                } else {
                    outcan = false;
                    page.setVisibility(View.INVISIBLE);
                    soket.setVisibility(View.INVISIBLE);
                    jianqian.setVisibility(View.INVISIBLE);
                    jop.setVisibility(View.INVISIBLE);
                    out.setImageResource(R.drawable.shouru_hei);
                }
            }
        });
        water.setOnClickListener(this);
        eat.setOnClickListener(this);
        cloth.setOnClickListener(this);
        go.setOnClickListener(this);
        happy.setOnClickListener(this);
        sentic.setOnClickListener(this);
        other.setOnClickListener(this);
        page.setOnClickListener(this);
        jianqian.setOnClickListener(this);
        jop.setOnClickListener(this);
        soket.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inout_chuxing:{
                inout="支出";
                type="出行";
                changeColorToBlack();
                go.setImageResource(R.drawable.jiaotong_hong);
                break;
            }
            case R.id.inout_canyin:{
                inout="支出";
                type="餐饮";
                changeColorToBlack();
                eat.setImageResource(R.drawable.cnayin_hong);
                break;
            }
            case R.id.inout_dianzi:{
                inout="支出";
                type="电子产品";
                changeColorToBlack();
                sentic.setImageResource(R.drawable.dianzi_hong);
                break;
            }
            case R.id.inout_gongzi:{
                inout="收入";
                type="工资";
                changeColorToBlack();
                page.setImageResource(R.drawable.gongzi_hong);
                break;
            }
            case R.id.inout_hongbao:{
                inout="收入";
                type="红包";
                changeColorToBlack();
                soket.setImageResource(R.drawable.hongbao_hong);
                break;
            }
            case R.id.inout_jianqian:{
                inout="收入";
                type="捡钱";
                changeColorToBlack();
                jianqian.setImageResource(R.drawable.jiandao_hong);
                break;
            }
            case R.id.inout_jianzhi:{
                inout="收入";
                type="兼职";
                changeColorToBlack();
                jop.setImageResource(R.drawable.jianzhi_hong);
                break;
            }
            case R.id.inout_qita:{
                inout="支出";
                type="其他";
                changeColorToBlack();
                other.setImageResource(R.drawable.qita_hong);
                break;
            }
            case R.id.inout_shuidian:{
                inout="支出";
                type="水电";
                changeColorToBlack();
                water.setImageResource(R.drawable.shuidian_hong);
                break;
            }  case R.id.inout_yiwu:{
                inout="支出";
                type="衣物";
                changeColorToBlack();
                cloth.setImageResource(R.drawable.yiwu_hong);
                break;
            }
            case R.id.inout_yule:{
                inout="支出";
                type="娱乐";
                changeColorToBlack();
                happy.setImageResource(R.drawable.yule_hong);
                break;
            }


        }
        iOinput.send(inout,type);
    }

    public interface IOinput {
        void send(String s1,String s2);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iOinput = (IOinput) context;
    }
    public void changeColorToBlack(){
        water.setImageResource(R.drawable.shuidian_hei);
        go.setImageResource(R.drawable.jiaotong_hei);
        cloth.setImageResource(R.drawable.yiwu_hei);
        happy.setImageResource(R.drawable.yule_hei);
        sentic.setImageResource(R.drawable.dianzi_hei);
        other.setImageResource(R.drawable.qita_hei);
        jianqian.setImageResource(R.drawable.jiandao_hei);
        page.setImageResource(R.drawable.gongzi_hei);
        soket.setImageResource(R.drawable.hongbao_hei);
        jop.setImageResource(R.drawable.jinzhi_hei);
        eat.setImageResource(R.drawable.cnayin_hei);

    }
}

