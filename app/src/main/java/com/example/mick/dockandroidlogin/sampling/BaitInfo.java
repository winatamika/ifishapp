package com.example.mick.dockandroidlogin.sampling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
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
import com.example.mick.dockandroidlogin.adapter.AdapterBaitInfo;

import java.util.ArrayList;
import java.util.List;


public class BaitInfo extends AppCompatActivity implements OnItemSelectedListener  {

    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    String idTrip , tipeTemplate ;
    String TABEL_UMPAN = "tb_umpan" ;
    String[] species , kategori , total_kg , grid_a , grid_b  ;
    TextView notFound ;
    Intent i ;
    ListView lvBaitInfo;
    AdapterBaitInfo baitInfoAdapter ;
    Spinner SpinnerKategori , SpinnerSpecies , SpinnerGridA , SpinnerGridB , SpinnerGear , SpinnerDI;
    String keyKategori , keyGridA , keyGridB , keySpecies , keyGear , keyDI  ;
    EditText totalBait , estimasiBait , Pl_pengadaanUmpan , Pl_ember  , speciesCustom ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_bait_info);

         /* Get All Importtant Intent  */
        idTrip = getIntent().getStringExtra("idTrip");
        tipeTemplate = getIntent().getStringExtra("tipeTemplate");

        notFound = (TextView) findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);


        refreshListBait();


        //Button buttonAdd=(Button)findViewById(R.id.buttonAdd);
        FloatingActionButton buttonAdd = (FloatingActionButton)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showBaitDialog();
            }
        });



        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

    }


    private void refreshListBait(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_UMPAN  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        species = new String[cursor.getCount()];
        kategori = new String[cursor.getCount()];
        total_kg = new String[cursor.getCount()];
        grid_a = new String[cursor.getCount()];
        grid_b = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                kategori[cc] = cursor.getString(cursor.getColumnIndex("kategori"));
                species[cc] = cursor.getString(cursor.getColumnIndex("species"));
                total_kg[cc] = cursor.getString(cursor.getColumnIndex("total_kg"));
                grid_a[cc] = cursor.getString(cursor.getColumnIndex("grid_1"));
                grid_b[cc] = cursor.getString(cursor.getColumnIndex("grid_2"));

                notFound.setVisibility(View.INVISIBLE);
            }


        }else{
            notFound.setVisibility(View.VISIBLE);
        }

        lvBaitInfo = (ListView) findViewById(R.id.baitInfo);
        baitInfoAdapter = new AdapterBaitInfo(this,kategori , species , total_kg ,  grid_a, grid_b);
        lvBaitInfo.setAdapter(baitInfoAdapter);


        lvBaitInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {  "Update" , "Delete"  };
                AlertDialog.Builder dialog = new AlertDialog.Builder(BaitInfo.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                System.out.println("Do Update "+ idTrip + " " + kategori[position] + " " + species[position] );
                                showBaitDialogUpdate(idTrip ,kategori[position] ,  species[position]  );

                                break;
                            case 1:

                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_UMPAN + " WHERE id_trip = '" + idTrip + "' and kategori = '" + kategori[position] + "' and species = '" + species[position] + "'  ");
                                db.close();

                                refreshListBait();


                                break;

                        }
                    }
                }).show();

                return true;
            }

        }) ;

    }


    private void showBaitDialogUpdate(final String idTrip , final String Kategori , final String Species) {

        LayoutInflater layoutInflater = LayoutInflater.from(BaitInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_bait_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaitInfo.this);
        alertDialogBuilder.setTitle("BaitInfo Update");
        alertDialogBuilder.setView(promptView);

        totalBait = (EditText) promptView.findViewById(R.id.totalBait);
        estimasiBait = (EditText) promptView.findViewById(R.id.estimasiBait);
        Pl_pengadaanUmpan = (EditText) promptView.findViewById(R.id.Pl_pengadaanUmpan);
        Pl_ember = (EditText) promptView.findViewById(R.id.Pl_ember);

        SpinnerKategori = (Spinner) promptView.findViewById(R.id.kategori);
        SpinnerSpecies = (Spinner) promptView.findViewById(R.id.species);
        SpinnerKategori.setOnItemSelectedListener(this);
        String selectQuery1;
        if (tipeTemplate.equals("HL")) {
            selectQuery1 = " SELECT  DISTINCT(category) AS category  FROM  tb_master_baits where category in ('A' , 'B' , 'C' , 'D' , 'E', 'F' , 'G') order by category";
        } else {
            selectQuery1 = " SELECT  DISTINCT(category) AS category  FROM  tb_master_baits where category in ('T' , 'U' , 'V' , 'W' , 'X', 'Y' , 'Z') order by category";
        }
        loadSpinnerData(selectQuery1, SpinnerKategori);
        SpinnerKategori.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyKategori = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getApplicationContext(),
                        keyKategori, Toast.LENGTH_SHORT).show();*/


                String selectQuery2 = " SELECT  scientific_name  FROM  tb_master_baits where category = '" + keyKategori + "' order by scientific_name";
                loadSpinnerData(selectQuery2, SpinnerSpecies);
                SpinnerSpecies.setSelection(getIndex(SpinnerSpecies, Species  ));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        speciesCustom = (EditText) promptView.findViewById(R.id.speciesCustom);
        speciesCustom.setEnabled(false);
        speciesCustom.setTextColor(Color.BLACK);


        SpinnerSpecies.setOnItemSelectedListener(this);
        SpinnerSpecies.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keySpecies = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getApplicationContext(),
                        keySpecies, Toast.LENGTH_SHORT).show(); */


                if(keySpecies.equals("")) {
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


        SpinnerGear = (Spinner) promptView.findViewById(R.id.gear);
        SpinnerGear.setOnItemSelectedListener(this);
        String selectQuery3 = " SELECT  indonesia  FROM  tb_master_gear where status = 'bait_gear' order by indonesia";

        loadSpinnerData(selectQuery3, SpinnerGear);
        SpinnerGear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyGear = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getApplicationContext(),
                        keyGear, Toast.LENGTH_SHORT).show(); */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerGridA = (Spinner) promptView.findViewById(R.id.grid_a);
        SpinnerGridA.setOnItemSelectedListener(this);
        String[] gridA_lists = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gridA_lists);
        SpinnerGridA.setAdapter(adapter1);
        SpinnerGridA.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyGridA = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyGridA, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerGridB = (Spinner) promptView.findViewById(R.id.grid_b);
        SpinnerGridB.setOnItemSelectedListener(this);
        String[] gridB_lists = new String[50];
        for (int i = 0; i < 50; i++) {
            gridB_lists[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gridB_lists);
        SpinnerGridB.setAdapter(adapter2);
        SpinnerGridB.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyGridB = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyGridB, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerDI = (Spinner) promptView.findViewById(R.id.hlDomesticImpor);
        SpinnerDI.setOnItemSelectedListener(this);
        String[] DI = new String[]{"D", "I"};

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, DI);
        SpinnerDI.setAdapter(adapter3);
        SpinnerDI.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyDI = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyDI, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        if (tipeTemplate.equals("HL")) {

            TextView txtViewPlPengadaanUmpan = promptView.findViewById(R.id.txtViewPlPengadaanUmpan);
            txtViewPlPengadaanUmpan.setVisibility(View.GONE);
            TextView txtViewPlEmber = promptView.findViewById(R.id.txtViewPlEmber);
            txtViewPlEmber.setVisibility(View.GONE);

            EditText Pl_pengadaanUmpan = promptView.findViewById(R.id.Pl_pengadaanUmpan);
            Pl_pengadaanUmpan.setVisibility(View.GONE);
            EditText Pl_ember = promptView.findViewById(R.id.Pl_ember);
            Pl_ember.setVisibility(View.GONE);


        } else if (tipeTemplate.equals("PL")) {

            TextView txtViewHlDomesticImpor = promptView.findViewById(R.id.txtViewHlDomesticImpor);
            txtViewHlDomesticImpor.setVisibility(View.GONE);

            SpinnerDI.setVisibility(View.GONE);
            keyDI = "None";
        }


        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_UMPAN + " where id_trip = '" + idTrip + "' and kategori = '" + Kategori + "' and species = '" + Species + "' ", null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    SpinnerKategori.setSelection(getIndex(SpinnerKategori, cursor.getString(1)));
                    System.out.println(Species) ;
                    SpinnerSpecies.setSelection(getIndex(SpinnerSpecies, Species ));
                    speciesCustom.setText(Species);
                    SpinnerGridA.setSelection(getIndex(SpinnerGridA, cursor.getString(3)));
                    SpinnerGridB.setSelection(getIndex(SpinnerGridB, cursor.getString(4)));
                    totalBait.setText(cursor.getString(5));
                    estimasiBait.setText(cursor.getString(6));
                    SpinnerGear.setSelection(getIndex(SpinnerGear, cursor.getString(7)));
                    SpinnerDI.setSelection(getIndex(SpinnerDI, cursor.getString(8)));
                    Pl_pengadaanUmpan.setText(cursor.getString(9));
                    Pl_ember.setText(cursor.getString(10));


                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveUpdate(idTrip , Kategori , Species);
                            refreshListBait();
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


    private void showBaitDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(BaitInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_bait_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaitInfo.this);
        alertDialogBuilder.setTitle("BaitInfo");
        alertDialogBuilder.setView(promptView);

        totalBait = (EditText) promptView.findViewById(R.id.totalBait);
        estimasiBait = (EditText) promptView.findViewById(R.id.estimasiBait);
        Pl_pengadaanUmpan = (EditText) promptView.findViewById(R.id.Pl_pengadaanUmpan);
        Pl_ember = (EditText) promptView.findViewById(R.id.Pl_ember);

        SpinnerKategori = (Spinner) promptView.findViewById(R.id.kategori);
        SpinnerSpecies = (Spinner) promptView.findViewById(R.id.species);
        SpinnerKategori.setOnItemSelectedListener(this);
        String selectQuery1 ;
        if(tipeTemplate.equals("HL")) {
             selectQuery1 = " SELECT  DISTINCT(category) AS category  FROM  tb_master_baits where category in ('A' , 'B' , 'C' , 'D' , 'E', 'F' , 'G') order by category";
        }else{
             selectQuery1 = " SELECT  DISTINCT(category) AS category  FROM  tb_master_baits where category in ('T' , 'U' , 'V' , 'W' , 'X', 'Y' , 'Z') order by category";
        }
        loadSpinnerData(selectQuery1 , SpinnerKategori);
        SpinnerKategori.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyKategori = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getApplicationContext(),
                        keyKategori , Toast.LENGTH_SHORT).show(); */


                String selectQuery2 = " SELECT  scientific_name  FROM  tb_master_baits where category = '"+ keyKategori +"' order by scientific_name";
                loadSpinnerData(selectQuery2 , SpinnerSpecies);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        speciesCustom = (EditText) promptView.findViewById(R.id.speciesCustom);
        speciesCustom.setEnabled(false);
        speciesCustom.setTextColor(Color.BLACK);

        SpinnerSpecies.setOnItemSelectedListener(this);
        SpinnerSpecies.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keySpecies = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getApplicationContext(),
                        keySpecies , Toast.LENGTH_SHORT).show();*/
                if(keySpecies.equals("")) {
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


        SpinnerGear = (Spinner) promptView.findViewById(R.id.gear);
        SpinnerGear.setOnItemSelectedListener(this);
        String selectQuery3 = " SELECT  indonesia  FROM  tb_master_gear where status = 'bait_gear' order by indonesia";

        loadSpinnerData(selectQuery3 , SpinnerGear);
        SpinnerGear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyGear = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getApplicationContext(),
                        keyGear , Toast.LENGTH_SHORT).show(); */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerGridA = (Spinner) promptView.findViewById(R.id.grid_a);
        SpinnerGridA.setOnItemSelectedListener(this);
        String[] gridA_lists = new String[] { "" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "I" , "J" , "K" , "L", "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" , "U" , "V" , "W" , "X" , "Y" , "Z"  };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gridA_lists);
        SpinnerGridA.setAdapter(adapter1);
        SpinnerGridA.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyGridA = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyGridA , Toast.LENGTH_SHORT).show(); */


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerGridB = (Spinner) promptView.findViewById(R.id.grid_b);
        SpinnerGridB.setOnItemSelectedListener(this);
        String[] gridB_lists =  new String[50]  ;
        for (int i=0 ; i<50; i++){
            gridB_lists[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gridB_lists);
        SpinnerGridB.setAdapter(adapter2);
        SpinnerGridB.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyGridB = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyGridB , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerDI = (Spinner) promptView.findViewById(R.id.hlDomesticImpor);
        SpinnerDI.setOnItemSelectedListener(this);
        String[] DI = new String[] { "" , "D" , "I"  };

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, DI);
        SpinnerDI.setAdapter(adapter3);
        SpinnerDI.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyDI = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyDI , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        if(tipeTemplate.equals("HL")){

            TextView txtViewPlPengadaanUmpan = promptView.findViewById(R.id.txtViewPlPengadaanUmpan);
            txtViewPlPengadaanUmpan.setVisibility(View.GONE);
            TextView txtViewPlEmber = promptView.findViewById(R.id.txtViewPlEmber);
            txtViewPlEmber.setVisibility(View.GONE);

            EditText Pl_pengadaanUmpan = promptView.findViewById(R.id.Pl_pengadaanUmpan);
            Pl_pengadaanUmpan.setVisibility(View.GONE);
            EditText Pl_ember = promptView.findViewById(R.id.Pl_ember);
            Pl_ember.setVisibility(View.GONE);


        }else if(tipeTemplate.equals("PL")){

            TextView txtViewHlDomesticImpor = promptView.findViewById(R.id.txtViewHlDomesticImpor);
            txtViewHlDomesticImpor.setVisibility(View.GONE);

            SpinnerDI.setVisibility(View.GONE);
            keyDI = "None";
        }


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveAdd();
                        refreshListBait();
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

    private void saveAdd(){


        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String insertSql ;
        if(!keySpecies.equals("")){
             insertSql = "INSERT INTO " + TABEL_UMPAN + " ( id_trip , kategori , species , grid_1 , grid_2 , total_kg , estimasi_kg , alat_tangkap ,  hl_domestic_import , pl_pengadaan , pl_ember  ) VALUES ('"+ idTrip +"' , '" + keyKategori + "' , '" + keySpecies + "' , '" + keyGridA + "' , '" + keyGridB+ "' , '" + totalBait.getText().toString() + "' , '" + estimasiBait.getText().toString() + "' , '" + keyGear + "' , '" + keyDI  + "' , '" + Pl_pengadaanUmpan.getText().toString() + "' , '" + Pl_ember.getText().toString()  + "' )" ;

        }else{
             insertSql = "INSERT INTO " + TABEL_UMPAN + " ( id_trip , kategori , species , grid_1 , grid_2 , total_kg , estimasi_kg , alat_tangkap ,  hl_domestic_import , pl_pengadaan , pl_ember  ) VALUES ('"+ idTrip +"' , '" + keyKategori + "' , '" + speciesCustom.getText().toString() + "' , '" + keyGridA + "' , '" + keyGridB+ "' , '" + totalBait.getText().toString() + "' , '" + estimasiBait.getText().toString() + "' , '" + keyGear + "' , '" + keyDI  + "' , '" + Pl_pengadaanUmpan.getText().toString() + "' , '" + Pl_ember.getText().toString()  + "' )" ;

        }
           Log.e("update sqlite ", "" + insertSql);
        try {
            db.execSQL(insertSql);
            Toast.makeText(getApplicationContext(),
                    "Data Herhasil Submit! ", Toast.LENGTH_SHORT).show();
        }
        catch(SQLException e){

            if(e instanceof SQLiteConstraintException){
                Toast.makeText(getApplicationContext(),
                        "Data Sudah pernah dimasukkan!", Toast.LENGTH_SHORT).show();

            }else if(e instanceof SQLiteDatatypeMismatchException) {
                Toast.makeText(getApplicationContext(),
                        "Kesalahan Tipe Data!", Toast.LENGTH_SHORT).show();
            }else{
                throw e;
            }
                System.out.println(e);

        }
        db.close();

    }

    private void saveUpdate(String idTrip , String Kategori , String Species ){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String insertSql ;
        if(!keySpecies.equals("")) {
            insertSql = "UPDATE " + TABEL_UMPAN + " SET kategori = '" + keyKategori + "' ,  species = '" + keySpecies + "' , grid_1 = '" + keyGridA + "' , grid_2 = '" + keyGridB + "'  , total_kg = '" + totalBait.getText().toString() + "' , estimasi_kg = '" + estimasiBait.getText().toString() + "'  , alat_tangkap = '" + keyGear + "' ,  hl_domestic_import = '" + keyDI + "' , pl_pengadaan = '" + Pl_pengadaanUmpan.getText().toString() + "' , pl_ember = '" + Pl_ember.getText().toString() + "' WHERE id_trip = '" + idTrip + "' and kategori = '" + Kategori + "' and species = '" + Species + "' ";
        }else{
            insertSql = "UPDATE " + TABEL_UMPAN + " SET kategori = '" + keyKategori + "' ,  species = '" + speciesCustom.getText().toString() + "' , grid_1 = '" + keyGridA + "' , grid_2 = '" + keyGridB + "'  , total_kg = '" + totalBait.getText().toString() + "' , estimasi_kg = '" + estimasiBait.getText().toString() + "'  , alat_tangkap = '" + keyGear + "' ,  hl_domestic_import = '" + keyDI + "' , pl_pengadaan = '" + Pl_pengadaanUmpan.getText().toString() + "' , pl_ember = '" + Pl_ember.getText().toString() + "' WHERE id_trip = '" + idTrip + "' and kategori = '" + Kategori + "' and species = '" + Species + "' ";
        }
        Log.e("update sqlite ", "" + insertSql);
        try {
            db.execSQL(insertSql);
            Toast.makeText(getApplicationContext(),
                    "Data Berhasil Masuk !", Toast.LENGTH_SHORT).show();
        }
        catch(SQLException e){

            if(e instanceof SQLiteConstraintException){
                Toast.makeText(getApplicationContext(),
                        "Data Sudah pernah dimasukkan!", Toast.LENGTH_SHORT).show();

            }else if(e instanceof SQLiteDatatypeMismatchException) {
                Toast.makeText(getApplicationContext(),
                        "Kesalahan Tipe Data!", Toast.LENGTH_SHORT).show();
            }else{
                throw e;
            }
            System.out.println(e);

        }

        db.close();

    }

    private void loadSpinnerData(String selectQuery , Spinner spiner) {

        List<String> labels = new ArrayList<String>();

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        labels.add( "" );
        if (cursor.moveToFirst()) {
            do {
                labels.add(   cursor.getString(0) );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
