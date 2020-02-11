package com.example.mick.dockandroidlogin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.R;

/**
 * Created by Mick on 2/16/2018.
 */

public class AdapterIkanKecil extends BaseAdapter {

    Activity context;
    String container_no[];
    String berat_keranjang[];
    String species[];
    String panjang[];
    String nomorIkan[] ;

    public AdapterIkanKecil(Activity context, String[] container_no ,  String[] berat_keranjang, String[] species , String[] panjang , String[] nomorIkan ) {
        super();
        this.context = context;
        this.container_no = container_no;
        this.berat_keranjang = berat_keranjang ;
        this.species = species ;
        this.panjang = panjang;
        this.nomorIkan = nomorIkan;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return container_no.length;
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
        TextView txtViewContainer;
        TextView txtViewBerat;
        TextView txtViewSpecies;
        TextView txtViewPanjang;
        TextView txtViewNomorIkan ;
    }



    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdapterIkanKecil.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_ikan_kecil, null);
            holder = new AdapterIkanKecil.ViewHolder();
            holder.txtViewContainer = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewBerat = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewSpecies = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewPanjang = (TextView) convertView.findViewById(R.id.textView4);
            holder.txtViewNomorIkan = (TextView) convertView.findViewById(R.id.textView0);

            convertView.setTag(holder);
        }
        else
        {
            holder = (AdapterIkanKecil.ViewHolder) convertView.getTag();
        }

        holder.txtViewContainer.setText(container_no[position]);
        holder.txtViewBerat.setText(berat_keranjang[position]);
        holder.txtViewSpecies.setText(species[position]);
        holder.txtViewPanjang.setText(panjang[position]);
        holder.txtViewNomorIkan.setText(nomorIkan[position]);

        return convertView;
    }

}
