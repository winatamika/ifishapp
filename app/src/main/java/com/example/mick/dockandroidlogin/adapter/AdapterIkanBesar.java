package com.example.mick.dockandroidlogin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.R;

/**
 * Created by Mick on 2/17/2018.
 */

public class AdapterIkanBesar extends BaseAdapter {

    Activity context;
    String species[];
    String kode[];
    String berat[];
    String panjang[];
    String nomorIkan[];


    public AdapterIkanBesar(Activity context, String[] species, String[] kode , String[] berat , String[] panjang , String[] nomorIkan) {
        super();
        this.context = context;
        this.species = species ;
        this.kode = kode ;
        this.berat = berat;
        this.panjang = panjang;
        this.nomorIkan = nomorIkan ;

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
        TextView txtViewSpecies;
        TextView txtViewKode;
        TextView txtViewBerat;
        TextView txtViewPanjang;
        TextView txtViewNomorIkan;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdapterIkanBesar.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_ikan_besar, null);
            holder = new AdapterIkanBesar.ViewHolder();

            holder.txtViewKode = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewSpecies = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewBerat = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewPanjang = (TextView) convertView.findViewById(R.id.textView4);
            holder.txtViewNomorIkan = (TextView) convertView.findViewById(R.id.textView0);

            convertView.setTag(holder);
        }
        else
        {
            holder = (AdapterIkanBesar.ViewHolder) convertView.getTag();
        }

        holder.txtViewKode.setText(kode[position]);
        holder.txtViewSpecies.setText(species[position]);
        holder.txtViewBerat.setText(berat[position]);
        holder.txtViewPanjang.setText(panjang[position]);
        holder.txtViewNomorIkan.setText(nomorIkan[position]);

        return convertView;
    }



}
