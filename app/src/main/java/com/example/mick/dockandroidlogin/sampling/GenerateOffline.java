package com.example.mick.dockandroidlogin.sampling;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.Login;
import com.example.mick.dockandroidlogin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import android.support.v4.app.Fragment;
//import android.app.Fragment;


/**
 * Created by Mick on 2/5/2018.
 */

public class GenerateOffline extends AppCompatActivity implements OnItemSelectedListener , View.OnClickListener {

    Spinner Slanding , Ssupplier , Stemplate;
    String databaseId ;
    Button btntanggal, btnwaktu;
    EditText txttanggal, txtwaktu , txt_notes;
    Button btn_submit, btn_cancel;
    int mYear , mMonth , mDay , mHour , mMinute ;
    String nTpi , nSupp , nTemplate;
    SharedPreferences sharedpreferences;
    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    String keyLanding , valLanding , keySupp , valSupp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_offline_code);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

        Stemplate = (Spinner) findViewById(R.id.templateTipe);
        String[] templateLists = new String[] { "HL", "PL" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, templateLists);
        Stemplate.setAdapter(adapter);
        Stemplate.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                nTemplate = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        Slanding = (Spinner) findViewById(R.id.landingSite);
        Ssupplier = (Spinner) findViewById(R.id.supplierSite);
        final String selectQuery = " SELECT  *  FROM  tb_master_tpi order by k_tpi " ;
        loadSpinnerDataDinamic(selectQuery , Slanding);
        Slanding.setOnItemSelectedListener(

                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {

                        Spinner spinner = (Spinner) parent;
                        keyLanding = ( (SpinnerObject) spinner.getSelectedItem () ).getId() ;
                        nTpi = keyLanding ;
                        String getFront = nTpi.substring(0,4);
                        valLanding = parent.getItemAtPosition(position).toString();


                        //call Perusahaan from Landing Data
                        String selectQuery2 = " SELECT  *   FROM  tb_master_perusahaan where k_perusahaan like '%"+ getFront +"%'  order by k_perusahaan";
                        System.out.println(selectQuery2);
                        loadSpinnerDataDinamicSupp(selectQuery2 , Ssupplier);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                }

        );

        Ssupplier.setOnItemSelectedListener(

                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {

                        Spinner spinner = (Spinner) parent;
                        keySupp = ( (SpinnerObject) spinner.getSelectedItem () ).getId() ;
                        nSupp = keySupp ;
                        valSupp = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                }



        );
        //loadSpinnerData("tb_master_tpi" , Slanding);
        //loadSpinnerData("tb_master_perusahaan" , Ssupplier);
        btntanggal = (Button) findViewById(R.id.btntanggal);
        btnwaktu = (Button) findViewById(R.id.btnwaktu);
        txttanggal = (EditText) findViewById(R.id.txttanggal);
        txttanggal.setEnabled(false);
        txttanggal.setTextColor(Color.BLACK);
        txtwaktu = (EditText) findViewById(R.id.txtwaktu);
        txtwaktu.setEnabled(false);
        txtwaktu.setTextColor(Color.BLACK);
        btntanggal.setOnClickListener(this);
        btnwaktu.setOnClickListener(this);
        txt_notes = (EditText) findViewById(R.id.txt_notes);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save();
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //blank();
                    finish();
                }
            });
    }

    private void save() {
        String  datesNow = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());
     /*
        System.out.println("N Template " + nTemplate);
        System.out.println("N TPI " + nTpi);
        System.out.println("N Supp " + nSupp);
        System.out.println("Notes " + txt_notes.getText().toString());
        System.out.println("date " + txttanggal.getText().toString());
        System.out.println("time " + txtwaktu.getText().toString());
        System.out.println("id_pengguna " +   sharedpreferences.getString("id", null) );
        System.out.println("now " + datesNow);
    */

        String getFirstCharA = nTpi;
        String getA = getFirstCharA.substring(0,4);
        String getFirstCharB = nSupp;
        String getB = getFirstCharB.substring(0,4);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String  dates = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String datesReq =  txttanggal.getText().toString() ;
        //if tanggal today tidak lebih besar daripada tanggal yang diketik
        Date dateToday = null , dateReq = null ;

        try {

             dateToday = formatter.parse(dates);
             dateReq = formatter.parse(datesReq);
            //System.out.println(dateReq);
            //System.out.println(formatter.format(dateReq));

        } catch (ParseException e) {
            e.printStackTrace();
        }




        if ( nTemplate.equals("") ||
                nTpi.equals("000000")  ||
                nSupp.equals("") ||
                txttanggal.getText().toString().equals("") ||
                txtwaktu.getText().toString().equals("")
                ) {
            Toast.makeText(getApplicationContext(),
                    "Please input value the empty value below!", Toast.LENGTH_SHORT).show();
        }
        else if( !getA.equals(getB) ){
            Toast.makeText(getApplicationContext(),
                    "Make Sure Landing site and Supplier are in same location!", Toast.LENGTH_SHORT).show();
        }
        else if(dateReq.compareTo(dateToday) > 0){
            Toast.makeText(getApplicationContext(),
                    "Make Sure the Date not tommorow!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Success Input to database!", Toast.LENGTH_SHORT).show();
            /*
            Fragment fragment =  new Trip();
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
            */
            databaseHandler.insertTriplists(nTpi , nSupp , txttanggal.getText().toString() , txtwaktu.getText().toString() , txt_notes.getText().toString() , nTemplate ,    datesNow , sharedpreferences.getString("id", null)  );
            databaseHandler.insertKualitas(nTpi , nSupp) ;


            Intent intent = new Intent(GenerateOffline.this, TripLists.class);
            finish();
            startActivity(intent);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        //String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        /*
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
        */

        /*
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.landingSite)
        {
            databaseId = ( (SpinnerObject) spinner.getSelectedItem () ).getId () ;
            nTpi = ( (SpinnerObject) spinner.getSelectedItem () ).getId () ;
            nTpi = keyLanding ;


        }
        else if(spinner.getId() == R.id.supplierSite)
        {
             databaseId = parent.getItemAtPosition(position).toString();
             nSupp = ( (SpinnerObject) spinner.getSelectedItem () ).getId () ;
        }
        */
        /* Toast.makeText(parent.getContext(), "You selected : " + databaseId,
                Toast.LENGTH_LONG).show(); */


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }



    private void loadSpinnerData(String table , Spinner spiner) {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<SpinnerObject> lables = db.getAllLabels(table);

        // Creating adapter for spinner
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spiner.setAdapter(dataAdapter);
    }

    private void loadSpinnerDataDinamic(String selectQuery , Spinner spiner) {

        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

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


    private void loadSpinnerDataDinamicSupp(String selectQuery , Spinner spiner) {

        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        labels.add(   new SpinnerObject (  "----" ,  "Pilih Supplier" )  );
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



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btntanggal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                txttanggal.setText( year  + "-" +   String.format( "%02d" , monthOfYear + 1)   + "-" + String.format("%02d", dayOfMonth)  );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btnwaktu:

                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                txtwaktu.setText(String.format( "%02d" , hourOfDay )  + ":" + String.format( "%02d" ,minute ) );
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
        }
    }




}
