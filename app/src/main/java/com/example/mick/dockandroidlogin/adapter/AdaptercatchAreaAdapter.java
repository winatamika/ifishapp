package com.example.mick.dockandroidlogin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.R;

/**
 * Created by Mick on 2/14/2018.
 */

public class AdaptercatchAreaAdapter  extends BaseAdapter {

    Activity context;
    String grid_a[];
    String grid_b[];


    public AdaptercatchAreaAdapter(Activity context, String[] grid_a, String[] grid_b ) {
        super();
        this.context = context;
        this.grid_a = grid_a;
        this.grid_b = grid_b;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return grid_a.length;
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
        TextView txtViewgrid_a;
        TextView txtViewgrid_b;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        AdaptercatchAreaAdapter.ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_catch_area, null);
            holder = new AdaptercatchAreaAdapter.ViewHolder();
            holder.txtViewgrid_a = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtViewgrid_b = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);
        }
        else
        {
            holder = (AdaptercatchAreaAdapter.ViewHolder) convertView.getTag();
        }

        holder.txtViewgrid_a.setText(grid_a[position]);
        holder.txtViewgrid_b.setText(grid_b[position]);


        return convertView;
    }


}
