package com.example.mick.dockandroidlogin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.R;

/**
 * Created by Mick on 2/15/2018.
 */

public class AdapterBycatchInfo extends BaseAdapter {


    Activity context;
    String species[];
    String ekor[];
    String total_kg[];
    String estimasi[];


    public AdapterBycatchInfo(Activity context, String[] species, String[] ekor , String[] total_kg , String[] estimasi  ) {
        super();
        this.context = context;
        this.species = species ;
        this.ekor = ekor ;
        this.total_kg = total_kg;
        this.estimasi = estimasi;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return species.length;
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
        ImageView template_image;
        TextView txtViewSpecies;
        TextView txtViewEkor;
        TextView txtViewTotalKg;
        TextView txtViewEstimasi;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdapterBycatchInfo.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_bycatch_info, null);
            holder = new AdapterBycatchInfo.ViewHolder();
            holder.template_image = (ImageView) convertView.findViewById(R.id.template_image);
            holder.txtViewSpecies = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewEkor = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewTotalKg = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewEstimasi = (TextView) convertView.findViewById(R.id.textView4);
            convertView.setTag(holder);
        }
        else
        {
            holder = (AdapterBycatchInfo.ViewHolder) convertView.getTag();
        }

        holder.template_image.setImageResource(R.drawable.bycatch);
        holder.txtViewSpecies.setText(species[position]);
        holder.txtViewEkor.setText(ekor[position]);
        holder.txtViewTotalKg.setText(total_kg[position]);
        holder.txtViewEstimasi.setText(estimasi[position]);

        return convertView;
    }



}
