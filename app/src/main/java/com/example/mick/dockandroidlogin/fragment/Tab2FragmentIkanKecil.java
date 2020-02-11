package com.example.mick.dockandroidlogin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.AdapterIkanKecil;
import com.example.mick.dockandroidlogin.model.InputFilterMinMax;
import com.example.mick.dockandroidlogin.sampling.SamplingInfo;
import com.example.mick.dockandroidlogin.sampling.SmallFishAdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mick on 2/15/2018.
 */

public class Tab2FragmentIkanKecil extends Fragment {

    DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
    String TABEL_IKAN_KECIL = "tb_ikan_kecil" ;
    String[] idDatabase, container_no , berat_keranjang  , species , panjang , nomorIkan  ;
    String idTrip , tipeTemplate ;
    TextView notFound ;
    ListView lvIkanKecil;
    AdapterIkanKecil ikanKecilAdapter ;
    EditText editKontainer , editKeranjang , editSpecies , editPanjang ;
    private Button buttonSave;
    private RadioGroup radioGroupSpecies;
    private RadioButton radioButtonSp;
    Integer getSpecies ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_ikan_kecil,container,false);


        SamplingInfo activity = (SamplingInfo) getActivity();
        idTrip = activity.getIdTrip();
        tipeTemplate = activity.getTipeTemplate();

        notFound = (TextView) view.findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);

        lvIkanKecil = (ListView) view.findViewById(R.id.ikanKecil);


        refreshList();


        //Button buttonAdd=(Button) view.findViewById(R.id.buttonAdd);
        FloatingActionButton buttonAdd = (FloatingActionButton)view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //showDialog();
                Intent intent = new Intent(Tab2FragmentIkanKecil.this.getActivity(), SmallFishAdd.class);
                intent.putExtra("idTrip", idTrip);
                startActivity(intent);

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

        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_IKAN_KECIL  + " where id_trip = '" + idTrip +"' order by container_no * 1 , id   ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        idDatabase = new String[cursor.getCount()];
        container_no = new String[cursor.getCount()];
        berat_keranjang = new String[cursor.getCount()];
        species = new String[cursor.getCount()];
        panjang = new String[cursor.getCount()];
        nomorIkan =  new String[cursor.getCount()];


        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                idDatabase[cc] =  cursor.getString(cursor.getColumnIndex("id"));
                container_no[cc] = cursor.getString(cursor.getColumnIndex("container_no"));
                berat_keranjang[cc] = cursor.getString(cursor.getColumnIndex("berat_keranjang"));
                species[cc] = cursor.getString(cursor.getColumnIndex("species"));
                panjang[cc] = cursor.getString(cursor.getColumnIndex("panjang"));
                nomorIkan[cc] = Integer.toString (cc + 1 ) ;


                notFound.setVisibility(View.INVISIBLE);
            }


        }else{
            notFound.setVisibility(View.VISIBLE);
        }

        cursor.close();
        db.close();

        ikanKecilAdapter = new AdapterIkanKecil(Tab2FragmentIkanKecil.this.getActivity() , container_no ,  berat_keranjang  , species , panjang , nomorIkan );
        lvIkanKecil.setAdapter(ikanKecilAdapter);
        justifyListViewHeightBasedOnChildren(lvIkanKecil);
        lvIkanKecil.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                                System.out.println("Do Update "+ idTrip + " " + idDatabase[position] + " ");
                                showDialogUpdate(idTrip ,idDatabase[position] );

                                break;
                            case 1:

                                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_IKAN_KECIL + " WHERE id_trip = '" + idTrip + "' and id = '" + idDatabase[position] + "'");
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
        View promptView = layoutInflater.inflate(R.layout.dialogbox_ikankecil_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Small Fish Add");
        alertDialogBuilder.setView(promptView);

        editKontainer = (EditText) promptView.findViewById(R.id.editKontainer);
        editKeranjang = (EditText) promptView.findViewById(R.id.editKeranjang);
        editPanjang = (EditText) promptView.findViewById(R.id.editPanjang);
        editPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(15, 100)});

        radioGroupSpecies = (RadioGroup) promptView.findViewById(R.id.radioGroupSpecies);
        radioButtonSp = (RadioButton) promptView.findViewById(R.id.radioSKJ);

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

        buttonSave = (Button) promptView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                saveAdd();
                refreshList();
                clearPanjang();
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
        View promptView = layoutInflater.inflate(R.layout.dialogbox_ikankecil_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Small Fish Add");
        alertDialogBuilder.setView(promptView);

        editKontainer = (EditText) promptView.findViewById(R.id.editKontainer);
        editKeranjang = (EditText) promptView.findViewById(R.id.editKeranjang);
        editPanjang = (EditText) promptView.findViewById(R.id.editPanjang);
        //editPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(15, 100)});
        radioGroupSpecies = (RadioGroup) promptView.findViewById(R.id.radioGroupSpecies);
        radioButtonSp = (RadioButton) promptView.findViewById(R.id.radioSKJ);

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

        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_IKAN_KECIL + " where id_trip = '" + id_Trip + "' and id = '" + idDb + "'", null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    editKontainer.setText(cursor.getString(2));
                    editKeranjang.setText(cursor.getString(3));
                    editPanjang.setText(cursor.getString(5));

                    if(cursor.getString(4).equals("ALB")){
                        getSpecies = 0;
                    }else if(cursor.getString(4).equals("BET")){
                        getSpecies = 1;
                    }else if(cursor.getString(4).equals("SKJ")){
                        getSpecies = 2;
                    }else if(cursor.getString(4).equals("YFT")){
                        getSpecies = 3;
                    }
                    ((RadioButton)radioGroupSpecies.getChildAt(getSpecies)).setChecked(true);

                } while (cursor.moveToNext());
            }

            buttonSave = (Button) promptView.findViewById(R.id.buttonSave);
            buttonSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    saveUpdate(id_Trip, idDb);
                    refreshList();
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

        if(!editKontainer.getText().toString().equals("") && !editKeranjang.getText().toString().equals("") && !editPanjang.getText().toString().equals("") ) {


            System.out.println(radioButtonSp.getText() + " " + editKontainer.getText().toString() + " " + editKeranjang.getText().toString() + " " + editPanjang.getText().toString());

            DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
            SQLiteDatabase db = databaseHandler.getWritableDatabase();

            String insertSql = "INSERT INTO " + TABEL_IKAN_KECIL + " ( id_trip , container_no , berat_keranjang , species , panjang  ) VALUES ('" + idTrip + "' , '" + editKontainer.getText().toString() + "' , '" + editKeranjang.getText().toString() + "' , '" + radioButtonSp.getText() + "' , '" + editPanjang.getText().toString() + "'  )";
            Log.e("insert sqlite ", "" + insertSql);
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


    private void saveUpdate(String id_Trip , String idDb){
        /*
        System.out.println(id_Trip + " " + idDb );
        System.out.println(radioButtonSp.getText() + " " +  editKontainer.getText().toString() + " " + editKeranjang.getText().toString() + " " + editPanjang.getText().toString() );
        */
        if(!editKontainer.getText().toString().equals("") && !editKeranjang.getText().toString().equals("") && !editPanjang.getText().toString().equals("") ) {


            if (Double.parseDouble(editPanjang.getText().toString()) < 15 || Double.parseDouble(editPanjang.getText().toString()) > 85) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "GAGAL ! Panjang ikan harus diantara 15Cm sampai 85cm !", Toast.LENGTH_SHORT).show();

            } else {

                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                SQLiteDatabase db = databaseHandler.getWritableDatabase();

                String updateKeranjang = "UPDATE " + TABEL_IKAN_KECIL + " SET  berat_keranjang = '" + editKeranjang.getText().toString() + "'  WHERE id_trip = '" + id_Trip + "' and container_no = '" + editKontainer.getText().toString() + "'";
                db.execSQL(updateKeranjang);

                String insertSql = "UPDATE " + TABEL_IKAN_KECIL + " SET container_no = '" + editKontainer.getText().toString() + "' ,  berat_keranjang = '" + editKeranjang.getText().toString() + "' , species  = '" + radioButtonSp.getText() + "' , panjang = '" + editPanjang.getText().toString() + "'  WHERE id_trip = '" + id_Trip + "' and id = '" + idDb + "'";
                Log.e("update sqlite ", "" + insertSql);
                try {
                    db.execSQL(insertSql);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Data Berhasil Update !", Toast.LENGTH_SHORT).show();
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


    private void clearPanjang(){

        editPanjang.setText("");

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
