package com.buu.app.travel.http;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import com.buu.app.travel.Cfg;
import com.buu.app.travel.R;
import com.buu.app.travel.error.LocalError;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

/**
 * Created by king- on 2017/8/20.
 */

public abstract class SendCode {
    public final int SEND = 0;
    public final int CHECK = 1;
    private String phoneNum;
    private Button send;
    private Activity activity;
    public SendCode(Activity activity,String phoneNum, Button send){
        this.activity = activity;
        this.phoneNum = phoneNum;
        this.send = send;
    }
    public void send(){
        if(phoneNum.isEmpty()||phoneNum.length()<11){
            Toast.makeText(activity, R.string.please_input_phone_number_rightly, Toast.LENGTH_SHORT).show();
            return;
        }
        SMSSDK.getInstance().getSmsCodeAsyn(phoneNum, "1", new SmscodeListener() {
            @Override
            public void getCodeSuccess(String s) {
                OnSuccess(SEND,s);
                send.setClickable(false);
                send.setText(Cfg.CODE_TIME_OUT+activity.getString(R.string.X_sends_retry));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = Cfg.CODE_TIME_OUT;
                        while (i>0){
                            final int finalI = i;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    send.setText(finalI +activity.getString(R.string.X_sends_retry));
                                }
                            });
                            i--;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                send.setText(R.string.loginCode_send_code);
                                send.setClickable(true);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void getCodeFail(int i, String s) {
                new LocalError(activity,""+i) {
                    @Override
                    protected void OnResult(final String result) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
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
        });
    }
    public void check(String code){
        SMSSDK.getInstance().checkSmsCodeAsyn(phoneNum, code, new SmscheckListener() {
            @Override
            public void checkCodeSuccess(String s) {
                OnSuccess(CHECK,s);
            }

            @Override
            public void checkCodeFail(int i, String s) {
                new LocalError(activity,""+i) {
                    @Override
                    protected void OnResult(final String result) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
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
        });
    }
    protected abstract void OnSuccess(int i,String s);
}
