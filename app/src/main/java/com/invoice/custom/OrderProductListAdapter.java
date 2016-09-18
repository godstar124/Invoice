package com.invoice.custom;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.activity.AddOrderActivity;
import com.invoice.activity.CustomerActivity;
import com.invoice.activity.OrderDetailActivity;
import com.invoice.activity.ProductDetailActivity;
import com.invoice.global.Global;

public class OrderProductListAdapter extends BaseAdapter {

    private Context mContext;
    private String[] codeArray;
    private String[] nameArray;
    private String[] priceArray;
    private String[] cbmArray;
    private String[] qtyArray;

    static class ViewHolder {
        TextView productCode;
        TextView productName;
        TextView productPrice;
        TextView productCBM;
        TextView productQTY;
        ImageButton upQTY;
        ImageButton downQTY;
        LinearLayout contentLayout;
    }

    public OrderProductListAdapter(Context context, String[] code, String[] name, String[] price, String[] cbm, String[] qty) {

        super();
        mContext    = context;
        codeArray   = code;
        nameArray   = name;
        priceArray  = price;
        cbmArray    = cbm;
        qtyArray    = qty;
    }

    public int getCount() {
        return nameArray.length;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_product_item, null);

            holder = new ViewHolder();
            holder.productCode = (TextView)view.findViewById(R.id.tv_code);
            holder.productName = (TextView)view.findViewById(R.id.tv_name);
            holder.productPrice = (TextView)view.findViewById(R.id.tv_price);
            holder.productCBM = (TextView)view.findViewById(R.id.tv_cbm);
            holder.productQTY = (TextView)view.findViewById(R.id.tv_qyt);
            holder.upQTY = (ImageButton)view.findViewById(R.id.btn_up);
            holder.downQTY = (ImageButton)view.findViewById(R.id.btn_down);
            holder.contentLayout = (LinearLayout)view.findViewById(R.id.contentLayout);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.productCode.setText(codeArray[position]);
        holder.productName.setText(nameArray[position]);
        holder.productPrice.setText(priceArray[position]);
        holder.productCBM.setText(cbmArray[position]);
        holder.productQTY.setText(qtyArray[position]);

        holder.upQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQTY = Integer.parseInt(holder.productQTY.getText().toString());
                Global.qtyList.add(position, currentQTY+1);
                holder.productQTY.setText(String.format("%d", currentQTY + 1));
            }
        });

        holder.downQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQTY = Integer.parseInt(holder.productQTY.getText().toString());
                if (currentQTY > 1) {
                    holder.productQTY.setText(String.format("%d", currentQTY-1));
                    Global.qtyList.add(position, currentQTY-1);
                }
            }
        });

//        holder.contentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ProductDetailActivity.class);
//                intent.putExtra("position", position);
//                mContext.startActivity(intent);
//                ((AddOrderActivity)mContext).finish();
//            }
//        });
        return view;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
}
