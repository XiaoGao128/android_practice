package com.example.didida_corder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.ChineseCalendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

//import java.util.Calendar;
import com.example.didida_corder.ToolClass.Login;

import java.util.Date;
import java.util.zip.Inflater;

import static java.lang.Math.sqrt;

public class CalculFragment implements InOutFragment.IOinput{
    private String inout,type,number,date,info;
    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_add,
            btn_del, btn_mul, btn_ac, btn_sub, btn_equal, btn_divide, btn_date,
            btn_point;
    private TextView tv_cord, tv_calcul;
    private EditText et_tell;
    private double init = 0;//当前输入数据
    private char calcul = '#';//运算符
    private double nowsum = 0;//当前运算结果
    private int pointnum = 0;//小数点输入次数
    private RelativeLayout root;
    private Context context;
    private String username;
    private Dialog dialog;
    private changeAdater changeadater;
    boolean isaheadchar = false, isnowpoint = false, isaheadequal = false, isaheadsqr = false;

    //isaheadchar: 是否输入过运算符
    //isnowpoint：小数点状态
    //isaheadequal：前一个运算符是否为=
    //isaheadsqr：前一个运算符是否为平方，主要为setText方便而设置
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CalculFragment(RelativeLayout root, Context context,String inout,String type,String username,Dialog dialog,changeAdater cc) {
        this.root = root;
        this.context = context;
        this.inout=inout;
        this.type=type;
        this.dialog=dialog;
        Log.d("=========clacu",username);
        this.username=username;
        bindandset(this.root);
        this.changeadater=cc;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public RelativeLayout bindandset(View view) {
        btn_0 = root.findViewById(R.id.calcul_btn_0);
        btn_1 = root.findViewById(R.id.calcul_btn_1);
        btn_2 = root.findViewById(R.id.calcul_btn_2);
        btn_3 = root.findViewById(R.id.calcul_btn_3);
        btn_4 = root.findViewById(R.id.calcul_btn_4);
        btn_5 = root.findViewById(R.id.calcul_btn_5);
        btn_6 = root.findViewById(R.id.calcul_btn_6);
        btn_7 = root.findViewById(R.id.calcul_btn_7);
        btn_8 = root.findViewById(R.id.calcul_btn_8);
        btn_9 = root.findViewById(R.id.calcul_btn_9);
        btn_date = root.findViewById(R.id.calcul_btn_date);
        btn_point = root.findViewById(R.id.calcul_btn_point);
        btn_sub = root.findViewById(R.id.calcul_btn_sub);
        btn_ac = root.findViewById(R.id.calcul_btn_ac);
        btn_add = root.findViewById(R.id.calcul_btn_add);
        et_tell=root.findViewById(R.id.tv_tell);
        btn_equal = root.findViewById(R.id.calcul_btn_equal);
        btn_del = root.findViewById(R.id.calcul_btn_del);
        btn_divide = root.findViewById(R.id.calcul_btn_divide);
        btn_mul = root.findViewById(R.id.calcul_btn_multiply);
        tv_calcul = root.findViewById(R.id.tv_calcul);
        tv_cord = root.findViewById(R.id.tv_cord);
        tv_calcul.setText("");
        tv_cord.setText("");
        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                } else tv_calcul.setText(tv_calcul.getText() + "0");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("1");
                } else tv_calcul.setText(tv_calcul.getText() + "1");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("2");
                } else tv_calcul.setText(tv_calcul.getText() + "2");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("3");
                } else tv_calcul.setText(tv_calcul.getText() + "3");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("4");
                } else tv_calcul.setText(tv_calcul.getText() + "4");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("5");
                } else tv_calcul.setText(tv_calcul.getText() + "5");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("6");
                } else tv_calcul.setText(tv_calcul.getText() + "6");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("7");
                } else tv_calcul.setText(tv_calcul.getText() + "7");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("8");
                } else tv_calcul.setText(tv_calcul.getText() + "8");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal || isaheadchar) {
                    btn_equal.setText("=");
                    tv_calcul.setText("9");
                } else tv_calcul.setText(tv_calcul.getText() + "9");
                init = Double.parseDouble((String) tv_calcul.getText());
                isaheadequal = false;
                isaheadchar = false;
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (calcul) {
                    case '+': {
                        nowsum += init;
                        break;
                    }
                    case '-': {
                        nowsum -= init;

                        break;
                    }
                    case '*': {
                        nowsum *= init;

                        break;
                    }
                    case '/': {
                        if (init != 0) {
                            nowsum /= init;
                        } else
                            break;
                    }
                    default: {
                        nowsum += init;
                    }
                }
                if (isaheadchar) {
                    if (!isaheadsqr)
                        tv_cord.setText(tv_cord.getText().subSequence(0, tv_cord.getText().length() - 1));
                    calcul = '+';

                }
                if (!isaheadequal) {
                    if (!isaheadchar)
                        tv_cord.setText(tv_cord.getText() + "" + init + "+");
                    else tv_cord.setText(tv_cord.getText() + "+");
                    tv_calcul.setText("" + nowsum);
                } else {
                    tv_cord.setText("" + nowsum + "+");
                    tv_calcul.setText("" + nowsum);
                }
                calcul = '+';
                init = 0;
                isaheadequal = false;
                isaheadchar = true;
                isaheadsqr = false;
                isnowpoint = false;
                pointnum = 0;
            }
        });
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal) {
                    tv_cord.setText("");
                }
                switch (calcul) {
                    case '+': {
                        nowsum += init;

                        break;
                    }
                    case '-': {
                        nowsum -= init;

                        break;
                    }
                    case '*': {
                        nowsum *= init;
                        break;
                    }
                    case '/': {
                        if (init != 0) {
                            nowsum /= init;
                        } else
                            break;
                    }
                    case '士': {
                        nowsum *= -1;
                        break;
                    }
                    case '%': {
                        nowsum %= init;
                        break;
                    }
                    default: {
                        nowsum += init;
                    }
                }
                if (isaheadchar) {
                    if (!isaheadsqr)
                        tv_cord.setText(tv_cord.getText().subSequence(0, tv_cord.getText().length() - 1));
                    calcul = '-';
                }
                if (!isaheadequal) {
                    if (!isaheadchar)
                        tv_cord.setText(tv_cord.getText() + "" + init + "-");
                    else tv_cord.setText(tv_cord.getText() + "-");
                    tv_calcul.setText("" + nowsum);
                } else {
                    tv_cord.setText("" + nowsum + "-");
                    tv_calcul.setText("" + nowsum);
                }
                calcul = '-';
                init = 0;
                isaheadequal = false;
                isaheadchar = true;
                isaheadsqr = false;
                isnowpoint = false;
                pointnum = 0;
            }
        });
        btn_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal) {
                    tv_cord.setText("");
                }
                switch (calcul) {
                    case '+': {
                        nowsum += init;
                        break;
                    }
                    case '-': {
                        nowsum -= init;
                        break;
                    }
                    case '*': {
                        nowsum *= init;
                        break;
                    }
                    case '/': {
                        if (init != 0) {
                            nowsum /= init;
                        } else
                            break;
                    }
                    case '士': {
                        nowsum *= -1;
                        break;
                    }
                    case '%': {
                        nowsum %= init;
                        break;
                    }
                    default: {
                        nowsum += init;
                    }
                }
                //if ptr calcul is calcul change it
                if (isaheadchar) {
                    if (!isaheadsqr)
                        tv_cord.setText(tv_cord.getText().subSequence(0, tv_cord.getText().length() - 1));
                    calcul = '*';
                }
                if (!isaheadequal) {
                    if (!isaheadchar)
                        tv_cord.setText(tv_cord.getText() + "" + init + "×");
                    else tv_cord.setText(tv_cord.getText() + "×");
                    tv_calcul.setText("" + nowsum);
                } else {
                    tv_cord.setText("" + nowsum + "×");
                    tv_calcul.setText("" + nowsum);
                }
                calcul = '*';
                init = 0;
                isaheadchar = true;
                isaheadequal = false;
                isaheadsqr = false;
                isnowpoint = false;
                pointnum = 0;
            }
        });
        btn_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal) {
                    tv_cord.setText("");
                }
                switch (calcul) {
                    case '+': {
                        nowsum += init;
                        break;
                    }
                    case '-': {
                        nowsum -= init;
                        break;
                    }
                    case '*': {
                        nowsum *= init;
                        break;
                    }
                    case '/': {
                        if (init != 0) {
                            nowsum /= init;
                        } else
                            break;
                    }
                    case '士': {
                        nowsum *= -1;
                        break;
                    }
                    case '%': {
                        nowsum %= init;
                        break;
                    }
                    default: {
                        nowsum += init;
                    }
                }
                if (isaheadchar) {
                    if (!isaheadsqr)
                        tv_cord.setText(tv_cord.getText().subSequence(0, tv_cord.getText().length() - 1));
                    calcul = '/';
                }
                if (!isaheadequal) {
                    if (!isaheadchar)
                        tv_cord.setText(tv_cord.getText() + "" + init + "÷");
                    else tv_cord.setText(tv_cord.getText() + "÷");
                    tv_calcul.setText("" + nowsum);
                } else {

                    tv_cord.setText("" + nowsum + "÷");
                    tv_calcul.setText("" + nowsum);
                }
                calcul = '/';
                init = 0;
                isaheadequal = false;
                isaheadchar = true;
                isaheadsqr = false;
                isnowpoint = false;
                pointnum = 0;
            }
        });
        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_calcul.setText(tv_calcul.getText() + ".");
                isnowpoint = true;
            }
        });
        btn_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isaheadequal) {
                    tv_cord.setText("");
                }else
                switch (calcul) {
                    case '+': {
                        nowsum += init;
                        break;
                    }
                    case '-': {
                        nowsum -= init;
                        break;
                    }
                    case '*': {
                        nowsum *= init;

                        break;
                    }
                    case '/': {
                        if (init != 0) {
                            nowsum /= init;
                        } else
                            break;
                    }
                    case '士': {
                        nowsum *= -1;
                        break;
                    }
                    case '%': {
                        nowsum %= init;
                        break;
                    }
                    default: {
                        nowsum = init;
                    }
                }
                if (!isaheadsqr)
                    tv_cord.setText(tv_cord.getText() + "" + init + "=");
                else tv_cord.setText(tv_cord.getText() + "=");
                tv_calcul.setText("" + nowsum);
                String ss=tv_calcul.getText().toString();
                Log.d("-----------------",ss);
                calcul = '#';
                init = 0;
                if (btn_equal.getText().toString().equals("完成")){
                    Log.d("-----------------",ss);
                    new Login(context).dataIn(username,inout,type,ss,btn_date.getText().toString(),et_tell.getText().toString());
                    dialog.dismiss();
                    changeadater.change();
                    Log.d("=-=-=-=----------------","传输完成"+username);
                }
                btn_equal.setText("完成");
                isaheadequal = true;
                isaheadsqr = false;
                isnowpoint = false;
                pointnum = 0;
            }
        });
        btn_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_calcul.setText("");
                tv_cord.setText("");
                calcul = '#';
                init = 0;
                nowsum = 0;
                isaheadchar = false;
                isnowpoint = false;
                isaheadequal = false;
                isaheadsqr = false;
                pointnum = 0;
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_calcul.getText().length() != 0)
                    tv_calcul.setText(tv_calcul.getText().subSequence(0, tv_calcul.getText().length() - 1));
            }
        });
        final Calendar calendar = Calendar.getInstance();
        String time = "" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        btn_date.setText(time);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1=ChineseCalendar.getInstance();
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btn_date.setText(""+year+"/"+(month+1)+"/"+dayOfMonth);
                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        return (RelativeLayout) view;
    }

    @Override
    public void send(String s1, String s2) {
        this.inout=s1;
        this.type=s2;
        Log.d("inout=================","完成");
    }
    public interface changeAdater{
        public void change();
    }
}
