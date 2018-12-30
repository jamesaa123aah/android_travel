package com.buu.app.travel;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.buu.app.travel.adapter.ChatAdapter;
import com.buu.app.travel.role.Msg1;

import java.util.ArrayList;
import java.util.List;

public class Aid extends AppCompatActivity {
        RecyclerView recyclerView;
        EditText editText;
        Button button;
        ChatAdapter adapter;
        List<Msg1> msgList;
        Resources rescourse;
        String[] a = {};
        private String b;
        View view;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_aid);
            initView();
            rescourse = getResources();
            a = rescourse.getStringArray(R.array.first_aid);
            b = rescourse.getString(R.string.first_aid);
            initDatas();
            adapter = new ChatAdapter(msgList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = editText.getText().toString();

                    if("1".equals(content))
                    {
                        Msg1 msg_right = new Msg1(content,Msg1.TYPE_RIGHT);
                        msgList.add(msg_right);
                        Msg1 msg_left = new Msg1(a[0],Msg1.TYPE_LEFT);
                        msgList.add(msg_left);
                        adapter.notifyItemChanged(msgList.size()-1);
                        editText.setText("");
                    }

//                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if("2".equals(content))
                    {
                        Msg1 msg_right = new Msg1(content,Msg1.TYPE_RIGHT);
                        msgList.add(msg_right);
                        Msg1 msg_left = new Msg1(a[1],Msg1.TYPE_LEFT);
                        msgList.add(msg_left);
                        adapter.notifyItemChanged(msgList.size()-1);
                        editText.setText("");
                    }

                    if("3".equals(content))
                    {
                        Msg1 msg_right = new Msg1(content,Msg1.TYPE_RIGHT);
                        msgList.add(msg_right);
                        Msg1 msg_left = new Msg1(a[2],Msg1.TYPE_LEFT);
                        msgList.add(msg_left);
                        adapter.notifyItemChanged(msgList.size()-1);
                        editText.setText("");
                    }

                    if("4".equals(content))
                    {
                        Msg1 msg_right = new Msg1(content,Msg1.TYPE_RIGHT);
                        msgList.add(msg_right);
                        Msg1 msg_left = new Msg1(a[3],Msg1.TYPE_LEFT);
                        msgList.add(msg_left);
                        adapter.notifyItemChanged(msgList.size()-1);
                        editText.setText("");
                    }
                    if("5".equals(content))
                    {
                        Msg1 msg_right = new Msg1(content,Msg1.TYPE_RIGHT);
                        msgList.add(msg_right);
                        Msg1 msg_left = new Msg1(a[4],Msg1.TYPE_LEFT);
                        msgList.add(msg_left);
                        adapter.notifyItemChanged(msgList.size()-1);
                        editText.setText("");
                    }
                    hidesoft();

                }
            });

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hidesoft();
                }
            });
        }

    private void initDatas() {
        msgList = new ArrayList<Msg1>();
        Msg1 msg = new Msg1(b,0);
        msgList.add(msg);
    }

    private void initView() {
        view = getWindow().getDecorView();
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycleview);
        editText = (EditText)findViewById(R.id.chat_edit);
        button = (Button)findViewById(R.id.chat_send);
    }

    private void hidesoft(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(msgList.size()-1);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
