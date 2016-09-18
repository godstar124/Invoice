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

public class AddCustomerActivity extends AppCompatActivity {

    private String toScreen = "";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_customer);

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

                    saveNewCustomer();
                    Intent intent = new Intent(AddCustomerActivity.this, AddOrderActivity.class);
                    Global.customerPosition = position;
                    startActivity(intent);
                    finish();
                } else {

                    saveNewCustomer();
                    Intent intent = new Intent(AddCustomerActivity.this, MainActivity.class);
                    intent.putExtra("tab", Global.TAB_CUSTOMER);
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
                    Intent intent = new Intent(AddCustomerActivity.this, CustomerActivity.class);
                    intent.putExtra("tab", Global.TAB_ORDERS);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddCustomerActivity.this, MainActivity.class);
                    intent.putExtra("tab", Global.TAB_CUSTOMER);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void saveNewCustomer() {

        String name = ((EditText)findViewById(R.id.et_name)).getText().toString();
        String contact = ((EditText)findViewById(R.id.et_contact)).getText().toString();
        String billingAddress = ((EditText)findViewById(R.id.et_billingAddress)).getText().toString();
        String billingAddress1 = ((EditText)findViewById(R.id.et_billingAddress1)).getText().toString();
        String billingPhone = ((EditText)findViewById(R.id.et_billingPhone)).getText().toString();
        String billingEmail = ((EditText)findViewById(R.id.et_billingEmail)).getText().toString();
        String shippingAddress = ((EditText)findViewById(R.id.et_shippingAddress)).getText().toString();
        String shippingAddress1 = ((EditText)findViewById(R.id.et_shippingAddress1)).getText().toString();
        String shippingPhone = ((EditText)findViewById(R.id.et_shippingPhone)).getText().toString();
        String shippingEmail = ((EditText)findViewById(R.id.et_shippingEmail)).getText().toString();

        SharedPreferences pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        position = pref.getInt("customerNum", 0);
        editor.putString(String.format("name_%d", position), name);
        editor.putString(String.format("contact_%d", position), contact);
        editor.putString(String.format("bAddres_%d", position), billingAddress);
        editor.putString(String.format("bAddres1_%d", position), billingAddress1);
        editor.putString(String.format("bPhone_%d", position), billingPhone);
        editor.putString(String.format("bEmail_%d", position), billingEmail);
        editor.putString(String.format("sAddres_%d", position), shippingAddress);
        editor.putString(String.format("sAddres1_%d", position), shippingAddress1);
        editor.putString(String.format("sPhone_%d", position), shippingPhone);
        editor.putString(String.format("sEmail_%d", position), shippingEmail);

        editor.putInt("customerNum", position + 1);

        editor.commit();
    }
}
