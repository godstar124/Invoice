package com.invoice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.invoice.R;
import com.invoice.global.Global;

public class ProductDetailActivity extends AppCompatActivity {

    private int position;

    private EditText et_code;
    private EditText et_name;
    private EditText et_price;
    private EditText et_cbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_product_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("position", 0);
        }

        init();
        loadDetails();
    }

    void init() {

        et_code = (EditText)findViewById(R.id.et_code);
        et_name = (EditText)findViewById(R.id.et_name);
        et_price = (EditText)findViewById(R.id.et_price);
        et_cbm = (EditText)findViewById(R.id.et_cbm);

        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_PRODUCT);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_PRODUCT);
                startActivity(intent);
                finish();
            }
        });
    }

    void loadDetails() {

        SharedPreferences pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);

        et_code.setText(pref.getString(String.format("code_%d", position), ""));
        et_name.setText(pref.getString(String.format("name_%d", position), ""));
        et_price.setText(pref.getString(String.format("price_%d", position), ""));
        et_cbm.setText(pref.getString(String.format("cbm_%d", position), ""));
    }

    private void save() {

        SharedPreferences pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(String.format("code_%d", position), et_code.getText().toString());
        editor.putString(String.format("name_%d", position), et_name.getText().toString());
        editor.putString(String.format("price_%d", position), et_price.getText().toString());
        editor.putString(String.format("cbm_%d", position), et_cbm.getText().toString());

        editor.commit();
    }
}
