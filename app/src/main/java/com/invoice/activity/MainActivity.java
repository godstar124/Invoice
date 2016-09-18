package com.invoice.activity;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.global.Global;

public class MainActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        setTabView();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String tab = extras.getString("tab");
            if(tab.equals(Global.TAB_ORDERS))
                tabHost.setCurrentTab(0);

            if(tab.equals(Global.TAB_CUSTOMER))
                tabHost.setCurrentTab(1);

            if(tab.equals(Global.TAB_PRODUCT))
                tabHost.setCurrentTab(2);

            if(tab.equals(Global.TAB_SETTINGS))
                tabHost.setCurrentTab(3);
        }
    }

    private void setTabView() {

        tabHost = getTabHost();
        tabHost.setup();

        setupTab(new TextView(this), Global.TAB_ORDERS);
        setupTab(new TextView(this), Global.TAB_CUSTOMER);
        setupTab(new TextView(this), Global.TAB_PRODUCT);
        setupTab(new TextView(this), Global.TAB_SETTINGS);
    }

    private void setupTab(final View view, final String tag) {

        final View tabView = createTabView(tabHost.getContext(), tag);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(tabView);

        Intent intent;
        if(tag.equals(Global.TAB_ORDERS)) {

            intent = new Intent(MainActivity.this, OrdersActivity.class);
        } else if(tag.equals(Global.TAB_CUSTOMER)) {

            intent = new Intent(MainActivity.this, CustomerActivity.class);
        } else if(tag.equals(Global.TAB_PRODUCT)) {

            intent = new Intent(MainActivity.this, ProductActivity.class);
        } else {

            intent = new Intent(MainActivity.this, SettingActivity.class);
        }

        tabHost.setCurrentTab(0);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
    }

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }
}
