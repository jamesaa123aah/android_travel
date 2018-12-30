package com.buu.app.travel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.buu.app.travel.error.ServerError;
import com.buu.app.travel.http.Login;
import com.buu.app.travel.http.Register;
import com.buu.app.travel.http.SendCode;

import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    public void actionStart(Context context){
        Intent start = new Intent(context,RegisterActivity.class);
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
    ///////////////////////////
    private static final String TAG = "RegisterActivity";
    private Context mContext;
    private Handler mHandler;
    private EditText phone;
    private EditText code;
    private EditText password;
    private TextView natePre;
    private Spinner nate;
    private Button send;
    private ProgressDialog PD;
    private boolean CANCEL = false;
    private final int REGISTER = 1;
    private final int LOGIN = 2;
    private SendCode sendCode;
    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        init();
        initListener();
        initHandler();
    }
    private void init(){
        nate = (Spinner) findViewById(R.id.register_spinner_nate);
        phone = (EditText) findViewById(R.id.register_etext_number);
        code = (EditText) findViewById(R.id.register_etext_code);
        password = (EditText) findViewById(R.id.register_etext_password);
        natePre = (TextView) findViewById(R.id.register_text_tip_nate_code);
        send = (Button) findViewById(R.id.register_bt_send_code);
    }
    private void initListener(){
        send.setOnClickListener(this);
        findViewById(R.id.register_bt_back).setOnClickListener(this);
        findViewById(R.id.register_bt_register).setOnClickListener(this);
        findViewById(R.id.register_text_account_register).setOnClickListener(this);
    }
    private void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case REGISTER:
                        String reRe = (String) msg.obj;
                        regBack(reRe);
                        break;
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
            case R.id.register_bt_back:
                back();
                break;
            case R.id.register_text_account_register:
                new RegisterAccountActivity().actionStart(mContext);
                break;
            case R.id.register_bt_send_code:
               sendCode = new SendCode(this, phone.getText().toString(), send) {
                   @Override
                   protected void OnSuccess(int i, String s) {
                       if(i==CHECK){
                           register();
                       }
                   }
                };
                sendCode.send();
                break;
            case R.id.register_bt_register:
                if(check()){
                    sendCode.check(code.getText().toString());
                }
                break;
            default:
                break;
        }
    }
    private boolean check(){
        String ph = phone.getText().toString();
        String co = code.getText().toString();
        String pa = password.getText().toString();
        if(ph.isEmpty()){
            Toast.makeText(mContext,R.string.error_phone_number_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern php = Pattern.compile("^((0\\d{2,3}-\\d{7,8})|(1\\d{10}))$");
        if(!php.matcher(ph).find()){
            Toast.makeText(mContext,R.string.please_input_phone_number_rightly,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(co.isEmpty()){
            Toast.makeText(mContext,R.string.error_code_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern cop = Pattern.compile("^[a-zA-Z0-9]{4,8}$");
        if(!cop.matcher(co).find()){
            Toast.makeText(mContext,R.string.error_code_wrong,Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern pPa = Pattern.compile("^[a-zA-Z0-9 _~!@#$%\\^&*()+\\-=|?/.\\\\]*$");
        if(password.length()>7) {
            if (!pPa.matcher(pa).find()) {
                Toast.makeText(mContext, R.string.error_password_cant_not_include_special_char, Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(mContext, R.string.error_password_too_short,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void register(){
        PD = new ProgressDialog(mContext);
        PD.setMessage(getString(R.string.register_doing));
        PD.setCancelable(true);
        PD.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                CANCEL = false;
            }
        });
        PD.show();
        String ph = phone.getText().toString();
        String pa = password.getText().toString();
        new Register(mHandler,REGISTER,ph,pa).start();
    }
    private void regBack(String reRe){
        if(!CANCEL){
            if(reRe.equals("success")){
                PD.setMessage(getString(R.string.register_success_jump));
                login();
            }else if(reRe.equals("fail")){
                PD.dismiss();
                Toast.makeText(mContext,R.string.register_fail,Toast.LENGTH_SHORT).show();
            }else if(reRe.equals("null")){
                PD.dismiss();
                Toast.makeText(mContext, R.string.UNKNOW_ERR , Toast.LENGTH_SHORT).show();
            }else{
                new ServerError(mContext,reRe) {
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
    private void login(){
        String ph = phone.getText().toString();
        new Login(mHandler,LOGIN,ph).start();
    }
    private void lgBack(String re){
        if(!CANCEL){
            if(re.indexOf("success")>-1){
                Cfg.ID = Integer.parseInt(re.split(":")[1]);
                PD.setMessage(getString(R.string.login_success_jump));
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                PD.dismiss();
                finish();
            }else if(re.equals("fail")){
                PD.dismiss();
                Toast.makeText(mContext,R.string.register_fail,Toast.LENGTH_SHORT).show();
            }else if(re.equals("null")){
                PD.dismiss();
                Toast.makeText(mContext, R.string.UNKNOW_ERR , Toast.LENGTH_SHORT).show();
            }else{
                new ServerError(mContext,re) {
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
