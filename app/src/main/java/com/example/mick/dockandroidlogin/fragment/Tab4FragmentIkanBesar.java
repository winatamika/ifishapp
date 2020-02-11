package com.example.mick.dockandroidlogin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.AdapterIkanBesar;
import com.example.mick.dockandroidlogin.sampling.SamplingInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mick on 2/15/2018.
 */

public class Tab4FragmentIkanBesar extends Fragment {

    DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
    String TABEL_IKAN_BESAR = "tb_ikan_besar" ;
    String[] idDatabase , species , kode ,  berat , panjang , loin_berat , loin_panjang , insang , perut , pl_daging , nomorIkan   ;
    String idTrip , tipeTemplate , kode_tpi ;
    TextView notFound , txtDagingPerut  ;
    ListView lvIkanBesar;
    AdapterIkanBesar ikanBesarAdapter ;
    EditText editSpecies , editKode , editBerat , editPanjang , editLoinBerat , editLoinPanjang , editInsang , editPerut , editPlDaging  ;
    private RadioGroup radioGroupSpecies;
    private RadioButton radioButtonSp;
    private Button buttonSave;
    Integer getSpecies ;
    Spinner SpinnerKode , SpinnerInsang , SpinnerPerut , SpinnerDagingPerut ;
    String keyKode , keyInsang , keyPerut , keyDagingPerut  ;
    String[] question = new String[]{"" , "Ya", "Tidak" };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_ikan_besar,container,false);


        SamplingInfo activity = (SamplingInfo) getActivity();
        idTrip = activity.getIdTrip();
        tipeTemplate = activity.getTipeTemplate();
        kode_tpi = activity.getKode_tpi();

        notFound = (TextView) view.findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);

        lvIkanBesar = (ListView) view.findViewById(R.id.ikanBesar);

        refreshList();

        //Button buttonAdd=(Button) view.findViewById(R.id.buttonAdd);
        FloatingActionButton buttonAdd = (FloatingActionButton)view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog();
            }
        });

        Button buttonRefresh=(Button) view.findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                refreshList();
            }
        });



        return view;
    }


    private void refreshList(){

        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_IKAN_BESAR  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        idDatabase = new String[cursor.getCount()];
        kode = new String[cursor.getCount()];
        species = new String[cursor.getCount()];
        berat = new String[cursor.getCount()];
        panjang = new String[cursor.getCount()];
        loin_berat = new String[cursor.getCount()];
        loin_panjang = new String[cursor.getCount()];
        insang = new String[cursor.getCount()];
        perut = new String[cursor.getCount()];
        pl_daging = new String[cursor.getCount()];
        nomorIkan = new String[cursor.getCount()];

        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                idDatabase[cc] =  cursor.getString(cursor.getColumnIndex("id"));
                species[cc] = cursor.getString(cursor.getColumnIndex("species"));
                kode[cc] = cursor.getString(cursor.getColumnIndex("kode"));
                berat[cc] = cursor.getString(cursor.getColumnIndex("berat"));
                panjang[cc] = cursor.getString(cursor.getColumnIndex("panjang"));
                nomorIkan[cc] = Integer.toString (cc + 1 ) ;

                notFound.setVisibility(View.INVISIBLE);
            }


        }else{
            notFound.setVisibility(View.VISIBLE);
        }
        cursor.close();
        db.close();

        ikanBesarAdapter = new AdapterIkanBesar(Tab4FragmentIkanBesar.this.getActivity() , species ,  kode , berat , panjang , nomorIkan );
        lvIkanBesar.setAdapter(ikanBesarAdapter);
        justifyListViewHeightBasedOnChildren(lvIkanBesar);
        lvIkanBesar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id){

                final CharSequence[] dialogitem = {  "Update" , "Delete"  };
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:

                                System.out.println("Do Update "+ idTrip + " " + idDatabase[position] + " "  );
                                showDialogUpdate(idTrip ,idDatabase[position] );

                                break;
                            case 1:

                                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_IKAN_BESAR + " WHERE id_trip = '" + idTrip + "' and id = '" + idDatabase[position] + "'");
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


    private void showDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dialogbox_ikanbesar_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Large Fish Add");
        alertDialogBuilder.setView(promptView);

        SpinnerKode = (Spinner) promptView.findViewById(R.id.kode);
        String selectQuery = " SELECT kode FROM  tb_ringkasan_besar where id_trip = '"+ idTrip+"' order by kode";
        loadSpinnerData(selectQuery, SpinnerKode);
        SpinnerKode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyKode = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getActivity().getApplicationContext(),
                        keyKode, Toast.LENGTH_SHORT).show(); */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerKode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyKode = parent.getItemAtPosition(position).toString();
                /*  Toast.makeText(getActivity().getApplicationContext(),
                        keyKode, Toast.LENGTH_SHORT).show(); */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerInsang = (Spinner) promptView.findViewById(R.id.insang);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, question);
        SpinnerInsang.setAdapter(adapter1);
        SpinnerInsang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyInsang = (String) parent.getItemAtPosition(position);

               /*  Toast.makeText(getActivity().getApplicationContext(),
                        keyInsang, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerPerut = (Spinner) promptView.findViewById(R.id.perut);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, question);
        SpinnerPerut.setAdapter(adapter2);
        SpinnerPerut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyPerut = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getActivity().getApplicationContext(),
                        keyPerut, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerDagingPerut = (Spinner) promptView.findViewById(R.id.dagingPerut);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, question);
        SpinnerDagingPerut.setAdapter(adapter3);
        SpinnerDagingPerut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyDagingPerut = (String) parent.getItemAtPosition(position);

                /*  Toast.makeText(getActivity().getApplicationContext(),
                        keyDagingPerut, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        editBerat = (EditText) promptView.findViewById(R.id.editBerat);
            //editBerat.setFilters(new InputFilter[]{ new InputFilterMinMax(10, 150)});
        editPanjang = (EditText) promptView.findViewById(R.id.editPanjang);
            //editPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(20, 250)});
        editLoinBerat = (EditText) promptView.findViewById(R.id.editLoinBerat);
            //editLoinBerat.setFilters(new InputFilter[]{ new InputFilterMinMax(5, 25)});
        editLoinPanjang = (EditText) promptView.findViewById(R.id.editLoinPanjang);
            //editLoinPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(30, 115)});
        radioGroupSpecies = (RadioGroup) promptView.findViewById(R.id.radioGroupSpecies);
        radioButtonSp = (RadioButton) promptView.findViewById(R.id.radioYFT);
        radioGroupSpecies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(RadioGroup group, int checkedId)
                                                         {
                                                             LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                                                             View promptView = layoutInflater.inflate(R.layout.dialogbox_ikankecil_add, null);

                                                             int selectedId = radioGroupSpecies.getCheckedRadioButtonId();
                                                             radioButtonSp = (RadioButton) promptView.findViewById(selectedId);

                                                             Toast.makeText(getActivity(),
                                                                     radioButtonSp.getText(), Toast.LENGTH_SHORT).show();



                                                         }
                                                     }
        );


        if (tipeTemplate.equals("PL")) {

            TextView txtDagingPerut = promptView.findViewById(R.id.txtDagingPerut);
            txtDagingPerut.setVisibility(View.GONE);

            SpinnerDagingPerut.setVisibility(View.GONE);
            keyDagingPerut = "None";

        }


        buttonSave = (Button) promptView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                saveAdd();
                refreshList();
                clearText();
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


    private void showDialogUpdate(final String id_Trip ,final  String idDb ) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dialogbox_ikanbesar_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Large Fish Update");
        alertDialogBuilder.setView(promptView);

        SpinnerKode = (Spinner) promptView.findViewById(R.id.kode);
        String selectQuery = " SELECT kode FROM  tb_ringkasan_besar where id_trip = '" + idTrip + "' order by kode";
        loadSpinnerData(selectQuery, SpinnerKode);
        SpinnerKode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyKode = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity().getApplicationContext(),
                        keyKode, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerKode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                keyKode = parent.getItemAtPosition(position).toString();
                /* Toast.makeText(getActivity().getApplicationContext(),
                        keyKode, Toast.LENGTH_SHORT).show(); */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerInsang = (Spinner) promptView.findViewById(R.id.insang);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, question);
        SpinnerInsang.setAdapter(adapter1);
        SpinnerInsang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyInsang = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getActivity().getApplicationContext(),
                        keyInsang, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerPerut = (Spinner) promptView.findViewById(R.id.perut);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, question);
        SpinnerPerut.setAdapter(adapter2);
        SpinnerPerut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyPerut = (String) parent.getItemAtPosition(position);

               /*  Toast.makeText(getActivity().getApplicationContext(),
                        keyPerut, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        SpinnerDagingPerut = (Spinner) promptView.findViewById(R.id.dagingPerut);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, question);
        SpinnerDagingPerut.setAdapter(adapter3);
        SpinnerDagingPerut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keyDagingPerut = (String) parent.getItemAtPosition(position);

                /* Toast.makeText(getActivity().getApplicationContext(),
                        keyDagingPerut, Toast.LENGTH_SHORT).show(); */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        editBerat = (EditText) promptView.findViewById(R.id.editBerat);
            //editBerat.setFilters(new InputFilter[]{ new InputFilterMinMax(10, 150)});
        editPanjang = (EditText) promptView.findViewById(R.id.editPanjang);
            //editPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(50, 250)});
        editLoinBerat = (EditText) promptView.findViewById(R.id.editLoinBerat);
            //editLoinBerat.setFilters(new InputFilter[]{ new InputFilterMinMax(2, 25)});
        editLoinPanjang = (EditText) promptView.findViewById(R.id.editLoinPanjang);
            //editLoinPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(30, 115)});
        radioGroupSpecies = (RadioGroup) promptView.findViewById(R.id.radioGroupSpecies);
        radioButtonSp = (RadioButton) promptView.findViewById(R.id.radioYFT);
        radioGroupSpecies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                             LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                                                             View promptView = layoutInflater.inflate(R.layout.dialogbox_ikankecil_add, null);

                                                             int selectedId = radioGroupSpecies.getCheckedRadioButtonId();
                                                             radioButtonSp = (RadioButton) promptView.findViewById(selectedId);

                                                             Toast.makeText(getActivity(),
                                                                     radioButtonSp.getText(), Toast.LENGTH_SHORT).show();


                                                         }
                                                     }
        );


        if (tipeTemplate.equals("PL")) {

            TextView txtDagingPerut = promptView.findViewById(R.id.txtDagingPerut);
            txtDagingPerut.setVisibility(View.GONE);

            SpinnerDagingPerut.setVisibility(View.GONE);
            keyDagingPerut = "None";

        }


        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_IKAN_BESAR + " where id_trip = '" + id_Trip + "' and id = '" + idDb + "'", null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    SpinnerKode.setSelection(getIndex(SpinnerKode, cursor.getString(2)));
                    editBerat.setText(cursor.getString(4));
                    editPanjang.setText(cursor.getString(5));
                    editLoinBerat.setText(cursor.getString(6));
                    editLoinPanjang.setText(cursor.getString(7));
                    SpinnerInsang.setSelection(getIndex(SpinnerInsang, cursor.getString(8)));
                    SpinnerPerut.setSelection(getIndex(SpinnerPerut, cursor.getString(9)));
                    SpinnerDagingPerut.setSelection(getIndex(SpinnerDagingPerut, cursor.getString(10)));

                    if (cursor.getString(3).equals("ALB")) {
                        getSpecies = 0;
                    } else if (cursor.getString(3).equals("BET")) {
                        getSpecies = 1;
                    } else if (cursor.getString(3).equals("YFT")) {
                        getSpecies = 2;
                    }
                    ((RadioButton) radioGroupSpecies.getChildAt(getSpecies)).setChecked(true);

                } while (cursor.moveToNext());
            }



            buttonSave = (Button) promptView.findViewById(R.id.buttonSave);
            buttonSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    saveUpdate(id_Trip, idDb);
                    refreshList();
                    clearText();
                }
            });
            buttonSave.setVisibility(View.GONE);



            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveUpdate(id_Trip, idDb);
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
        cursor.close();
        db.close();

    }


    private void saveAdd(){

        if(!keyKode.toString().equals("") &&  ( !editPanjang.getText().toString().equals("") ||  !editLoinPanjang.getText().toString().equals("") ) ) {

            int checking = 0;

            if( !editPanjang.getText().toString().equals("") ) {


                if( Double.parseDouble(editPanjang.getText().toString()) <  50 ||  Double.parseDouble(editPanjang.getText().toString()) >   250 ){

                    checking = 1;

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Panjang harus diantara 50Cm sampai 250Cm", Toast.LENGTH_SHORT).show();

                }

            }


            if( !editBerat.getText().toString().equals("") ) {


                if( Double.parseDouble(editBerat.getText().toString()) < 10 || Double.parseDouble(editBerat.getText().toString()) > 150  ){

                    checking = 1;

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Berat harus diantara 10Kg sampai 150Kg", Toast.LENGTH_SHORT).show();

                }

            }


            if( !editLoinPanjang.getText().toString().equals("") ) {


                if( Double.parseDouble(editLoinPanjang.getText().toString()) < 30 || Double.parseDouble(editLoinPanjang.getText().toString()) > 115  ){

                    checking = 1;

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Panjang loin harus diantara 30Cm sampai 115Cm", Toast.LENGTH_SHORT).show();
                }

            }


            if( !editLoinBerat.getText().toString().equals("") ) {


                if( Double.parseDouble(editLoinBerat.getText().toString()) < 2 || Double.parseDouble(editLoinBerat.getText().toString()) > 25  ){

                    if( Double.parseDouble(editLoinBerat.getText().toString()) < 2 && ( kode_tpi.equals("820401") || kode_tpi.equals("820402") || kode_tpi.equals("820403") || kode_tpi.equals("820404") ) )
                    {
                        if( Double.parseDouble(editLoinBerat.getText().toString()) < 1 ){
                            checking = 1;
                            //wilayah maluku utara boleh dibawah 2 kg namun tidak boleh kurang dari 1 kg
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Berat harus diantara 1Kg sampai 25Kg Untuk wilayah Maluku Utara", Toast.LENGTH_SHORT).show();
                        }else{
                            checking = 0;
                            //wilayah maluku utara boleh dibawah 2 kg
                        }


                    }else {
                        checking = 1;

                        Toast.makeText(getActivity().getApplicationContext(),
                                "Berat harus diantara 2Kg sampai 25Kg", Toast.LENGTH_SHORT).show();
                    }
                }

            }


            if( checking <= 0 ) {

                System.out.println(keyKode + " " + radioButtonSp.getText() + " " + editBerat.getText().toString() + " " + editPanjang.getText().toString() + " " + editLoinBerat.getText().toString() + " " + editLoinPanjang.getText().toString() + " " + keyInsang + " " + keyPerut + " " + keyDagingPerut + " ");

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                SQLiteDatabase db = databaseHandler.getWritableDatabase();

                String insertSql = "INSERT INTO " + TABEL_IKAN_BESAR + " ( id_trip , kode , species , berat , panjang , loin_berat , loin_panjang , insang , perut , pl_daging ) VALUES ('" + idTrip + "' , '" + keyKode + "' , '" + radioButtonSp.getText() + "' , '" + editBerat.getText().toString() + "' , '" + editPanjang.getText().toString() + "' , '" + editLoinBerat.getText().toString() + "' , '" + editLoinPanjang.getText().toString() + "' , '" + keyInsang + "' , '" + keyPerut + "' , '" + keyDagingPerut + "'  )";
                Log.e("update sqlite ", "" + insertSql);

                try {
                    db.execSQL(insertSql);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Data Berhasil Insert !", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {

                    if (e instanceof SQLiteConstraintException) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Data Sudah pernah dimasukkan!", Toast.LENGTH_SHORT).show();

                    } else if (e instanceof SQLiteDatatypeMismatchException) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Kesalahan Tipe Data!", Toast.LENGTH_SHORT).show();
                    } else {
                        throw e;
                    }
                    System.out.println(e);

                }

                db.close();

            }

        }

    }

    private void saveUpdate(String id_Trip , String idDb){

        if(!keyKode.toString().equals("") &&  ( !editPanjang.getText().toString().equals("") ||  !editLoinPanjang.getText().toString().equals("") ) ) {


            int checking = 0;

            if( !editPanjang.getText().toString().equals("") ) {


                if( Double.parseDouble(editPanjang.getText().toString()) <  50 ||  Double.parseDouble(editPanjang.getText().toString()) >   250 ){

                    checking = 1;

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Panjang harus diantara 50Cm sampai 250Cm", Toast.LENGTH_SHORT).show();

                }

            }


            if( !editBerat.getText().toString().equals("") ) {


                if( Double.parseDouble(editBerat.getText().toString()) < 10 || Double.parseDouble(editBerat.getText().toString()) > 150  ){

                    checking = 1;

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Berat harus diantara 10Kg sampai 150Kg", Toast.LENGTH_SHORT).show();

                }

            }


            if( !editLoinPanjang.getText().toString().equals("") ) {


                if( Double.parseDouble(editLoinPanjang.getText().toString()) < 30 || Double.parseDouble(editLoinPanjang.getText().toString()) > 115  ){

                    checking = 1;

                    Toast.makeText(getActivity().getApplicationContext(),
                            "Panjang loin harus diantara 30Cm sampai 11Cm", Toast.LENGTH_SHORT).show();
                }

            }


            if( !editLoinBerat.getText().toString().equals("") ) {


                if( Double.parseDouble(editLoinBerat.getText().toString()) < 2 || Double.parseDouble(editLoinBerat.getText().toString()) > 25  ){

                    if( Double.parseDouble(editLoinBerat.getText().toString()) < 2 && ( kode_tpi.equals("820401") || kode_tpi.equals("820402") || kode_tpi.equals("820403") || kode_tpi.equals("820404") ) )
                    {
                        if( Double.parseDouble(editLoinBerat.getText().toString()) < 1 ){
                            checking = 1;
                            //wilayah maluku utara boleh dibawah 2 kg namun tidak boleh kurang dari 1 kg
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Berat harus diantara 1Kg sampai 25Kg Untuk wilayah Maluku Utara", Toast.LENGTH_SHORT).show();
                        }else{
                            checking = 0;
                            //wilayah maluku utara boleh dibawah 2 kg
                        }


                    }else {

                        checking = 1;

                        Toast.makeText(getActivity().getApplicationContext(),
                                "Berat harus diantara 2Kg sampai 25Kg", Toast.LENGTH_SHORT).show();
                    }
                }

            }


            if( checking <= 0 ) {

                System.out.println(keyKode + " " + radioButtonSp.getText() + " " + editBerat.getText().toString() + " " + editPanjang.getText().toString() + " " + editLoinBerat.getText().toString() + " " + editLoinPanjang.getText().toString() + " " + keyInsang + " " + keyPerut + " " + keyDagingPerut + " ");

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                String insertSql = "UPDATE " + TABEL_IKAN_BESAR + " SET kode = '" + keyKode + "' ,  species = '" + radioButtonSp.getText() + "' , species  = '" + radioButtonSp.getText() + "' , berat = '" + editBerat.getText().toString() + "' , panjang = '" + editPanjang.getText().toString() + "' ,  loin_berat = '" + editLoinBerat.getText().toString() + "' , loin_panjang = '" + editLoinPanjang.getText().toString() + "' , insang = '" + keyInsang + "' , perut = '" + keyPerut + "' , pl_daging = '" + keyDagingPerut + "' WHERE id_trip = '" + id_Trip + "' and id = '" + idDb + "'";
                Log.e("update sqlite ", "" + insertSql);
                try {
                    db.execSQL(insertSql);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Data Berhasil Insert !", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {

                    if (e instanceof SQLiteConstraintException) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Data Sudah pernah dimasukkan!", Toast.LENGTH_SHORT).show();

                    } else if (e instanceof SQLiteDatatypeMismatchException) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Kesalahan Tipe Data!", Toast.LENGTH_SHORT).show();
                    } else {
                        throw e;
                    }
                    System.out.println(e);

                }
                db.close();


            }

        }
    }


    private void loadSpinnerData(String selectQuery , Spinner spiner) {

        List<String> labels = new ArrayList<String>();

        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(   cursor.getString(0) );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
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


    private void clearText(){

        editPanjang.setText("");
        editBerat.setText("");
        editLoinPanjang.setText("");
        editLoinBerat.setText("");

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

}
