package com.invoice.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.invoice.custom.ProductListAdpater;
import com.invoice.global.Global;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private ArrayList<String> codeArray = new ArrayList<String>();
    private ArrayList<String> nameArray = new ArrayList<String>();
    private ArrayList<String> priceArray = new ArrayList<String>();
    private ArrayList<String> cbmArray = new ArrayList<String>();
    private ArrayList<Integer> searchResultPosition = new ArrayList<Integer>();

    private String toScreen = "";
    private boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_product);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            toScreen = extras.getString("tab");
        }

        loadProductData();
        init();

    }

    private void init() {

        RelativeLayout topLayout = (RelativeLayout)findViewById(R.id.topLayout);
        if(toScreen.equals(Global.TAB_ORDERS)) {
            topLayout.setVisibility(View.VISIBLE);
        } else {
            topLayout.setVisibility(View.GONE);
        }

        final ListView list = (ListView)findViewById(R.id.list);
        final ProductListAdpater listAdpater = new ProductListAdpater(this, codeArray, nameArray, priceArray, cbmArray);
        list.setAdapter(listAdpater);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isSearch)
                    position = searchResultPosition.get(position);

                if (toScreen.equals(Global.TAB_ORDERS)) {

                    Intent intent = new Intent(ProductActivity.this, AddOrderActivity.class);
                    for (int i = 0; i < Global.orderList.size(); i++) {
                        if (Global.orderList.get(i) == position) {
                            showAlert(ProductActivity.this, "The selected Product is already exist in your Order!");
                            return;
                        }
                    }
                    Global.orderList.add(position);
                    Global.qtyList.add(1);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
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
                    listAdpater.updateData(codeArray, nameArray, priceArray, cbmArray);
                    list.setAdapter(listAdpater);
                } else {

                    isSearch = true;
                    ArrayList<String> resultCode = new ArrayList<String>();
                    ArrayList<String> resultName = new ArrayList<String>();
                    ArrayList<String> resultPrice = new ArrayList<String>();
                    ArrayList<String> resultCBM = new ArrayList<String>();
                    searchResultPosition.clear();

                    for (int i = 0; i < codeArray.size(); i ++) {
                        if(codeArray.get(i).startsWith(newText) || nameArray.get(i).startsWith(newText)) {
                            resultCode.add(codeArray.get(i));
                            resultName.add(nameArray.get(i));
                            resultPrice.add(priceArray.get(i));
                            resultCBM.add(cbmArray.get(i));
                            searchResultPosition.add(i);
                        }
                    }
                    listAdpater.updateData(resultCode, resultName, resultPrice, resultCBM);
                    list.setAdapter(listAdpater);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                isSearch = false;
                listAdpater.updateData(codeArray, nameArray, priceArray, cbmArray);
                list.setAdapter(listAdpater);
                return false;
            }
        });

        ImageButton btn_product = (ImageButton)findViewById(R.id.ibtn_add_product);
        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductActivity.this, AddItemActivity.class);
                if (toScreen.equals(Global.TAB_ORDERS)) {
                    intent.putExtra("tab", Global.TAB_ORDERS);
                } else {
                    intent.putExtra("tab", Global.TAB_PRODUCT);
                }
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, AddOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadProductData() {

        SharedPreferences pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);
        int number = pref.getInt(String.format("proNum"), 0);

        for (int i = 0; i < number; i++) {
            codeArray.add(pref.getString(String.format("code_%d", i), ""));
            nameArray.add(pref.getString(String.format("name_%d", i), ""));
            priceArray.add(pref.getString(String.format("price_%d", i), ""));
            cbmArray.add(pref.getString(String.format("cbm_%d", i), ""));
        }
    }

    private void showAlert(final Context context, String msg) {

        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(context);
        confirmAlert
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        confirmAlert.show();
    }
}
