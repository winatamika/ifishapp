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
 * Created by Mick on 2/14/2018.
 */

public class AdapterBaitInfo extends BaseAdapter  {

    Activity context;
    String kategori[];
    String species[];
    String total_kg[];
    String grid_a[];
    String grid_b[];

    public AdapterBaitInfo(Activity context, String[] kategori, String[] species , String[] total_kg , String[] grid_a , String[] grid_b ) {
        super();
        this.context = context;
        this.kategori = kategori ;
        this.species = species ;
        this.total_kg = total_kg;
        this.grid_a = grid_a;
        this.grid_b = grid_b;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return kategori.length;
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
        TextView txtViewKategori;
        TextView txtViewSpecies;
        TextView txtViewTotalKg;
        TextView txtViewGridA;
        TextView txtViewGridB;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdapterBaitInfo.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_bait_info, null);
            holder = new AdapterBaitInfo.ViewHolder();
            holder.template_image = (ImageView) convertView.findViewById(R.id.template_image);
            holder.txtViewKategori = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewSpecies = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewTotalKg = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewGridA = (TextView) convertView.findViewById(R.id.textView4);
            holder.txtViewGridB = (TextView) convertView.findViewById(R.id.textView5);
            convertView.setTag(holder);
        }
        else
        {
            holder = (AdapterBaitInfo.ViewHolder) convertView.getTag();
        }

        if(kategori[position].equals("A")  ) {
            holder.template_image.setImageResource(R.drawable.umpan_a);
        }else if(kategori[position].equals("B")) {
            holder.template_image.setImageResource(R.drawable.umpan_b);
        }
        else if(kategori[position].equals("C")) {
            holder.template_image.setImageResource(R.drawable.umpan_c);
        }
        else if(kategori[position].equals("D")) {
            holder.template_image.setImageResource(R.drawable.umpan_d);
        }
        else if(kategori[position].equals("E")) {
            holder.template_image.setImageResource(R.drawable.umpan_e);
        }
        else if(kategori[position].equals("F")) {
            holder.template_image.setImageResource(R.drawable.umpan_f);
        }
        else if(kategori[position].equals("G")) {
            holder.template_image.setImageResource(R.drawable.umpan_g);
        }
        else if(kategori[position].equals("T")) {
            holder.template_image.setImageResource(R.drawable.umpan_t);
        }
        else if(kategori[position].equals("U")) {
            holder.template_image.setImageResource(R.drawable.umpan_u);
        }
        else if(kategori[position].equals("V")) {
            holder.template_image.setImageResource(R.drawable.umpan_v);
        }
        else if(kategori[position].equals("W")) {
            holder.template_image.setImageResource(R.drawable.umpan_w);
        }
        else if(kategori[position].equals("X")) {
            holder.template_image.setImageResource(R.drawable.umpan_x);
        }
        else if(kategori[position].equals("Y")) {
            holder.template_image.setImageResource(R.drawable.umpan_y);
        }
        else  {
            holder.template_image.setImageResource(R.drawable.umpan_z);
        }
        holder.txtViewKategori.setText(kategori[position]);
        holder.txtViewSpecies.setText(species[position]);
        holder.txtViewTotalKg.setText(total_kg[position]);
        holder.txtViewGridA.setText(grid_a[position]);
        holder.txtViewGridB.setText(grid_b[position]);

        return convertView;
    }



}
