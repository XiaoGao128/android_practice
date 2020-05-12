package com.example.didida_corder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.didida_corder.ToolClass.Login;
import com.example.didida_corder.ToolClass.SPUtils;
import com.example.didida_corder.ToolClass.SQLiteUserChager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_log,btn_register,btn_defult;
    private String nowusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        if (getIntent().getExtras()!=null)
            if(getIntent().getExtras().get("refresh")!=null)
            autoIn();
            autoIn_2();
        btn_log=findViewById(R.id.login_btn_log);
        btn_register=findViewById(R.id.login_btn_regster);
        btn_defult=findViewById(R.id.login_btn_defult);
        btn_defult.setOnClickListener(this);
        btn_log.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }
    public void autoIn(){
            Bundle bundle=new Bundle();
            bundle.putString("username",getIntent().getExtras().getString("username"));
            bundle.putInt("type",1);
            bundle.putInt("refresh",1);
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtras(bundle);
            finish();
            startActivity(intent);

        }
        public void autoIn_2(){
            if ((Integer.parseInt(SPUtils.get(getApplicationContext(),"auto",222).toString())==1)){
                Bundle bundle=new Bundle();
                bundle.putString("username",SPUtils.get(getApplicationContext(),"username","222").toString());
                bundle.putInt("type",0);
                Log.d("autoin_2===========","");
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_regster:{
                final AlertDialog.Builder builder4=new AlertDialog.Builder(this);
                LayoutInflater inflater=LayoutInflater.from(this);
                View view=inflater.inflate(R.layout.layout_register,null);
                Button button=view.findViewById(R.id.adm_btn);
                final EditText editText=view.findViewById(R.id.adm_et);
                final EditText editText1=view.findViewById(R.id.adm_et2);
                final EditText et_confirm=view.findViewById(R.id.adm_et_confirm);
                final EditText et_name=view.findViewById(R.id.adm_name);
                final AlertDialog alertDialog=builder4.setView(view).show();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().equals("")||editText1.getText().toString().equals("")||et_confirm.getText().toString().equals("")||et_name.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "请补全信息！", Toast.LENGTH_LONG).show();
                        }else if (new Login(getApplicationContext()).isInstance(editText.getText().toString())){
                            Toast.makeText(getApplicationContext(),"用户名重复！请重新输入！",Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        } else {
                            SPUtils.put(getApplicationContext(),"username",editText.getText().toString()).
                                    put(getApplicationContext(),"password",editText1.getText().toString()).
                                    put(getApplicationContext(),"name",et_name.getText().toString());

                            SQLiteUserChager sc=new SQLiteUserChager(getApplicationContext(),"Userr",null,1);
                            SQLiteDatabase db= sc.getWritableDatabase();
                            db.execSQL("insert into user values(?,?,?)",new String[]{et_name.getText().toString(),editText.getText().toString(),editText1.getText().toString()});
                            nowusername=editText.getText().toString();
                            Bundle bundle=new Bundle();
                            bundle.putString("username",nowusername);
                            bundle.putInt("type",0);
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtras(bundle);
                            finish();
                            startActivity(intent);
                            alertDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }

            case R.id.login_btn_log:{
                final AlertDialog.Builder builder4=new AlertDialog.Builder(this);
                LayoutInflater inflater=LayoutInflater.from(this);
                View view=inflater.inflate(R.layout.layout_admin,null);
                Button button=view.findViewById(R.id.adm_btn);
                final EditText editText=view.findViewById(R.id.adm_et);
                final EditText editText1=view.findViewById(R.id.adm_et2);
                final CheckBox rb_rember=view.findViewById(R.id.admin_radio_remeber);
                final CheckBox rb_auto=view.findViewById(R.id.admin_radio_autoin);
                Log.d("----------",SPUtils.get(this,"rember",3).toString());
                if (Integer.parseInt(SPUtils.get(this,"rember",3).toString())==1){
                    editText.setText(SPUtils.get(this,"username","111").toString());
                    editText1.setText(SPUtils.get(this,"password","111").toString());
                }
                final AlertDialog alertDialog=builder4.setView(view).show();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().equals("")||editText1.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "请补全信息！", Toast.LENGTH_LONG).show();
                        }else {
                            if (new Login(getApplicationContext()).sentenceLog(editText.getText().toString(),editText1.getText().toString()))
                            {
                                SPUtils.put(getApplicationContext(),"username",editText.getText().toString()).
                                        put(getApplicationContext(),"password",editText1.getText().toString());
                                if (rb_rember.isChecked()) SPUtils.put(getApplicationContext(),"rember",1);
                                else SPUtils.put(getApplicationContext(),"rember",0);
                                if (rb_auto.isChecked()) SPUtils.put(getApplicationContext(),"auto",1);
                                else SPUtils.put(getApplicationContext(),"auto",0);
                                Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
                                nowusername=editText.getText().toString();
                                Bundle bundle=new Bundle();
                                bundle.putString("username",nowusername);
                                bundle.putInt("type",0);
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtras(bundle);
                                finish();
                                startActivity(intent);
                                alertDialog.dismiss();
                            }
                            else Toast.makeText(getApplicationContext(),"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            }
            case R.id.login_btn_defult:{
                nowusername="defult";
                Bundle bundle=new Bundle();
                bundle.putString("username",nowusername);
                bundle.putInt("type",1);
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
                break;
            }

        }

    }

}
