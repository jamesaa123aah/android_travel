package com.buu.app.travel.http;

import android.os.Handler;
import android.os.Message;

import com.buu.app.travel.Cfg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by king- on 2017/8/17.
 */

public class Login extends Thread{
    private Handler handler;
    private int what;
    private String phone;
    private String account;
    private String password;
    private String code;
    public Login(Handler handler, int what, String phone){
        this.handler = handler;
        this.what = what;
        this.phone = DoCode(phone);
        password = DoCode("autologin0000");
        account = DoCode(account);
        code = DoCode(code);
    }
    public Login(Handler handler, int what, String account, String password, String code){
        this.handler = handler;
        this.what = what;
        this.account = DoCode(account);
        this.password = DoCode(password);
        this.code = DoCode(code);
        phone = DoCode(phone);
    }
    @Override
    public void run() {
        try {
//            URL url = new URL(Cfg.BASE_URL+"login.php");
//            URL url=new URL("http://10.0.2.2:8080/Travel/LoginServlet?username="+account+"&password="+password);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(15*1000);
//            conn.setReadTimeout(15*1000);
//            conn.setDoOutput(true);
//            StringBuffer params = new StringBuffer();
//            params.append("phone").append("=").append(phone).append("&")
//                    .append("account").append("=").append(account).append("&")
//                    .append("password").append("=").append(password).append("&")
//                    .append("code").append("=").append(code).append("&")
//                    .append("IMEI").append("=").append(Cfg.IMEI).append("&");
//            byte[] bytes = params.toString().getBytes();
//            conn.getOutputStream().write(bytes);
//            InputStream inputStream = conn.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()
                    .url("http://10.0.2.2:8080/Travel/LoginServlet?username='"+account+"'&password='"+password+"'")
                    .build();
            Response response= null;
            String message=null;
            try {
                response = okHttpClient.newCall(request).execute();
                System.out.println("111111111");
                message=response.body().string();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.print(message);
            sendMSG(message);

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
