package com.buu.app.travel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.buu.app.travel.adapter.FriendsAdapter;
import com.buu.app.travel.role.Friend;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.util.ArrayList;

public class FriendsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "FriendsActivity";
    private RecyclerView Friends;
    private FriendsAdapter friendsAdapter;
    private EditText search;
    private ArrayList<Friend> AllFriends = new ArrayList<>();
    private ArrayList<Friend> tmpFriends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        init();
        initListener();
    }

    private void init() {
        search = (EditText) findViewById(R.id.friends_et_search);
        Friends = (RecyclerView) findViewById(R.id.friends_rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Friends.setLayoutManager(linearLayoutManager);
        ArrayList<Friend> friends = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            friends.add(new Friend("http://p2.so.qhmsg.com/bdr/326__/t01f38ac4e8f4cf8e17.jpg", "name", "lastmessage", i, ((i + 1) * 10) % 100, i % 3 == 0 ? true : false, (i % 3 == 0 && i % 2 == 0) ? true : false));
        }
        refreshFriends(friends);
    }

    private void initListener() {
        findViewById(R.id.friends_bt_back).setOnClickListener(this);
        findViewById(R.id.friends_bt_add).setOnClickListener(this);
        findViewById(R.id.friends_bt_clear).setOnClickListener(this);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (search.getText().toString().length() > 0) {
                    String str = search.getText().toString();
                    tmpFriends = new ArrayList<>();
                    for (Friend fd : AllFriends) {
                        String name = fd.getName();
                        try {
                            str = PinyinHelper.getShortPinyin(str);
                            name = PinyinHelper.convertToPinyinString(name, ",", PinyinFormat.WITHOUT_TONE);
                        } catch (PinyinException e) {
                            e.printStackTrace();
                        }
                        char[] strs = str.toCharArray();
                        String[] names = name.split(",");
                        boolean su = true;
                        for (int i = 0; i < str.length(); i++) {
                            if (names[i].toCharArray()[0] != strs[i]) {
                                su = false;
                                break;
                            }
                        }
                        if (su) {
                            tmpFriends.add(fd);
                        }
                    }
                    friendsAdapter = new FriendsAdapter(tmpFriends);
                    Friends.setAdapter(friendsAdapter);
                    friendsAdapter.notifyDataSetChanged();
                } else {
                    friendsAdapter = new FriendsAdapter(AllFriends);
                    Friends.setAdapter(friendsAdapter);
                    friendsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_bt_back:
                //back
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
            case R.id.friends_bt_add:
                //add
                break;
            case R.id.friends_bt_clear:
                search.setText("");
                break;
            default:
                break;

        }
    }

    private void refreshFriends(final ArrayList<Friend> friends) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Friend> onlineS = new ArrayList<>();
                ArrayList<Friend> offlineS = new ArrayList<>();
                for (Friend f : friends) {
                    if (f.isOnline()) {
                        onlineS.add(f);
                    } else {
                        offlineS.add(f);
                    }
                }
                int on = onlineS.size();
                int off = offlineS.size();
                for (int i = 1; i < on; i++) {
                    for (int j = 0; j < on - i; j++) {
                        if (onlineS.get(j).getMegCount() < onlineS.get(j + 1).getMegCount()) {
                            Friend tmp = onlineS.get(j);
                            onlineS.set(j, onlineS.get(j + 1));
                            onlineS.set(j + 1, tmp);
                        }
                    }
                }
                for (int i = 1; i < off; i++) {
                    for (int j = 0; j < off - i; j++) {
                        if (offlineS.get(j).getMegCount() < offlineS.get(j + 1).getMegCount()) {
                            Friend tmp = offlineS.get(j);
                            offlineS.set(j, offlineS.get(j + 1));
                            offlineS.set(j + 1, tmp);
                        }
                    }
                }
                ArrayList<Friend> fs = new ArrayList<>();
                fs.add(new Friend("service", getString(R.string.service), getString(R.string.ask_help), 0, 1, true, false));
                fs.addAll(onlineS);
                fs.addAll(offlineS);
                final ArrayList<Friend> finalFs = fs;
                AllFriends = fs;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (friendsAdapter == null) {
                            friendsAdapter = new FriendsAdapter(finalFs);
                            Friends.setAdapter(friendsAdapter);
                        } else {
                            friendsAdapter = new FriendsAdapter(finalFs);
                            Friends.setAdapter(friendsAdapter);
                            friendsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }

}
