package com.buu.app.travel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.buu.app.travel.error.ServerError;
import com.buu.app.travel.http.Login;
import com.buu.app.travel.role.User;
import com.buu.app.travel.tools.UpdateUDB;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public void actionStart(Context context) {
        Intent start = new Intent(context, LoginActivity.class);
        context.startActivity(start);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean BACK = false;

    private void back() {
        if (!BACK) {
            BACK = true;
            Toast.makeText(this, R.string.back_again_exit, Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BACK = false;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ActivityCollector.finishAll();
            System.exit(0);
        }
    }

    /////////////////////////////////
    private static final String TAG = "LoginActivity";
    private final int AUTO = 0;
    private final int CHINESE = 1;
    private final int ENGLISH = 2;
    private Context mContext;
    private Handler mHandler;
    private EditText eAccount;
    private EditText ePassword;
    private EditText eCode;
    private ImageView iCode;
    private CheckBox cbRemember;
    private CheckBox cbAuto;
    private final int LOGIN = 1;
    private ProgressDialog PD;
    private boolean CANCEL = false;
    private TextView look;
    public static Intent intent;

    //////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getMesg();
        LitePal.initialize(this);
        init();
        initListener();
        initHandler();
        mContext = this;

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }
//private void getMesg(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient okHttpClient=new OkHttpClient();
//                Request request=new Request.Builder()
//                        .url("http://10.0.2.2:8080/Travel/LoginServlet?username='17301260630'&password='123456'")
//                        .build();
//                Response response= null;
//                try {
//                    response = okHttpClient.newCall(request).execute();
//                    System.out.println("111111111");
//                    System.out.println(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//
//}
//    @Override
//    public void onClick(View v) {
//
//    }
//
    private void init() {
        iCode = (ImageView) findViewById(R.id.login_code_img);
        eAccount = (EditText) findViewById(R.id.login_etext_account);
        ePassword = (EditText) findViewById(R.id.login_etext_password);
        eCode = (EditText) findViewById(R.id.login_etext_code);
        look = (TextView) findViewById(R.id.login_look);
    }

    private void initListener() {

        findViewById(R.id.login_bt_register).setOnClickListener(this);
        findViewById(R.id.login_bt_login).setOnClickListener(this);

        findViewById(R.id.login_text_language).setOnClickListener(this);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
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
    protected void onStart() {
        super.onStart();
        User lastUser = DataSupport.findLast(User.class);
        if (lastUser != null && lastUser.isAutoLogin()) {
            long id = lastUser.getAcId();
            if ((System.currentTimeMillis() - lastUser.getLastTime()) > 15 * 24 * 60 * 60 * 1000) {
                Toast.makeText(mContext, R.string.auto_login_over_time, Toast.LENGTH_SHORT).show();
            } else {
                Cfg.ID = id;
                //auto_login
            }
        }
        loadCode(iCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_register:
                new RegisterActivity().actionStart(mContext);
                break;
            case R.id.login_bt_login:
                /*new MainActivity().actionStart(mContext);
                finish();*/
                if (check()) {
                    login();
                }
                break;


            case R.id.login_text_language:
                new AlertDialog.Builder(mContext)
                        .setTitle("LANGUAGE")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setItems(new String[]{"AUTO", "简体中文", "ENGLISH"},
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (which == AUTO) {
                                            Cfg.LANGUAGE = "";
                                        } else if (which == CHINESE) {
                                            Cfg.LANGUAGE = "zh";
                                        } else if (which == ENGLISH) {
                                            Cfg.LANGUAGE = "en";
                                        }
                                        LoginActivity.this.actionStart(mContext);
                                        intent = new Intent(LoginActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        mContext.startActivity(intent);

                                    }
                                }
                        )
                        .setNegativeButton(R.string.CANCEL, null)
                        .show();
                break;
            default:
                break;
        }
    }

    private void loadCode(ImageView code) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.loading).error(R.drawable.load_fail).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).load(Cfg.BASE_URL + "codeImage.php?IMEI=" + Cfg.IMEI).apply(options).into(code);
    }

    private boolean check() {
        String account = eAccount.getText().toString();
        String password = ePassword.getText().toString();
        String code = eCode.getText().toString();
        if (account.isEmpty()) {
            Toast.makeText(mContext, R.string.error_account_can_not_null, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(mContext, R.string.error_password_can_not_null, Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (code.isEmpty()) {
//            Toast.makeText(mContext, R.string.error_code_can_not_null, Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

    private void login() {
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
        String account = eAccount.getText().toString();
        String password = ePassword.getText().toString();
        String code = eCode.getText().toString();
        new Login(mHandler, LOGIN, account, password, code).start();
    }

    private void lgBack(String re) {
        if (!CANCEL) {
            String account = eAccount.getText().toString();
            String password = ePassword.getText().toString();
            if (re.indexOf("success") > -1) {
//                Cfg.ID = Integer.parseInt(re.split(":")[1]);
                PD.setMessage(getString(R.string.login_success_jump));
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                PD.dismiss();
                if (Cfg.REMEMBER_ME) {
                    UpdateUDB.Update(account, password);
                }
                finish();
            } else if (re.indexOf("fail")>-1) {
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
