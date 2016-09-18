package com.invoice.custom;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.invoice.R;

import java.util.ArrayList;

public class FilePahAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> nameArray = new ArrayList<String>();

    static class ViewHolder {
        TextView name;
    }

    public FilePahAdapter(Context context, ArrayList<String> name) {

        super();
        mContext    = context;
        nameArray   = name;
    }

    public int getCount() {
        return nameArray.size();
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.filename_itme, null);

            holder = new ViewHolder();
            holder.name = (TextView)view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.name.setText(nameArray.get(position));

        return view;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<String> name) {
        nameArray   = name;
    }

}