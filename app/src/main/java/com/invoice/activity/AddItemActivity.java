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

public class AddItemActivity extends AppCompatActivity {

    private String toScreen = "";
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_item);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            toScreen = extras.getString("tab");
        }

        init();
    }

    private void init() {
        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toScreen.equals(Global.TAB_ORDERS)) {

                    saveNewProduct();
                    Intent intent = new Intent(AddItemActivity.this, AddOrderActivity.class);
                    Global.orderList.add(position);
                    Global.qtyList.add(1);
                    startActivity(intent);
                    finish();
                } else {

                    saveNewProduct();
                    Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                    intent.putExtra("tab", Global.TAB_PRODUCT);
                    startActivity(intent);
                    finish();
                }
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toScreen.equals(Global.TAB_ORDERS)) {
                    Intent intent = new Intent(AddItemActivity.this, ProductActivity.class);
                    intent.putExtra("tab", Global.TAB_ORDERS);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                    intent.putExtra("tab", Global.TAB_PRODUCT);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void saveNewProduct() {

        String code = ((EditText)findViewById(R.id.et_code)).getText().toString();
        String name = ((EditText)findViewById(R.id.et_name)).getText().toString();
        String price = ((EditText)findViewById(R.id.et_price)).getText().toString();
        String cbm = ((EditText)findViewById(R.id.et_cbm)).getText().toString();

        SharedPreferences pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        position = pref.getInt("proNum", 0);
        editor.putString(String.format("code_%d", position), code);
        editor.putString(String.format("name_%d", position), name);
        editor.putString(String.format("price_%d", position), price);
        editor.putString(String.format("cbm_%d", position), cbm);
        editor.putInt("proNum", position+1);

        editor.commit();
    }
}
