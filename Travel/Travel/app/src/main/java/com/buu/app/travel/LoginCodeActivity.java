package com.buu.app.travel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buu.app.travel.error.ServerError;
import com.buu.app.travel.http.Login;
import com.buu.app.travel.http.SendCode;
import com.buu.app.travel.tools.UpdateUDB;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

public class LoginCodeActivity extends BaseActivity implements View.OnClickListener{
    public void actionStart(Context context){
        Intent start = new Intent(context,LoginCodeActivity.class);
        context.startActivity(start);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            back();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void back(){

    }
    ///////////////////////////////////////////////
    private static final String TAG = "LoginCodeActivity";
    private final int LOGIN = 1;
    private Context mContext;
    private EditText ePhone;
    private EditText eCode;
    private Button btSend;
    private SendCode sendCode;
    private Handler mHandler;
    private ProgressDialog PD;
    private boolean CANCEL = false;
    /////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);
        mContext = this;
        init();
        initListener();
        initHandler();
    }
    private void init(){
        ePhone = (EditText) findViewById(R.id.loginCode_etext_number);
        eCode = (EditText) findViewById(R.id.loginCode_etext_code);
        btSend = (Button) findViewById(R.id.loginCode_bt_send_code);
    }
    private void initListener(){
        btSend.setOnClickListener(this);
        findViewById(R.id.loginCode_bt_login).setOnClickListener(this);
    }
    private void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case LOGIN:
                       lgBack((String) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginCode_bt_send_code:
                sendCode = new SendCode(this, ePhone.getText().toString(), btSend) {
                    @Override
                    protected void OnSuccess(int i, String s) {
                        if(i==CHECK){
                            login();
                        }
                    }
                };
                sendCode.send();
                break;
            case R.id.loginCode_bt_login:
                if(check()) {
                    PD = new ProgressDialog(mContext);
                    PD.setMessage(getString(R.string.login_doing));
                    PD.setCancelable(true);
                    PD.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            CANCEL = false;
                        }
                    });
                    PD.show();
                    sendCode.check(eCode.getText().toString());
                }
            default:
                break;
        }
    }
    private boolean check(){
        String phone = ePhone.getText().toString();
        String code = eCode.getText().toString();
        if(phone.isEmpty()){
            Toast.makeText(mContext,R.string.error_phone_number_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(code.isEmpty()){
            Toast.makeText(mContext,R.string.error_code_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(code.length()<4||code.length()>8){
            Toast.makeText(mContext,R.string.error_code_wrong,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void login(){
        new Login(mHandler,LOGIN,ePhone.getText().toString()).start();
    }
    private void lgBack(String re) {
        if (!CANCEL) {
            if (re.indexOf("success") > -1) {
                Cfg.ID = Integer.parseInt(re.split(":")[1]);
                PD.setMessage(getString(R.string.login_success_jump));
                Intent intent = new Intent(LoginCodeActivity.this,MainActivity.class);
                startActivity(intent);
                PD.dismiss();
                finish();
            } else if (re.equals("fail")) {
                PD.dismiss();
                Toast.makeText(mContext, R.string.register_fail, Toast.LENGTH_SHORT).show();
            } else if (re.equals("null")) {
                PD.dismiss();
                Toast.makeText(mContext, R.string.UNKNOW_ERR, Toast.LENGTH_SHORT).show();
            } else {
                new ServerError(mContext, re) {
                    @Override
                    protected void OnResult(final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PD.dismiss();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                                dialog.setTitle(R.string.ERROR_tip);
                                dialog.setMessage(result);
                                dialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialog.show();
                            }
                        });
                    }
                };
            }
        }
    }
}
