package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Rate extends AppCompatActivity {
    private static final String TAG = "Rate";
    Spinner spinner,spinner1;
    TextView resultMoney,resultMoney1;
    EditText editmoney,editmoney1;
    Boolean isFirst = true;
    private static String stringResult,stringResult1;
    private static float intResult1;
    private static double intResult;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rate);

        initView();
        initData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        SpinnerListener mylistener = new SpinnerListener();
        spinner.setOnItemSelectedListener(mylistener);
        SpinnerListener1 myListner1 = new SpinnerListener1();
        spinner1.setOnItemSelectedListener(myListner1);


        findViewById(R.id.main_rate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Bank.class));
            }
        });

    }

    private void initData(

    ) {
        list.add("Dollars");
        list.add("Euro");
        list.add("Yen");
        list.add("Hong Kong Dollar");
        list.add("Pound");
        list.add("Dollar A");
        list.add("NewZealander");
        list.add("Singaporean");
        list.add("Swing Franc");
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.main_rate_spinner);
        spinner1 = (Spinner) findViewById(R.id.main_rate_spinner1);
        resultMoney = (TextView)findViewById(R.id.main_rate_resultmoney);
        resultMoney1 = (TextView)findViewById(R.id.main_rate_resultmoney1);
        editmoney = (EditText)findViewById(R.id.main_rate_editmoney);
        editmoney1 = (EditText)findViewById(R.id.main_rate_editmoney1);

    }

    class  SpinnerListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "onItemSelected: "+l);
            stringResult = editmoney.getText().toString();
            if(stringResult.length()>5){
                Toast.makeText(getApplicationContext(),"Number of Money put out！", Toast.LENGTH_SHORT).show();
                resultMoney.setText("0");
            }
            else if(stringResult.equals("")&&isFirst == false){
                Toast.makeText(getApplicationContext(),"Input Money", Toast.LENGTH_SHORT).show();
            }
            else if("".equals(stringResult)){
                stringResult = "";
                isFirst = false;
            }
            else{

                switch(i){
                    case 0:
                        Log.d(TAG, "onItemSelected: "+editmoney.getText().toString().trim());
                        intResult = Integer.parseInt(stringResult)*6.8856;
                        Log.d(TAG, "onItemSelected: "+intResult);
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 1:
                        intResult = Integer.parseInt(stringResult)*7.4308;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 2:
                        intResult = Integer.parseInt(stringResult)*0.061869;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 3:
                        intResult = Integer.parseInt(stringResult)*0.88650;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 4:
                        intResult = Integer.parseInt(stringResult)*8.5898;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 5:
                        intResult = Integer.parseInt(stringResult)*5.2772;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 6:
                        intResult = Integer.parseInt(stringResult)*4.8469;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 7:
                        intResult = Integer.parseInt(stringResult)*6.9414;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();

                    case 8:
                        intResult = Integer.parseInt(stringResult)*7.4308;
                        resultMoney.setText(String.valueOf(intResult));
                        Looper.loop();
                    default:
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


    class SpinnerListener1 implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d(TAG, "onItemClick: "+i);
            stringResult1 = editmoney1.getText().toString();
            if(stringResult1.length()>5){
                Toast.makeText(getApplicationContext(),"Number of Money put out！", Toast.LENGTH_SHORT).show();
                resultMoney1.setText("0.0");
            }
            if("".equals(stringResult1)){
                stringResult1 = "";
            }

            else{

                switch(i){
                    case 0:
                        intResult1 = Float.parseFloat(stringResult1)/6.8858f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 1:
                        intResult1 = Float.parseFloat(stringResult1)/7.4308f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 2:
                        intResult1 = Float.parseFloat(stringResult1)/0.061869f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 3:
                        intResult1 = Float.parseFloat(stringResult1)/0.88650f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 4:
                        intResult1 = Float.parseFloat(stringResult1)/8.5898f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 5:
                        intResult1 = Float.parseFloat(stringResult1)/5.2772f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 6:
                        intResult1 = Float.parseFloat(stringResult1)/4.8469f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 7:
                        intResult1 = Float.parseFloat(stringResult1)/6.9414f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                    case 8:
                        intResult1 = Float.parseFloat(stringResult1)/7.4308f;
                        resultMoney1.setText(String.valueOf(intResult1));
                        Looper.loop();

                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
