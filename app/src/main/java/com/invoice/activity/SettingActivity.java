package com.invoice.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.invoice.R;
import com.invoice.global.Global;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);

        init();
    }

    public void init() {

        Button btn_company = (Button)findViewById(R.id.btn_company);
        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MyCompanyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btn_setting = (Button)findViewById(R.id.btn_docSetting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DocSettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btn_reset = (Button)findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingActivity.this);
                alertDialog
                        .setMessage("Are you sure?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                reset();

                                AlertDialog.Builder confirmAlert = new AlertDialog.Builder(SettingActivity.this);
                                confirmAlert
                                        .setMessage("Reset all database successfully!")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                confirmAlert.show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });

        Button btn_customerCSV = (Button)findViewById(R.id.btn_customerCSV);
        btn_customerCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, FileExplorerActivity.class);
                intent.putExtra("csv type", "customer");
                startActivity(intent);
                finish();
            }
        });

        Button btn_productCSV = (Button)findViewById(R.id.btn_productCSV);
        btn_productCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, FileExplorerActivity.class);
                intent.putExtra("csv type", "product");
                startActivity(intent);
                finish();
            }
        });
    }

    void reset() {

        SharedPreferences pref = getSharedPreferences(Global.ORDER_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.commit();

        pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.commit();

    }

}
