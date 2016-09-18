package com.invoice.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.invoice.R;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> orderArray = new ArrayList<String>();
    private ArrayList<String> contactArray = new ArrayList<String>();
    private ArrayList<String> repArray = new ArrayList<String>();
    private ArrayList<String> totalArray = new ArrayList<String>();

    static class ViewHolder {
        TextView order;
        TextView contact;
        TextView rep;
        TextView total;
    }

    public OrderListAdapter(Context context, ArrayList<String> order, ArrayList<String> contact,
                            ArrayList<String> rep, ArrayList<String> total) {

        super();
        mContext    = context;
        orderArray  = order;
        contactArray= contact;
        repArray    = rep;
        totalArray  = total;
    }

    public int getCount() {
        return orderArray.size();
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_list_item, null);

            holder = new ViewHolder();
            holder.order = (TextView)view.findViewById(R.id.tv_order);
            holder.contact = (TextView)view.findViewById(R.id.tv_contact);
            holder.rep = (TextView)view.findViewById(R.id.tv_rep);
            holder.total = (TextView)view.findViewById(R.id.tv_total);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.order.setText(orderArray.get(position));
        holder.contact.setText(contactArray.get(position));
        holder.rep.setText(repArray.get(position));
        holder.total.setText(totalArray.get(position));

        return view;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<String> order, ArrayList<String> contact,
                           ArrayList<String> rep, ArrayList<String> total) {
        orderArray  = order;
        contactArray= contact;
        repArray    = rep;
        totalArray  = total;
    }
}



