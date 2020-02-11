package com.example.mick.dockandroidlogin.sampling;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mick.dockandroidlogin.AppController;
import com.example.mick.dockandroidlogin.Dashboard;
import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.Server;
import com.example.mick.dockandroidlogin.adapter.AdapterTripProcess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

import static com.example.mick.dockandroidlogin.Login.my_shared_preferences;

/**
 * Created by Mick on 2/6/2018.
 */

public class TripProcess extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    public static final String TAG = AppController.class.getSimpleName();
    private String url = Server.URL + "postData";
    String[] daftar , idTrip , kode_tpi ,  landing , supplier , tipeTemplate , waktu, jam , namaKapal, totalKalkulasi ;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DatabaseHandler dbcenter;
    public static TripProcess TP;
    TextView notFound , IsConnected ;
    AlertDialog.Builder dialog;
    AlertDialog dialogProcess ;
    String idUser ;
    SharedPreferences sharedpreferences;
    JSONObject tripList , tripInfo  , baitInfo , bycatchInfo , ringkasanKecilInfo , ringkasanBesarInfo , kecilinfo, besarInfo , etpInfo ;

    ListView lview;
    AdapterTripProcess lviewAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_trip_process);

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

        /*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        */


        //back to Dashboard
        Button buttonBack=(Button)findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(TripProcess.this, Dashboard.class);
                startActivity(intent);
            }
        });

    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        idUser = sharedpreferences.getString("id", null);

        cursor = db.rawQuery("SELECT  id , t.k_tpi ,  t.n_tpi , p.n_perusahaan , waktu , jam , template_tipe , create_time  " +
                "FROM tb_trip_lists l , tb_master_tpi t , tb_master_perusahaan p " +
                "where l.k_tpi = t.k_tpi and l.k_perusahaan = p.k_perusahaan and l.status = 'process' and id_pengguna = '"+  idUser  +"' order by id desc ",null);
        daftar = new String[cursor.getCount()];
        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        idTrip = new String[cursor.getCount()];
        kode_tpi = new String[cursor.getCount()];
        landing = new String[cursor.getCount()];
        supplier = new String[cursor.getCount()];jam = new String[cursor.getCount()];
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
                kode_tpi[cc] = cursor.getString(cursor.getColumnIndex("k_tpi"));
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
        lviewAdapter = new AdapterTripProcess(this, landing, supplier, tipeTemplate ,waktu , jam , namaKapal , totalKalkulasi );
        System.out.println("adapter => "+lviewAdapter.getCount());
        lview.setAdapter(lviewAdapter);
        lview.setOnItemClickListener(this);


        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {"Update", "Delete" , "Upload To IFISH Server"};
                dialog = new AlertDialog.Builder(TripProcess.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                Intent intent = new Intent(TripProcess.this, SheetSelection.class);
                                intent.putExtra("idTrip", idTrip[position]);
                                intent.putExtra("kode_tpi", kode_tpi[position]);
                                intent.putExtra("n_tpi", landing[position]);
                                intent.putExtra("n_perusahaan", supplier[position]);
                                intent.putExtra("tipeTemplate", tipeTemplate[position]);
                                intent.putExtra("waktu", waktu[position]);
                                startActivity(intent);

                                break;
                            case 1:
                                AlertDialog.Builder builder = new AlertDialog.Builder(TripProcess.this);
                                builder.setTitle("Confirm");
                                builder.setMessage("Are you sure?");

                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            System.out.println("Doing the delete function");

                                            SQLiteDatabase db = dbcenter.getWritableDatabase();
                                            db.execSQL(" UPDATE tb_trip_lists set status = 'disable' where id = '" + idTrip[position] +"'");
                                            db.close();
                                            RefreshList();
                                            dialog.dismiss();
                                        }
                                    });

                                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();



                                break;
                            case 2:


                                AlertDialog.Builder builder2 = new AlertDialog.Builder(TripProcess.this);
                                builder2.setTitle("Confirm");
                                builder2.setMessage("Are you sure?");

                                builder2.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                System.out.println("Upload to server Start ! " +  idTrip[position] );

                                if(isConnected()){

                                    dialogProcess = new SpotsDialog(TripProcess.this);
                                    dialogProcess.show();

                                    tripList = getTripList(idTrip[position]);
                                    tripInfo = getTripInfo(idTrip[position]);
                                    baitInfo = getbaitInfo(idTrip[position]);
                                    bycatchInfo = getBycatchInfo(idTrip[position]);
                                    ringkasanKecilInfo = getRingkasanInfo(idTrip[position] , "tb_ringkasan_kecil");
                                    ringkasanBesarInfo = getRingkasanInfo(idTrip[position] , "tb_ringkasan_besar");
                                    kecilinfo = getKecilinfo(idTrip[position]);
                                    besarInfo = getBesarInfo(idTrip[position]);
                                    etpInfo = getEtpInfo(idTrip[position]);

                                    Toast.makeText(getApplicationContext(),"internet is available",Toast.LENGTH_SHORT).show();

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    Log.d(TAG, response.toString());
                                                    turnArchieve(idTrip[position]);
                                                    Toast.makeText(getApplicationContext(),"Success Upload To Server!",Toast.LENGTH_SHORT).show();

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                        Log.e(TAG, error.getMessage());
                                                    } else if (error instanceof AuthFailureError) {
                                                        Log.e(TAG, error.getMessage());
                                                    } else if (error instanceof ServerError) {
                                                        Log.e(TAG, error.getMessage());
                                                    } else if (error instanceof NetworkError) {
                                                        Log.e(TAG, error.getMessage());
                                                    } else if (error instanceof ParseError) {
                                                        Log.e(TAG, error.getMessage());
                                                    }
                                                    Toast.makeText(getApplicationContext(),"Something Wrong!",Toast.LENGTH_SHORT).show();

                                                }
                                            }){
                                        @Override
                                        protected Map<String,String> getParams(){
                                            Map<String,String> params = new HashMap<String, String>();
                                            params.put("id","1");
                                            params.put("tripList" , tripList.toString() );
                                            params.put("tripInfo" , tripInfo.toString() );
                                            params.put("baitInfo" , baitInfo.toString() );
                                            params.put("bycatchInfo" , bycatchInfo.toString() );
                                            params.put("ringkasanKecilInfo" , ringkasanKecilInfo.toString() );
                                            params.put("ringkasanBesarInfo" , ringkasanBesarInfo.toString() );
                                            params.put("kecilInfo" , kecilinfo.toString() );
                                            params.put("besarInfo" , besarInfo.toString() );
                                            params.put("etpInfo" , etpInfo.toString());

                                            return params;
                                        }

                                    };

                                    AppController.getInstance().addToRequestQueue(stringRequest);




                                    final Timer timer2 = new Timer();
                                    timer2.schedule(new TimerTask() {
                                        public void run() {
                                            dialogProcess.dismiss();
                                            timer2.cancel(); //this will cancel the timer of the system
                                        }
                                    }, 5000); // the timer will count 5 seconds....


                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"internet is NOT available",Toast.LENGTH_SHORT).show();

                                }



                                        dialog.dismiss();

                                    }
                                });


                                builder2.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });


                                AlertDialog alert2 = builder2.create();
                                alert2.show();

                                RefreshList();

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


    private void turnArchieve(String id_trip) {

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String insertSql = "UPDATE tb_trip_lists  SET status = 'archieve'  WHERE id = '" + id_trip + "'";
        Log.e("update sqlite ", "" + insertSql);
        try {
            db.execSQL(insertSql);

        } catch (SQLException e) {

            if (e instanceof SQLiteConstraintException) {

            } else if (e instanceof SQLiteDatatypeMismatchException) {

            } else {
                throw e;
            }
            System.out.println(e);


        }

        RefreshList();

    }


    private JSONObject getTripList(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_trip_lists  where id = '" + id_trip + "'", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

            obj = new JSONObject();
            try {

                obj.put("id", id_trip);
                obj.put("k_tpi", cursor.getString(1));
                obj.put("k_perusahaan", cursor.getString(2));
                obj.put("waktu", cursor.getString(3));
                obj.put("jam", cursor.getString(4));
                obj.put("notes", cursor.getString(5));
                obj.put("template_tipe",cursor.getString(6));
                obj.put("create_time", cursor.getString(7));
                obj.put("id_pengguna", cursor.getString(8));
                obj.put("apps_version" , Server.app_version) ;


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("tripList", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;


    }

    private JSONObject getTripInfo(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_trip_info  where idTrip = '" + id_trip + "'", null);
                /* Json Start */
        JSONObject obj = null;
        JSONObject obj1 = null;
        JSONObject obj2 = null;
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id", cursor.getString(0));
                    obj.put("idTrip", cursor.getString(1));
                    obj.put("vic", cursor.getString(2));
                    obj.put("total_tangkapan", cursor.getString(3));
                    obj.put("ikan_hilang", cursor.getString(4));
                    obj.put("lama_trip", cursor.getString(5));
                    obj.put("satuan_trip",cursor.getString(6));
                    obj.put("bbm", cursor.getString(7));
                    obj.put("jum_hari_memancing", cursor.getString(8));
                    obj.put("es", cursor.getString(9));
                    obj.put("awak", cursor.getString(10));
                    obj.put("kapten", cursor.getString(11));
                    obj.put("gt", cursor.getString(12));
                    obj.put("panjang", cursor.getString(13));
                    obj.put("pk", cursor.getString(14));
                    obj.put("bahan", cursor.getString(15));
                    obj.put("hl_tipe_mata_pancing", cursor.getString(16));
                    obj.put("hl_handline_troll", cursor.getString(17));
                    obj.put("hl_alattangkaplain", cursor.getString(18));
                    obj.put("pl_jumlah_pancing", cursor.getString(19));
                    obj.put("pl_kapasitas_ember", cursor.getString(20));
                    obj.put("nama_kapal", cursor.getString(21));
                    obj.put("rumpon", cursor.getString(22));
                    obj.put("gearType", cursor.getString(23));
                    obj.put("gearName", cursor.getString(24));
                    obj.put("enumerator_1", cursor.getString(25));
                    obj.put("enumerator_2", cursor.getString(26));
                    obj.put("tlc", cursor.getString(27));
                    obj.put("st", cursor.getString(28));
                    obj.put("flywire", cursor.getString(29));
                    obj.put("pds", cursor.getString(30));
                    obj.put("gps", cursor.getString(31));
                    obj.put("gps_merk", cursor.getString(32));
                    obj.put("daya_cahaya", cursor.getString(33));
                    obj.put("kedalaman_min", cursor.getString(34));
                    obj.put("kedalaman_max", cursor.getString(35));
                    obj.put("jum_palka", cursor.getString(36));
                    obj.put("kapasitas_palka", cursor.getString(37));
                    obj.put("kapal_andon", cursor.getString(38));
                    obj.put("asal_andon", cursor.getString(39));
                    obj.put("jum_rumpon", cursor.getString(40));
                    obj.put("ft_name", cursor.getString(41));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("tripInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        SQLiteDatabase db1 = databaseHandler.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery("select * from tb_trip_info_catchArea  where id_trip = '" + id_trip + "'", null);
        if (cursor1.moveToFirst()) {
            do {
                obj1 = new JSONObject();
                try {
                    obj1.put("id_trip", cursor1.getString(0));
                    obj1.put("grid_a", cursor1.getString(1));
                    obj1.put("grid_b", cursor1.getString(2));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray1.put(obj1);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        db1.close();
        try {
            finalobject.put("tripCatchArea", jsonArray1);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        SQLiteDatabase db2 = databaseHandler.getReadableDatabase();
        Cursor cursor2 = db2.rawQuery("select * from tb_trip_info_tunaLocating where id_trip = '" + id_trip + "'", null);
        if (cursor2.moveToFirst()) {
            do {
                obj2 = new JSONObject();
                try {
                    obj2.put("id_trip", cursor2.getString(0));
                    obj2.put("teknik", cursor2.getString(1));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray2.put(obj2);
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        db2.close();
        try {
            finalobject.put("tripTunaLoc", jsonArray2);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;

    }


    private JSONObject getbaitInfo(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_umpan  where id_trip = '" + id_trip + "' order by kategori", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id_trip", cursor.getString(0));
                    obj.put("kategori", cursor.getString(1));
                    obj.put("species", cursor.getString(2));
                    obj.put("grid_1", cursor.getString(3));
                    obj.put("grid_2", cursor.getString(4));
                    obj.put("total_kg", cursor.getString(5));
                    obj.put("estimasi_kg", cursor.getString(6));
                    obj.put("alat_tangkap", cursor.getString(7));
                    obj.put("hl_domestic_import", cursor.getString(8));
                    obj.put("pl_pengadaan", cursor.getString(9));
                    obj.put("pl_ember", cursor.getString(10));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("baitInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;



    }




    private JSONObject getBycatchInfo(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_bycatch  where id_trip = '" + id_trip + "' order by species", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id_trip", cursor.getString(0));
                    obj.put("species", cursor.getString(1));
                    obj.put("ekor", cursor.getString(2));
                    obj.put("total_kg", cursor.getString(3));
                    obj.put("estimasi", cursor.getString(4));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("bycatchInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;



    }


    private JSONObject getRingkasanInfo(String id_trip , String Tabel){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ Tabel +"  where id_trip = '" + id_trip + "' order by kode", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id_trip", cursor.getString(0));
                    obj.put("kode", cursor.getString(1));
                    obj.put("deskripsi", cursor.getString(2));
                    obj.put("total_kg", cursor.getString(3));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("ringkasanInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;

    }


    private JSONObject getKecilinfo(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_ikan_kecil  where id_trip = '" + id_trip + "' order by container_no , berat_keranjang , species , panjang", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id", cursor.getString(0));
                    obj.put("id_trip", cursor.getString(1));
                    obj.put("container_no", cursor.getString(2));
                    obj.put("berat_keranjang", cursor.getString(3));
                    obj.put("species", cursor.getString(4));
                    obj.put("panjang", cursor.getString(5));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("ikanKecilInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;


    }


    private JSONObject getBesarInfo(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_ikan_besar  where id_trip = '" + id_trip + "' order by kode , species ", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id", cursor.getString(0));
                    obj.put("id_trip", cursor.getString(1));
                    obj.put("kode", cursor.getString(2));
                    obj.put("species", cursor.getString(3));
                    obj.put("berat", cursor.getString(4));
                    obj.put("panjang", cursor.getString(5));
                    obj.put("loin_berat", cursor.getString(6));
                    obj.put("loin_panjang", cursor.getString(7));
                    obj.put("insang", cursor.getString(8));
                    obj.put("perut", cursor.getString(9));
                    obj.put("pl_daging", cursor.getString(10));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        try {
            finalobject.put("ikanBesarInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        return finalobject;

    }


    private JSONObject getEtpInfo(String id_trip){

        JSONObject finalobject = new JSONObject();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_etp  where id_trip = '" + id_trip + "' order by hewan", null);
                /* Json Start */
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {

                obj = new JSONObject();
                try {

                    obj.put("id", cursor.getString(0));
                    obj.put("id_trip", cursor.getString(1));
                    obj.put("hewan", cursor.getString(2));
                    obj.put("interaksi", cursor.getString(3));
                    obj.put("interaksi_count", cursor.getString(4));
                    obj.put("interaksi_perkiraan", cursor.getString(5));
                    obj.put("didaratkan_count", cursor.getString(6));
                    obj.put("didaratkan_perkiraaan", cursor.getString(7));
                    obj.put("d1", cursor.getString(8));
                    obj.put("d2", cursor.getString(9));
                    obj.put("d3", cursor.getString(10));
                    obj.put("d4", cursor.getString(11));
                    obj.put("d5", cursor.getString(12));
                    obj.put("d6", cursor.getString(13));
                    obj.put("d7", cursor.getString(14));
                    obj.put("d8", cursor.getString(15));
                    obj.put("d9", cursor.getString(16));
                    obj.put("d10", cursor.getString(17));
                    obj.put("t1", cursor.getString(18));
                    obj.put("t2", cursor.getString(19));
                    obj.put("t3", cursor.getString(20));
                    obj.put("t4", cursor.getString(21));
                    obj.put("t5", cursor.getString(22));
                    obj.put("kode", cursor.getString(23));
                    obj.put("yakin_species", cursor.getString(24));
                    obj.put("lokal", cursor.getString(25));
                    obj.put("yakin_lokal", cursor.getString(26));
                    obj.put("lokasi_rumpon", cursor.getString(27));
                    obj.put("lokasi_perjalanan", cursor.getString(28));
                    obj.put("lokasi_lainnya", cursor.getString(29));
                    obj.put("sp_etp", cursor.getString(30));
                    obj.put("sp_lain", cursor.getString(31));
                    obj.put("alat_tangan", cursor.getString(32));
                    obj.put("alat_kapal", cursor.getString(33));
                    obj.put("alat_lain", cursor.getString(34));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj);




            } while (cursor.moveToNext());
        }
        cursor.close();


        try {
            finalobject.put("etpInfo", jsonArray);
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        JSONObject obj2 = null;
        cursor = db.rawQuery("select * from tb_etp_info  where id_trip = '" + id_trip + "'", null);
        if (cursor.moveToFirst()) {
            do {

                obj2 = new JSONObject();
                try {

                    obj2.put("id", cursor.getString(0));
                    obj2.put("pewawancara", cursor.getString(1));
                    obj2.put("umur", cursor.getString(2));
                    obj2.put("lama_bekerja_tahun", cursor.getString(3));
                    obj2.put("lama_bekerja_bulan", cursor.getString(4));
                    obj2.put("jabatan", cursor.getString(5));
                    obj2.put("keterangan", cursor.getString(6));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArray.put(obj2);

            } while (cursor.moveToNext());
        }
        cursor.close();




        System.out.println("jsonString: "+ finalobject);
                                    /* Json End */

        db.close();
        return finalobject;

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
        Intent intent = new Intent(TripProcess.this, SheetSelection.class);
        intent.putExtra("idTrip", idTrip[position]);
        intent.putExtra("kode_tpi", kode_tpi[position]);
        intent.putExtra("n_tpi", landing[position]);
        intent.putExtra("n_perusahaan", supplier[position]);
        intent.putExtra("tipeTemplate", tipeTemplate[position]);
        intent.putExtra("waktu", waktu[position]);
        intent.putExtra("jam", jam[position]);
        startActivity(intent);

    }



    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

          return null ;

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
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
