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
 * Created by Mick on 2/18/2018.
 */

public class AdapterEtpInfo extends BaseAdapter {


    Activity context;
    String hewan[];
    String interaksi[];
    String interaksi_perkiraan[];
    String interaksi_count[];
    String didaratkan_perkiraaan[];
    String didaratkan_count[];

    public AdapterEtpInfo(Activity context, String[] hewan, String[] interaksi , String[] interaksi_perkiraan , String[] interaksi_count , String[] didaratkan_perkiraaan  , String[] didaratkan_count ) {
        super();
        this.context = context;
        this.hewan = hewan ;
        this.interaksi = interaksi ;
        this.interaksi_perkiraan = interaksi_perkiraan;
        this.interaksi_count = interaksi_count;
        this.didaratkan_perkiraaan = didaratkan_perkiraaan;
        this.didaratkan_count = didaratkan_count ;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return hewan.length;
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
        TextView txtViewhewan;
        TextView txtViewInteraksi;
        TextView txtViewInteraksiPerkiraan;
        TextView txtViewInteraksiCount;
        TextView txtViewDidaratkanPerkiraan;
        TextView txtViewDidaratkanCount;
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdapterEtpInfo.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_etp_info, null);
            holder = new AdapterEtpInfo.ViewHolder();
            holder.template_image = (ImageView) convertView.findViewById(R.id.template_image);
            holder.txtViewhewan = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewInteraksi = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewInteraksiPerkiraan = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewInteraksiCount = (TextView) convertView.findViewById(R.id.textView4);
            holder.txtViewDidaratkanPerkiraan = (TextView) convertView.findViewById(R.id.textView5);
            holder.txtViewDidaratkanCount = (TextView) convertView.findViewById(R.id.textView6);
            convertView.setTag(holder);
        }
        else
        {
            holder = (AdapterEtpInfo.ViewHolder) convertView.getTag();
        }

        if(hewan[position].equals("Hiu")){
            holder.template_image.setImageResource(R.drawable.etp_hiu);
        }else if(hewan[position].equals("Pari")){
            holder.template_image.setImageResource(R.drawable.etp_pari);
        }
        else if(hewan[position].equals("Lumba-lumba")){
            holder.template_image.setImageResource(R.drawable.etp_lumba);
        }else if(hewan[position].equals("Paus")){
            holder.template_image.setImageResource(R.drawable.etp_paus);
        }else if(hewan[position].equals("Penyu")){
            holder.template_image.setImageResource(R.drawable.etp_penyu);
        }else if(hewan[position].equals("Burung")){
            holder.template_image.setImageResource(R.drawable.etp_burung);
        }

        holder.txtViewhewan.setText(hewan[position]);
        holder.txtViewInteraksi.setText(interaksi[position]);
        holder.txtViewInteraksiPerkiraan.setText(interaksi_perkiraan[position]);
        holder.txtViewInteraksiCount.setText(interaksi_count[position]);
        holder.txtViewDidaratkanPerkiraan.setText(didaratkan_perkiraaan[position]);
        holder.txtViewDidaratkanCount.setText(didaratkan_count[position]);

        return convertView;
    }






}
