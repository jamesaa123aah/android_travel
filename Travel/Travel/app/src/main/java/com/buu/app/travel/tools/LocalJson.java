package com.buu.app.travel.tools;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by bei on 2017/9/17.
 */

public class LocalJson {
    public static String getJson(String fileName,Context context){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager manager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    manager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
