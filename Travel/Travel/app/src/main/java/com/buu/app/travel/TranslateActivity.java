package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;

import java.io.ObjectStreamClass;

public class TranslateActivity extends BaseActivity implements View.OnClickListener {
    public void actionStart(Context context) {
        Intent start = new Intent(context, TranslateActivity.class);
        context.startActivity(start);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean BACK = false;

    private void back() {
        if (!BACK) {
            BACK = true;
            Toast.makeText(this, R.string.back_again_exit, Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BACK = false;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ActivityCollector.finishAll();
            System.exit(0);
        }
    }

    /////////////////////////////
    private static final String TAG = "TranslateActivity";
    private Context mContext;
    private Spinner sForm;
    private Spinner sTo;
    private EditText eForm;
    private TextView tTo;

    /////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mContext = this;
        init();
        initListener();
    }

    private void init() {
        sForm = (Spinner) findViewById(R.id.translate_spinner_from);
        sTo = (Spinner) findViewById(R.id.translate_spinner_to);
        eForm = (EditText) findViewById(R.id.translate_etext_form);
        tTo = (TextView) findViewById(R.id.translate_text_to);
    }

    private void initListener() {
        findViewById(R.id.translate_bt_photo).setOnClickListener(this);
        findViewById(R.id.translate_bt_translation).setOnClickListener(this);
        findViewById(R.id.translate_lin_from).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate_lin_from:
                eForm.requestFocus();
                break;
            case R.id.translate_bt_photo:
                break;
            case R.id.translate_bt_translation:
                translation();
                break;
            default:
                break;
        }
    }

    private void translation() {
        String ef = eForm.getText().toString();
        final int sf = sForm.getSelectedItemPosition();
        final int st = sTo.getSelectedItemPosition();
        if(sf!=1&&st!=1&&sf!=0&&st!=0){
            fanyi(ef, sf, 1, new TranslateListener() {

                @Override
                public void onResult(Translate result, String input) {//查询成功
                    String re = "";
                    if (result.getExplains() != null) {
                        re = result.getExplains().get(0);
                    } else {
                        re = result.getTranslations().get(0);
                    }
                    fanyi(re, 1, st, new TranslateListener() {

                        @Override
                        public void onResult(Translate result, String input) {//查询成功
                            String re1 = "";
                            if (result.getExplains() != null) {
                                re1 = result.getExplains().get(0);
                            } else {
                                re1 = result.getTranslations().get(0);
                            }
                            tTo.setText(re1.substring(re1.indexOf(".")+1,re1.length()));
                        }

                        @Override
                        public void onError(TranslateErrorCode error) {//查询失败
                            Toast.makeText(mContext, getString(R.string.tanslation_fail) + ":" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(TranslateErrorCode error) {//查询失败
                    Toast.makeText(mContext, getString(R.string.tanslation_fail) + ":" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            fanyi(ef, sf, st, new TranslateListener() {

                @Override
                public void onResult(Translate result, String input) {//查询成功
                    StringBuilder re = new StringBuilder();
                    if (result.getExplains() != null) {
                        for (String s : result.getExplains()) {
                            re.append(s).append("\n");
                        }
                    } else {
                        for (String s : result.getTranslations()) {
                            re.append(s).append("\n");
                        }
                    }
                    tTo.setText(re.toString());
                }

                @Override
                public void onError(TranslateErrorCode error) {//查询失败
                    Toast.makeText(mContext, getString(R.string.tanslation_fail) + ":" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void fanyi(String input, int from ,int to,TranslateListener listener){
        //查词对象初始化，请设置source参数为app对应的名称（英文字符串)
        Language langFrom = LanguageUtils.getLangByName(Cfg.NATE_DEFAULT[from]);
        //若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        //Language langFrom = LanguageUtils.getLangByName("自动");
        Language langTo = LanguageUtils.getLangByName(Cfg.NATE_DEFAULT[to]);
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("Travel")
                .from(langFrom).to(langTo).build();
        Translator translator = Translator.getInstance(tps);
        //查询，返回两种情况，一种是成功，相关结果存储在result参数中，另外一种是失败，失败信息放在TranslateErrorCode 是一个枚举类，整个查询是异步的，为了简化操作，回调都是在主线程发生。
        translator.lookup(input, listener);
    }
}
