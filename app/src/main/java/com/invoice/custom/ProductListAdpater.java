package com.invoice.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.invoice.R;

import java.util.ArrayList;

public class ProductListAdpater extends BaseAdapter{

    private Context mContext;
    private ArrayList<String> codeArray = new ArrayList<String>();
    private ArrayList<String> nameArray = new ArrayList<String>();
    private ArrayList<String> priceArray = new ArrayList<String>();
    private ArrayList<String> cbmArray = new ArrayList<String>();

    static class ViewHolder {
        TextView productCode;
        TextView productName;
        TextView productPrice;
        TextView productCBM;
    }

    public ProductListAdpater(Context context, ArrayList<String> code, ArrayList<String> name,
                              ArrayList<String> price, ArrayList<String> cbm) {

        super();
        mContext    = context;
        codeArray   = code;
        nameArray   = name;
        priceArray  = price;
        cbmArray    = cbm;
    }

    public int getCount() {
        return nameArray.size();
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_list_item, null);

            holder = new ViewHolder();
            holder.productCode = (TextView)view.findViewById(R.id.tv_code);
            holder.productName = (TextView)view.findViewById(R.id.tv_name);
            holder.productPrice = (TextView)view.findViewById(R.id.tv_price);
            holder.productCBM = (TextView)view.findViewById(R.id.tv_cbm);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.productCode.setText(codeArray.get(position));
        holder.productName.setText(nameArray.get(position));
        holder.productPrice.setText(priceArray.get(position));
        holder.productCBM.setText(cbmArray.get(position));

        return view;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<String> code, ArrayList<String> name,
                           ArrayList<String> price, ArrayList<String> cbm) {
        codeArray   = code;
        nameArray   = name;
        priceArray  = price;
        cbmArray    = cbm;
    }
}



