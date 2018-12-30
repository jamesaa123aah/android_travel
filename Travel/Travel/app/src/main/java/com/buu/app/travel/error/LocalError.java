package com.buu.app.travel.error;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by king- on 2017/8/17.
 */

public abstract class LocalError {
    public LocalError(final Context context, final String error){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "null";
                String ERR = error;
                try {
                    InputStream in = context.getAssets().open("ErrorTXT/LocalErr.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = br.readLine())!=null){
                        String[] l = line.split(":");
                        if(l[0].equals(ERR)){
                            result = l[1];
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    OnResult(result);
                }
            }
        }).start();
    }
    protected abstract void OnResult(String result);
}
