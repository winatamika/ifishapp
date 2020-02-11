package com.example.mick.dockandroidlogin.sampling;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.Server;
import com.example.mick.dockandroidlogin.adapter.AdapterTripProcess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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

public class TripArchieve extends AppCompatActivity implements AdapterView.OnItemClickListener ,  View.OnClickListener  {

    String[] daftar   , idTrip , landing , supplier , tipeTemplate , waktu , jam , namaKapal , totalKalkulasi ;
    ListView ListView01;
    AdapterTripProcess lviewAdapter;
    Menu menu;
    protected Cursor cursor;
    DatabaseHandler dbcenter;
    public static TripArchieve TA;
    TextView notFound ;
    String idUser ;
    SharedPreferences sharedpreferences;
    Spinner Slanding ;
    String keyLanding , valLanding ;
    Button btnawal , btnakhir , buttonSearch ;
    EditText txtawal , txtakhir ;
    int mYear , mMonth , mDay ;

    AlertDialog.Builder dialog;
    AlertDialog dialogProcess ;
    JSONObject tripList , tripInfo  , baitInfo , bycatchInfo , ringkasanKecilInfo , ringkasanBesarInfo , kecilinfo, besarInfo , etpInfo ;
    public static final String TAG = AppController.class.getSimpleName();
    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    private String url = Server.URL + "postData";
    String sqlQuery = "" ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_trip_archieve);

        notFound = (TextView) findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);

        TA = this;
        dbcenter = new DatabaseHandler(this);
        RefreshList();


        /* Landing Site */
        Slanding = (Spinner) findViewById(R.id.landingSite);
        final String selectQuery = " select distinct(t.k_tpi) , n_tpi from tb_trip_lists p , tb_master_tpi t where  p.k_tpi = t.k_tpi order by t.k_tpi " ;
        loadSpinnerDataDinamic(selectQuery , Slanding);
        Slanding.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {

                        Spinner spinner = (Spinner) parent;
                        keyLanding = ( (SpinnerObject) spinner.getSelectedItem () ).getId() ;
                        valLanding = parent.getItemAtPosition(position).toString();

                        System.out.println(keyLanding) ;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                }

        );



        buttonSearch = (Button) findViewById(R.id.buttonSearch);

        btnawal = (Button) findViewById(R.id.btnawal);
        btnakhir = (Button) findViewById(R.id.btnakhir);
        txtawal = (EditText) findViewById(R.id.txtawal);
        txtawal.setEnabled(false);
        txtawal.setTextColor(Color.BLACK);
        txtakhir = (EditText) findViewById(R.id.txtakhir);
        txtakhir.setEnabled(false);
        txtakhir.setTextColor(Color.BLACK);
        btnawal.setOnClickListener(this);
        btnakhir.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlQuery = "";

                if( !txtawal.getText().toString().equals("")  && !txtakhir.getText().toString().equals("") && !keyLanding.equals("") ) {
                    System.out.println(keyLanding + " " + txtawal.getText().toString() + " " + txtakhir.getText().toString() + " ");
                    sqlQuery = " and ";
                    sqlQuery = sqlQuery + "  t.k_tpi in ('" + keyLanding + "') ";

                    sqlQuery = sqlQuery + " and ";
                    sqlQuery = sqlQuery + " waktu BETWEEN '" + txtawal.getText().toString() + "' and '" + txtakhir.getText().toString()  +"'";
                }else  if( !keyLanding.equals("")  &&  ( txtawal.getText().toString().equals("") || txtakhir.getText().toString().equals("") ) ){
                    sqlQuery = " and ";
                    sqlQuery = sqlQuery + "  t.k_tpi in ('" + keyLanding + "') ";

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Mohon isikan Landing atau Tanggal", Toast.LENGTH_SHORT).show();
                }
                RefreshList();
            }
        });


    }


    private void loadSpinnerDataDinamic(String selectQuery , Spinner spiner) {

        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        labels.add( new SpinnerObject (   ""  ,  "Pilih Landing Site" ) ) ;
        if (cursor.moveToFirst()) {
            do {
                labels.add(   new SpinnerObject (  cursor.getString(0) ,  cursor.getString(1) )  );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);

    }


    public void RefreshList( ){
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        idUser = sharedpreferences.getString("id", null);


        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT  id , t.n_tpi , p.n_perusahaan , waktu , jam , template_tipe , create_time  " +
                "FROM tb_trip_lists l , tb_master_tpi t , tb_master_perusahaan p " +
                "where l.k_tpi = t.k_tpi and l.k_perusahaan = p.k_perusahaan and l.status = 'archieve' and id_pengguna = '"+ idUser +"' "+ sqlQuery +"  order by id desc ",null);
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
                idTrip[cc] = cursor.getString(cursor.getColumnIndex("id"));
                landing[cc] = cursor.getString(cursor.getColumnIndex("n_tpi"));
                supplier[cc] = cursor.getString(cursor.getColumnIndex("n_perusahaan"));
                tipeTemplate[cc] = cursor.getString(cursor.getColumnIndex("template_tipe"));
                waktu[cc] = cursor.getString(cursor.getColumnIndex("waktu"));
                jam[cc] = cursor.getString(cursor.getColumnIndex("jam"));
                namaKapal[cc] = getNamaKapal(cursor.getString(cursor.getColumnIndex("id"))) ;
                totalKalkulasi[cc] = Integer.toString ( getKalkulasiTotalTangkapan(cursor.getString(cursor.getColumnIndex("id"))) );




                /*
                //daftar[cc] = cursor.getString(0).toString();
                list.add(cursor.getString(cursor.getColumnIndex("id")));
                list.add(cursor.getString(cursor.getColumnIndex("n_tpi")));
                list.add(cursor.getString(cursor.getColumnIndex("n_perusahaan")));
                list.add(cursor.getString(cursor.getColumnIndex("waktu")));
                list.add(cursor.getString(cursor.getColumnIndex("jam")));
                list.add(cursor.getString(cursor.getColumnIndex("template_tipe")));
                list.add(cursor.getString(cursor.getColumnIndex("create_time")));
                */

            }
        }else{

            notFound.setVisibility(View.VISIBLE);

        }


        ListView01 = (ListView)findViewById(R.id.listView1);
        lviewAdapter = new AdapterTripProcess(this, landing, supplier, tipeTemplate ,waktu , jam , namaKapal , totalKalkulasi);
        System.out.println("adapter => "+lviewAdapter.getCount());
        ListView01.setAdapter(lviewAdapter);
        ListView01.setOnItemClickListener(this);
        //ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, list));
        //ListView01.setSelected(true);
        /*
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat ", "Update ", "Hapus "};
                AlertDialog.Builder builder = new AlertDialog.Builder(TripArchieve.this);
                builder.setTitle("Pilihan");

                builder.create().show();
            }});*/
        //((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();

        ListView01.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {"Edit Again" ,   "Upload To IFISH Server"};
                dialog = new AlertDialog.Builder(TripArchieve.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                //Trip Archieve go to Trip Lists


                                AlertDialog.Builder builder3 = new AlertDialog.Builder(TripArchieve.this);
                                builder3.setTitle("Confirm");
                                builder3.setMessage("Are you sure to RE-EDIT the trip archieve ?");

                                builder3.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        //DO TURN ARCHIEVE
                                        System.out.println("Do Edit ");
                                        turnArchievetoList(idTrip[position]);
                                    }
                                });


                                builder3.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });


                                AlertDialog alert3 = builder3.create();
                                alert3.show();




                                //End Trip Archeve go to Trip Lists
                            break;
                            case 1:


                                AlertDialog.Builder builder2 = new AlertDialog.Builder(TripArchieve.this);
                                builder2.setTitle("Confirm");
                                builder2.setMessage("Are you sure to REUPLOAD the trip archieve ?");

                                builder2.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        System.out.println("Upload to server Start ! " +  idTrip[position] );

                                        if(isConnected()){

                                            dialogProcess = new SpotsDialog(TripArchieve.this);
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


    private void turnArchievetoList(String id_trip) {

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String insertSql = "UPDATE tb_trip_lists  SET status = 'process'  WHERE id = '" + id_trip + "'";
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


    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

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
        Cursor cursor = db.rawQuery("select * from tb_umpan  where id_trip = '" + id_trip + "'", null);
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
        Cursor cursor = db.rawQuery("select * from tb_bycatch  where id_trip = '" + id_trip + "'", null);
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
        Cursor cursor = db.rawQuery("select * from "+ Tabel +"  where id_trip = '" + id_trip + "'", null);
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
        Cursor cursor = db.rawQuery("select * from tb_ikan_kecil  where id_trip = '" + id_trip + "'", null);
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
        Cursor cursor = db.rawQuery("select * from tb_ikan_besar  where id_trip = '" + id_trip + "'", null);
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
        Cursor cursor = db.rawQuery("select * from tb_etp  where id_trip = '" + id_trip + "'", null);
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnawal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                txtawal.setText( year  + "-" +   String.format( "%02d" , monthOfYear + 1)   + "-" + String.format("%02d", dayOfMonth)  );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;
            case R.id.btnakhir:

                Calendar c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                txtakhir.setText( year  + "-" +   String.format( "%02d" , monthOfYear + 1)   + "-" + String.format("%02d", dayOfMonth)  );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();


                break;
        }
    }

}
