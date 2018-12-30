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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.buu.app.travel.error.ServerError;
import com.buu.app.travel.http.Login;
import com.buu.app.travel.http.Register;
import com.buu.app.travel.role.User;
import com.buu.app.travel.tools.UpdateUDB;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterAccountActivity extends BaseActivity implements View.OnClickListener{
    public void actionStart(Context context){
        Intent start = new Intent(context,RegisterAccountActivity.class);
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
    private static final String TAG = "RegisterAccountActivity";
    private Context mContext;
    private EditText eAccount;
    private EditText eNickname;
    private EditText ePassword;
    private EditText eRePassword;
    private EditText eCode;
    private ImageView codeImg;
    private Handler mHandler;
    private ProgressDialog PD;
    private boolean CANCEL = false;
    private final int REGISTER = 1;
    private final int LOGIN = 2;
    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        LitePal.initialize(this);
        init();
        initListener();
        initHandler();
    }
    private void init(){
        mContext = this;
        eAccount = (EditText) findViewById(R.id.registerAccount_etext_account);
        eNickname = (EditText) findViewById(R.id.registerAccount_etext_nickname);
        ePassword = (EditText) findViewById(R.id.registerAccount_etext_password);
        eRePassword = (EditText) findViewById(R.id.registerAccount_etext_repeat_password);
        eCode = (EditText) findViewById(R.id.registerAccount_etext_code);
        codeImg = (ImageView) findViewById(R.id.registerAccount_code_img);
        loadCode(codeImg);
    }
    private void initListener(){
        findViewById(R.id.registerAccount_bt_back).setOnClickListener(this);
        findViewById(R.id.registerAccount_refresh_code).setOnClickListener(this);
        findViewById(R.id.registerAccount_bt_register).setOnClickListener(this);
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
            case R.id.registerAccount_bt_back:
                back();
                break;
            case R.id.registerAccount_refresh_code:
                loadCode(codeImg);
                break;
            case R.id.registerAccount_bt_register:
                if(check()){
                    Register();
                };
                break;
            default:
                break;
        }
    }
    private void loadCode(ImageView code){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.loading).error(R.drawable.load_fail).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).load(Cfg.BASE_URL+"codeImage.php?IMEI="+Cfg.IMEI).apply(options).into(code);
    }
    private boolean check(){
        String account = eAccount.getText().toString();
        String nickname = eNickname.getText().toString();
        String password = ePassword.getText().toString();
        String rPassword = eRePassword.getText().toString();
        String code = eCode.getText().toString();
        Pattern pAc = Pattern.compile("^([a-zA-Z])([a-zA-Z0-9 _\\-])*$");
        Pattern pPa = Pattern.compile("^[a-zA-Z0-9 _~!@#$%\\^&*()+\\-=|?/.\\\\]*$");
        Pattern pCo = Pattern.compile("^[a-zA-Z0-9]{4,8}$");
        if(account.isEmpty()){
            Toast.makeText(mContext, R.string.error_account_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(account.length()>3) {
            if (!pAc.matcher(account).find()) {
                Toast.makeText(mContext, R.string.error_account_format_wrong,Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(mContext, R.string.error_account_too_short,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nickname.isEmpty()){
            Toast.makeText(mContext, R.string.error_nickname_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nickname.length()<=3){
            Toast.makeText(mContext, R.string.error_nickname_toot_short,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.isEmpty()){
            Toast.makeText(mContext, R.string.error_password_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length()>7) {
            if (!pPa.matcher(password).find()) {
                Toast.makeText(mContext, R.string.error_password_cant_not_include_special_char, Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(mContext, R.string.error_password_too_short,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(rPassword.isEmpty()){
            Toast.makeText(mContext, R.string.error_repeat_password_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(rPassword)){
            Toast.makeText(mContext, R.string.error_twice_password_different,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(code.isEmpty()){
            Toast.makeText(mContext, R.string.error_code_can_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pCo.matcher(code).find()){
            Toast.makeText(mContext, R.string.error_code_wrong,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void Register(){
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
        String account = eAccount.getText().toString();
        String nickname = eNickname.getText().toString();
        String password = ePassword.getText().toString();
        String code = eCode.getText().toString();
        new Register(mHandler,REGISTER,account,nickname,password,code).start();
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
        String account = eAccount.getText().toString();
        String password = ePassword.getText().toString();
        String code = eCode.getText().toString();
        new Login(mHandler,LOGIN,account,password,code).start();
    }
    private void lgBack(String re){
        if(!CANCEL){
            if(re.indexOf("success")>-1){
                Cfg.ID = Integer.parseInt(re.split(":")[1]);
                PD.setMessage(getString(R.string.login_success_jump));
                Intent intent = new Intent(RegisterAccountActivity.this,MainActivity.class);
                startActivity(intent);
                PD.dismiss();
                UpdateUDB.Update(eAccount.getText().toString(),ePassword.getText().toString());
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
