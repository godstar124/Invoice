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

public class MyCompanyActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_addre;
    private EditText et_addre2;
    private EditText et_city;
    private EditText et_state;
    private EditText et_code;
    private EditText et_business;
    private EditText et_email;
    private EditText et_phone2;
    private EditText et_fax;
    private EditText et_site;
    private EditText et_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_company);

        init();
        loadCompanyInfo();
    }

    private void init() {

        et_name = (EditText)findViewById(R.id.et_company_name);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_addre = (EditText)findViewById(R.id.et_company_address);
        et_addre2 = (EditText)findViewById(R.id.et_company_address2);
        et_city = (EditText)findViewById(R.id.et_city);
        et_state = (EditText)findViewById(R.id.et_state);
        et_code = (EditText)findViewById(R.id.et_code);
        et_business = (EditText)findViewById(R.id.et_business);
        et_email = (EditText)findViewById(R.id.et_email);
        et_phone2 = (EditText)findViewById(R.id.et_phone2);
        et_fax = (EditText)findViewById(R.id.et_fax);
        et_site = (EditText)findViewById(R.id.et_site);
        et_other = (EditText)findViewById(R.id.et_other);

        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveCompanyInfo();
                Intent intent = new Intent(MyCompanyActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_SETTINGS);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyCompanyActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_SETTINGS);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadCompanyInfo() {

        SharedPreferences pref = getSharedPreferences(Global.SETTING_DB, MODE_PRIVATE);
        et_name.setText(pref.getString("name", ""));
        et_phone.setText(pref.getString("phone", ""));
        et_addre.setText(pref.getString("addre", ""));
        et_addre2.setText(pref.getString("addre2", ""));
        et_city.setText(pref.getString("city", ""));
        et_state.setText(pref.getString("state", ""));
        et_code.setText(pref.getString("code", ""));
        et_business.setText(pref.getString("business", ""));
        et_email.setText(pref.getString("email", ""));
        et_phone2.setText(pref.getString("phone2", ""));
        et_fax.setText(pref.getString("fax", ""));
        et_site.setText(pref.getString("site", ""));
        et_other.setText(pref.getString("other", ""));
    }

    private void saveCompanyInfo() {

        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String addre = et_addre.getText().toString();
        String addre2 = et_addre2.getText().toString();
        String city = et_city.getText().toString();
        String state = et_state.getText().toString();
        String code = et_code.getText().toString();
        String business = et_business.getText().toString();

        String email = et_email.getText().toString();
        String phone2 = et_phone2.getText().toString();
        String fax = et_fax.getText().toString();
        String site = et_site.getText().toString();
        String other = et_other.getText().toString();

        SharedPreferences pref = getSharedPreferences(Global.SETTING_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("name", name);
        editor.putString("phone", phone);
        editor.putString("addre", addre);
        editor.putString("addre2", addre2);
        editor.putString("city", city);
        editor.putString("state", state);
        editor.putString("code", code);
        editor.putString("business", business);
        editor.putString("email", email);
        editor.putString("phone2", phone2);
        editor.putString("fax", fax);
        editor.putString("site", site);
        editor.putString("other", other);

        editor.commit();
    }
}
