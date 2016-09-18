package com.invoice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.custom.OrderDetailsProductAdapter;
import com.invoice.custom.OrderProductListAdapter;
import com.invoice.global.Global;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.microedition.khronos.egl.EGLDisplay;

public class AddOrderActivity extends AppCompatActivity {

    public String name = "";

    public ArrayList<String> codeArray;
    public ArrayList<String> nameArray;
    public ArrayList<String> priceArray;
    public ArrayList<String> cbmArray;
    public ArrayList<String> qtyArray;

    public TextView tv_order;
    public TextView tv_date;
    public Spinner repSpinner;

    public TextView tv_customer;
    public TextView tv_contact;
    public EditText et_billingAddress;
    public EditText et_billingAddress1;
    public EditText et_billingEmail;
    public EditText et_billingPhone;
    public EditText et_shippingAddress;
    public EditText et_shippingAddress1;
    public EditText et_shippingEmail;
    public EditText et_shippingPhone;

    public TextView tv_gst;
    public TextView tv_cbm;
    public TextView tv_subtotal;

    private ListView listView;
    private OrderDetailsProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_order);

        codeArray = new ArrayList<String>();
        nameArray = new ArrayList<String>();
        priceArray = new ArrayList<String>();
        cbmArray = new ArrayList<String>();
        qtyArray = new ArrayList<String>();

        tv_order = (TextView)findViewById(R.id.tv_order);
        tv_date = (TextView)findViewById(R.id.tv_date);
        repSpinner = (Spinner)findViewById(R.id.repSpinner);

        tv_customer = (TextView)findViewById(R.id.tv_name);
        tv_contact = (TextView)findViewById(R.id.tv_contact);
        et_billingAddress = (EditText)findViewById(R.id.et_billing_address);
        et_billingAddress1 = (EditText)findViewById(R.id.et_billing_address1);
        et_billingEmail = (EditText)findViewById(R.id.et_billing_email);
        et_billingPhone = (EditText)findViewById(R.id.et_billing_phone);
        et_shippingAddress = (EditText)findViewById(R.id.et_shipping_address);
        et_shippingAddress1 = (EditText)findViewById(R.id.et_shipping_address1);
        et_shippingEmail = (EditText)findViewById(R.id.et_shipping_email);
        et_shippingPhone = (EditText)findViewById(R.id.et_shipping_phone);

        tv_gst = (TextView)findViewById(R.id.tv_gst);
        tv_cbm = (TextView)findViewById(R.id.tv_cbm);
        tv_subtotal = (TextView)findViewById(R.id.tv_subtotal);

        loadSavedData();
        init();

    }

    private void init() {

        // order info
        Calendar mCurrentDate = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMMM yyyy");
        String date = df1.format(mCurrentDate.getTime());
        tv_date.setText(date);

        tv_order.setText(String.format("%s/%d", Global.orderPrefix, Global.startOrderNum));
        ArrayAdapter<String> repAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Global.repList);
        repSpinner.setAdapter(repAdapter);

        repSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(Global.repList.size() != 0){
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView = (ListView)findViewById(R.id.listView);
        adapter = new OrderDetailsProductAdapter(this, codeArray, nameArray, qtyArray, priceArray, cbmArray, true);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        // calculate GST, CBM, SubTotal
        updateQTY();

        Button btn_new_customer = (Button)findViewById(R.id.btn_new_customer);
        btn_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddOrderActivity.this, CustomerActivity.class);
                intent.putExtra("tab", Global.TAB_ORDERS);
                startActivity(intent);
                finish();
            }
        });

        Button btn_add_item = (Button)findViewById(R.id.btn_add_product);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddOrderActivity.this, ProductActivity.class);
                intent.putExtra("tab", Global.TAB_ORDERS);
                startActivity(intent);
                finish();
            }
        });

        ImageButton btn_save = (ImageButton)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveOrder();
                Intent intent = new Intent(AddOrderActivity.this, MainActivity.class);
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
                Global.orderList.clear();
                Global.qtyList.clear();
                Global.customerPosition = -1;

                Intent intent = new Intent(AddOrderActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_ORDERS);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadSavedData() {

        //Customer Info
        int num = Global.customerPosition;
        if(num != -1) {

            SharedPreferences pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);

            tv_customer.setText(pref.getString(String.format("name_%d", num), ""));
            tv_contact.setText(pref.getString(String.format("contact_%d", num), ""));
            et_billingAddress.setText(pref.getString(String.format("bAddres_%d", num), ""));
            et_billingAddress1.setText(pref.getString(String.format("bAddres1_%d", num), ""));
            et_billingPhone.setText(pref.getString(String.format("bPhone_%d", num), ""));
            et_billingEmail.setText(pref.getString(String.format("bEmail_%d", num), ""));
            et_shippingAddress.setText(pref.getString(String.format("sAddres_%d", num), ""));
            et_shippingAddress1.setText(pref.getString(String.format("sAddres1_%d", num), ""));
            et_shippingPhone.setText(pref.getString(String.format("sPhone_%d", num), ""));
            et_shippingEmail.setText(pref.getString(String.format("sEmail_%d", num), ""));

        }

        //Product Info
        SharedPreferences pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);
        int number = Global.orderList.size();

        for (int i = 0; i < number; i++) {
            int position = Global.orderList.get(i);
            codeArray.add(pref.getString(String.format("code_%d", position), ""));
            nameArray.add(pref.getString(String.format("name_%d", position), ""));
            priceArray.add(pref.getString(String.format("price_%d", position), ""));
            cbmArray.add(pref.getString(String.format("cbm_%d", position), ""));
            qtyArray.add(String.format("%d", Global.qtyList.get(i)));
        }
    }

    private void saveOrder() {

        SharedPreferences pref = getSharedPreferences(Global.ORDER_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        int num = pref.getInt("orderNum", 0);

        String rep = String.valueOf(repSpinner.getSelectedItem());
        editor.putString(String.format("order_%d", num), tv_order.getText().toString());
        editor.putString(String.format("date_%d", num), tv_date.getText().toString());
        editor.putString(String.format("rep_%d", num), rep);

        editor.putString(String.format("name_%d", num), tv_customer.getText().toString());
        editor.putString(String.format("contact_%d", num), tv_contact.getText().toString());
        editor.putString(String.format("bAddres_%d", num), et_billingAddress.getText().toString());
        editor.putString(String.format("bAddres1_%d", num), et_billingAddress1.getText().toString());
        editor.putString(String.format("bPhone_%d", num), et_billingPhone.getText().toString());
        editor.putString(String.format("bEmail_%d", num), et_billingEmail.getText().toString());
        editor.putString(String.format("sAddres_%d", num), et_shippingAddress.getText().toString());
        editor.putString(String.format("sAddres1_%d", num), et_shippingAddress1.getText().toString());
        editor.putString(String.format("sPhone_%d", num), et_shippingPhone.getText().toString());
        editor.putString(String.format("sEmail_%d", num), et_shippingEmail.getText().toString());

        int productNum = Global.orderList.size();
        editor.putInt(String.format("product_Num_%d", num), productNum);
        long totalPrice = 0;
        long totalCBM = 0;
        long totalTax = 0;
        for (int i = 0; i < productNum; i++) {
            editor.putString(String.format("code_%d_%d", num, i), codeArray.get(i));
            editor.putString(String.format("pro_name_%d_%d", num, i), nameArray.get(i));
            editor.putString(String.format("price_%d_%d", num, i), priceArray.get(i));
            editor.putString(String.format("cbm_%d_%d", num, i), cbmArray.get(i));
            editor.putString(String.format("qyt_%d_%d", num, i), qtyArray.get(i));

            totalPrice += Long.parseLong(priceArray.get(i)) * Integer.parseInt(qtyArray.get(i));
            totalCBM += Long.parseLong(cbmArray.get(i)) * Integer.parseInt(qtyArray.get(i));
            totalTax += totalPrice * Global.tax / 100;
        }

        editor.putString(String.format("gst_%d", num), Long.toString(totalPrice + totalTax));
        editor.putString(String.format("subtotal_%d", num), Long.toString(totalPrice));
        editor.putString(String.format("totalcbm_%d", num), Long.toString(totalCBM));

        editor.putInt("orderNum", num + 1);
        editor.commit();

        // reset
        Global.orderList.clear();
        Global.qtyList.clear();
        Global.customerPosition = -1;
        Global.startOrderNum++;
        pref = getSharedPreferences(Global.SETTING_DB, MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt("startOrder", Global.startOrderNum);
        editor.commit();
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
        Global.orderList.remove(position);

        codeArray.remove(position);
        nameArray.remove(position);
        qtyArray.remove(position);
        cbmArray.remove(position);

        reloadData();
    }
}
