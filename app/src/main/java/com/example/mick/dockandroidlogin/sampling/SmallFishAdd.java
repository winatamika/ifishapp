package com.example.mick.dockandroidlogin.sampling;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;

/**
 * Created by Mick on 3/29/2018.
 */

public class SmallFishAdd  extends AppCompatActivity {

    String TABEL_IKAN_KECIL = "tb_ikan_kecil" ;
    String idTrip;
    EditText editKontainer , editKeranjang , editSpecies , editPanjang ;
    TextView list1 , list2 ;
    private Button buttonSave;
    private RadioGroup radioGroupSpecies;
    private RadioButton radioButtonSp;
    Integer getSpecies ;
    Button  buttonSeven , buttonEight , buttonNine , buttonFour , buttonFive , buttonSix , buttonOne , buttonTwo , buttonThree , buttonDot , buttonZero , buttonClear  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_smallfish_add);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


        idTrip = getIntent().getStringExtra("idTrip");


        editKontainer = (EditText) findViewById(R.id.editKontainer);
        editKeranjang = (EditText) findViewById(R.id.editKeranjang);
        editPanjang = (EditText) findViewById(R.id.editPanjang);
        list1 = (TextView) findViewById(R.id.list1);
        list2 = (TextView) findViewById(R.id.list2);
        //editPanjang.setFilters(new InputFilter[]{ new InputFilterMinMax(15, 100)});


        radioGroupSpecies = (RadioGroup) findViewById(R.id.radioGroupSpecies);
        radioButtonSp = (RadioButton) findViewById(R.id.radioSKJ);


        radioGroupSpecies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(RadioGroup group, int checkedId)
                                                         {

                                                             int selectedId = radioGroupSpecies.getCheckedRadioButtonId();
                                                             radioButtonSp = (RadioButton) findViewById(selectedId);

                                                             Toast.makeText(SmallFishAdd.this,
                                                                     radioButtonSp.getText(), Toast.LENGTH_SHORT).show();



                                                         }
                                                     }
        );



        buttonNumber();
        refreshBasket();
        refreshJumIkan();


        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                saveAdd();
                clearPanjang();
            }
        });

    }


    private void buttonNumber(){

        buttonZero = (Button) findViewById(R.id.buttonZero) ;
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "0");
            }
        });

        buttonOne = (Button) findViewById(R.id.buttonOne) ;
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "1");
            }
        });


        buttonTwo = (Button) findViewById(R.id.buttonTwo) ;
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "2");
            }
        });


        buttonThree = (Button) findViewById(R.id.buttonThree) ;
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "3");
            }
        });

        buttonFour = (Button) findViewById(R.id.buttonFour) ;
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "4");
            }
        });

        buttonFive = (Button) findViewById(R.id.buttonFive) ;
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "5");
            }
        });

        buttonSix = (Button) findViewById(R.id.buttonSix) ;
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "6");
            }
        });


        buttonSeven = (Button) findViewById(R.id.buttonSeven) ;
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "7");
            }
        });

        buttonEight = (Button) findViewById(R.id.buttonEight) ;
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "8");
            }
        });

        buttonNine = (Button) findViewById(R.id.buttonNine) ;
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText(editPanjang.getText() + "9");
            }
        });

        buttonDot = (Button) findViewById(R.id.buttonDot) ;
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editPanjang.setText(editPanjang.getText() + ".");
                Toast.makeText(SmallFishAdd.this.getApplicationContext(),
                        "Panjang tidak diperbolehkan memakai desimal !", Toast.LENGTH_SHORT).show();

            }
        });

        buttonClear = (Button) findViewById(R.id.buttonClear) ;
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPanjang.setText("");
            }
        });



    }

    private void refreshBasket(){
        list1.setText("");
        DatabaseHandler databaseHandler = new DatabaseHandler(SmallFishAdd.this);

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT distinct(container_no) as container FROM " + TABEL_IKAN_KECIL + " " +
                "where id_trip = '"+ idTrip +"' ",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
                int loop = 0;
                String pemisah = " - ";
            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                if(loop == 0){
                    pemisah = "" ;
                }else{
                    pemisah = " - ";
                }
                //System.out.println(cursor.getString(cursor.getColumnIndex("container")));
                list1.setText(list1.getText() + pemisah + cursor.getString(cursor.getColumnIndex("container")) );

                loop++;
            }
        }
        db.close();


    }


    private void refreshJumIkan(){

        DatabaseHandler databaseHandler = new DatabaseHandler(SmallFishAdd.this);

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(container_no) as jumlah FROM " + TABEL_IKAN_KECIL + " " +
                "where id_trip = '"+ idTrip +"' ",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);

                //System.out.println(cursor.getString(cursor.getColumnIndex("jumlah")));
                list2.setText( " " + cursor.getString(cursor.getColumnIndex("jumlah")) );
                int jumlahIkan = cursor.getInt(cursor.getColumnIndex("jumlah") )  ;
                if(jumlahIkan >= 200){
                    buttonSave.setEnabled(false);
                    Toast.makeText(SmallFishAdd.this.getApplicationContext(),
                            "Perhatian ! Anda sudah melakukan sampling sebanyak 200 Ikan !", Toast.LENGTH_LONG).show();
                }

            }
        }
        db.close();


    }


    private void saveAdd(){

        if(editKeranjang.getText().toString().equals("")){
            editKeranjang.setText("0");
        }

        if(!editKontainer.getText().toString().equals("") && !editKeranjang.getText().toString().equals("") && !editPanjang.getText().toString().equals("") ) {

            if (Double.parseDouble(editPanjang.getText().toString()) < 15 || Double.parseDouble(editPanjang.getText().toString()) > 85) {

                Toast.makeText(SmallFishAdd.this.getApplicationContext(),
                        "GAGAL ! Panjang ikan harus diantara 15Cm sampai 85cm !", Toast.LENGTH_SHORT).show();

            } else {

                System.out.println(radioButtonSp.getText() + " " + editKontainer.getText().toString() + " " + editKeranjang.getText().toString() + " " + editPanjang.getText().toString());

                DatabaseHandler databaseHandler = new DatabaseHandler(SmallFishAdd.this);
                SQLiteDatabase db = databaseHandler.getWritableDatabase();

                String insertSql = "INSERT INTO " + TABEL_IKAN_KECIL + " ( id_trip , container_no , berat_keranjang , species , panjang  ) VALUES ('" + idTrip + "' , '" + editKontainer.getText().toString() + "' , '" + editKeranjang.getText().toString() + "' , '" + radioButtonSp.getText() + "' , '" + editPanjang.getText().toString() + "'  )";
                Log.e("insert sqlite ", "" + insertSql);
                try {
                    db.execSQL(insertSql);
                    Toast.makeText(SmallFishAdd.this.getApplicationContext(),
                            "Data Berhasil Insert !", Toast.LENGTH_SHORT).show();

                    refreshBasket();
                    refreshJumIkan();

                } catch (SQLException e) {

                    if (e instanceof SQLiteConstraintException) {
                        Toast.makeText(SmallFishAdd.this.getApplicationContext(),
                                "Data Sudah pernah dimasukkan!", Toast.LENGTH_SHORT).show();

                    } else if (e instanceof SQLiteDatatypeMismatchException) {
                        Toast.makeText(SmallFishAdd.this.getApplicationContext(),
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
