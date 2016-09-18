package com.invoice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.invoice.R;
import com.invoice.custom.OrderListAdapter;
import com.invoice.custom.ProductListAdpater;
import com.invoice.global.Global;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {


    private ProgressDialog dialog;

    private ArrayList<String> order = new ArrayList<String>();
    private ArrayList<String> contact = new ArrayList<String>();
    private ArrayList<String> rep = new ArrayList<String>();
    private ArrayList<String> total = new ArrayList<String>();
    private ArrayList<Integer> searchResultPosition = new ArrayList<Integer>();
    private boolean isSearch = false;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_orders);

        loadCustomerData();
        init();

    }

    private void init() {

        final ListView list = (ListView)findViewById(R.id.orderList);
        final OrderListAdapter listAdpater = new OrderListAdapter(this, order, contact, rep, total);
        list.setAdapter(listAdpater);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isSearch)
                    position = searchResultPosition.get(position);

                Intent intent = new Intent(OrdersActivity.this, OrderDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_order = (ImageButton)findViewById(R.id.ibtn_add_customer);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, AddOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    isSearch = false;
                    listAdpater.updateData(order, contact, rep, total);
                    list.setAdapter(listAdpater);
                } else {

                    isSearch = true;
                    ArrayList<String> resultOrder = new ArrayList<String>();
                    ArrayList<String> resultContact = new ArrayList<String>();
                    ArrayList<String> resultRep = new ArrayList<String>();
                    ArrayList<String> resultTotal = new ArrayList<String>();
                    searchResultPosition.clear();

                    for (int i = 0; i < order.size(); i++) {
                        if (order.get(i).startsWith(newText) || contact.get(i).startsWith(newText)) {
                            resultOrder.add(order.get(i));
                            resultContact.add(contact.get(i));
                            resultRep.add(rep.get(i));
                            resultTotal.add(total.get(i));
                            searchResultPosition.add(i);
                        }
                    }
                    listAdpater.updateData(resultOrder, resultContact, resultRep, resultTotal);
                    list.setAdapter(listAdpater);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearch = false;
                listAdpater.updateData(order, contact, rep, total);
                list.setAdapter(listAdpater);
                return false;
            }
        });

    }

    private void loadCustomerData() {

        //dialog = ProgressDialog.show(OrdersActivity.this, "", "Loading. Please wait...", true);

        SharedPreferences pref = getSharedPreferences(Global.SETTING_DB, MODE_PRIVATE);
        Global.orderPrefix = pref.getString("prefix", "");
        Global.startOrderNum = pref.getInt("startOrder", 0);
        Global.tax = pref.getInt("tax", 0);

        // Get Sales Rep list
        Global.repList.clear();             // Init
        for (int i = 0; i < 10; i++) {      // load
            String rep = pref.getString(String.format("rep%d", i), "");
            if(!rep.isEmpty()) {
                Global.repList.add(rep);
            }
        }

        pref = getSharedPreferences(Global.ORDER_DB, MODE_PRIVATE);
        int num = pref.getInt(String.format("orderNum"), 0);

        for (int i = 0; i < num; i++) {
            order.add(pref.getString(String.format("order_%d", i), ""));
            contact.add(pref.getString(String.format("contact_%d", i), ""));
            rep.add(pref.getString(String.format("rep_%d", i), ""));
            total.add(pref.getString(String.format("subtotal_%d", i), ""));
        }

        //dialog.hide();
    }


}
