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
import com.example.mick.dockandroidlogin.adapter.AdapterBycatchInfo;

import java.util.ArrayList;
import java.util.List;

public class BycatchInfo extends AppCompatActivity implements OnItemSelectedListener {

    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    String idTrip , tipeTemplate ;
    String TABEL_BYCATCH = "tb_bycatch" ;
    String[] species , ekor , total_kg , estimasi  ;
    TextView notFound ;
    Intent i ;
    ListView lvByatchInfo;
    AdapterBycatchInfo bycatchInfoAdapter ;
    Spinner  SpinnerSpecies , SpinnerEst ;
    String  keySpecies , keyEst  ;
    EditText textEkor , textTotalKg , speciesCustom  ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_bycatch_info);

         /* Get All Importtant Intent  */
        idTrip = getIntent().getStringExtra("idTrip");
        tipeTemplate = getIntent().getStringExtra("tipeTemplate");

        notFound = (TextView) findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);


        refreshListBycatch();


        //Button buttonAdd=(Button)findViewById(R.id.buttonAdd);
        FloatingActionButton buttonAdd = (FloatingActionButton)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showBycatchDialog();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


    }


    private void refreshListBycatch(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_BYCATCH  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        species = new String[cursor.getCount()];
        ekor = new String[cursor.getCount()];
        total_kg = new String[cursor.getCount()];
        estimasi = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                species[cc] = cursor.getString(cursor.getColumnIndex("species"));
                total_kg[cc] = cursor.getString(cursor.getColumnIndex("total_kg"));
                ekor[cc] = cursor.getString(cursor.getColumnIndex("ekor"));
                estimasi[cc] = cursor.getString(cursor.getColumnIndex("estimasi"));

                notFound.setVisibility(View.INVISIBLE);
            }


        }else{
            notFound.setVisibility(View.VISIBLE);
        }

        lvByatchInfo = (ListView) findViewById(R.id.bycatchInfo);
        bycatchInfoAdapter = new AdapterBycatchInfo(this,  species , ekor , total_kg ,  estimasi );
        lvByatchInfo.setAdapter(bycatchInfoAdapter);


        lvByatchInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {  "Update" , "Delete"  };
                AlertDialog.Builder dialog = new AlertDialog.Builder(BycatchInfo.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                System.out.println("Do Update "+ idTrip + " " + species[position] + " " + ekor[position] +  " " + total_kg[position] + " " + estimasi[position] );
                                showBycatchDialogUpdate(idTrip ,species[position]  );

                                break;
                            case 1:

                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_BYCATCH + " WHERE id_trip = '" + idTrip + "' and species = '" + species[position] + "'  ");
                                db.close();

                                refreshListBycatch();


                                break;

                        }
                    }
                }).show();

                return true;
            }

        }) ;


    }

    private void showBycatchDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(BycatchInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_bycatch_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BycatchInfo.this);
        alertDialogBuilder.setTitle("BycatchInfo");
        alertDialogBuilder.setView(promptView);

        textEkor = (EditText) promptView.findViewById(R.id.textEkor);
        textTotalKg =  (EditText) promptView.findViewById(R.id.textTotalKg);


        speciesCustom = (EditText) promptView.findViewById(R.id.speciesCustom);
        speciesCustom.setEnabled(false);
        speciesCustom.setTextColor(Color.BLACK);



        SpinnerSpecies = (Spinner) promptView.findViewById(R.id.species);
        String selectQuery1 ;
            selectQuery1 = " SELECT  species_name  FROM  tb_master_species  where tipe = 'bycatch'  order by species_name";

        loadSpinnerData(selectQuery1 , SpinnerSpecies);
        SpinnerSpecies.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keySpecies = parent.getItemAtPosition(position).toString();
                /*Toast.makeText(getApplicationContext(),
                        keySpecies , Toast.LENGTH_SHORT).show(); */

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

        SpinnerEst = (Spinner) promptView.findViewById(R.id.estimasi);
        SpinnerEst.setOnItemSelectedListener(this);
        String[] Est_lists = new String[] { "" , "Ya" , "Tidak" };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Est_lists);
        SpinnerEst.setAdapter(adapter1);
        SpinnerEst.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyEst = (String) parent.getItemAtPosition(position);

                /*Toast.makeText(getApplicationContext(),
                        keyEst , Toast.LENGTH_SHORT).show(); */
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
                        refreshListBycatch();
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

    private void showBycatchDialogUpdate(final String id_Trip  , final String Species ) {

        LayoutInflater layoutInflater = LayoutInflater.from(BycatchInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_bycatch_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BycatchInfo.this);
        alertDialogBuilder.setTitle("BycatchInfo");
        alertDialogBuilder.setView(promptView);

        textEkor = (EditText) promptView.findViewById(R.id.textEkor);
        textTotalKg = (EditText) promptView.findViewById(R.id.textTotalKg);


        speciesCustom = (EditText) promptView.findViewById(R.id.speciesCustom);
        speciesCustom.setEnabled(false);
        speciesCustom.setTextColor(Color.BLACK);


        SpinnerSpecies = (Spinner) promptView.findViewById(R.id.species);
        String selectQuery1;
        selectQuery1 = " SELECT  species_name  FROM  tb_master_species  where tipe = 'bycatch'  order by species_name";

        loadSpinnerData(selectQuery1, SpinnerSpecies);
        SpinnerSpecies.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keySpecies = parent.getItemAtPosition(position).toString();
                /*Toast.makeText(getApplicationContext(),
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

        SpinnerEst = (Spinner) promptView.findViewById(R.id.estimasi);
        SpinnerEst.setOnItemSelectedListener(this);
        String[] Est_lists = new String[]{"Ya", "Tidak"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Est_lists);
        SpinnerEst.setAdapter(adapter1);
        SpinnerEst.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyEst = (String) parent.getItemAtPosition(position);

                /*Toast.makeText(getApplicationContext(),
                        keyEst, Toast.LENGTH_SHORT).show(); */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_BYCATCH + " where id_trip = '" + id_Trip + "' and species = '" + Species + "' ", null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    SpinnerSpecies.setSelection(getIndex(SpinnerSpecies, cursor.getString(1)));
                    speciesCustom.setText(cursor.getString(1));
                    textEkor.setText(cursor.getString(2));
                    textTotalKg.setText(cursor.getString(3));
                    SpinnerEst.setSelection(getIndex(SpinnerEst, cursor.getString(4)));


                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveUpdate(id_Trip, Species);
                            refreshListBycatch();
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

    private void saveAdd(){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String insertSql ;
        if(!keySpecies.equals("")) {
            insertSql = "INSERT INTO " + TABEL_BYCATCH + " ( id_trip , species , ekor , total_kg , estimasi  ) VALUES ('" + idTrip + "' , '" + keySpecies + "' , '" + textEkor.getText().toString() + "' , '" + textTotalKg.getText().toString() + "' , '" + keyEst + "' )";
        }else{
            insertSql = "INSERT INTO " + TABEL_BYCATCH + " ( id_trip , species , ekor , total_kg , estimasi  ) VALUES ('" + idTrip + "' , '" + speciesCustom.getText().toString() + "' , '" + textEkor.getText().toString() + "' , '" + textTotalKg.getText().toString() + "' , '" + keyEst + "' )";
        }
            Log.e("update sqlite ", "" + insertSql);
        try {
            db.execSQL(insertSql);
        }  catch(SQLException e){

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

    private void saveUpdate(String id_Trip , String Species){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String insertSql ;
        if(!keySpecies.equals("")) {
             insertSql = "UPDATE " + TABEL_BYCATCH + " SET  species = '" + keySpecies + "' , ekor = '" + textEkor.getText().toString() + "' , total_kg = '" + textTotalKg.getText().toString() + "'  , estimasi = '" + keyEst + "' WHERE id_trip = '" + id_Trip + "' and species = '" + Species + "' ";
        }else{
            insertSql = "UPDATE " + TABEL_BYCATCH + " SET  species = '" + speciesCustom.getText().toString() + "' , ekor = '" + textEkor.getText().toString() + "' , total_kg = '" + textTotalKg.getText().toString() + "'  , estimasi = '" + keyEst + "' WHERE id_trip = '" + id_Trip + "' and species = '" + Species + "' ";

        }
            Log.e("update sqlite ", "" + insertSql);

        try {
            db.execSQL(insertSql);
            Toast.makeText(getApplicationContext(),
                    "Data Berhasil Masuk !", Toast.LENGTH_SHORT).show();
        }catch(SQLException e){

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
        labels.add("");
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
