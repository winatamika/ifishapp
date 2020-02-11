package com.example.mick.dockandroidlogin.sampling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.AdapterEtpInfo;

import java.util.ArrayList;
import java.util.List;

public class EtpInfo extends AppCompatActivity implements OnItemSelectedListener   {


    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    String idTrip , tipeTemplate ;
    String TABEL_ETP = "tb_etp" ;
    String[] idDatabase , hewan , interaksi , interaksi_perkiraan , interaksi_count , didaratkan_count ,  didaratkan_perkiraaan ;
    TextView notFound ;
    Intent i ;
    ListView lvEtpInfo;
    AdapterEtpInfo etpInfoAdapter ;
    Spinner SpinnerHewan , SpinnerInteraksi , SpinnerKode , SpinnerInteraksiPerkiraan , SpinnerDidaratkanPerkiraaan ,  SpinnerYakinSpecies , SpinnerYakinLokal , SpinnerAlat_tangan , SpinnerAlat_kapal ;
    String keyHewan , keyInteraksi , keyKode , keyInteraksiPerkiraan ,  keyDidaratkanPerkiraan , keyYakinSpecies , keyYakinLokal , keyAlatTangan , keyAlatKapal  ;
    EditText editinteraksi_count , editdidaratkan_count , d1 , d2 , d3 ,d4, d5 , d6 , d7 , d8 , d9 , d10 , t1 , t2 , t3 , t4 , t5 ,  lokal , lokasi_rumpon , lokasi_perjalanan , lokasi_lainnya ,  sp_etp , sp_lain , alat_lain , speciesCustom ;
    EditText  pewawancara , umur , lama_bekerja_tahun , lama_bekerja_bulan , jabatan , keterangan ;
    String[] lists1  = new String[]{"Zero" };
    String TABEL_TRIP_INFO = "tb_trip_info" ;
    Boolean isExsist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_etp_info);

         /* Get All Importtant Intent  */
        idTrip = getIntent().getStringExtra("idTrip");
        tipeTemplate = getIntent().getStringExtra("tipeTemplate");

        notFound = (TextView) findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);

        refreshList();


        //Button buttonAdd=(Button)findViewById(R.id.buttonAdd);
        FloatingActionButton buttonAdd = (FloatingActionButton)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog();
            }
        });

        //Button buttonAddInfo=(Button)findViewById(R.id.button);
        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialogInfo();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


    }


    private void refreshList(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_ETP  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        idDatabase = new String[cursor.getCount()];
        hewan = new String[cursor.getCount()];
        interaksi = new String[cursor.getCount()];
        interaksi_perkiraan = new String[cursor.getCount()];
        interaksi_count = new String[cursor.getCount()];
        didaratkan_count = new String[cursor.getCount()];
        didaratkan_perkiraaan = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                idDatabase[cc] = cursor.getString(cursor.getColumnIndex("id"));
                hewan[cc] = cursor.getString(cursor.getColumnIndex("hewan"));
                interaksi[cc] = cursor.getString(cursor.getColumnIndex("interaksi"));
                interaksi_perkiraan[cc] = cursor.getString(cursor.getColumnIndex("interaksi_perkiraan"));
                interaksi_count[cc] = cursor.getString(cursor.getColumnIndex("interaksi_count"));
                didaratkan_count[cc] = cursor.getString(cursor.getColumnIndex("didaratkan_count"));
                didaratkan_perkiraaan[cc] = cursor.getString(cursor.getColumnIndex("didaratkan_perkiraaan"));

                notFound.setVisibility(View.INVISIBLE);
            }


        }else{
            notFound.setVisibility(View.VISIBLE);
        }

        lvEtpInfo = (ListView) findViewById(R.id.etpInfo);
        etpInfoAdapter = new AdapterEtpInfo(this,hewan , interaksi , interaksi_perkiraan ,  interaksi_count, didaratkan_perkiraaan , didaratkan_count);
        lvEtpInfo.setAdapter(etpInfoAdapter);

        lvEtpInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {  "Update" , "Delete"  };
                AlertDialog.Builder dialog = new AlertDialog.Builder(EtpInfo.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                System.out.println("Do Update "+ idTrip + " " + idDatabase[position] + " " + hewan[position] );
                                showDialogUpdate(idTrip ,idDatabase[position]   );

                                break;
                            case 1:

                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_ETP + " WHERE id_trip = '" + idTrip + "' and id = '" + idDatabase[position] + "' ");
                                db.close();

                                refreshList();


                                break;

                        }
                    }
                }).show();

                return true;
            }

        }) ;


    }


    private boolean checkExsist(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select enumerator_1 from " + TABEL_TRIP_INFO + " where idTrip = '" + idTrip+ "'", null);

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    pewawancara.setText(cursor.getString(0) ) ;

                } while (cursor.moveToNext());
            }


            cursor.close();
            db.close();


            return true;
        }
        return false ;
    }


    private void showDialogInfo(){

        LayoutInflater layoutInflater = LayoutInflater.from(EtpInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_etp_info_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EtpInfo.this);
        alertDialogBuilder.setTitle("ETP Info Pewawancara");
        alertDialogBuilder.setView(promptView);

        //pewawancara , umur , lama_bekerja_tahun , lama_bekerja_bulan , jabatan , keterangan
        pewawancara = (EditText) promptView.findViewById(R.id.pewawancara);

        umur = (EditText) promptView.findViewById(R.id.umur);
        lama_bekerja_tahun = (EditText) promptView.findViewById(R.id.lama_bekerja_tahun);
        lama_bekerja_bulan = (EditText) promptView.findViewById(R.id.lama_bekerja_bulan);
        jabatan = (EditText) promptView.findViewById(R.id.jabatan);
        keterangan = (EditText) promptView.findViewById(R.id.keterangan);

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * from tb_etp_info where id_trip = '"+  idTrip  +"' ",null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    pewawancara.setText(cursor.getString(1));
                    umur.setText(cursor.getString(2));
                    lama_bekerja_tahun.setText(cursor.getString(3));
                    lama_bekerja_bulan.setText(cursor.getString(4));
                    jabatan.setText(cursor.getString(5));
                    keterangan.setText(cursor.getString(6));
                } while (cursor.moveToNext());
            }
        }
            cursor.close();


        if( pewawancara.getText().toString().equals((""))) {
            isExsist = checkExsist();

            if(checkExsist()) {

                Toast.makeText(getApplicationContext(),
                        "Berdasarkan Enumerator dalam trip !", Toast.LENGTH_SHORT).show();
            }
        }


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveAddInfo();
                        System.out.println("Ok Dialog");
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }





    private void showDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(EtpInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_etp_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EtpInfo.this);
        alertDialogBuilder.setTitle("ETP Info");
        alertDialogBuilder.setView(promptView);


        editinteraksi_count = (EditText) promptView.findViewById(R.id.editinteraksi_count);
        editdidaratkan_count  = (EditText) promptView.findViewById(R.id.editdidaratkan_count);
        d1 = (EditText) promptView.findViewById(R.id.d1);
        d2 = (EditText) promptView.findViewById(R.id.d2);
        d3 = (EditText) promptView.findViewById(R.id.d3);
        d4 = (EditText) promptView.findViewById(R.id.d4);
        d5 = (EditText) promptView.findViewById(R.id.d5);
        d6 = (EditText) promptView.findViewById(R.id.d6);
        d7 = (EditText) promptView.findViewById(R.id.d7);
        d8 = (EditText) promptView.findViewById(R.id.d8);
        d9 = (EditText) promptView.findViewById(R.id.d9);
        d10 = (EditText) promptView.findViewById(R.id.d10);
        t1 = (EditText) promptView.findViewById(R.id.t1);
        t2 = (EditText) promptView.findViewById(R.id.t2);
        t3 = (EditText) promptView.findViewById(R.id.t3);
        t4 = (EditText) promptView.findViewById(R.id.t4);
        t5 = (EditText) promptView.findViewById(R.id.t5);
        lokal = (EditText) promptView.findViewById(R.id.lokal);
        lokasi_rumpon = (EditText) promptView.findViewById(R.id.lokasi_rumpon);
        lokasi_perjalanan = (EditText) promptView.findViewById(R.id.lokasi_perjalanan);
        lokasi_lainnya = (EditText) promptView.findViewById(R.id.lokasi_lainnya);
        sp_etp = (EditText) promptView.findViewById(R.id.sp_etp);
        sp_lain = (EditText) promptView.findViewById(R.id.sp_lain);
        alat_lain = (EditText) promptView.findViewById(R.id.alat_lain); ;

        speciesCustom = (EditText) promptView.findViewById(R.id.speciesCustom);
        speciesCustom.setEnabled(false);
        speciesCustom.setTextColor(Color.BLACK);

        SpinnerHewan = (Spinner) promptView.findViewById(R.id.SpinnerHewan);
        SpinnerKode = (Spinner) promptView.findViewById(R.id.SpinnerKode);
        SpinnerHewan.setOnItemSelectedListener(this);
        String[] lists0 = new String[]{"Hiu", "Pari" , "Lumba-lumba" , "Paus" , "Penyu" , "Burung"};
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lists0);
        SpinnerHewan.setAdapter(adapter0);
        SpinnerHewan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyHewan = (String) parent.getItemAtPosition(position);

                if(keyHewan.equals("Hiu")){
                    lists1 = new String[]{"" ,"PTH","BTH","ALV","OCS","DUS","TIG","BSH","HEH","HEE","SMA","LMA","PSK","ALS","CCA","CCB","FAL","CCL","NGA","CCK","HCM","LMT","CCP","CCF","SPL","SPK","SPZ","DGS","LMP","RHN" };
                }else if(keyHewan.equals("Pari")){
                    lists1 = new String[]{"" , "RMB","RMA","MAF","PLS","RBQ","TNO","TNQ" };
                }else if(keyHewan.equals("Lumba-lumba")){
                    lists1 = new String[]{"" , "DRR","Oceanic dolphins","DHI","IRD","PFI","Bottlenose dolphin","BCW","TGW","DUG" };
                }else if(keyHewan.equals("Paus")){
                    lists1 = new String[]{ "" , "BLW","FIW","SIW","BRW","MIW","HUW","SPW","KIW","FAW","GLO","MEW" };
                }else if(keyHewan.equals("Penyu")){
                    lists1 = new String[]{ "" , "LKV","TTL","TUG","DKK","TTH","FBT" };
                }else if(keyHewan.equals("Burung")){
                    lists1 = new String[]{ "" , "PTZ","SZV","DSQ","Lesser Frigatebird","Christmas Island Frigatebir","Greater Frigatebird","SVZ" };
                }

                loadSpinnerDataStatic(lists1 , SpinnerKode);


                /* Toast.makeText(getApplicationContext(),
                        keyHewan, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerKode.setOnItemSelectedListener(this);
        SpinnerKode.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyKode = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyKode, Toast.LENGTH_SHORT).show(); */

                if(keyKode.equals("")) {
                    speciesCustom.setEnabled(true);
                }else{
                    speciesCustom.setText("");
                    speciesCustom.setEnabled(false);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        String[] yesNo = new String[]{"" , "Ya", "Tidak"};
        String[] yakinTidak = new String[]{"" , "Sangat Yakin", "Agak Yakin" , "Tidak Yakin"};
        SpinnerInteraksi =  (Spinner) promptView.findViewById(R.id.SpinnerInteraksi);
        loadSpinnerDataStatic(yesNo , SpinnerInteraksi);
        SpinnerInteraksi.setOnItemSelectedListener(this);
        SpinnerInteraksi.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyInteraksi = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyInteraksi, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerInteraksiPerkiraan =  (Spinner) promptView.findViewById(R.id.SpinnerInteraksiPerkiraan);
        loadSpinnerDataStatic(yesNo , SpinnerInteraksiPerkiraan);
        SpinnerInteraksiPerkiraan.setOnItemSelectedListener(this);
        SpinnerInteraksiPerkiraan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyInteraksiPerkiraan = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyInteraksiPerkiraan, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerDidaratkanPerkiraaan =  (Spinner) promptView.findViewById(R.id.SpinnerDidaratkanPerkiraaan);
        loadSpinnerDataStatic(yesNo , SpinnerDidaratkanPerkiraaan);
        SpinnerDidaratkanPerkiraaan.setOnItemSelectedListener(this);
        SpinnerDidaratkanPerkiraaan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyDidaratkanPerkiraan = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyDidaratkanPerkiraan, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerYakinSpecies =  (Spinner) promptView.findViewById(R.id.SpinnerYakinSpecies);
        loadSpinnerDataStatic(yakinTidak , SpinnerYakinSpecies);
        SpinnerYakinSpecies.setOnItemSelectedListener(this);
        SpinnerYakinSpecies.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyYakinSpecies = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyYakinSpecies, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerYakinLokal =  (Spinner) promptView.findViewById(R.id.SpinnerYakinLokal);
        loadSpinnerDataStatic(yakinTidak , SpinnerYakinLokal);
        SpinnerYakinLokal.setOnItemSelectedListener(this);
        SpinnerYakinLokal.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyYakinLokal = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyYakinLokal, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerAlat_tangan =  (Spinner) promptView.findViewById(R.id.SpinnerAlat_tangan);
        loadSpinnerDataStatic(yesNo , SpinnerAlat_tangan);
        SpinnerAlat_tangan.setOnItemSelectedListener(this);
        SpinnerAlat_tangan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyAlatTangan = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyAlatTangan, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerAlat_kapal =  (Spinner) promptView.findViewById(R.id.SpinnerAlat_kapal);
        loadSpinnerDataStatic(yesNo , SpinnerAlat_kapal);
        SpinnerAlat_kapal.setOnItemSelectedListener(this);
        SpinnerAlat_kapal.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyAlatKapal = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyAlatKapal, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveAdd();
                        refreshList();
                        System.out.println("Ok Dialog");
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }


    private void showDialogUpdate(final String id_trip , final String idDb){

        LayoutInflater layoutInflater = LayoutInflater.from(EtpInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_etp_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EtpInfo.this);
        alertDialogBuilder.setTitle("ETP Info");
        alertDialogBuilder.setView(promptView);


        editinteraksi_count = (EditText) promptView.findViewById(R.id.editinteraksi_count);
        editdidaratkan_count  = (EditText) promptView.findViewById(R.id.editdidaratkan_count);
        d1 = (EditText) promptView.findViewById(R.id.d1);
        d2 = (EditText) promptView.findViewById(R.id.d2);
        d3 = (EditText) promptView.findViewById(R.id.d3);
        d4 = (EditText) promptView.findViewById(R.id.d4);
        d5 = (EditText) promptView.findViewById(R.id.d5);
        d6 = (EditText) promptView.findViewById(R.id.d6);
        d7 = (EditText) promptView.findViewById(R.id.d7);
        d8 = (EditText) promptView.findViewById(R.id.d8);
        d9 = (EditText) promptView.findViewById(R.id.d9);
        d10 = (EditText) promptView.findViewById(R.id.d10);
        t1 = (EditText) promptView.findViewById(R.id.t1);
        t2 = (EditText) promptView.findViewById(R.id.t2);
        t3 = (EditText) promptView.findViewById(R.id.t3);
        t4 = (EditText) promptView.findViewById(R.id.t4);
        t5 = (EditText) promptView.findViewById(R.id.t5);
        lokal = (EditText) promptView.findViewById(R.id.lokal);
        lokasi_rumpon = (EditText) promptView.findViewById(R.id.lokasi_rumpon);
        lokasi_perjalanan = (EditText) promptView.findViewById(R.id.lokasi_perjalanan);
        lokasi_lainnya = (EditText) promptView.findViewById(R.id.lokasi_lainnya);
        sp_etp = (EditText) promptView.findViewById(R.id.sp_etp);
        sp_lain = (EditText) promptView.findViewById(R.id.sp_lain);
        alat_lain = (EditText) promptView.findViewById(R.id.alat_lain); ;

        speciesCustom = (EditText) promptView.findViewById(R.id.speciesCustom);
        speciesCustom.setEnabled(false);
        speciesCustom.setTextColor(Color.BLACK);

        SpinnerHewan = (Spinner) promptView.findViewById(R.id.SpinnerHewan);
        SpinnerKode = (Spinner) promptView.findViewById(R.id.SpinnerKode);
        SpinnerHewan.setOnItemSelectedListener(this);
        String[] lists0 = new String[]{"Hiu", "Pari" , "Lumba-lumba" , "Paus" , "Penyu" , "Burung"};
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lists0);
        SpinnerHewan.setAdapter(adapter0);
        SpinnerHewan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyHewan = (String) parent.getItemAtPosition(position);

                if(keyHewan.equals("Hiu")){
                    lists1 = new String[]{"" , "PTH","BTH","ALV","OCS","DUS","TIG","BSH","HEH","HEE","SMA","LMA","PSK","ALS","CCA","CCB","FAL","CCL","NGA","CCK","HCM","LMT","CCP","CCF","SPL","SPK","SPZ","DGS","LMP","RHN" };
                }else if(keyHewan.equals("Pari")){
                    lists1 = new String[]{ "RMB","RMA","MAF","PLS","RBQ","TNO","TNQ" };
                }else if(keyHewan.equals("Lumba-lumba")){
                    lists1 = new String[]{"DRR","Oceanic dolphins","DHI","IRD","PFI","Bottlenose dolphin","BCW","TGW","DUG" };
                }else if(keyHewan.equals("Paus")){
                    lists1 = new String[]{ "BLW","FIW","SIW","BRW","MIW","HUW","SPW","KIW","FAW","GLO","MEW" };
                }else if(keyHewan.equals("Penyu")){
                    lists1 = new String[]{ "LKV","TTL","TUG","DKK","TTH","FBT" };
                }else if(keyHewan.equals("Burung")){
                    lists1 = new String[]{ "PTZ","SZV","DSQ","Lesser Frigatebird","Christmas Island Frigatebir","Greater Frigatebird","SVZ" };
                }

                loadSpinnerDataStatic(lists1 , SpinnerKode);


                /* Toast.makeText(getApplicationContext(),
                        keyHewan, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerKode.setOnItemSelectedListener(this);
        SpinnerKode.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyKode = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyKode, Toast.LENGTH_SHORT).show(); */
                if(keyKode.equals("")) {
                    speciesCustom.setEnabled(true);
                }else{
                    speciesCustom.setText("");
                    speciesCustom.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        String[] yesNo = new String[]{"" , "Ya", "Tidak"};
        String[] yakinTidak = new String[]{ "" , "Sangat Yakin", "Agak Yakin" , "Tidak Yakin"};
        SpinnerInteraksi =  (Spinner) promptView.findViewById(R.id.SpinnerInteraksi);
        loadSpinnerDataStatic(yesNo , SpinnerInteraksi);
        SpinnerInteraksi.setOnItemSelectedListener(this);
        SpinnerInteraksi.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyInteraksi = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyInteraksi, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerInteraksiPerkiraan =  (Spinner) promptView.findViewById(R.id.SpinnerInteraksiPerkiraan);
        loadSpinnerDataStatic(yesNo , SpinnerInteraksiPerkiraan);
        SpinnerInteraksiPerkiraan.setOnItemSelectedListener(this);
        SpinnerInteraksiPerkiraan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyInteraksiPerkiraan = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyInteraksiPerkiraan, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerDidaratkanPerkiraaan =  (Spinner) promptView.findViewById(R.id.SpinnerDidaratkanPerkiraaan);
        loadSpinnerDataStatic(yesNo , SpinnerDidaratkanPerkiraaan);
        SpinnerDidaratkanPerkiraaan.setOnItemSelectedListener(this);
        SpinnerDidaratkanPerkiraaan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyDidaratkanPerkiraan = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyDidaratkanPerkiraan, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerYakinSpecies =  (Spinner) promptView.findViewById(R.id.SpinnerYakinSpecies);
        loadSpinnerDataStatic(yakinTidak , SpinnerYakinSpecies);
        SpinnerYakinSpecies.setOnItemSelectedListener(this);
        SpinnerYakinSpecies.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyYakinSpecies = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyYakinSpecies, Toast.LENGTH_SHORT).show();  */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerYakinLokal =  (Spinner) promptView.findViewById(R.id.SpinnerYakinLokal);
        loadSpinnerDataStatic(yakinTidak , SpinnerYakinLokal);
        SpinnerYakinLokal.setOnItemSelectedListener(this);
        SpinnerYakinLokal.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyYakinLokal = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyYakinLokal, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerAlat_tangan =  (Spinner) promptView.findViewById(R.id.SpinnerAlat_tangan);
        loadSpinnerDataStatic(yesNo , SpinnerAlat_tangan);
        SpinnerAlat_tangan.setOnItemSelectedListener(this);
        SpinnerAlat_tangan.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyAlatTangan = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyAlatTangan, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerAlat_kapal =  (Spinner) promptView.findViewById(R.id.SpinnerAlat_kapal);
        loadSpinnerDataStatic(yesNo , SpinnerAlat_kapal);
        SpinnerAlat_kapal.setOnItemSelectedListener(this);
        SpinnerAlat_kapal.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyAlatKapal = (String) parent.getItemAtPosition(position);
                /* Toast.makeText(getApplicationContext(),
                        keyAlatKapal, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_ETP + " where id_trip = '" + id_trip + "' and id = '" + idDb + "'", null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    SpinnerHewan.setSelection(getIndex(SpinnerHewan, cursor.getString(2)));
                    SpinnerInteraksi.setSelection(getIndex(SpinnerInteraksi, cursor.getString(3)));
                    editinteraksi_count.setText(cursor.getString(4));
                    SpinnerInteraksiPerkiraan.setSelection(getIndex(SpinnerInteraksiPerkiraan, cursor.getString(5)));
                    editdidaratkan_count.setText(cursor.getString(6));
                    SpinnerDidaratkanPerkiraaan.setSelection(getIndex(SpinnerDidaratkanPerkiraaan, cursor.getString(7)));
                    d1.setText(cursor.getString(8));
                    d2.setText(cursor.getString(9));
                    d3.setText(cursor.getString(10));
                    d4.setText(cursor.getString(11));
                    d5.setText(cursor.getString(12));
                    d6.setText(cursor.getString(13));
                    d7.setText(cursor.getString(14));
                    d8.setText(cursor.getString(15));
                    d9.setText(cursor.getString(16));
                    d10.setText(cursor.getString(17));
                    t1.setText(cursor.getString(18));
                    t2.setText(cursor.getString(19));
                    t3.setText(cursor.getString(20));
                    t4.setText(cursor.getString(21));
                    t5.setText(cursor.getString(22));
                    SpinnerKode.setSelection(getIndex(SpinnerKode, cursor.getString(23)));
                    speciesCustom.setText(cursor.getString(23));
                    SpinnerYakinSpecies.setSelection(getIndex(SpinnerYakinSpecies, cursor.getString(24)));
                    lokal.setText(cursor.getString(25));
                    SpinnerYakinLokal.setSelection(getIndex(SpinnerYakinSpecies, cursor.getString(26)));
                    lokasi_rumpon.setText(cursor.getString(27));
                    lokasi_perjalanan.setText(cursor.getString(28));
                    lokasi_lainnya.setText(cursor.getString(29));
                    sp_etp.setText(cursor.getString(30));
                    sp_lain.setText(cursor.getString(31));
                    SpinnerAlat_tangan.setSelection(getIndex(SpinnerYakinSpecies, cursor.getString(32)));
                    SpinnerAlat_kapal.setSelection(getIndex(SpinnerYakinSpecies, cursor.getString(33)));
                    alat_lain.setText(cursor.getString(34));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveUpdate(id_trip , idDb );
                        refreshList();
                        System.out.println("Ok Dialog");
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();



    }

    }


    private void loadSpinnerDataStatic(String[] list , Spinner spiner){

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        spiner.setAdapter(adapter1);


    }

    private Boolean checkInfo(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  id_trip from tb_etp_info where id_trip = '"+  idTrip  +"' ",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {

            return true;

        }

        db.close();

        return false;
    }


    private void saveAddInfo(){



        Boolean exsist =  checkInfo();
        System.out.println(exsist);
        if(exsist.equals(Boolean.TRUE)){
            System.out.println("Update ! ");
            SQLiteDatabase db = databaseHandler.getWritableDatabase();

            String insertSql = "UPDATE tb_etp_info SET pewawancara = '"+ pewawancara.getText().toString() +"' , umur = '"+umur.getText().toString()+"' , lama_bekerja_tahun = '"+lama_bekerja_tahun.getText().toString() +"', lama_bekerja_bulan = '"+lama_bekerja_bulan.getText().toString()+"', jabatan = '"+jabatan.getText().toString() +"' , keterangan = '" + keterangan.getText().toString()  +"'  WHERE id_trip = '"+ idTrip + "'" ;
            Log.e("update sqlite ", "" + insertSql);
            db.execSQL(insertSql);
            db.close();

        }else{
            System.out.println("Insert ! ");
            SQLiteDatabase db = databaseHandler.getWritableDatabase();

            String insertSql = "INSERT INTO tb_etp_info ( id_trip , pewawancara , umur , lama_bekerja_tahun , lama_bekerja_bulan , jabatan , keterangan   ) VALUES " +
                    "(  '"+idTrip+"' ,  '"+ pewawancara.getText().toString()+"' , '"+ umur.getText().toString() +"' , '"+lama_bekerja_tahun.getText().toString()+"' , '"+lama_bekerja_bulan.getText().toString()+"' ,  '"+jabatan.getText().toString()+"' , '"+keterangan.getText().toString()+"')" ;

            db.execSQL(insertSql);

            db.close();
        }



    }

    private void saveAdd(){

        //String keyHewan , keyInteraksi , keyKode , keyInteraksiPerkiraan ,  keyDidaratkanPerkiraan , keyYakinSpecies , keyYakinLokal , keyAlatTangan , keyAlatKapal  ;
        //EditText editinteraksi_count , editdidaratkan_count , d1 , d2 , d3 ,d4, d5 , d6 , d7 , d8 , d9 , d10 , t1 , t2 , t3 , t4 , t5 ,  lokal , lokasi_rumpon , lokasi_perjalanan , lokasi_lainnya ,  sp_etp , sp_lain , alat_lain ;

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        System.out.println( idTrip + " " + keyHewan + " " + keyInteraksi + " " + editinteraksi_count.getText().toString() + " "  + keyInteraksiPerkiraan + " "  + editdidaratkan_count.getText().toString() + " " + keyDidaratkanPerkiraan + " " + d1.getText().toString() + " " + d2.getText().toString() + " " + d3.getText().toString() + " " + d4.getText().toString()+ " " + d5.getText().toString() + " " + d6.getText().toString() + " " + d7.getText().toString() + " " + d8.getText().toString() + " " + d9.getText().toString() + " " + d10.getText().toString() + " " + t1.getText().toString() + " " +t2.getText().toString() + " " + t3.getText().toString() + " " + t4.getText().toString() + " " + t5.getText().toString() + " " + keyKode + " " + keyYakinSpecies + " " + lokal.getText().toString() + " " + keyYakinLokal + " " + lokasi_rumpon.getText().toString() +  " " + lokasi_perjalanan.getText().toString() + " " + lokasi_lainnya.getText().toString() + " " + sp_etp.getText().toString() + " " + sp_lain.getText().toString() + " " + keyAlatTangan +  " " + keyAlatKapal +  " " + alat_lain.getText().toString() + " ");

        String insertSql ;

        if(!keyKode.equals("")) {
             insertSql = "INSERT INTO " + TABEL_ETP + " ( id_trip , hewan , interaksi , interaksi_count , interaksi_perkiraan , didaratkan_count , didaratkan_perkiraaan , d1 ,  d2 , d3 , d4 , d5 , d6 , d7 , d8 , d9 ,d10 , t1 , t2 , t3 , t4 , t5 , kode , yakin_species , lokal , yakin_lokal , lokasi_rumpon , lokasi_perjalanan , lokasi_lainnya , sp_etp , sp_lain , alat_tangan , alat_kapal , alat_lain  ) VALUES " +
                    "(  '" + idTrip + "' , '" + keyHewan + "' , '" + keyInteraksi + "' , '" + editinteraksi_count.getText().toString() + "' , '" + keyInteraksiPerkiraan + "' , '" + editdidaratkan_count.getText().toString() + "' , '" + keyDidaratkanPerkiraan + "' , '" + d1.getText().toString() + "' ,  '" + d2.getText().toString() + "' , '" + d3.getText().toString() + "' , '" + d4.getText().toString() + "' , '" + d5.getText().toString() + "' , '" + d6.getText().toString() + "' , '" + d7.getText().toString() + "' , '" + d8.getText().toString() + "' , '" + d9.getText().toString() + "' , '" + d10.getText().toString() + "' , '" + t1.getText().toString() + "', '" + t2.getText().toString() + "' , '" + t3.getText().toString() + "', '" + t4.getText().toString() + "' , '" + t5.getText().toString() + "' , '" + keyKode + "' , '" + keyYakinSpecies + "', '" + lokal.getText().toString() + "' , '" + keyYakinLokal + "' , '" + lokasi_rumpon.getText().toString() + "' , '" + lokasi_perjalanan.getText().toString() + "' , '" + lokasi_lainnya.getText().toString() + "' , '" + sp_etp.getText().toString() + "' , '" + sp_lain.getText().toString() + "' , '" + keyAlatTangan + "' , '" + keyAlatKapal + "' , '" + alat_lain.getText().toString() + "'  )";
        }else{
             insertSql = "INSERT INTO " + TABEL_ETP + " ( id_trip , hewan , interaksi , interaksi_count , interaksi_perkiraan , didaratkan_count , didaratkan_perkiraaan , d1 ,  d2 , d3 , d4 , d5 , d6 , d7 , d8 , d9 ,d10 , t1 , t2 , t3 , t4 , t5 , kode , yakin_species , lokal , yakin_lokal , lokasi_rumpon , lokasi_perjalanan , lokasi_lainnya , sp_etp , sp_lain , alat_tangan , alat_kapal , alat_lain  ) VALUES " +
                    "(  '" + idTrip + "' , '" + keyHewan + "' , '" + keyInteraksi + "' , '" + editinteraksi_count.getText().toString() + "' , '" + keyInteraksiPerkiraan + "' , '" + editdidaratkan_count.getText().toString() + "' , '" + keyDidaratkanPerkiraan + "' , '" + d1.getText().toString() + "' ,  '" + d2.getText().toString() + "' , '" + d3.getText().toString() + "' , '" + d4.getText().toString() + "' , '" + d5.getText().toString() + "' , '" + d6.getText().toString() + "' , '" + d7.getText().toString() + "' , '" + d8.getText().toString() + "' , '" + d9.getText().toString() + "' , '" + d10.getText().toString() + "' , '" + t1.getText().toString() + "', '" + t2.getText().toString() + "' , '" + t3.getText().toString() + "', '" + t4.getText().toString() + "' , '" + t5.getText().toString() + "' , '" + speciesCustom.getText().toString() + "' , '" + keyYakinSpecies + "', '" + lokal.getText().toString() + "' , '" + keyYakinLokal + "' , '" + lokasi_rumpon.getText().toString() + "' , '" + lokasi_perjalanan.getText().toString() + "' , '" + lokasi_lainnya.getText().toString() + "' , '" + sp_etp.getText().toString() + "' , '" + sp_lain.getText().toString() + "' , '" + keyAlatTangan + "' , '" + keyAlatKapal + "' , '" + alat_lain.getText().toString() + "'  )";

        }
            Log.e("update sqlite ", "" + insertSql);
        db.execSQL(insertSql);
        db.close();


    }


    private void saveUpdate(String id_trip , String idDb){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String insertSql ;

        if(!keyKode.equals("")) {
             insertSql = "UPDATE " + TABEL_ETP + " SET hewan = '" + keyHewan + "' , interaksi = '" + keyInteraksi + "' , interaksi_count = '" + editinteraksi_count.getText().toString() + "', interaksi_perkiraan = '" + keyInteraksiPerkiraan + "', didaratkan_count = '" + editdidaratkan_count.getText().toString() + "' ,  didaratkan_perkiraaan = '" + keyDidaratkanPerkiraan + "'  , d1 = '" + d1.getText().toString() + "' , d2 = '" + d2.getText().toString() + "' , d3 = '" + d3.getText().toString() + "' ,d4 = '" + d4.getText().toString() + "' ,d5 = '" + d5.getText().toString() + "' , d6 = '" + d6.getText().toString() + "' ,d7 = '" + d7.getText().toString() + "' ,d8 = '" + d8.getText().toString() + "' ,d9  = '" + d9.getText().toString() + "', d10 = '" + d10.getText().toString() + "' , t1 = '" + t1.getText().toString() + "' , t2 = '" + t2.getText().toString() + "' , t3 = '" + t3.getText().toString() + "' , t4 = '" + t4.getText().toString() + "' , t5 = '" + t5.getText().toString() + "' , kode = '" + keyKode + "' , yakin_species = '" + keyYakinSpecies + "', lokal = '" + lokal.getText().toString() + "', yakin_lokal= '" + keyYakinLokal + "' ,  lokasi_rumpon= '" + lokasi_rumpon.getText().toString() + "' ,lokasi_perjalanan = '" + lokasi_perjalanan.getText().toString() + "', lokasi_lainnya = '" + lokasi_lainnya.getText().toString() + "' ,sp_etp = '" + sp_etp.getText().toString() + "' , sp_lain = '" + sp_lain.getText().toString() + "' , alat_tangan = '" + keyAlatTangan + "' , alat_kapal= '" + keyAlatKapal + "' ,  alat_lain = '" + alat_lain.getText().toString() + "' WHERE id_trip = '" + id_trip + "' and id = '" + idDb + "'";
        }else{
             insertSql = "UPDATE " + TABEL_ETP + " SET hewan = '" + keyHewan + "' , interaksi = '" + keyInteraksi + "' , interaksi_count = '" + editinteraksi_count.getText().toString() + "', interaksi_perkiraan = '" + keyInteraksiPerkiraan + "', didaratkan_count = '" + editdidaratkan_count.getText().toString() + "' ,  didaratkan_perkiraaan = '" + keyDidaratkanPerkiraan + "'  , d1 = '" + d1.getText().toString() + "' , d2 = '" + d2.getText().toString() + "' , d3 = '" + d3.getText().toString() + "' ,d4 = '" + d4.getText().toString() + "' ,d5 = '" + d5.getText().toString() + "' , d6 = '" + d6.getText().toString() + "' ,d7 = '" + d7.getText().toString() + "' ,d8 = '" + d8.getText().toString() + "' ,d9  = '" + d9.getText().toString() + "', d10 = '" + d10.getText().toString() + "' , t1 = '" + t1.getText().toString() + "' , t2 = '" + t2.getText().toString() + "' , t3 = '" + t3.getText().toString() + "' , t4 = '" + t4.getText().toString() + "' , t5 = '" + t5.getText().toString() + "' , kode = '" + speciesCustom.getText().toString() + "' , yakin_species = '" + keyYakinSpecies + "', lokal = '" + lokal.getText().toString() + "', yakin_lokal= '" + keyYakinLokal + "' ,  lokasi_rumpon= '" + lokasi_rumpon.getText().toString() + "' ,lokasi_perjalanan = '" + lokasi_perjalanan.getText().toString() + "', lokasi_lainnya = '" + lokasi_lainnya.getText().toString() + "' ,sp_etp = '" + sp_etp.getText().toString() + "' , sp_lain = '" + sp_lain.getText().toString() + "' , alat_tangan = '" + keyAlatTangan + "' , alat_kapal= '" + keyAlatKapal + "' ,  alat_lain = '" + alat_lain.getText().toString() + "' WHERE id_trip = '" + id_trip + "' and id = '" + idDb + "'";

        }
            Log.e("update sqlite ", "" + insertSql);
        db.execSQL(insertSql);
        db.close();


    }



    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
