package com.invoice.custom;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.activity.AddOrderActivity;
import com.invoice.activity.OrderDetailActivity;
import com.invoice.global.Global;

import java.util.ArrayList;
import java.util.Locale;

public class OrderDetailsProductAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> codeArray;
    private ArrayList<String> nameArray;
    private ArrayList<String> qtyArray;
    private ArrayList<String> priceArray;
    private ArrayList<String> cbmArray;
    private boolean isAdd;

    static class ViewHolder {
        TextView productCode;
        TextView productName;
        ImageButton downQTY;
        TextView productQTY;
        ImageButton upQTY;
        TextView productPrice;
        TextView productTotal;
        TextView productCBM;
        Button delete;
        LinearLayout content;
        LinearLayout content1;
    }

    public OrderDetailsProductAdapter(Context context, ArrayList<String> code, ArrayList<String> name,
                                      ArrayList<String> qty, ArrayList<String> price, ArrayList<String> cbm, boolean isAdd) {

        super();
        mContext    = context;
        codeArray   = code;
        nameArray   = name;
        qtyArray    = qty;
        priceArray  = price;
        cbmArray    = cbm;
        this.isAdd  = isAdd;
    }

    public int getCount() {
        return nameArray.size();
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_details_product_item, null);

            holder = new ViewHolder();
            holder.productCode = (TextView)view.findViewById(R.id.tv_code);
            holder.productName = (TextView)view.findViewById(R.id.tv_name);
            holder.downQTY = (ImageButton)view.findViewById(R.id.btn_down);
            holder.productQTY = (TextView)view.findViewById(R.id.tv_qty);
            holder.upQTY = (ImageButton)view.findViewById(R.id.btn_up);
            holder.productPrice = (TextView)view.findViewById(R.id.tv_price);
            holder.productTotal = (TextView)view.findViewById(R.id.tv_lineTotal);
            holder.productCBM = (TextView)view.findViewById(R.id.tv_cbm);
            holder.delete = (Button)view.findViewById(R.id.btn_delete);
            holder.content = (LinearLayout)view.findViewById(R.id.content);
            holder.content1 = (LinearLayout)view.findViewById(R.id.content1);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.productCode.setText(codeArray.get(position));
        holder.productName.setText(nameArray.get(position));
        holder.productQTY.setText(qtyArray.get(position));
        holder.productPrice.setText(priceArray.get(position));
        Long total = Long.parseLong(priceArray.get(position)) * Integer.parseInt(qtyArray.get(position));
        holder.productTotal.setText(Long.toString(total));
        Long cbm = Long.parseLong(cbmArray.get(position)) * Integer.parseInt(qtyArray.get(position));
        holder.productCBM.setText(Long.toString(cbm));

        holder.upQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQTY = Integer.parseInt(holder.productQTY.getText().toString());
                holder.productQTY.setText(String.format("%d", currentQTY + 1));
                Global.qtyList.set(position, currentQTY + 1);

                if (isAdd) {
                    ((AddOrderActivity)mContext).reloadData();
                } else {
                    ((OrderDetailActivity)mContext).reloadData();
                }

            }
        });

        holder.downQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQTY = Integer.parseInt(holder.productQTY.getText().toString());
                if (currentQTY > 1) {
                    holder.productQTY.setText(String.format("%d", currentQTY - 1));
                    Global.qtyList.set(position, currentQTY - 1);

                    if (isAdd) {
                        ((AddOrderActivity)mContext).reloadData();
                    } else {
                        ((OrderDetailActivity)mContext).reloadData();
                    }
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd) {
                    ((AddOrderActivity)mContext).delete(position);
                } else {
                    ((OrderDetailActivity)mContext).delete(position);
                }
            }
        });
        return view;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<String> code, ArrayList<String> name,
                           ArrayList<String> qty, ArrayList<String> price, ArrayList<String> cbm) {

        codeArray   = code;
        nameArray   = name;
        qtyArray    = qty;
        priceArray  = price;
        cbmArray    = cbm;
    }
}



