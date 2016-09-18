package com.invoice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.global.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocSettingActivity extends AppCompatActivity {

    public EditText et_orderPrefix;
    public EditText et_startOrder;
    public EditText et_tax;
    public EditText et_rep1;
    public EditText et_rep2;
    public EditText et_rep3;
    public EditText et_rep4;
    public EditText et_rep5;
    public EditText et_rep6;
    public EditText et_rep7;
    public EditText et_rep8;
    public EditText et_rep9;
    public EditText et_rep10;
    //public Spinner sp_rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_doc_setting);

        init();
        loadDocInfo();
    }

    private void init() {

        et_orderPrefix = (EditText)findViewById(R.id.et_prefix);
        et_startOrder = (EditText)findViewById(R.id.et_start_order);
        et_tax = (EditText)findViewById(R.id.et_tax);
        et_rep1 = (EditText)findViewById(R.id.et_rep1);
        et_rep2 = (EditText)findViewById(R.id.et_rep2);
        et_rep3 = (EditText)findViewById(R.id.et_rep3);
        et_rep4 = (EditText)findViewById(R.id.et_rep4);
        et_rep5 = (EditText)findViewById(R.id.et_rep5);
        et_rep6 = (EditText)findViewById(R.id.et_rep6);
        et_rep7 = (EditText)findViewById(R.id.et_rep7);
        et_rep8 = (EditText)findViewById(R.id.et_rep8);
        et_rep9 = (EditText)findViewById(R.id.et_rep9);
        et_rep10 = (EditText)findViewById(R.id.et_rep10);

        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDocInfo();
                Intent intent = new Intent(DocSettingActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_SETTINGS);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DocSettingActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_SETTINGS);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadDocInfo() {

        SharedPreferences pref = getSharedPreferences(Global.SETTING_DB, MODE_PRIVATE);
        et_orderPrefix.setText(pref.getString("prefix", ""));
        et_startOrder.setText(String.format("%d", pref.getInt("startOrder", 0)));
        et_tax.setText(String.format("%d", pref.getInt("tax", 0)));
        et_rep1.setText(pref.getString("rep1", ""));
        et_rep2.setText(pref.getString("rep2", ""));
        et_rep3.setText(pref.getString("rep3", ""));
        et_rep4.setText(pref.getString("rep4", ""));
        et_rep5.setText(pref.getString("rep5", ""));
        et_rep6.setText(pref.getString("rep6", ""));
        et_rep7.setText(pref.getString("rep7", ""));
        et_rep8.setText(pref.getString("rep8", ""));
        et_rep9.setText(pref.getString("rep9", ""));
        et_rep10.setText(pref.getString("rep10", ""));
    }

    private void saveDocInfo() {

        String prefix = et_orderPrefix.getText().toString();
        String startOrder = et_startOrder.getText().toString();
        String tax = et_tax.getText().toString();

        SharedPreferences pref = getSharedPreferences(Global.SETTING_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("prefix", prefix);
        editor.putInt("startOrder", Integer.parseInt(startOrder));
        editor.putInt("tax", Integer.parseInt(tax));
        editor.putString("rep1", et_rep1.getText().toString());
        editor.putString("rep2", et_rep2.getText().toString());
        editor.putString("rep3", et_rep3.getText().toString());
        editor.putString("rep4", et_rep4.getText().toString());
        editor.putString("rep5", et_rep5.getText().toString());
        editor.putString("rep6", et_rep6.getText().toString());
        editor.putString("rep7", et_rep7.getText().toString());
        editor.putString("rep8", et_rep8.getText().toString());
        editor.putString("rep9", et_rep9.getText().toString());
        editor.putString("rep10", et_rep10.getText().toString());
        //editor.putString("rep", rep);

        editor.commit();
    }

}
