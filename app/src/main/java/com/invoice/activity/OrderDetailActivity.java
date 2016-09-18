package com.invoice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.custom.OrderDetailsProductAdapter;
import com.invoice.global.Global;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    int position;
    public EditText et_order;
    public TextView tv_date;
    public TextView tv_rep;
    public TextView tv_customer;
    public EditText et_contact;
    public EditText et_billingAddre;
    public EditText et_billingAddre1;
    public EditText et_billingEmail;
    public EditText et_billingPhone;
    public EditText et_shippingAddre;
    public EditText et_shippingAddre1;
    public EditText et_shippingEmail;
    public EditText et_shippingPhone;

    public TextView tv_gst;
    public TextView tv_cbm;
    public TextView tv_subtotal;
    public ArrayList<String> codeArray;
    public ArrayList<String> nameArray;
    public ArrayList<String> priceArray;
    public ArrayList<String> cbmArray;
    public ArrayList<String> qtyArray;

    private ListView listView;
    private OrderDetailsProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_order_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("position", 0);
        }

        codeArray = new ArrayList<String>();
        nameArray = new ArrayList<String>();
        priceArray = new ArrayList<String>();
        cbmArray = new ArrayList<String>();
        qtyArray = new ArrayList<String>();

        init();
        loadOrderdetails();
    }

    private void init() {

        et_order = (EditText)findViewById(R.id.et_order);
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_rep = (TextView)findViewById(R.id.tv_rep);
        tv_customer = (TextView)findViewById(R.id.tv_customer);
        et_contact = (EditText)findViewById(R.id.et_contact);
        et_billingAddre = (EditText)findViewById(R.id.et_billing_address);
        et_billingAddre1 = (EditText)findViewById(R.id.et_billing_address1);
        et_billingEmail = (EditText)findViewById(R.id.et_billing_email);
        et_billingPhone = (EditText)findViewById(R.id.et_billing_phone);
        et_shippingAddre = (EditText)findViewById(R.id.et_shipping_address);
        et_shippingAddre1 = (EditText)findViewById(R.id.et_shipping_address1);
        et_shippingEmail = (EditText)findViewById(R.id.et_shipping_email);
        et_shippingPhone = (EditText)findViewById(R.id.et_shipping_phone);
        tv_gst = (TextView)findViewById(R.id.tv_gst);
        tv_cbm = (TextView)findViewById(R.id.tv_cbm);
        tv_subtotal = (TextView)findViewById(R.id.tv_subtotal);
        listView = (ListView)findViewById(R.id.listView);
        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrderDetails();
                Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_ORDERS);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // reset
                Global.qtyList.clear();

                Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_ORDERS);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadOrderdetails() {

        SharedPreferences pref = getSharedPreferences(Global.ORDER_DB, MODE_PRIVATE);

        et_order.setText(pref.getString(String.format("order_%d", position), ""));
        tv_date.setText(pref.getString(String.format("date_%d", position), ""));
        tv_rep.setText(pref.getString(String.format("rep_%d", position), ""));
        tv_customer.setText(pref.getString(String.format("name_%d", position), ""));
        et_contact.setText(pref.getString(String.format("contact_%d", position), ""));

        et_billingAddre.setText(pref.getString(String.format("bAddres_%d", position), ""));
        et_billingAddre1.setText(pref.getString(String.format("bAddres1_%d", position), ""));
        et_billingPhone.setText(pref.getString(String.format("bPhone_%d", position), ""));
        et_billingEmail.setText(pref.getString(String.format("bEmail_%d", position), ""));
        et_shippingAddre.setText(pref.getString(String.format("sAddres_%d", position), ""));
        et_shippingAddre1.setText(pref.getString(String.format("sAddres1_%d", position), ""));
        et_shippingPhone.setText(pref.getString(String.format("sPhone_%d", position), ""));
        et_shippingEmail.setText(pref.getString(String.format("sEmail_%d", position), ""));

        tv_gst.setText(pref.getString(String.format("gst_%d", position), ""));
        tv_cbm.setText(pref.getString(String.format("totalcbm_%d", position), ""));
        tv_subtotal.setText(pref.getString(String.format("subtotal_%d", position), ""));

        int productNum = pref.getInt(String.format("product_Num_%d", position), 0);

        for (int i = 0; i < productNum; i++) {
            codeArray.add(pref.getString(String.format("code_%d_%d", position, i), ""));
            nameArray.add(pref.getString(String.format("pro_name_%d_%d", position, i), ""));
            qtyArray.add(pref.getString(String.format("qyt_%d_%d", position, i), ""));
            priceArray.add(pref.getString(String.format("price_%d_%d", position, i), ""));
            cbmArray.add(pref.getString(String.format("cbm_%d_%d", position, i), ""));

            Global.qtyList.add(Integer.parseInt(pref.getString(String.format("qyt_%d_%d", position, i), "")));
        }

        adapter = new OrderDetailsProductAdapter(this, codeArray, nameArray, qtyArray, priceArray, cbmArray, false);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, GridLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void saveOrderDetails() {

        SharedPreferences pref = getSharedPreferences(Global.ORDER_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(String.format("order_%d", position), et_order.getText().toString());
        editor.putString(String.format("date_%d", position), tv_date.getText().toString());
        editor.putString(String.format("rep_%d", position), tv_rep.getText().toString());

        editor.putString(String.format("name_%d", position), tv_customer.getText().toString());
        editor.putString(String.format("contact_%d", position), et_contact.getText().toString());
        editor.putString(String.format("bAddres_%d", position), et_billingAddre.getText().toString());
        editor.putString(String.format("bAddres1_%d", position), et_billingAddre1.getText().toString());
        editor.putString(String.format("bPhone_%d", position), et_billingPhone.getText().toString());
        editor.putString(String.format("bEmail_%d", position), et_billingEmail.getText().toString());
        editor.putString(String.format("sAddres_%d", position), et_shippingAddre.getText().toString());
        editor.putString(String.format("sAddres1_%d", position), et_shippingAddre1.getText().toString());
        editor.putString(String.format("sPhone_%d", position), et_shippingPhone.getText().toString());
        editor.putString(String.format("sEmail_%d", position), et_shippingEmail.getText().toString());

        editor.putInt(String.format("gst_%d", position), Integer.parseInt(tv_gst.getText().toString()));
        editor.putInt(String.format("totalcbm_%d", position), Integer.parseInt(tv_cbm.getText().toString()));
        editor.putInt(String.format("subtotal_%d", position), Integer.parseInt(tv_subtotal.getText().toString()));


        int productNum = Global.qtyList.size();
        editor.putInt(String.format("product_Num_%d", position), productNum);
        long totalPrice = 0;
        long totalCBM = 0;
        long totalTax = 0;
        for (int i = 0; i < productNum; i++) {
            editor.putString(String.format("code_%d_%d", position, i), codeArray.get(i));
            editor.putString(String.format("pro_name_%d_%d", position, i), nameArray.get(i));
            editor.putString(String.format("price_%d_%d", position, i), priceArray.get(i));
            editor.putString(String.format("cbm_%d_%d", position, i), cbmArray.get(i));
            editor.putString(String.format("qyt_%d_%d", position, i), qtyArray.get(i));

            totalPrice += Long.parseLong(priceArray.get(i)) * Integer.parseInt(qtyArray.get(i));
            totalCBM += Long.parseLong(cbmArray.get(i)) * Integer.parseInt(qtyArray.get(i));
            totalTax += totalPrice * Global.tax / 100;
        }

        editor.putString(String.format("gst_%d", position), Long.toString(totalPrice + totalTax));
        editor.putString(String.format("subtotal_%d", position), Long.toString(totalPrice));
        editor.putString(String.format("totalcbm_%d", position), Long.toString(totalCBM));

        editor.putInt("orderNum", position + 1);
        editor.commit();

        // reset
        Global.qtyList.clear();
    }

    private void updateQTY() {

        long totalPrice = 0;
        long totalCBM = 0;
        long totalTax = 0;

        qtyArray.clear();
        for (int i = 0; i < Global.qtyList.size(); i ++) {

            qtyArray.add(String.format("%d", Global.qtyList.get(i)));
            totalPrice += Long.parseLong(priceArray.get(i)) * Global.qtyList.get(i);
            totalCBM += Long.parseLong(cbmArray.get(i)) * Global.qtyList.get(i);
            totalTax += totalPrice * Global.tax / 100;
        }

        tv_gst.setText(Long.toString(totalPrice + totalTax));
        tv_cbm.setText(Long.toString(totalCBM));
        tv_subtotal.setText(Long.toString(totalPrice));
    }

    public void reloadData() {

        updateQTY();
        adapter.updateData(codeArray, nameArray, qtyArray, priceArray, cbmArray);
        listView.setAdapter(adapter);

    }

    public void delete(int position) {

        Global.qtyList.remove(position);

        codeArray.remove(position);
        nameArray.remove(position);
        qtyArray.remove(position);
        cbmArray.remove(position);

        reloadData();
    }
}
