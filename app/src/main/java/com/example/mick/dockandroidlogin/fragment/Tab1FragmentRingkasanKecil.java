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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.AdapterRingkasan;
import com.example.mick.dockandroidlogin.sampling.SamplingInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mick on 2/15/2018.
 */

public class Tab1FragmentRingkasanKecil extends Fragment  {

    DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
    private static final String TAG = "Tab1Fragment";
    String TABEL_RINGKASAN = "tb_ringkasan_kecil" ;
    String[] kode , deskripsi , total_kg   ;
    String idTrip , tipeTemplate ;
    TextView notFound ;
    ListView lvRingkasan;
    AdapterRingkasan ringkasanAdapter ;
    private Button btnTEST;
    EditText editKode , editDeskripsi , editTotalKg ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_ringkasan_ikan_kecil,container,false);


        SamplingInfo activity = (SamplingInfo) getActivity();
        idTrip = activity.getIdTrip();
        tipeTemplate = activity.getTipeTemplate();


        notFound = (TextView) view.findViewById(R.id.notFound);
        notFound.setVisibility(View.INVISIBLE);

        lvRingkasan = (ListView) view.findViewById(R.id.ringkasan);

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

        return view;
    }


    private void refreshList(){
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * from " + TABEL_RINGKASAN  + " where id_trip = '" + idTrip +"'  ",null);

        final List<String> list = new ArrayList<String>();
        cursor.moveToFirst();

        kode = new String[cursor.getCount()];
        deskripsi = new String[cursor.getCount()];
        total_kg = new String[cursor.getCount()];


        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                kode[cc] = cursor.getString(cursor.getColumnIndex("kode"));
                deskripsi[cc] = cursor.getString(cursor.getColumnIndex("deskripsi"));
                total_kg[cc] = cursor.getString(cursor.getColumnIndex("total_kg"));

                notFound.setVisibility(View.INVISIBLE);
            }


        }else{
            notFound.setVisibility(View.VISIBLE);
        }
        cursor.close();
        db.close();

        ringkasanAdapter = new AdapterRingkasan(Tab1FragmentRingkasanKecil.this.getActivity() , kode , deskripsi , total_kg );
        lvRingkasan.setAdapter(ringkasanAdapter);
        //Make ListView not use scrollview
        justifyListViewHeightBasedOnChildren(lvRingkasan);

        lvRingkasan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                                System.out.println("Do Update "+ idTrip + " " + kode[position] + " " + deskripsi[position] + " " + total_kg[position] );
                                showDialogUpdate(idTrip ,kode[position] );

                                break;
                            case 1:

                                DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
                                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                                db.execSQL("DELETE FROM " + TABEL_RINGKASAN + " WHERE id_trip = '" + idTrip + "' and kode = '" + kode[position] + "'");
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
        View promptView = layoutInflater.inflate(R.layout.dialogbox_ringkasan_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Ringkasan Add");
        alertDialogBuilder.setView(promptView);

        editKode = (EditText) promptView.findViewById(R.id.editKode);
        editDeskripsi = (EditText) promptView.findViewById(R.id.editDeskripsi);
        editTotalKg = (EditText) promptView.findViewById(R.id.editTotalKg);

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


    private void showDialogUpdate(String id_trip , final String Kode) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dialogbox_ringkasan_add, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Ringkasan Add");
        alertDialogBuilder.setView(promptView);

        editKode = (EditText) promptView.findViewById(R.id.editKode);
        editDeskripsi = (EditText) promptView.findViewById(R.id.editDeskripsi);
        editTotalKg = (EditText) promptView.findViewById(R.id.editTotalKg);

        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABEL_RINGKASAN + " where id_trip = '" + idTrip + "' and kode = '" + Kode + "'", null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {


                    editKode.setText(cursor.getString(1));
                    editDeskripsi.setText(cursor.getString(2));
                    editTotalKg.setText(cursor.getString(3));

                } while (cursor.moveToNext());
            }


            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveUpdate( idTrip , Kode);
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

        if(!editKode.getText().toString().equals("") && !editTotalKg.getText().toString().equals("") ) {

            System.out.println(editKode.getText().toString() + " " + editDeskripsi.getText().toString() + " " + editTotalKg.getText().toString() );


            DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
            SQLiteDatabase db = databaseHandler.getWritableDatabase();

            String insertSql = "INSERT INTO " + TABEL_RINGKASAN + " ( id_trip , kode , deskripsi , total_kg ) VALUES ('" + idTrip + "' , '" + editKode.getText().toString() + "' , '" + editDeskripsi.getText().toString() + "' , '" + editTotalKg.getText().toString() + "'  )";
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


    private  void saveUpdate(String id_trip , String Kode ){


        if(!editKode.getText().toString().equals("") && !editTotalKg.getText().toString().equals("") ) {

            System.out.println(id_trip + " " + " " + Kode + " " + editKode.getText().toString() + " " + editDeskripsi.getText().toString() + " " + editTotalKg.getText().toString() );

            DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
            SQLiteDatabase db = databaseHandler.getWritableDatabase();
            String insertSql = "UPDATE " + TABEL_RINGKASAN + " SET kode = '" + editKode.getText().toString() + "' ,  deskripsi = '" + editDeskripsi.getText().toString() + "' , total_kg  = '" + editTotalKg.getText().toString() + "'  WHERE id_trip = '" + id_trip + "' and kode = '" + Kode + "'";
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
