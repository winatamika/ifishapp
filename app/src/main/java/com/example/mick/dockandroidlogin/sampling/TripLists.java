package com.example.mick.dockandroidlogin.sampling;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.AppController;
import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.AdapterTripProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mick on 2/6/2018.
 */

public class TripLists extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    public static final String TAG = AppController.class.getSimpleName();
    String[] daftar , idTrip , landing , supplier , tipeTemplate , waktu  , jam , namaKapal , totalKalkulasi ;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor , cursor1 ;
    DatabaseHandler dbcenter;
    public static TripLists TP;
    TextView notFound , IsConnected ;
    AlertDialog.Builder dialog;
    ListView lview;
    AdapterTripProcess lviewAdapter;
    String maxId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_trip_lists);

        notFound = (TextView) findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);
        IsConnected = (TextView) findViewById(R.id.IsConnected);
        if(isConnected()){
            IsConnected.setBackgroundColor(0xFF00CC00);
            IsConnected.setText("You are connected");
        }
        else{
            IsConnected.setText("You are NOT connected");
        }


        TP = this;
        dbcenter = new DatabaseHandler(this);
        RefreshList();



        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


    }


    public void RefreshList(){

        SQLiteDatabase db = dbcenter.getReadableDatabase();

        cursor = db.rawQuery("SELECT max(id) as id from tb_trip_lists where status = 'process' ", null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                maxId = cursor.getString(cursor.getColumnIndex("id"));
                System.out.println(maxId);
            }
        }

        cursor = db.rawQuery("SELECT  id , t.n_tpi , p.n_perusahaan , waktu , jam , template_tipe , create_time  " +
                "FROM tb_trip_lists l , tb_master_tpi t , tb_master_perusahaan p " +
                "where l.k_tpi = t.k_tpi and l.k_perusahaan = p.k_perusahaan and l.status = 'process' and id = '" + maxId +"' order by id desc",null);
        daftar = new String[cursor.getCount()];
        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        idTrip = new String[cursor.getCount()];
        landing = new String[cursor.getCount()];
        supplier = new String[cursor.getCount()];
        tipeTemplate = new String[cursor.getCount()];
        waktu = new String[cursor.getCount()];
        jam = new String[cursor.getCount()];
        namaKapal = new String[cursor.getCount()];
        totalKalkulasi = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                //daftar[cc] = cursor.getString(0).toString();

                idTrip[cc] = cursor.getString(cursor.getColumnIndex("id"));
                landing[cc] = cursor.getString(cursor.getColumnIndex("n_tpi"));
                supplier[cc] = cursor.getString(cursor.getColumnIndex("n_perusahaan"));
                tipeTemplate[cc] = cursor.getString(cursor.getColumnIndex("template_tipe"));
                waktu[cc] = cursor.getString(cursor.getColumnIndex("waktu"));
                jam[cc] = cursor.getString(cursor.getColumnIndex("jam"));
                namaKapal[cc] = getNamaKapal(cursor.getString(cursor.getColumnIndex("id"))) ;
                totalKalkulasi[cc] = Integer.toString ( getKalkulasiTotalTangkapan(cursor.getString(cursor.getColumnIndex("id"))) );




                list.add(cursor.getString(cursor.getColumnIndex("id")));
                list.add(cursor.getString(cursor.getColumnIndex("n_tpi")));
                list.add(cursor.getString(cursor.getColumnIndex("n_perusahaan")));
                list.add(cursor.getString(cursor.getColumnIndex("waktu")));
                list.add(cursor.getString(cursor.getColumnIndex("jam")));
                list.add(cursor.getString(cursor.getColumnIndex("template_tipe")));
                list.add(cursor.getString(cursor.getColumnIndex("create_time")));


            }
        }else{

            notFound.setVisibility(View.VISIBLE);

        }

        lview = (ListView) findViewById(R.id.listView2);
        lviewAdapter = new AdapterTripProcess(this, landing, supplier, tipeTemplate ,waktu , jam , namaKapal , totalKalkulasi);
        System.out.println("adapter => "+lviewAdapter.getCount());
        lview.setAdapter(lviewAdapter);
        lview.setOnItemClickListener(this);


        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {"Update"};
                dialog = new AlertDialog.Builder(TripLists.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                Intent intent = new Intent(TripLists.this, SheetSelection.class);
                                intent.putExtra("idTrip", idTrip[position]);
                                intent.putExtra("n_tpi", landing[position]);
                                intent.putExtra("n_perusahaan", supplier[position]);
                                intent.putExtra("tipeTemplate", tipeTemplate[position]);
                                intent.putExtra("waktu", waktu[position]);
                                startActivity(intent);

                                break;

                        }
                    }
                }).show();
                return true;
            }

        });

        db.close();

    }

    private String getNamaKapal(String idTrip){
        String nama = "";

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nama_kapal " +
                "FROM tb_trip_info " +
                "where idTrip = '"+ idTrip +"' ",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                nama = cursor.getString(cursor.getColumnIndex("nama_kapal"));

            }
        }

        db.close();


        return nama;
    }

    private Integer getKalkulasiTotalTangkapan(String idTrip){
        Integer Total = 0;
        Integer totalBycatch = 0 ; Integer totalRingkasanKecil = 0 ;  Integer totalIkanBesar = 0 ;
        SQLiteDatabase db = dbcenter.getReadableDatabase();


        //total bycatch
        Cursor cursor1  = db.rawQuery(" SELECT SUM(total_kg) as total from tb_bycatch where id_trip = '"+ idTrip +"' ",null);
        if(cursor1.getCount() > 0) {
            for (int cc = 0; cc < cursor1.getCount(); cc++) {
                cursor1.moveToPosition(cc);
                totalBycatch = totalBycatch + cursor1.getInt(cursor1.getColumnIndex("total"));
            }
        }
        //total ringkasan ikan kecil
        Cursor cursor2  = db.rawQuery(" SELECT SUM(total_kg) as total from tb_ringkasan_kecil where id_trip = '"+ idTrip +"' ",null);
        if(cursor2.getCount() > 0) {
            for (int cc = 0; cc < cursor2.getCount(); cc++) {
                cursor2.moveToPosition(cc);
                totalRingkasanKecil = totalRingkasanKecil + cursor2.getInt(cursor2.getColumnIndex("total"));
            }
        }


        //total ikan besar
        Cursor cursor3  = db.rawQuery(" SELECT SUM(berat) as total from tb_ikan_besar where id_trip = '"+ idTrip +"' ",null);
        if(cursor3.getCount() > 0) {
            for (int cc = 0; cc < cursor3.getCount(); cc++) {
                cursor3.moveToPosition(cc);
                totalIkanBesar = totalIkanBesar + cursor3.getInt(cursor3.getColumnIndex("total"));
            }
        }

        Total = totalBycatch + totalRingkasanKecil + totalIkanBesar ;

        db.close();
        return Total;

    }


    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }



    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        Toast.makeText(this,"Title => "+landing[position]+"=> n Description"+supplier[position]+tipeTemplate[position], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TripLists.this, SheetSelection.class);
        intent.putExtra("idTrip", idTrip[position]);
        intent.putExtra("n_tpi", landing[position]);
        intent.putExtra("n_perusahaan", supplier[position]);
        intent.putExtra("tipeTemplate", tipeTemplate[position]);
        intent.putExtra("waktu", waktu[position]);
        startActivity(intent);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }









}
