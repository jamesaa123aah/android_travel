package com.buu.app.travel.tools;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by bei on 2017/9/24.
 */

public class DynamicChangeView {
    private Animation tmpAnim;
    private ViewGroup tmpParent;
    private View tmpChild;
    private View tmpClick;
    private Activity tmpActivity;
    public DynamicChangeView(Activity tmpActivity){
        this.tmpActivity = tmpActivity;
    }
    public void AddView(ViewGroup parent, View child){
        if(tmpClick!=null){
            tmpClick=null;
        }
        if(child.getAnimation()==null||child.getAnimation()==tmpAnim) {
            Animation anim = new TranslateAnimation(child.getWidth(), 0, 0, 0);
            child.setAnimation(anim);
            anim.setDuration(500);
            child.startAnimation(anim);
            tmpAnim = anim;
        }
        parent.addView(child);
        tmpParent = parent;
        tmpChild = child;
    }
    public void AddView(ViewGroup parent, View child, final View click){
        click.setClickable(false);
        long time = 500;
        if(child.getAnimation()==null||child.getAnimation()==tmpAnim) {
            Animation anim = new TranslateAnimation(child.getWidth(),0,0,0);
            anim.setDuration(500);
            child.setAnimation(anim);
            child.startAnimation(anim);
            tmpAnim = anim;
        }else {
            time = child.getAnimation().getDuration();
        }
        parent.addView(child);
        tmpParent = parent;
        tmpChild = child;
        tmpClick = click;
        final long finalTime = time;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(finalTime);
                    tmpActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            click.setClickable(true);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public  void RemoveView(){
        long time = 500;
        if(tmpClick!=null){
            tmpClick.setClickable(false);
        }
        if(tmpChild.getAnimation()==null||tmpChild.getAnimation()==tmpAnim){
            Animation anim = new TranslateAnimation(0,-tmpChild.getWidth(),0,0);
            anim.setDuration(500);
            tmpChild.setAnimation(anim);
            tmpChild.startAnimation(anim);
            tmpAnim = anim;
        }
        tmpParent.removeView(tmpChild);
        if(tmpClick!=null){
            final long finalTime = time;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(finalTime);
                        tmpActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tmpClick.setClickable(true);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public void RemoveView(ViewGroup parent,View child){
        if(tmpClick!=null){
            tmpClick=null;
        }
        if(child.getAnimation()==null||child.getAnimation()==tmpAnim){
            Animation anim = new TranslateAnimation(0,-child.getWidth(),0,0);
            anim.setDuration(500);
            child.setAnimation(anim);
            child.startAnimation(anim);
            tmpAnim = anim;
        }
        parent.removeView(child);
    }
    public void RemoveView(ViewGroup parent, View child, final View click){
        click.setClickable(false);
        long time = 500;
        if(child.getAnimation()==null||child.getAnimation()==tmpAnim){
            Animation anim = new TranslateAnimation(0,-child.getWidth(),0,0);
            anim.setDuration(500);
            child.setAnimation(anim);
            child.startAnimation(anim);
            tmpAnim = anim;
        }else {
            time = child.getAnimation().getDuration();
        }
        parent.removeView(child);
        final long finalTime = time;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(finalTime);
                    tmpActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            click.setClickable(true);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public View getTmpChild() {
        return tmpChild;
    }

    public void setTmpChild(View tmpChild) {
        this.tmpChild = tmpChild;
    }

    public View getTmpClick() {
        return tmpClick;
    }

    public void setTmpClick(View tmpClick) {
        this.tmpClick = tmpClick;
    }
}
