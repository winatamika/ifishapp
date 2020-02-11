package com.example.mick.dockandroidlogin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.R;

/**
 * Created by Mick on 2/15/2018.
 */

public class AdapterRingkasan extends BaseAdapter {

    Activity context;
    String kode[];
    String deskripsi[];
    String total_kg[];

    public AdapterRingkasan(Activity context, String[] kode, String[] deskripsi , String[] total_kg ) {
        super();
        this.context = context;
        this.kode = kode ;
        this.deskripsi = deskripsi ;
        this.total_kg = total_kg;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return kode.length;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtViewKode;
        TextView txtViewDeskripsi;
        TextView txtViewTotalKg;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdapterRingkasan.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_ringkasan, null);
            holder = new AdapterRingkasan.ViewHolder();
            holder.txtViewKode = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewDeskripsi = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewTotalKg = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        }
        else
        {
            holder = (AdapterRingkasan.ViewHolder) convertView.getTag();
        }

        holder.txtViewKode.setText(kode[position]);
        holder.txtViewDeskripsi.setText(deskripsi[position]);
        holder.txtViewTotalKg.setText(total_kg[position]);

        return convertView;
    }

}
