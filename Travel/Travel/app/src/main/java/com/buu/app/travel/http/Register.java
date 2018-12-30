package com.buu.app.travel.http;

import android.os.Handler;
import android.os.Message;

import com.buu.app.travel.Cfg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by king- on 2017/8/13.
 */

public class Register extends Thread{
    private Handler handler;
    private int what;
    private String phone;
    private String account;
    private String nickname;
    private String password;
    private String code;
    public Register(Handler handler, int what, String phone,String password){
        this.handler = handler;
        this.what = what;
        this.phone = DoCode(phone);
        this.password = DoCode(password);
        account = DoCode(account);
        nickname = DoCode(nickname);
        code = DoCode(code);
    }
    public Register(Handler handler, int what, String account, String nickname, String password, String code){
        this.handler = handler;
        this.what = what;
        this.account = DoCode(account);
        this.nickname = DoCode(nickname);
        this.password = DoCode(password);
        this.code = DoCode(code);
        phone = DoCode(phone);
    }
    @Override
    public void run() {
       try {
            URL url = new URL(Cfg.BASE_URL+"register.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15*1000);
            conn.setReadTimeout(15*1000);
            conn.setDoOutput(true);
            StringBuffer params = new StringBuffer();
            params.append("phone").append("=").append(phone).append("&")
                    .append("account").append("=").append(account).append("&")
                    .append("nickname").append("=").append(nickname).append("&")
                    .append("password").append("=").append(password).append("&")
                    .append("code").append("=").append(code).append("&")
                    .append("IMEI").append("=").append(Cfg.IMEI).append("&");
            byte[] bytes = params.toString().getBytes();
            conn.getOutputStream().write(bytes);
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            sendMSG(stringBuilder.toString());
        } catch (Exception e) {
            sendMSG("fail");
        }
    }
    private void sendMSG(String re){
        Message meg = new Message();
        meg.what = what;
        meg.obj = re;
        handler.sendMessage(meg);
    }
    private String DoCode(String data){
        if(data==null){
            return "";
        }
        String d;
        try {
            d = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return data;
        }
        if(d!=null){
            return d;
        }else {
            return data;
        }
    }
}
