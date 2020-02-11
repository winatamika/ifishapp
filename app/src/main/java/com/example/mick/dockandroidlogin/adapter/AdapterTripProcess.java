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
 * Created by Mick on 2/6/2018.
 */

public class AdapterTripProcess  extends BaseAdapter {
    Activity context;
    String landing[];
    String supplier[];
    String tipeTemplate[];
    String waktu[];
    String jam[];
    String namaKapal[];
    String totalKalkulasi[] ;

    public AdapterTripProcess(Activity context, String[] landing, String[] supplier , String[] tipeTemplate , String[] waktu , String jam[] , String[] namaKapal , String[] totalKalkulasi ) {
        super();
        this.context = context;
        this.landing = landing;
        this.supplier = supplier;
        this.tipeTemplate = tipeTemplate;
        this.waktu = waktu;
        this.jam = jam ;
        this.namaKapal = namaKapal ;
        this.totalKalkulasi = totalKalkulasi ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return landing.length;
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
        TextView txtViewlanding;
        TextView txtViewsupplier;
        TextView txtViewtipeTemplate;
        TextView txtViewwaktu;
        TextView txtViewjam;
        TextView txtViewnamaKapal;
        TextView txtViewtotalKalkulasi ;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_trip_process, null);
            holder = new ViewHolder();
            holder.template_image = (ImageView) convertView.findViewById(R.id.template_image);
            holder.txtViewlanding = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewsupplier = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewtipeTemplate = (TextView) convertView.findViewById(R.id.textView3);
            holder.txtViewwaktu = (TextView) convertView.findViewById(R.id.textView4);
            holder.txtViewjam = (TextView) convertView.findViewById(R.id.textView5);
            holder.txtViewnamaKapal = (TextView) convertView.findViewById(R.id.textView6);
            holder.txtViewtotalKalkulasi = (TextView) convertView.findViewById(R.id.textView7);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(tipeTemplate[position].equals("HL")  ) {
            holder.template_image.setImageResource(R.drawable.handline);
        }else if(tipeTemplate[position].equals("PL")) {
            holder.template_image.setImageResource(R.drawable.polenline);
        }
        holder.txtViewlanding.setText(landing[position]);
        holder.txtViewsupplier.setText(supplier[position]);
        holder.txtViewtipeTemplate.setText(tipeTemplate[position]);
        holder.txtViewwaktu.setText(waktu[position]);
        holder.txtViewjam.setText(jam[position]);
        holder.txtViewnamaKapal.setText(namaKapal[position]);
        holder.txtViewtotalKalkulasi.setText(totalKalkulasi[position]);


        return convertView;
    }
}
