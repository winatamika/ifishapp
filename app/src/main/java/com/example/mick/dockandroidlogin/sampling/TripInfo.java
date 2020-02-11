package com.example.mick.dockandroidlogin.sampling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.AdaptercatchAreaAdapter;

import java.util.ArrayList;
import java.util.List;


public class TripInfo extends AppCompatActivity implements OnItemSelectedListener  {
    /* Inisialisasi public */
    TextView textViewVicValue , txtViewVesselLeng ,  txtViewVesselGt , txtViewVesselPk , txtViewVesselMaterial , txtViewHookType , txtViewUsingHandlineTroll , txtViewOtherGear , txtViewJumlahPancing , txtVieBucketCapacity , txtViewGearType , nama_tpi , nama_perusahaan , datetime   ;
    EditText vesselName , captainName , vesselLength , gt , pk , vesselMaterial  , noOfCrew ,   totalCatch , totalLost , longTrip  , bbm , fishingDays , ice  , handlineTroll , otherGear ,  jumlahPancing , bucketCapacity , notes , enumerator1 , enumerator2 , daya_cahaya , kedalaman_min , kedalaman_max , jum_palka , kapasitas_palka , jum_rumpon ;
    Spinner vesselList , satuanTrip ,  rumpon , hookType , gearType , Spinergrid_a , Spinergrid_b , Spinerteknik , tlc , st , flywire , pds , gps, gps_merk ,kapal_andon , asal_andon ,ft_name   ;
    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    String idTrip , tipeTemplate , k_tpi , k_perusahaan , n_tpi , n_perusahaan , waktu , jam;
    String key , val , vic , vesselNameSpinner , satuanSpinner ,  rumponSpiner , hookTypeSpiner , gearTypeSpiner, gearNameSpinner , tlcSpinner , stSpinner , flywireSpinner ,pdsSpinner , gpsSpinner , gpsMerkSpinner , kapalAddonSpinner, asalAddonSpinner, ftNameSpinner  ;
    Button btn_submit, btn_cancel;
    String TABEL_TRIP_INFO = "tb_trip_info" ;
    String TABEL_TRIP_INFO_CATCHAREA = "tb_trip_info_catchArea" ;
    String TABEL_TRIP_INFO_TUNALCATING = "tb_trip_info_tunaLocating" ;
    Boolean isExsist ;
    Intent i ;

    ListView lvcatchArea , lvtunaLocation ;
    TextView notFoundTunaLocation , notFoundCatchArea  ;
    AdaptercatchAreaAdapter catchAreaAdapter ;
    String[] grid_a , grid_b , teknik ;
    String keyA , keyB , keyTeknik ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_trip_info);

        /* HL Properties*/
        txtViewHookType  = (TextView)findViewById(R.id.txtViewHookType);
        txtViewUsingHandlineTroll = (TextView)findViewById(R.id.txtViewUsingHandlineTroll);
        txtViewOtherGear = (TextView)findViewById(R.id.txtViewOtherGear);

        /* PL Properties*/
        txtViewJumlahPancing = (TextView)findViewById(R.id.txtViewJumlahPancing);
        txtVieBucketCapacity = (TextView)findViewById(R.id.txtViewBucketCapacity);

        /* All Properties */
        vesselName      = (EditText)findViewById(R.id.vesselName);
        captainName    = (EditText)findViewById(R.id.captainName);
        vesselLength   = (EditText)findViewById(R.id.vesselLength);
        gt             = (EditText)findViewById(R.id.gt);
        pk              = (EditText)findViewById(R.id.pk);
        vesselMaterial  = (EditText)findViewById(R.id.vesselMaterial);
        noOfCrew        = (EditText)findViewById(R.id.noOfCrew);
        totalCatch      = (EditText)findViewById(R.id.totalCatch);
        totalLost       = (EditText)findViewById(R.id.totalLost);
        longTrip        = (EditText)findViewById(R.id.longTrip);
        bbm             = (EditText)findViewById(R.id.bbm);
        fishingDays     = (EditText)findViewById(R.id.fishingDays);
        ice             = (EditText)findViewById(R.id.ice);
        handlineTroll   = (EditText)findViewById(R.id.handlineTroll);
        otherGear       = (EditText)findViewById(R.id.otherGear);
        jumlahPancing   = (EditText)findViewById(R.id.jumlahPancing);
        bucketCapacity  = (EditText)findViewById(R.id.bucketCapacity);
        textViewVicValue = (TextView) findViewById(R.id.textViewVicValue);
        notes           = (EditText)findViewById(R.id.notes);
        enumerator1     = (EditText)findViewById(R.id.enumerator1);
        enumerator2     = (EditText)findViewById(R.id.enumerator2);

        daya_cahaya      = (EditText)findViewById(R.id.daya_cahaya);
        kedalaman_min      = (EditText)findViewById(R.id.kedalaman_min);
        kedalaman_max      = (EditText)findViewById(R.id.kedalaman_max);
        jum_palka      = (EditText)findViewById(R.id.jum_palka);
        jum_rumpon      = (EditText)findViewById(R.id.jum_rumpon);
        kapasitas_palka  = (EditText)findViewById(R.id.kapasitas_palka);



         /* Get All Importtant Intent  */
        idTrip = getIntent().getStringExtra("idTrip");
        tipeTemplate = getIntent().getStringExtra("tipeTemplate");
        n_tpi = getIntent().getStringExtra("n_tpi");
        n_perusahaan = getIntent().getStringExtra("n_perusahaan");


        /* select data dari tb_trip untuk mendapatkan data k_tpi , k_perusahaan */
            SQLiteDatabase db = databaseHandler.getReadableDatabase();
            Cursor cursor = db.rawQuery("select k_tpi , k_perusahaan , waktu , jam  from tb_trip_lists where id = '" + idTrip+ "'", null);
            if (cursor.moveToFirst()) {
                do {
                    k_tpi = cursor.getString(0) ;
                    k_perusahaan = cursor.getString(1);
                    waktu = cursor.getString(2);
                    jam = cursor.getString(3);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        nama_tpi  = (TextView)findViewById(R.id.nama_tpi);
            nama_tpi.setText(n_tpi);
        nama_perusahaan  = (TextView)findViewById(R.id.nama_perusahaan);
            nama_perusahaan.setText(n_perusahaan);
        datetime  = (TextView)findViewById(R.id.datetime);
            datetime.setText(waktu +" "+jam);



        /* Assignt Value for Spinner vessel , rumpon , hook , gear  */
                vesselList = (Spinner) findViewById(R.id.vesselList);
                vesselList.setOnItemSelectedListener(this);
                String selectQuery = " SELECT  vic , nama_kapal  FROM  tb_master_vessels where k_tpi = '" + k_tpi +"' and k_perusahaan = '"+ k_perusahaan +"' order by nama_kapal";
                loadSpinnerDataVessel(selectQuery , vesselList);
                vesselList.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            Spinner spinner = (Spinner) parent;

                                key = ( (SpinnerObject) spinner.getSelectedItem () ).getId() ;
                                vic  = ( (SpinnerObject) spinner.getSelectedItem () ).getId () ;
                                vesselNameSpinner =  parent.getItemAtPosition(position).toString();
                                val = parent.getItemAtPosition(position).toString();
                                textViewVicValue.setText(vic);
                                setVicData(key);
                                /*Toast.makeText(getApplicationContext(),
                                    key + " "+ val, Toast.LENGTH_SHORT).show();*/

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });


                rumpon = (Spinner) findViewById(R.id.rumpon);
                rumpon.setOnItemSelectedListener(this);
                String[] rumponList = new String[] { "F", "X" , "N" };
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, rumponList);
                rumpon.setAdapter(adapter1);
                rumpon.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        key = (String) parent.getItemAtPosition(position);
                        rumponSpiner  = (String) parent.getItemAtPosition(position);
                       /* Toast.makeText(getApplicationContext(),
                                key , Toast.LENGTH_SHORT).show(); */
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });




                hookType = (Spinner) findViewById(R.id.hookType);
                hookType.setOnItemSelectedListener(this);
                String[] hookList = new String[] { "" , "Satu", "Banyak" , "Campuran" };
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, hookList);
                hookType.setAdapter(adapter2);
                hookType.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        key = (String) parent.getItemAtPosition(position);
                        hookTypeSpiner  = (String) parent.getItemAtPosition(position);

                        /* Toast.makeText(getApplicationContext(),
                                key , Toast.LENGTH_SHORT).show(); */
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });


                satuanTrip = (Spinner) findViewById(R.id.satuanTrip);
                satuanTrip.setOnItemSelectedListener(this);
                String[] satuanList = new String[] { "Hari", "Jam"  };
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, satuanList);
                satuanTrip.setAdapter(adapter3);
                satuanTrip.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        key = (String) parent.getItemAtPosition(position);
                        satuanSpinner  = (String) parent.getItemAtPosition(position);
                        /* Toast.makeText(getApplicationContext(),
                                key , Toast.LENGTH_SHORT).show(); */
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });

                gearType = (Spinner) findViewById(R.id.gearType);
                gearType.setOnItemSelectedListener(this);
                String selectQuery2 = " SELECT  k_alattangkap , english  FROM  tb_master_gear where status = 'trip_gear'";
                loadSpinnerData(selectQuery2 , gearType);
                gearType.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        Spinner spinner = (Spinner) parent;

                        key = ( (SpinnerObject) spinner.getSelectedItem () ).getId () ;
                        gearTypeSpiner = ( (SpinnerObject) spinner.getSelectedItem () ).getId () ;
                        gearNameSpinner = parent.getItemAtPosition(position).toString();
                        val =parent.getItemAtPosition(position).toString();
                        /*  Toast.makeText(getApplicationContext(),
                                key + " "+ val, Toast.LENGTH_SHORT).show(); */

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });


            tlc = (Spinner) findViewById(R.id.tlc);
            tlc.setOnItemSelectedListener(this);
            String[] satuanYesNo = new String[] { "Tidak" , "Ya"  };
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, satuanYesNo);
            tlc.setAdapter(adapter4);
            tlc.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    key = (String) parent.getItemAtPosition(position);
                    tlcSpinner  = (String) parent.getItemAtPosition(position);
                            /* Toast.makeText(getApplicationContext(),
                                    key , Toast.LENGTH_SHORT).show(); */
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });


            st = (Spinner) findViewById(R.id.st);
            st.setOnItemSelectedListener(this);
            st.setAdapter(adapter4);
            st.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    key = (String) parent.getItemAtPosition(position);
                    stSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });


        flywire = (Spinner) findViewById(R.id.flywire);
        flywire.setOnItemSelectedListener(this);
        flywire.setAdapter(adapter4);
        flywire.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = (String) parent.getItemAtPosition(position);
                flywireSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        pds = (Spinner) findViewById(R.id.pds);
        pds.setOnItemSelectedListener(this);
        pds.setAdapter(adapter4);
        pds.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = (String) parent.getItemAtPosition(position);
                pdsSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        gps = (Spinner) findViewById(R.id.gps);
        gps.setOnItemSelectedListener(this);
        gps.setAdapter(adapter4);
        gps.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = (String) parent.getItemAtPosition(position);
                gpsSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        gps_merk = (Spinner) findViewById(R.id.gps_merk);
        gps_merk.setOnItemSelectedListener(this);
        gps_merk.setAdapter(adapter4);
        gps_merk.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = (String) parent.getItemAtPosition(position);
                gpsMerkSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        kapal_andon = (Spinner) findViewById(R.id.kapal_andon);
        kapal_andon.setOnItemSelectedListener(this);
        kapal_andon.setAdapter(adapter4);
        kapal_andon.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = (String) parent.getItemAtPosition(position);
                kapalAddonSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        asal_andon = (Spinner) findViewById(R.id.asal_andon);
        asal_andon.setOnItemSelectedListener(this);
        String[] satuanAdon = new String[] { "" , "Provinsi Maluku", "Provinsi NTB", "Provinsi NTT", "Provinsi Maluku Utara", "Provinsi Papua Barat", "Provinsi Sulawesi Utara" };
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, satuanAdon);
        asal_andon.setAdapter(adapter5);
        asal_andon.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = (String) parent.getItemAtPosition(position);
                asalAddonSpinner  = (String) parent.getItemAtPosition(position);
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        ft_name = (Spinner) findViewById(R.id.ft_name);
        ft_name.setOnItemSelectedListener(this);
        //String[] satuanFT = new String[] { "" , "Fair Trade Name" };
        String selectQuery3 = " SELECT  nama_ft  FROM  tb_master_ft where k_tpi = '" + k_tpi + "'";
        loadSpinnerDataSingle(selectQuery3 , ft_name);

        ft_name.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                key = parent.getItemAtPosition(position).toString();
                ftNameSpinner  = parent.getItemAtPosition(position).toString();
                                /* Toast.makeText(getApplicationContext(),
                                        key , Toast.LENGTH_SHORT).show(); */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });




        //jika HL maka sembunyikan nilai PL
        //Jika PL maka sembunyikan nilai HL
        if(tipeTemplate.equals("HL")){

            txtViewJumlahPancing.setVisibility(View.GONE);
            txtVieBucketCapacity.setVisibility(View.GONE);

            jumlahPancing.setVisibility(View.GONE);
            bucketCapacity.setVisibility(View.GONE);


        }else if(tipeTemplate.equals("PL") ){


            txtViewHookType.setVisibility(View.GONE);
            txtViewUsingHandlineTroll.setVisibility(View.GONE);
            txtViewOtherGear.setVisibility(View.GONE);

            hookType.setVisibility(View.GONE);
            handlineTroll.setVisibility(View.GONE);
            otherGear.setVisibility(View.GONE);
            hookTypeSpiner = "None";

        }


        /* Check If Trip Exsist */
        isExsist = checkExsist();

        if(checkExsist()) {

            SQLiteDatabase db1 = databaseHandler.getReadableDatabase();
            cursor = db1.rawQuery("select * from " + TABEL_TRIP_INFO + " where idTrip = '" + idTrip + "'", null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {

                        vesselList.setSelection(getIndex(vesselList, cursor.getString(21) ));
                        hookType.setSelection(getIndex(hookType, cursor.getString(16)));
                        rumpon.setSelection(getIndex(rumpon, cursor.getString(22) ));
                        gearType.setSelection(getIndex(gearType, cursor.getString(24) ));
                        satuanTrip.setSelection(getIndex(satuanTrip, cursor.getString(6) ));
                        tlc.setSelection(getIndex(tlc, cursor.getString(27) ));
                        st.setSelection(getIndex(st, cursor.getString(28) ));
                        flywire.setSelection(getIndex(flywire, cursor.getString(28) ));
                        pds.setSelection(getIndex(pds, cursor.getString(30) ));
                        gps.setSelection(getIndex(gps, cursor.getString(31) ));
                        gps_merk.setSelection(getIndex(gps_merk, cursor.getString(32) ));
                        kapal_andon.setSelection(getIndex(kapal_andon, cursor.getString(38) ));
                        asal_andon.setSelection(getIndex(asal_andon, cursor.getString(39) ));
                        ft_name.setSelection(getIndex(ft_name, cursor.getString(41) ));

                    } while (cursor.moveToNext());
                }
                cursor.close();
                db1.close();

                /*
                ArrayAdapter myAdap = (ArrayAdapter) vesselList.getAdapter();
                int spinnerPosition = myAdap.getPosition("520301/005");
                System.out.println("Spinner Position " + spinnerPosition);
                */


            }

        }

        getNotes();


        //call data trip_info_catch_area
            //masukkan data ke listView
        notFoundCatchArea = (TextView) findViewById(R.id.notFoundCatchArea);
        notFoundCatchArea.setVisibility(View.INVISIBLE);
        refreshListCatchArea();
        Button buttonAddCatchArea=(Button)findViewById(R.id.buttonAddCatchArea);
        buttonAddCatchArea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showCatchAreaDialog();
            }
        });


        //call data trip_info_tuna_locating
            //masukkan ke listView
        notFoundTunaLocation = (TextView) findViewById(R.id.notFoundTunaLocation);
        notFoundTunaLocation.setVisibility(View.INVISIBLE);
        refreshListTunaLoc();
        Button buttonAddTunaLocation=(Button)findViewById(R.id.buttonAddTunaLocation);
        buttonAddTunaLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showTunaLocDialog();
            }
        });

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

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


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

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

    private void save() {

        //System.out.println( vic.toString() + " "  +  rumponSpiner.toString() ) ;
        if ( vic.toString().equals("") ||  totalCatch.getText().toString().equals(("") )  || fishingDays.getText().toString().equals("")  || longTrip.getText().toString().equals("") ) {
            if(totalCatch.getText().toString().equals(("") )){
                Toast.makeText(getApplicationContext(),
                        "Mohon isi Total tangkapan, Tulis 0 jika kosong !", Toast.LENGTH_SHORT).show();
            }

            if(fishingDays.getText().toString().equals(("") )){
                Toast.makeText(getApplicationContext(),
                        "Mohon isi jumlah hari memancing !", Toast.LENGTH_SHORT).show();
            }

            if(longTrip.getText().toString().equals(("") )){
                Toast.makeText(getApplicationContext(),
                        "Mohon isi lama trip !", Toast.LENGTH_SHORT).show();
            }


        } else if( ( satuanSpinner .toString().equals("Hari") ) && (Integer.parseInt(fishingDays.getText().toString()) > Integer.parseInt(longTrip.getText().toString()) )   ){
            Toast.makeText(getApplicationContext(),
                    "Lama Hari memancing lebih besar dari lama trip !", Toast.LENGTH_SHORT).show();
        }
        else
        {

            SQLiteDatabase db = databaseHandler.getWritableDatabase();

            if(isExsist){
                String updateSql = "UPDATE " + TABEL_TRIP_INFO + " SET vic = '" + vic.toString() + "' , total_tangkapan = '" + totalCatch.getText().toString() + "' , ikan_hilang = '" + totalLost.getText().toString() + "' , lama_trip = '" + longTrip.getText().toString() + "' , satuan_trip = '" + satuanSpinner .toString() + "' ,  bbm = '" + bbm.getText().toString() + "' , jum_hari_memancing = '" + fishingDays.getText().toString() + "' , es = '" + ice.getText().toString() + "' , awak = '"+ noOfCrew.getText().toString() + "' , kapten = '"  + captainName.getText().toString() + "' ,  gt = '" + gt.getText().toString() + "' ,  panjang = '" + vesselLength.getText().toString() + "' , pk = '" + pk.getText().toString() + "' , bahan = '" + vesselMaterial.getText().toString() + "' , hl_tipe_mata_pancing = '"+ hookTypeSpiner.toString() +"' , hl_handline_troll = '"+ handlineTroll.getText().toString() + "' , hl_alattangkaplain = '"+ otherGear.getText().toString() +"' , pl_jumlah_pancing = '"+ jumlahPancing.getText().toString() +"' , pl_kapasitas_ember = '"+ bucketCapacity.getText().toString() +"' , nama_kapal = '"+ vesselName.getText().toString() +  "'  , rumpon = '" + rumponSpiner.toString() + "' , gearType = '" + gearTypeSpiner.toString()  +"' , gearName = '"+ gearNameSpinner.toString() +"' , enumerator_1 = '"+ enumerator1.getText().toString() +"' , enumerator_2 = '"+ enumerator2.getText().toString() +"' , tlc = '"+ tlcSpinner.toString() +"' , st  = '"+ stSpinner.toString() +"' , flywire = '"+ flywireSpinner.toString() +"' , pds = '"+ pdsSpinner.toString() +"' , gps = '"+ gpsSpinner.toString() +"' , gps_merk = '"+ gpsMerkSpinner.toString() +"' , daya_cahaya = '"+ daya_cahaya.getText().toString() +"' , kedalaman_min = '"+ kedalaman_min.getText().toString() +"' , kedalaman_max = '"+kedalaman_max.getText().toString()+"' , jum_palka  = '"+ jum_palka.getText().toString() +"' , kapasitas_palka = '"+kapasitas_palka.getText().toString()+"' , kapal_andon = '"+ kapalAddonSpinner.toString() +"' , asal_andon = '"+ asalAddonSpinner.toString() +"' , jum_rumpon = '"+ jum_rumpon.getText().toString() +"' , ft_name = '"+ ftNameSpinner.toString() +"'  where idTrip = '"+ idTrip +"'  " ;
                Log.e("update sqlite ", "" + updateSql);

                try {
                    db.execSQL(updateSql);
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

                i = new Intent(TripInfo.this,SheetSelection.class);
                i.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
                i.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
                i.putExtra("n_tpi",getIntent().getStringExtra("n_tpi"));
                i.putExtra("n_perusahaan",getIntent().getStringExtra("n_perusahaan"));
                startActivity(i);

                Toast.makeText(getApplicationContext(),
                         "Berhasil Update! " , Toast.LENGTH_SHORT).show();


            } else {

                String insertSql = "INSERT INTO " + TABEL_TRIP_INFO + " (  " +
                        "idTrip , " +
                        "vic , \n" +
                        "total_tangkapan , \n" +
                        "ikan_hilang , \n" +
                        "lama_trip , \n" +
                        "satuan_trip ,  \n" +
                        "bbm , \n" +
                        "jum_hari_memancing , \n" +
                        "es , \n" +
                        "awak , \n" +
                        "kapten , \n" +
                        "gt , \n" +
                        "panjang , \n" +
                        "pk , \n" +
                        "bahan , \n" +
                        "hl_tipe_mata_pancing , \n" +
                        "hl_handline_troll , \n" +
                        "hl_alattangkaplain, \n" +
                        "pl_jumlah_pancing, \n" +
                        "pl_kapasitas_ember, " +
                        "nama_kapal," +
                        "rumpon," +
                        "gearType," +
                        "gearName , " +
                        "enumerator_1 , " +
                        "enumerator_2 ," +
                        "tlc ," +
                        "st ," +
                        "flywire ," +
                        "pds ," +
                        "gps ," +
                        "gps_merk ," +
                        "daya_cahaya ," +
                        "kedalaman_min ," +
                        "kedalaman_max ," +
                        "jum_palka ," +
                        "kapasitas_palka ," +
                        "kapal_andon ," +
                        "asal_andon ," +
                        "jum_rumpon ," +
                        "ft_name " +
                        " ) " +
                        "VALUES " +
                        "('" + idTrip + "', " +
                        "'" + vic.toString() + "' , " +
                        "'" + totalCatch.getText().toString() + "' , " +
                        "'" + totalLost.getText().toString() + "' , " +
                        "'" + longTrip.getText().toString() + "' , " +
                        "'" + satuanSpinner .toString() + "' , " +
                        "'" + bbm.getText().toString() + "' , " +
                        "'" + fishingDays.getText().toString() + "' , " +
                        "'" + ice.getText().toString() + "' ," +
                        " ' " + noOfCrew.getText().toString() + "' , " +
                        "'" + captainName.getText().toString() + "' , " +
                        "'" + gt.getText().toString() + "' , " +
                        "'" + vesselLength.getText().toString() + "' , " +
                        "'" + pk.getText().toString() + "' , " +
                        "'" + vesselMaterial.getText().toString() + "' , " +
                        "'" + hookTypeSpiner.toString() + "' , " +
                        "'" + handlineTroll.getText().toString() + "' , " +
                        "'" + otherGear.getText().toString() + "' , " +
                        "'" + jumlahPancing.getText().toString() + "' , " +
                        "'" + bucketCapacity.getText().toString() + "', " +
                        "'"+ vesselName.getText().toString() + "', " +
                        "'"+ rumponSpiner.toString() + "' , " +
                        "'"+ gearTypeSpiner.toString() +"', " +
                        "'"+ gearNameSpinner.toString() +"'," +
                        "'"+ enumerator1.getText().toString() +"' , " +
                        "'"+ enumerator2.getText().toString() +"' , " +

                        "'"+ tlcSpinner.toString() +"' , " +
                        "'"+ stSpinner.toString() +"' ," +
                        "'"+ flywireSpinner.toString() +"' ," +
                        "'"+ pdsSpinner.toString() +"', " +
                        "'"+ gpsSpinner.toString() +"', " +
                        "'"+ gpsMerkSpinner.toString() +"' ," +
                        "'"+ daya_cahaya.getText().toString() +"' ," +
                        "'"+ kedalaman_min.getText().toString() +"', " +
                        "'"+ kedalaman_max.getText().toString() +"' ," +
                        "'"+ jum_palka.getText().toString() +"' ," +
                        "'"+ kapasitas_palka.getText().toString() +"', " +
                        "'"+ kapalAddonSpinner.toString() +"', " +
                        "'"+ asalAddonSpinner.toString() +"' ," +
                        "'"+ jum_rumpon.getText().toString() +"', " +
                        "'"+ ftNameSpinner.toString() +"' " +

                        " )";

                Log.e("insert sqlite ", "" + insertSql);

                try {
                    db.execSQL(insertSql);
                } catch(SQLException e){

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

                i = new Intent(TripInfo.this,SheetSelection.class);
                i.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
                i.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
                i.putExtra("n_tpi",getIntent().getStringExtra("n_tpi"));
                i.putExtra("n_perusahaan",getIntent().getStringExtra("n_perusahaan"));
                startActivity(i);

                Toast.makeText(getApplicationContext(),
                        "Berhasil Insert Data Trip Baru! " , Toast.LENGTH_SHORT).show();

            }
            db.close();

            saveNotes() ;

        }


    }


    private void getNotes(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select notes from tb_trip_lists where id = '" + idTrip + "'", null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                notes.setText( cursor.getString(0) );

            }
        }

        db.close();




    }

    private void saveNotes(){
        SQLiteDatabase db = databaseHandler.getWritableDatabase();


        String updateSql = "UPDATE tb_trip_lists SET notes = '" + notes.getText().toString() + "'  where id = '"+ idTrip +"'  " ;
        Log.e("update sqlite ", "" + updateSql);

        try {
            db.execSQL(updateSql);
        }
        catch(SQLException e){

            System.out.println(e);

        }


        db.close();

    }

    private boolean checkExsist(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_TRIP_INFO + " where idTrip = '" + idTrip+ "'", null);

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    vesselName.setText(cursor.getString(21) );
                    captainName.setText(cursor.getString(11) );
                    vesselLength.setText(cursor.getString(13) );
                    gt.setText(cursor.getString(12) );
                    pk.setText(cursor.getString(14) );
                    vesselMaterial.setText(cursor.getString(15) );
                    noOfCrew.setText(cursor.getString(10) );
                    totalCatch.setText(cursor.getString(3) );
                    totalLost.setText(cursor.getString(4) );
                    longTrip.setText(cursor.getString(5) );
                    bbm.setText(cursor.getString(7) );
                    fishingDays.setText(cursor.getString(8) );
                    ice.setText(cursor.getString(9) );
                    handlineTroll.setText(cursor.getString(17) );
                    otherGear.setText(cursor.getString(18) );
                    jumlahPancing.setText(cursor.getString(19) );
                    bucketCapacity.setText(cursor.getString(20) );
                    enumerator1.setText(cursor.getString(25) ) ;
                    enumerator2.setText(cursor.getString(26) ) ;
                    daya_cahaya.setText(cursor.getString(33) ) ;
                    kedalaman_min.setText(cursor.getString(34) );
                    kedalaman_max.setText(cursor.getString(35) ) ;
                    jum_palka.setText(cursor.getString(36) ) ;
                    kapasitas_palka.setText(cursor.getString(37) ) ;
                    jum_rumpon.setText(cursor.getString(40)) ;

                } while (cursor.moveToNext());
            }


            cursor.close();
            db.close();


            return true;
        }else{

            setClearText();
            return false ;
        }


    }


    private void loadSpinnerDataVessel(String selectQuery , Spinner spiner){


        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        labels.add(  new SpinnerObject (  "Null" ,  "Choose Vessels" ) ) ;
        if (cursor.moveToFirst()) {
            do {
                labels.add( new SpinnerObject (  cursor.getString(0) ,  cursor.getString(1) ));
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

    private void loadSpinnerDataSingle(String selectQuery , Spinner spiner) {

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



    private void loadSpinnerData(String selectQuery , Spinner spiner) {

        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add( new SpinnerObject (  cursor.getString(0) ,  cursor.getString(1) ));
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


    private void setVicData(String vic) {

        vesselName.setText("");
        captainName.setText("");
        vesselLength.setText("");
        gt.setText("");
        pk.setText("");
        vesselMaterial.setText("");
        noOfCrew.setText("");


        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select nama_kapal , nama_kapten ,  jumlah_abk , panjang_kapal , gross_tonnage , pk , bahan_kapal from tb_master_vessels where vic = '" + vic + "' ", null);
        if (cursor.moveToFirst()) {
            do {
                //System.out.println( cursor.getString(0)  + " | "+  cursor.getString(1) + " | "+  cursor.getString(2) + " | "+  cursor.getString(3)+ " | "+  cursor.getString(4)+ " | "+  cursor.getString(5)+ " | "+  cursor.getString(6)) ;
                vesselName.setText(cursor.getString(0));
                captainName.setText(cursor.getString(1));
                if(cursor.getString(1).equals("")){
                    getKapalFromInput();
                }
                vesselLength.setText(cursor.getString(3));
                gt.setText(cursor.getString(4));
                pk.setText(cursor.getString(5));
                vesselMaterial.setText(cursor.getString(6));
                noOfCrew.setText(cursor.getString(2));
                if(cursor.getString(2).equals("") || cursor.getString(2).equals("0") || cursor.getString(2).equals("null")){
                    getCrewFromInput();
                }


            } while (cursor.moveToNext());


            cursor.close();
            db.close();


        } else {

            System.out.println("No Vessel Start to search trip database info!");
            SQLiteDatabase db2 = databaseHandler.getReadableDatabase();
            Cursor cursor2 = db2.rawQuery("select * from " + TABEL_TRIP_INFO + " where idTrip = '" + idTrip + "'", null);
            if (cursor2.getCount() > 0) {
                if (cursor2.moveToFirst()) {
                    do {

                        vesselName.setText(cursor2.getString(21) );
                        captainName.setText(cursor2.getString(11) );
                        vesselLength.setText(cursor2.getString(13) );
                        gt.setText(cursor2.getString(12) );
                        pk.setText(cursor2.getString(14) );
                        vesselMaterial.setText(cursor2.getString(15) );
                        noOfCrew.setText(cursor2.getString(10) );

                    } while (cursor2.moveToNext());
                }

            }

            cursor2.close();
            db2.close();



        }


    }


    private void getKapalFromInput(){


        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("select kapten from " + TABEL_TRIP_INFO + " where idTrip = '" + idTrip + "'", null);
        if (cursor2.getCount() > 0) {
            if (cursor2.moveToFirst()) {
                do {
                    if(!cursor2.getString(0).equals("")) {
                        captainName.setText(cursor2.getString(0));
                    }


                } while (cursor2.moveToNext());
            }

        }

        cursor2.close();
        db.close();


    }

    private void getCrewFromInput(){


        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("select awak from " + TABEL_TRIP_INFO + " where idTrip = '" + idTrip + "'", null);
        if (cursor2.getCount() > 0) {
            if (cursor2.moveToFirst()) {
                do {

                    if(!cursor2.getString(0).equals("")) {
                        noOfCrew.setText(cursor2.getString(0));
                    }

                } while (cursor2.moveToNext());
            }

        }

        cursor2.close();
        db.close();


    }



    private void setClearText(){

        vesselName.setText("");
        captainName.setText("");
        vesselLength.setText("");
        gt.setText("");
        pk.setText("");
        vesselMaterial.setText("");
        noOfCrew.setText("");
        totalCatch.setText("");
        totalLost.setText("");
        longTrip.setText("");
        bbm.setText("");
        fishingDays.setText("");
        ice.setText("");
        handlineTroll.setText("");
        otherGear.setText("");
        jumlahPancing.setText("");
        bucketCapacity.setText("");
        enumerator1.setText("");
        enumerator2.setText("");

        daya_cahaya.setText("");
        kedalaman_min.setText("") ;
        kedalaman_max.setText("") ;
        jum_palka.setText("") ;
        kapasitas_palka.setText("") ;
        jum_rumpon.setText("") ;

    }

    private void refreshListCatchArea(){
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_TRIP_INFO_CATCHAREA  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        grid_a = new String[cursor.getCount()];
        grid_b = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                grid_a[cc] = cursor.getString(cursor.getColumnIndex("grid_a"));
                grid_b[cc] = cursor.getString(cursor.getColumnIndex("grid_b"));

                list.add(cursor.getString(cursor.getColumnIndex("grid_a")));
                list.add(cursor.getString(cursor.getColumnIndex("grid_b")));

                System.out.println(cursor.getString(cursor.getColumnIndex("grid_a"))) ;

                notFoundCatchArea.setVisibility(View.INVISIBLE);
            }
        }else{
            notFoundCatchArea.setVisibility(View.VISIBLE);
        }

        lvcatchArea = (ListView) findViewById(R.id.catchArea);
        catchAreaAdapter = new AdaptercatchAreaAdapter(this, grid_a, grid_b);
        lvcatchArea.setAdapter(catchAreaAdapter);
        lvcatchArea.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = { "Delete" };
                AlertDialog.Builder dialog = new AlertDialog.Builder(TripInfo.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_TRIP_INFO_CATCHAREA + " WHERE id_trip = '" + idTrip + "' and grid_a = '" + grid_a[position] + "' and grid_b = '" + grid_b[position] + "'  ");
                                db.close();

                                refreshListCatchArea();

                                break;

                        }
                    }
                }).show();

                return true;
            }

        }) ;

        //Make ListView not use scrollview
        justifyListViewHeightBasedOnChildren(lvcatchArea);


        /* This allow listview scroll inside scrollview */
        /*
        lvcatchArea.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        */

        db.close();


    }


    private void showCatchAreaDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(TripInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_catcharea, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TripInfo.this);
        alertDialogBuilder.setTitle("Catch Area");
        alertDialogBuilder.setView(promptView);

        Spinergrid_a = (Spinner) promptView.findViewById(R.id.grid_a);
        Spinergrid_a.setOnItemSelectedListener(this);
        String[] gridA_lists = new String[] { "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "I" , "J" , "K" , "L", "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" , "U" , "V" , "W" , "X" , "Y" , "Z"  };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gridA_lists);
        Spinergrid_a.setAdapter(adapter1);
        Spinergrid_a.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyA = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyA , Toast.LENGTH_SHORT).show();*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Spinergrid_b = (Spinner) promptView.findViewById(R.id.grid_b);
        Spinergrid_b.setOnItemSelectedListener(this);
        //String[] gridB_lists = new String[] { "1" , "2" , "3" , "4" , "5" , "F" , "G" , "H" , "I" , "J" , "K" , "L", "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" , "U" , "V" , "W" , "X" , "Y" , "Z"  };
        String[] gridB_lists =  new String[50]  ;
        for (int i=0 ; i<50; i++){
            gridB_lists[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gridB_lists);
        Spinergrid_b.setAdapter(adapter2);
        Spinergrid_b.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyB = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getApplicationContext(),
                        keyB , Toast.LENGTH_SHORT).show(); */
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
                        saveCatchArea();
                        refreshListCatchArea();
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

    private void saveCatchArea(){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String insertSqlCatchArea = "INSERT INTO " + TABEL_TRIP_INFO_CATCHAREA + " ( 'id_trip' , grid_a , grid_b ) VALUES ('"+ idTrip+"' , '" + keyA + "' , '" + keyB + "' )  " ;
        Log.e("update sqlite ", "" + insertSqlCatchArea);
        try {
            db.execSQL(insertSqlCatchArea);
            Toast.makeText(getApplicationContext(),
                    "Data Berhasil Masuk !", Toast.LENGTH_SHORT).show();
        } catch(SQLException e){

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


    private void refreshListTunaLoc(){

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_TRIP_INFO_TUNALCATING  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        teknik = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                teknik[cc] = cursor.getString(cursor.getColumnIndex("teknik"));

                list.add(cursor.getString(cursor.getColumnIndex("teknik")));

                System.out.println(cursor.getString(cursor.getColumnIndex("teknik"))) ;

                notFoundTunaLocation.setVisibility(View.INVISIBLE);
            }
        }else{
            notFoundTunaLocation.setVisibility(View.VISIBLE);
        }

        lvtunaLocation = (ListView) findViewById(R.id.tunaLocation);
        lvtunaLocation.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, teknik));
        lvtunaLocation.setSelected(true);

        lvtunaLocation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = { "Delete" };
                AlertDialog.Builder dialog = new AlertDialog.Builder(TripInfo.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_TRIP_INFO_TUNALCATING + " WHERE id_trip = '" + idTrip + "' and teknik = '" + teknik[position] + "' ");
                                db.close();

                                refreshListTunaLoc();

                                break;

                        }
                    }
                }).show();

                return true;
            }

        }) ;


        //Make ListView not use scrollview
        justifyListViewHeightBasedOnChildren(lvtunaLocation);



        /* This allow listview scroll inside scrollview */
        /*
        lvtunaLocation.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        */

        db.close();

    }

    private void showTunaLocDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(TripInfo.this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_tunaloc, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TripInfo.this);
        alertDialogBuilder.setTitle("Tuna Locating Technique");
        alertDialogBuilder.setView(promptView);

        Spinerteknik = (Spinner) promptView.findViewById(R.id.teknik);
        Spinerteknik.setOnItemSelectedListener(this);
        String[] teknik_lists = new String[] { "Lumba-lumba" , "Ikan" , "Burung" , "Rumpon" , "FishFinder" , "Lain-lain"  };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, teknik_lists);
        Spinerteknik.setAdapter(adapter1);
        Spinerteknik.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyTeknik = (String) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        keyTeknik , Toast.LENGTH_SHORT).show();
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
                        saveTunaLoc();
                        refreshListTunaLoc();
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


    private void saveTunaLoc(){

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        String insertSqlTunaLoc = "INSERT INTO " + TABEL_TRIP_INFO_TUNALCATING + " ( id_trip , teknik  ) VALUES ('"+ idTrip +"' , '" + keyTeknik + "'  )  " ;
        Log.e("update sqlite ", "" + insertSqlTunaLoc);
        try {
            db.execSQL(insertSqlTunaLoc);
            Toast.makeText(getApplicationContext(),
                    "Data Berhasil Masuk !", Toast.LENGTH_SHORT).show();

        } catch(SQLException e){

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


    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }



    //@Override
   // public boolean onOptionsItemSelected(MenuItem item) {
        //return true;
    //}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                finish();
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
