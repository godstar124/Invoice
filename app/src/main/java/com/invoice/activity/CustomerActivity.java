package com.invoice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.invoice.R;
import com.invoice.custom.CustomerListAdapter;
import com.invoice.global.Global;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    private ArrayList<String> customerName = new ArrayList<String>();
    private ArrayList<Integer> searchResultPosition = new ArrayList<Integer>();
    private boolean isSearch = false;

    public String toScreen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_customer);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            toScreen = extras.getString("tab", "");
        }

        loadCustomerData();
        init();
    }

    private void init() {

        RelativeLayout topLayout = (RelativeLayout)findViewById(R.id.topLayout);
        if(toScreen.equals(Global.TAB_ORDERS)) {
            topLayout.setVisibility(View.VISIBLE);
        } else {
            topLayout.setVisibility(View.GONE);
        }

        final ListView listView = (ListView)findViewById(R.id.list);
        final CustomerListAdapter listAdpater = new CustomerListAdapter(this, customerName);
        listView.setAdapter(listAdpater);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isSearch)
                    position = searchResultPosition.get(position);

                if (toScreen.equals(Global.TAB_ORDERS)) {
                    Intent intent = new Intent(CustomerActivity.this, AddOrderActivity.class);
                    Global.customerPosition = position;
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                    finish();
                }
            }
        });

        SearchView searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    isSearch = false;
                    listAdpater.updateData(customerName);
                    listView.setAdapter(listAdpater);
                } else {

                    isSearch = true;
                    ArrayList<String> resultCustomer = new ArrayList<String>();
                    searchResultPosition.clear();

                    for (int i = 0; i < customerName.size(); i++) {
                        if (customerName.get(i).startsWith(newText)) {
                            resultCustomer.add(customerName.get(i));
                            searchResultPosition.add(i);
                        }
                    }
                    listAdpater.updateData(resultCustomer);
                    listView.setAdapter(listAdpater);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearch = false;
                listAdpater.updateData(customerName);
                listView.setAdapter(listAdpater);
                return false;
            }
        });

        ImageButton btn_add = (ImageButton)findViewById(R.id.ibtn_add_customer);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddCustomerActivity.class);
                if (toScreen.equals(Global.TAB_ORDERS)) {
                    intent.putExtra("tab", Global.TAB_ORDERS);
                } else {
                    intent.putExtra("tab", Global.TAB_CUSTOMER);
                }
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadCustomerData() {

        SharedPreferences pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);
        int number = pref.getInt(String.format("customerNum"), 0);

        for (int i = 0; i < number; i++) {
            customerName.add(pref.getString(String.format("name_%d", i), ""));
        }
    }

}

