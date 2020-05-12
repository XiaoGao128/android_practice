package com.example.didida_corder.ToolClass;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.didida_corder.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private TextView tv_title,tv_msg,tv_cancel,tv_confirm;
    private String title,message,cancel,confirm;
    private IOnCancelListener oncancelListener;
    private IOnConfirmListener confirmListener;
    public CustomDialog(@NonNull Context context) {
        super(context);
    }
    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context);
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog setCancel(String cancel,IOnCancelListener onCancelListener) {
        this.cancel = cancel;
        this.oncancelListener= (IOnCancelListener) onCancelListener;
        return this;
    }

    public CustomDialog setConfirm(String confirm,IOnConfirmListener onConfirmListener) {
        this.confirm = confirm;
        this.confirmListener= (IOnConfirmListener) onConfirmListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_dialog);
        tv_title=findViewById(R.id.tv_title);
        tv_cancel=findViewById(R.id.tv_cancel);
        tv_msg=findViewById(R.id.tv_msg);
        tv_confirm=findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(message)){
            tv_msg.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)){
            tv_cancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(confirm)){
            tv_confirm.setText(confirm);
        }
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_cancel:{
                if (oncancelListener!=null){
                    oncancelListener.onCancel(this);
                    dismiss();
                }
                break;
            }
            case R.id.tv_confirm:{
                if (confirmListener!=null){
                    confirmListener.onConfirm(this);
                    dismiss();
                }
                break;
            }
        }
    }


    public interface IOnCancelListener{
        void onCancel(CustomDialog dialog);
    }
    public interface IOnConfirmListener{
        void onConfirm(CustomDialog dialog);
    }
}
