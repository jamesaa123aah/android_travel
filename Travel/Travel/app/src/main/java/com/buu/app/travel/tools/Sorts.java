package com.buu.app.travel.tools;

import android.os.Handler;
import android.os.Message;

import com.buu.app.travel.role.Unique_Item;

import java.util.ArrayList;

/**
 * Created by king- on 2017/9/21.
 */

public  class Sorts {
    public static void Sort(final Handler handler, final int what, final int type, final ArrayList<Unique_Item> Unique_Items){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Unique_Item[] items = new Unique_Item[Unique_Items.size()];
                for(int i = 0;i<items.length;i++){
                    items[i] = Unique_Items.get(i);
                }
                int size = items.length;
                for(int i=1;i<size;i++){
                    for (int j = 0;j<size-i;j++){
                        if(calculate(type,items[j])<calculate(type,items[j+1])){
                            Unique_Item tmp = items[j];
                            items[j] = items[j+1];
                            items[j+1] = tmp;
                        }
                    }
                }
                ArrayList<Unique_Item> back = new ArrayList<Unique_Item>();
                for(Unique_Item items1:items){
                    back.add(items1);
                }
                Message meg = new Message();
                meg.what = what;
                meg.obj = back;
                handler.sendMessage(meg);
            }
        }).start();
    }
    private static double calculate(int type,Unique_Item item){
        double re = 0.0;
        switch (type){
            case 0://综合排序
                double total =  (((double) (item.getLook()+item.getComment()+item.getPraise())+item.getMoney())/(double) item.getStar())/4.0;
                re = total;
                break;
            case 1://热度
                double total2 = (double) (item.getLook()+item.getComment()+item.getPraise())/3.0;
                re = total2;
                break;
            case 2://
                double total3 = (double)item.getPraise()/(double) item.getComment();
                re = total3;
                break;
            case 3://消费
                double total4 = item.getMoney();
                re = total4;
                break;
            case 4://浏览量
                double total5 = (double) item.getLook();
                re = total5;
                break;
            default:
                break;
        }
        return re;
    }
}
