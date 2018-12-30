package com.buu.app.travel;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.buu.app.travel.adapter.MsgAdapter;
import com.buu.app.travel.adapter.SmileAdapter;
import com.buu.app.travel.role.Msg;
import com.buu.app.travel.tools.ScreenTools;

import java.io.IOException;
import java.util.ArrayList;

public class ChatActivity extends BaseActivity implements View.OnClickListener, View.OnLayoutChangeListener {
    private SmileAdapter smileAdapter;
    private RecyclerView smileRec;
    private MsgAdapter msgAdapter;
    private RecyclerView msgRec;
    private ArrayList<Msg> msgs;
    private EditText etInput;
    private boolean softInputOpen = false;
    private boolean smileShow  =false;
    private boolean addShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        initListener();
    }

    private void init() {
        msgs = new ArrayList<>();
        msgRec = (RecyclerView) findViewById(R.id.chat_rv_list);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (!name.isEmpty()) {
            ((TextView) findViewById(R.id.chat_title)).setText(name);
            if (name.equals(getString(R.string.service))) {
                msgs.add(new Msg(getString(R.string.well_come_to_use_the_Customer_Service), Msg.TYPE_RECEIVED, false));
            }
        }
        countSmile();
        msgAdapter = new MsgAdapter(msgs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRec.setLayoutManager(linearLayoutManager);
        msgRec.setAdapter(msgAdapter);
        etInput = (EditText) findViewById(R.id.chat_et_input);
    }

    private void initListener() {
        findViewById(R.id.chat_bt_back).setOnClickListener(this);
        findViewById(R.id.chat_bt_menu).setOnClickListener(this);
        findViewById(R.id.chat_bt_change_input).setOnClickListener(this);
        findViewById(R.id.chat_bt_input).setOnClickListener(this);
        findViewById(R.id.chat_bt_smile).setOnClickListener(this);
        findViewById(R.id.chat_bt_add).setOnClickListener(this);
        findViewById(R.id.chat_v_mask).setOnClickListener(this);
        findViewById(R.id.chat_bt_send).setOnClickListener(this);
        findViewById(R.id.chat_rootView).addOnLayoutChangeListener(this);
        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    findViewById(R.id.chat_v_mask).setVisibility(View.VISIBLE);
                }
            }
        });
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etInput.getText().toString().length() > 1) {
                    findViewById(R.id.chat_ll_smile_add).setVisibility(View.GONE);
                    findViewById(R.id.chat_bt_send).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.chat_ll_smile_add).setVisibility(View.VISIBLE);
                    findViewById(R.id.chat_bt_send).setVisibility(View.GONE);
                }
            }
        });
        msgRec.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                msgRec.scrollToPosition(msgs.size() - 1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_bt_back:
                new Thread(new Runnable() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            Log.e("Exception when sendKeyDownUpSync", e.toString());
                        }
                    }
                }).start();
                break;
            case R.id.chat_bt_menu:
                break;
            case R.id.chat_bt_change_input:
                if (findViewById(R.id.chat_bt_change_input).isSelected()) {
                    findViewById(R.id.chat_bt_change_input).setSelected(false);
                    findViewById(R.id.chat_bt_input).setVisibility(View.VISIBLE);
                    etInput.setVisibility(View.GONE);
                    if(softInputOpen) {
                        switchSoftInput(false);
                    }
                } else {
                    findViewById(R.id.chat_bt_change_input).setSelected(true);
                    findViewById(R.id.chat_bt_input).setVisibility(View.GONE);
                    etInput.setVisibility(View.VISIBLE);
                    switchSoftInput(true);
                }
                break;
            case R.id.chat_bt_input:
                break;
            case R.id.chat_bt_smile:
                if(smileShow){
                    switchAddSmile(false);
                }else {
                    if(addShow){
                        switchAddContent(false);
                    }
                    switchAddSmile(true);
                }
                break;
            case R.id.chat_bt_add:
                if(addShow){
                    switchAddContent(false);
                }else {
                    if(smileShow){
                        switchAddSmile(false);
                    }
                    switchAddContent(true);
                }
                break;
            case R.id.chat_v_mask:
                if(softInputOpen){
                    switchSoftInput(false);
                }else if(smileShow) {
                    switchAddSmile(false);
                }else {
                    switchAddContent(false);
                }
                break;
            case R.id.chat_bt_send:
                Msg msg = new Msg(etInput.getText().toString(), Msg.TYPE_SEND, false);
                AddMsg(msg);
                findViewById(R.id.chat_v_mask).setVisibility(View.GONE);
                etInput.setText("");
                if (getCurrentFocus() != null) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        switch (v.getId()) {
            case R.id.chat_rootView:
                int keyHeight = ScreenTools.getScreenSize(this).y / 3;
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    //up
                    if(!softInputOpen){
                        findViewById(R.id.chat_v_mask).setVisibility(View.VISIBLE);
                        softInputOpen = true;
                    }
                    if(smileShow){
                        switchAddSmile(false);
                    }
                    if(addShow){
                        switchAddContent(false);
                    }
                    findViewById(R.id.chat_v_mask).setVisibility(View.VISIBLE);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    //down
                    if(softInputOpen){
                        findViewById(R.id.chat_v_mask).setVisibility(View.GONE);
                        softInputOpen = false;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void switchAddSmile(boolean show) {
        if(show){
            if(softInputOpen){
                switchSoftInput(false);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.chat_ll_add_smile).setVisibility(View.VISIBLE);
                                findViewById(R.id.chat_v_mask).setVisibility(View.VISIBLE);
                                findViewById(R.id.chat_bt_smile).setSelected(true);
                                smileShow = true;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            findViewById(R.id.chat_ll_add_smile).setVisibility(View.GONE);
            findViewById(R.id.chat_v_mask).setVisibility(View.GONE);
            findViewById(R.id.chat_bt_smile).setSelected(false);
            smileShow = false;
        }
    }
    private void  switchAddContent(boolean show){
        if(show){
            if(softInputOpen){
                switchSoftInput(false);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.chat_ll_add_files).setVisibility(View.VISIBLE);
                                findViewById(R.id.chat_v_mask).setVisibility(View.VISIBLE);
                                findViewById(R.id.chat_bt_add).setSelected(true);
                                addShow = true;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            findViewById(R.id.chat_ll_add_files).setVisibility(View.GONE);
            findViewById(R.id.chat_v_mask).setVisibility(View.GONE);
            findViewById(R.id.chat_bt_add).setSelected(false);
            addShow = false;
        }
    }
    private void switchSoftInput(boolean op){
        if(op){
            etInput.requestFocus();
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(etInput, InputMethodManager.SHOW_IMPLICIT);
            findViewById(R.id.chat_v_mask).setVisibility(View.VISIBLE);
            softInputOpen = true;
        } else if (getCurrentFocus() != null) {
            etInput.clearFocus();
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            findViewById(R.id.chat_v_mask).setVisibility(View.GONE);
            softInputOpen = false;
        }
    }

    public void AddMsg(Msg msg) {
        msgs.add(msg);
        msgAdapter.notifyItemInserted(msgs.size() - 1);
        msgRec.scrollToPosition(msgs.size() - 1);
    }

    public void AddSmileMsg(int i, int type) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("emoji/ (" + i + ").png"));
            Msg msg = new Msg("", type, true);
            msg.setBitmap(bitmap);
            msgs.add(msg);
            msgAdapter.notifyItemInserted(msgs.size() - 1);
            msgRec.scrollToPosition(msgs.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddImgMsg(String uri, final int type) {
        Glide.with(this).load(uri).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                Msg msg = new Msg("", type, true);
                msg.setBitmap(drawableToBitmap(resource));
                msgs.add(msg);
                msgAdapter.notifyItemInserted(msgs.size() - 1);
                msgRec.scrollToPosition(msgs.size() - 1);
            }
        });
    }

    private void countSmile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> sm = new ArrayList<Integer>();
                int x = 1;
                while (true) {
                    try {
                        getAssets().open("emoji/ (" + x + ").png");
                        sm.add(x);
                        x++;
                    } catch (IOException e) {
                        break;
                    }
                }
                final ArrayList<Integer> fsm = sm;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        smileAdapter = new SmileAdapter(fsm, ChatActivity.this);
                        smileRec = (RecyclerView) findViewById(R.id.chat_rv_smiles);
                        GridLayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 4, GridLayoutManager.HORIZONTAL, false);
                        smileRec.setLayoutManager(layoutManager);
                        smileRec.setAdapter(smileAdapter);
                    }
                });
            }
        }).start();
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
