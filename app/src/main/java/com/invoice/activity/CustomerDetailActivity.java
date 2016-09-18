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

public class CustomerDetailActivity extends AppCompatActivity {

    private int position;

    private EditText et_name;
    private EditText et_contact;
    private EditText et_billingAddress;
    private EditText et_billingAddress1;
    private EditText et_billingPhone;
    private EditText et_billingEmail;
    private EditText et_shippingAddress;
    private EditText et_shippingAddress1;
    private EditText et_shippingPhone;
    private EditText et_shippingEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_customer_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("position", 0);
        }

        init();
        loadDetails();
    }

    void init() {

        et_name = (EditText)findViewById(R.id.et_name);
        et_contact = (EditText)findViewById(R.id.et_contact);
        et_billingAddress = (EditText)findViewById(R.id.et_billingAddress);
        et_billingAddress1 = (EditText)findViewById(R.id.et_billingAddress1);
        et_billingPhone = (EditText)findViewById(R.id.et_billingPhone);
        et_billingEmail = (EditText)findViewById(R.id.et_billingEmail);
        et_shippingAddress = (EditText)findViewById(R.id.et_shippingAddress);
        et_shippingAddress1 = (EditText)findViewById(R.id.et_shippingAddress1);
        et_shippingPhone = (EditText)findViewById(R.id.et_shippingPhone);
        et_shippingEmail = (EditText)findViewById(R.id.et_shippingEmail);

        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
                Intent intent = new Intent(CustomerDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_CUSTOMER);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomerDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_CUSTOMER);
                startActivity(intent);
                finish();
            }
        });
    }

    void loadDetails() {

        SharedPreferences pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);

        et_name.setText(pref.getString(String.format("name_%d", position), ""));
        et_contact.setText(pref.getString(String.format("contact_%d", position), ""));
        et_billingAddress.setText(pref.getString(String.format("bAddres_%d", position), ""));
        et_billingAddress1.setText(pref.getString(String.format("bAddres1_%d", position), ""));
        et_billingPhone.setText(pref.getString(String.format("bPhone_%d", position), ""));
        et_billingEmail.setText(pref.getString(String.format("bEmail_%d", position), ""));
        et_shippingAddress.setText(pref.getString(String.format("sAddres_%d", position), ""));
        et_shippingAddress1.setText(pref.getString(String.format("sAddres1_%d", position), ""));
        et_shippingPhone.setText(pref.getString(String.format("sPhone_%d", position), ""));
        et_shippingEmail.setText(pref.getString(String.format("sEmail_%d", position), ""));

    }

    private void save() {

        SharedPreferences pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(String.format("name_%d", position), et_name.getText().toString());
        editor.putString(String.format("contact_%d", position), et_contact.getText().toString());
        editor.putString(String.format("bAddres_%d", position), et_billingAddress.getText().toString());
        editor.putString(String.format("bAddres1_%d", position), et_billingAddress1.getText().toString());
        editor.putString(String.format("bPhone_%d", position), et_billingPhone.getText().toString());
        editor.putString(String.format("bEmail_%d", position), et_billingEmail.getText().toString());
        editor.putString(String.format("sAddres_%d", position), et_shippingAddress.getText().toString());
        editor.putString(String.format("sAddres1_%d", position), et_shippingAddress1.getText().toString());
        editor.putString(String.format("sPhone_%d", position), et_shippingPhone.getText().toString());
        editor.putString(String.format("sEmail_%d", position), et_shippingEmail.getText().toString());

        editor.commit();
    }
}
