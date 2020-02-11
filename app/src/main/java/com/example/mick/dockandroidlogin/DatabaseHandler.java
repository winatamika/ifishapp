package com.example.mick.dockandroidlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mick.dockandroidlogin.sampling.SpinnerObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mick on 2/3/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 14;

    private static final String DATABASE_NAME = "MobileDock10.db";

    private static final String TABLE_MASTER_TPI = "tb_master_tpi";
    private static final String TABLE_MASTER_PERUSAHAAN = "tb_master_perusahaan";
    private static final String TABLE_MASTER_SETUP = "tb_master_setup";
    private static final String TABLE_MASTER_SPECIES = "tb_master_species";
    private static final String TABLE_MASTER_GEAR = "tb_master_gear";
    private static final String TABLE_MASTER_BAITS = "tb_master_baits";
    private static final String TABLE_MASTER_VESSELS =  "tb_master_vessels";
    private static final String TABLE_MASTER_KUALITAS =  "tb_master_kualitas";
    private static final String TABLE_MASTER_FT = "tb_master_ft";

    private static final String TABEL_TRIP_LISTS = "tb_trip_lists";
    private static final String TABEL_TRIP_INFO = "tb_trip_info";
    private static final String TABEL_TRIP_INFO_CATCHAREA = "tb_trip_info_catchArea";
    private static final String TABEL_TRIP_INFO_TUNALCATING = "tb_trip_info_tunaLocating";

    private static final String TABEL_UMPAN = "tb_umpan" ;
    private static final String TABEL_BYCATCH = "tb_bycatch";

    private static final String TABEL_RINGKASAN_KECIL = "tb_ringkasan_kecil" ;
    private static final String TABEL_RINGKASAN_BESAR = "tb_ringkasan_besar";

    private static final String TABEL_IKAN_KECIL = "tb_ikan_kecil";
    private static final String TABEL_IKAN_BESAR = "tb_ikan_besar";

    private static final String TABEL_ETP = "tb_etp";
    private static final String TABEL_ETP_INFO = "tb_etp_info";



    private String  maxId;

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

       try {
           String CREATE_TABLE_MASTER_TPI = "CREATE TABLE " + TABLE_MASTER_TPI + "( k_tpi TEXT PRIMARY KEY NOT NULL, n_tpi TEXT , fake TEXT )";
           db.execSQL(CREATE_TABLE_MASTER_TPI);
            Log.d("DatabaseStatus", "Creating "+ TABLE_MASTER_TPI);
        }catch (SQLException e) {

        }

        try {
            String CREATE_TABLE_MASTER_SUPPLIER = "CREATE TABLE " + TABLE_MASTER_PERUSAHAAN + "( k_perusahaan TEXT PRIMARY KEY NOT NULL, n_perusahaan TEXT , status TEXT )";
            db.execSQL(CREATE_TABLE_MASTER_SUPPLIER);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_SUPPLIER);
        }catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_MASTER_SETUP = "CREATE TABLE " + TABLE_MASTER_SETUP + "( a NUMERIC PRIMARY KEY NOT NULL, b NUMERIC , v NUMERIC , k NUMERIC )";
            db.execSQL(CREATE_TABLE_MASTER_SETUP);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_SETUP);
        }catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_MASTER_SPECIES = "CREATE TABLE " + TABLE_MASTER_SPECIES + "(fishcode VARYING CHARACTER(255) NOT NULL,  scientific_name VARYING CHARACTER(255), species_name VARYING CHARACTER(255), tipe VARYING CHARACTER(255) NOT NULL , PRIMARY KEY (fishcode , tipe))";
            db.execSQL(CREATE_TABLE_MASTER_SPECIES);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_SPECIES);}
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_MASTER_GEAR = "CREATE TABLE " + TABLE_MASTER_GEAR + "(k_alattangkap VARYING CHARACTER(255) NOT NULL, indonesia VARYING CHARACTER(255), english VARYING CHARACTER(255), status VARYING CHARACTER(255) NOT NULL , PRIMARY KEY(k_alattangkap , status ) )";
            db.execSQL(CREATE_TABLE_MASTER_GEAR);
            Log.d("DatabaseStatus", "Creating " + CREATE_TABLE_MASTER_GEAR);
        }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_MASTER_BAITS = "CREATE TABLE " + TABLE_MASTER_BAITS + "(category VARYING CHARACTER(255) NOT NULL , scientific_name VARYING CHARACTER(255) NOT NULL, species_name VARYING CHARACTER(255), description TEXT , PRIMARY KEY(category , scientific_name))";
            db.execSQL(CREATE_TABLE_MASTER_BAITS);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_BAITS);
        }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_MASTER_VESSELS = "CREATE TABLE " + TABLE_MASTER_VESSELS + "(vic  VARYING CHARACTER(255) PRIMARY KEY NOT NULL , versi INTEGER, nama_kapal VARYING CHARACTER(255) , nama_pemilik VARYING CHARACTER(255) , tahun_pembuatan VARYING CHARACTER(255), jumlah_abk INTEGER DEFAULT 0, jenis_alat_tangkap TEXT, panjang_kapal NUMERIC DEFAULT 0 ,  lebar NUMERIC DEFAULT 0  , dalam NUMERIC DEFAULT 0 , gross_tonnage NUMERIC DEFAULT 0 , pk NUMERIC DEFAULT 0 , bahan_kapal VARYING CHARACTER(255) , k_tpi TEXT , n_tpi TEXT, k_perusahaan TEXT, n_perusahaan  TEXT , nama_kapten VARYING CHARACTER(255) )";
            db.execSQL(CREATE_TABLE_MASTER_VESSELS);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_VESSELS);
        }
        catch (SQLException e) {
        }


        try {
            String CREATE_TABLE_MASTER_KUALITAS = "CREATE TABLE " + TABLE_MASTER_KUALITAS + "(k_tpi VARYING CHARACTER NOT NULL , k_perusahaan VARYING CHARACTER NOT NULL  , tipe VARYING CHARACTER NOT NULL ,  kode VARYING CHARACTER NOT NULL  , deskripsi TEXT  ,  PRIMARY KEY( k_tpi , k_perusahaan , tipe , kode  ) )";
            db.execSQL(CREATE_TABLE_MASTER_KUALITAS);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_KUALITAS);
        }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_TRIP_LISTS = "CREATE TABLE " + TABEL_TRIP_LISTS + "( id INTEGER PRIMARY KEY autoincrement NOT NULL , k_tpi VARYING CHARACTER(255) , k_perusahaan VARYING CHARACTER(255) , waktu DATE  , jam VARYING CHARACTER(255) , notes TEXT  , template_tipe  VARYING CHARACTER(255), create_time VARYING CHARACTER(255), id_pengguna INTEGER , status VARYING CHARACTER(255) )";
            db.execSQL(CREATE_TABLE_TRIP_LISTS);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_TRIP_LISTS);
        }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_TRIP_INFO = "CREATE TABLE " + TABEL_TRIP_INFO + "( id INTEGER PRIMARY KEY  autoincrement NOT NULL ,  idTrip INTEGER NOT NULL ,  vic VARYING CHARACTER,  total_tangkapan NUMERIC DEFAULT 0 ,  ikan_hilang NUMERIC DEFAULT 0 ,  lama_trip NUMERIC DEFAULT 0, satuan_trip VARYING CHARACTER , bbm NUMERIC DEFAULT 0  ,  jum_hari_memancing INTEGER DEFAULT 0, es NUMERIC DEFAULT 0 ,  awak VARYING CHARACTER, kapten CHARACTER VARYING  ,  gt NUMERIC DEFAULT 0, panjang NUMERIC DEFAULT 0 , pk NUMERIC DEFAULT 0 , bahan CHARACTER VARYING, hl_tipe_mata_pancing CHARACTER VARYING, hl_handline_troll CHARACTER VARYING, hl_alattangkaplain CHARACTER VARYING, pl_jumlah_pancing CHARACTER VARYING , pl_kapasitas_ember CHARACTER VARYING , nama_kapal CHARACTER VARYING , rumpon CHARACTER VARYING , gearType CHARACTER VARYING , gearName CHARACTER VARYING , enumerator_1 CHARACTER VARYING , enumerator_2 CHARACTER VARYING , tlc CHARACTER VARYING , st CHARACTER VARYING , flywire CHARACTER VARYING , pds CHARACTER VARYING , gps CHARACTER VARYING , gps_merk CHARACTER VARYING , daya_cahaya CHARACTER VARYING , kedalaman_min CHARACTER VARYING , kedalaman_max CHARACTER VARYING , jum_palka CHARACTER VARYING , kapasitas_palka CHARACTER VARYING , kapal_andon CHARACTER VARYING , asal_andon CHARACTER VARYING , jum_rumpon CHARACTER VARYING , ft_name CHARACTER VARYING    )";
            db.execSQL(CREATE_TABLE_TRIP_INFO);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_TRIP_INFO);
        }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_TRIP_INFO_CATCHAREA = "CREATE TABLE " + TABEL_TRIP_INFO_CATCHAREA  + "( id_trip INTEGER , grid_a CHARACTER VARYING , grid_b CHARACTER VARYING , PRIMARY KEY(id_trip , grid_a , grid_b )   )";
            db.execSQL(CREATE_TABLE_TRIP_INFO_CATCHAREA);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_TRIP_INFO_CATCHAREA);
        }
        catch (SQLException e) {
        }

        try {
                String CREATE_TABLE_TRIP_INFO_TUNALOCATING = "CREATE TABLE " + TABEL_TRIP_INFO_TUNALCATING + "( id_trip INTEGER , teknik CHARACTER VARYING ,  PRIMARY KEY(id_trip , teknik  )   )";
                db.execSQL(CREATE_TABLE_TRIP_INFO_TUNALOCATING);
                Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_TRIP_INFO_TUNALOCATING);
            }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_UMPAN = "CREATE TABLE " + TABEL_UMPAN + " ( id_trip INTEGER , kategori CHARACTER VARYING , species CHARACTER VARYING , grid_1 CHARACTER VARYING , grid_2 CHARACTER VARYING , total_kg NUMERIC DEFAULT 0  , estimasi_kg NUMERIC DEFAULT 0 , alat_tangkap CHARACTER VARYING , hl_domestic_import CHARACTER VARYING , pl_pengadaan CHARACTER VARYING, pl_ember CHARACTER VARYING ,  PRIMARY KEY(id_trip , kategori , species  )  )";
            db.execSQL(CREATE_TABLE_UMPAN);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_UMPAN);
        }
        catch (SQLException e) {
        }

        try {
            String CREATE_TABLE_BYCATCH = "CREATE TABLE " + TABEL_BYCATCH + " ( id_trip INTEGER , species  CHARACTER VARYING ,  ekor CHARACTER VARYING  , total_kg NUMERIC DEFAULT 0 , estimasi CHARACTER VARYING ,   PRIMARY KEY(id_trip , species  )   )";
            db.execSQL(CREATE_TABLE_BYCATCH);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_BYCATCH);
                }
        catch (SQLException e) {
                }

        try {
            String CREATE_TABLE_RINGKASAN_KECIL = "CREATE TABLE " + TABEL_RINGKASAN_KECIL + " ( id_trip INTEGER , kode  CHARACTER VARYING ,  deskripsi CHARACTER VARYING  , total_kg NUMERIC DEFAULT 0 ,  PRIMARY KEY(id_trip , kode  )   )";
            db.execSQL(CREATE_TABLE_RINGKASAN_KECIL);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_RINGKASAN_KECIL);
                    } catch (SQLException e) {
        }


        try {
            String CREATE_TABLE_RINGKASAN_BESAR = "CREATE TABLE " + TABEL_RINGKASAN_BESAR + " ( id_trip INTEGER , kode  CHARACTER VARYING ,  deskripsi CHARACTER VARYING  , total_kg NUMERIC DEFAULT 0 ,  PRIMARY KEY(id_trip , kode  )   )";
            db.execSQL(CREATE_TABLE_RINGKASAN_BESAR);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_RINGKASAN_BESAR);
         }
        catch (SQLException e) {
        }

        try {String CREATE_TABLE_IKAN_KECIL = "CREATE TABLE " + TABEL_IKAN_KECIL + " ( id INTEGER PRIMARY KEY autoincrement NOT NULL , id_trip INTEGER , container_no  CHARACTER VARYING , berat_keranjang NUMERIC DEFAULT 0 , species CHARACTER VARYING ,  panjang NUMERIC DEFAULT 0   )";
        db.execSQL(CREATE_TABLE_IKAN_KECIL);
        Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_IKAN_KECIL); }
         catch (SQLException e) {
         }

        try {String CREATE_TABLE_IKAN_BESAR = "CREATE TABLE " + TABEL_IKAN_BESAR + " ( id INTEGER PRIMARY KEY autoincrement NOT NULL , id_trip INTEGER , kode CHARACTER VARYING , species CHARACTER VARYING ,  berat NUMERIC DEFAULT 0 , panjang NUMERIC DEFAULT 0 , loin_berat NUMERIC DEFAULT 0 , loin_panjang NUMERIC DEFAULT 0 , insang CHARACTER VARYING , perut CHARACTER VARYING , pl_daging CHARACTER VARYING  )";
            db.execSQL(CREATE_TABLE_IKAN_BESAR);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_IKAN_BESAR); }
             catch (SQLException e) {
         }

        try {String CREATE_TABLE_ETP = "CREATE TABLE " + TABEL_ETP + " ( id INTEGER PRIMARY KEY autoincrement NOT NULL , id_trip INTEGER , hewan CHARACTER VARYING , interaksi CHARACTER VARYING , interaksi_count INTEGER DEFAULT 0 , interaksi_perkiraan CHARACTER VARYING , didaratkan_count INTEGER DEFAULT 0 , didaratkan_perkiraaan CHARACTER VARYING , d1 CHARACTER VARYING , d2 CHARACTER VARYING , d3 CHARACTER VARYING , d4 CHARACTER VARYING , d5 CHARACTER VARYING , d6 CHARACTER VARYING , d7 CHARACTER VARYING , d8 CHARACTER VARYING , d9 CHARACTER VARYING , d10 CHARACTER VARYING , t1 CHARACTER VARYING , t2 CHARACTER VARYING , t3 CHARACTER VARYING , t4 CHARACTER VARYING , t5 CHARACTER VARYING , kode CHARACTER VARYING , yakin_species CHARACTER VARYING , lokal CHARACTER VARYING , yakin_lokal CHARACTER VARYING , lokasi_rumpon CHARACTER VARYING , lokasi_perjalanan CHARACTER VARYING , lokasi_lainnya CHARACTER VARYING , sp_etp CHARACTER VARYING , sp_lain CHARACTER VARYING , alat_tangan CHARACTER VARYING , alat_kapal CHARACTER VARYING , alat_lain CHARACTER VARYING  )";
            db.execSQL(CREATE_TABLE_ETP);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_ETP); }
              catch (SQLException e) {
           }

        try {String CREATE_TABLE_ETP_INFO = "CREATE TABLE " + TABEL_ETP_INFO + " ( id_trip INTEGER PRIMARY KEY NOT NULL , pewawancara  CHARACTER VARYING ,  umur CHARACTER VARYING, lama_bekerja_tahun CHARACTER VARYING, lama_bekerja_bulan CHARACTER VARYING, jabatan CHARACTER VARYING, keterangan CHARACTER VARYING  )";
            db.execSQL(CREATE_TABLE_ETP_INFO);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_ETP_INFO); }
             catch (SQLException e) {
        }


        try {String CREATE_TABLE_MASTER_FT = "CREATE TABLE " + TABLE_MASTER_FT + " ( id INTEGER PRIMARY KEY NOT NULL , k_tpi  CHARACTER VARYING ,  nama_ft CHARACTER VARYING   )";
            db.execSQL(CREATE_TABLE_MASTER_FT);
            Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_FT); }
        catch (SQLException e) {
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //https://thebhwgroup.com/blog/how-android-sqlite-onupgrade


        if(oldVersion < 11){

            /* Data Kapten */
            db.execSQL("DELETE FROM " + TABLE_MASTER_VESSELS);
            onCreate(db);
            String ALTER_TABLE_MASTER_VESSELS = " ALTER TABLE " + TABLE_MASTER_VESSELS + " ADD nama_kapten VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE_MASTER_VESSELS);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE_MASTER_VESSELS);

        }


        if(oldVersion < 12 ){

            try {
                String CREATE_TABLE_MASTER_KUALITAS = "CREATE TABLE " + TABLE_MASTER_KUALITAS + "(k_tpi VARYING CHARACTER NOT NULL , k_perusahaan VARYING CHARACTER NOT NULL  , tipe VARYING CHARACTER NOT NULL ,  kode VARYING CHARACTER NOT NULL  , deskripsi TEXT  ,  PRIMARY KEY( k_tpi , k_perusahaan , tipe , kode  ) )";
                db.execSQL(CREATE_TABLE_MASTER_KUALITAS);
                Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_KUALITAS);
            }
            catch (SQLException e) {
            }

        }

        if(oldVersion < 13 ){

            String ALTER_TABLE1 = " ALTER TABLE " + TABEL_TRIP_INFO + " ADD enumerator_1 VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE1);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE1);

            String ALTER_TABLE2 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD enumerator_2 VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE2);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE2);


        }

        if(oldVersion < 14){

            String ALTER_TABLE1 = " ALTER TABLE " + TABEL_TRIP_INFO + " ADD tlc VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE1);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE1);

            String ALTER_TABLE2 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD st VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE2);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE2);

            String ALTER_TABLE3 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD flywire VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE3);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE3);

            String ALTER_TABLE4 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD pds VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE4);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE4);

            String ALTER_TABLE5 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD gps VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE5);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE5);



            String ALTER_TABLE6 = " ALTER TABLE " + TABEL_TRIP_INFO + " ADD gps_merk VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE6);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE6);

            String ALTER_TABLE7 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD daya_cahaya VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE7);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE7);

            String ALTER_TABLE8 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD kedalaman_min VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE8);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE8);

            String ALTER_TABLE9 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD kedalaman_max VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE9);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE9);

            String ALTER_TABLE10 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD jum_palka VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE10);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE10);




            String ALTER_TABLE11 = " ALTER TABLE " + TABEL_TRIP_INFO + " ADD kapasitas_palka VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE11);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE11);

            String ALTER_TABLE12 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD kapal_andon VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE12);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE12);

            String ALTER_TABLE13 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD asal_andon VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE13);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE13);

            String ALTER_TABLE14 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD jum_rumpon VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE14);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE14);

            String ALTER_TABLE15 = " ALTER TABLE " + TABEL_TRIP_INFO + "  ADD ft_name VARYING CHARACTER(255)  ";
            db.execSQL(ALTER_TABLE15);
            Log.d("DatabaseStatus ", " ALTER  "+ ALTER_TABLE15);



            try {String CREATE_TABLE_MASTER_FT = "CREATE TABLE " + TABLE_MASTER_FT + " ( id INTEGER PRIMARY KEY NOT NULL , k_tpi  CHARACTER VARYING ,  nama_ft CHARACTER VARYING   )";
                db.execSQL(CREATE_TABLE_MASTER_FT);
                Log.d("DatabaseStatus", "Creating "+ CREATE_TABLE_MASTER_FT); }
            catch (SQLException e) {
            }
        }

    /*
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_TPI);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_PERUSAHAAN);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_SETUP);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_SPECIES);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_GEAR);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_BAITS);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_VESSELS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_TRIP_LISTS);
            onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_TRIP_INFO);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_TRIP_INFO_CATCHAREA);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_TRIP_INFO_TUNALCATING);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_UMPAN);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_BYCATCH);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_RINGKASAN_KECIL);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_RINGKASAN_BESAR);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_IKAN_KECIL);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_IKAN_BESAR);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_ETP);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_ETP_INFO);
        onCreate(db);

    */


    }

    public  boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                  String dbfield, String fieldValue) {

        SQLiteDatabase sqldb = getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue + "'";

        Cursor cursor = sqldb.rawQuery(Query, null);

        if(cursor.getCount() >= 1){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public  boolean CheckIsDataAlreadyInDBorNot2(String TableName,
                                                String dbfield, String dbfield2 ,  String fieldValue , String fieldValue2) {

        SQLiteDatabase sqldb = getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue + "' and " + dbfield2 +  " = '"+ fieldValue2 +"'";

        Cursor cursor = sqldb.rawQuery(Query, null);

        if(cursor.getCount() >= 1){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }


    public  boolean CheckIsDataAlreadyInDBorNot3(String TableName,
                                                 String dbfield, String dbfield2 , String dbfield3 ,   String fieldValue , String fieldValue2 , String fieldValue3) {

        SQLiteDatabase sqldb = getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue + "' and " + dbfield2 +  " = '"+ fieldValue2 +"' and " + dbfield3 + " = '"+  fieldValue3 +"'";

        Cursor cursor = sqldb.rawQuery(Query, null);

        if(cursor.getCount() >= 1){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public  boolean  CheckIsVesselUpdated(String TableName,
                                          String dbfield, String dbfield2 ,  String fieldValue , String fieldValue2) {


        SQLiteDatabase sqldb = getReadableDatabase();
        String Query = "Select vic from " + TableName + " where " + dbfield + " = '" + fieldValue + "' and " + dbfield2 +  " = '"+ fieldValue2 +"'";

        Cursor cursor = sqldb.rawQuery(Query, null);

        if(cursor.getCount() >= 1){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;


    }

    /* START MASTER TPI */
    public void saveMasterTpi(MasterTpi mastertpi){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("k_tpi", mastertpi.getKtpi());
        values.put("n_tpi", mastertpi.getNtpi());
        values.put("fake", mastertpi.getFake());

        //db.insert(TABLE_MASTER_TPI, null, values);
        if(this.CheckIsDataAlreadyInDBorNot(TABLE_MASTER_TPI , "k_tpi" , mastertpi.getKtpi() )){

            try {

                    db.insert(TABLE_MASTER_TPI, null, values);

                } catch(SQLException e) {
                    // do some error handling
                }

        }

        db.close();
    }

    public List<MasterTpi> findAll(){
        List<MasterTpi> lists=new ArrayList<MasterTpi>();
        String query="SELECT * FROM "+TABLE_MASTER_TPI;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterTpi master=new MasterTpi();
                master.setKtpi(String.valueOf(cursor.getString(0)));
                master.setNtpi(cursor.getString(1));
                master.setFake(cursor.getString(2));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER TPI*/



     /* START MASTER PERUSAHAAN */
    public void saveMasterSupplier(MasterSupplier mastersupplier){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("k_perusahaan", mastersupplier.getKperusahaan());
        values.put("n_perusahaan", mastersupplier.getNperusahaan());
        values.put("status", mastersupplier.getStatus());

        if(this.CheckIsDataAlreadyInDBorNot(TABLE_MASTER_PERUSAHAAN , "k_perusahaan" , mastersupplier.getKperusahaan() )){

            try {

                db.insert(TABLE_MASTER_PERUSAHAAN, null, values);

            } catch(SQLException e) {
                // do some error handling
            }

        }

        db.close();
    }

    public List<MasterSupplier> findAllSuppliers(){
        List<MasterSupplier> lists=new ArrayList<MasterSupplier>();
        String query="SELECT * FROM "+TABLE_MASTER_PERUSAHAAN;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterSupplier master=new MasterSupplier();
                master.setKperusahaan(String.valueOf(cursor.getString(0)));
                master.setNperusahaan(cursor.getString(1));
                master.setStatus(cursor.getString(2));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER PERUSAHAAN */


    /* START MASTER SETUP */
    public void saveMasterSetup(MasterSetup mastersetup){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("a", mastersetup.getA());
        values.put("b", mastersetup.getB());
        values.put("v", mastersetup.getV());
        values.put("k", mastersetup.getK());

        if(this.CheckIsDataAlreadyInDBorNot(TABLE_MASTER_SETUP , "a" , mastersetup.getA() )){

            try {

                db.insert(TABLE_MASTER_SETUP, null, values);

            } catch(SQLException e) {
                // do some error handling
            }

        }

        db.close();
    }

    public List<MasterSetup> findAllSetup(){
        List<MasterSetup> lists=new ArrayList<MasterSetup>();
        String query="SELECT * FROM "+TABLE_MASTER_SETUP;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterSetup master=new MasterSetup();
                master.setA(String.valueOf(cursor.getString(0)));
                master.setB(cursor.getString(1));
                master.setV(cursor.getString(2));
                master.setK(cursor.getString(3));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER SETUP */


    /* START MASTER SPECIES */
    public void saveMasterSpecies(MasterSpecies masterspecies){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("fishcode", masterspecies.getFishcode());
        values.put("scientific_name", masterspecies.getScientific_name());
        values.put("species_name", masterspecies.getSpecies_name());
        values.put("tipe", masterspecies.getTipe());

        if(this.CheckIsDataAlreadyInDBorNot2(TABLE_MASTER_SPECIES , "fishcode" , "tipe" ,  masterspecies.getFishcode() ,  masterspecies.getTipe() )){

            try {

                db.insert(TABLE_MASTER_SPECIES, null, values);

            } catch(SQLException e) {
                // do some error handling
            }

        }

        db.close();
    }

    public List<MasterSpecies> findAllSpecies(){
        List<MasterSpecies> lists=new ArrayList<MasterSpecies>();
        String query="SELECT * FROM "+TABLE_MASTER_SPECIES;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterSpecies master=new MasterSpecies();
                master.setFishcode(String.valueOf(cursor.getString(0)));
                master.setScientific_name(cursor.getString(1));
                master.setSpecies_name(cursor.getString(2));
                master.SetTipe(cursor.getString(3));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER SPECIES */


    /* START MASTER GEAR */
    public void saveMasterGear(MasterGear mastergear){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("k_alattangkap", mastergear.getKalattangkap());
        values.put("indonesia", mastergear.getIndonesia());
        values.put("english", mastergear.getEnglish());
        values.put("status", mastergear.getStatus());

        if(this.CheckIsDataAlreadyInDBorNot2(TABLE_MASTER_GEAR , "k_alattangkap" , "status" ,  mastergear.getKalattangkap() ,  mastergear.getStatus() )){

            try {

                db.insert(TABLE_MASTER_GEAR, null, values);

            } catch(SQLException e) {
                // do some error handling
            }

        }

        db.close();
    }

    public List<MasterGear> findAllGear(){
        List<MasterGear> lists=new ArrayList<MasterGear>();
        String query="SELECT * FROM "+TABLE_MASTER_GEAR;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterGear master=new MasterGear();
                master.setKalattangkap(String.valueOf(cursor.getString(0)));
                master.setIndonsia(cursor.getString(1));
                master.setEnglish(cursor.getString(2));
                master.setStatus(cursor.getString(3));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER GEAR */


    /* START MASTER BAITS */
    public void saveMasterBaits(MasterBaits masterbaits){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("category", masterbaits.getCategory());
        values.put("scientific_name", masterbaits.getScientific_name());
        values.put("species_name", masterbaits.getSpecies_name());
        values.put("description", masterbaits.getDescription());

        if(this.CheckIsDataAlreadyInDBorNot2(TABLE_MASTER_BAITS , "category" , "scientific_name" ,  masterbaits.getCategory() ,  masterbaits.getScientific_name() )){

            try {

                db.insert(TABLE_MASTER_BAITS, null, values);

            } catch(SQLException e) {
                // do some error handling
            }

        }

        db.close();
    }

    public List<MasterBaits> findAllBaits(){
        List<MasterBaits> lists=new ArrayList<MasterBaits>();
        String query="SELECT * FROM "+TABLE_MASTER_BAITS;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterBaits master=new MasterBaits();
                master.setCategory(String.valueOf(cursor.getString(0)));
                master.setScientific_name(cursor.getString(1));
                master.setSpecies_name(cursor.getString(2));
                master.setDescription(cursor.getString(3));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER BAITS */


    /* START MASTER VESSELS */
    public void saveMasterVessels(MasterVessels mastervessels){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("vic", mastervessels.getVic());
        values.put("versi", mastervessels.getVersi());
        values.put("nama_kapal", mastervessels.getNama_kapal());
        values.put("nama_pemilik", mastervessels.getNama_pemilik());
        values.put("tahun_pembuatan", mastervessels.getTahun_pembuatan());
        values.put("jumlah_abk", mastervessels.getJumlah_abk());
        values.put("jenis_alat_tangkap", mastervessels.getJenis_alat_tangkap());
        values.put("panjang_kapal", mastervessels.getPanjang_kapal());
        values.put("lebar", mastervessels.getLebar());
        values.put("dalam", mastervessels.getDalam());
        values.put("gross_tonnage", mastervessels.getGross_tonnage());
        values.put("pk", mastervessels.getPk());
        values.put("bahan_kapal", mastervessels.getBahan_kapal());
        values.put("k_tpi", mastervessels.getK_tpi());
        values.put("n_tpi", mastervessels.getN_tpi());
        values.put("k_perusahaan", mastervessels.getK_perusahaan());
        values.put("n_perusahaan", mastervessels.getN_perusahaan());
        values.put("nama_kapten" , mastervessels.getNama_kapten());


        if(this.CheckIsDataAlreadyInDBorNot(TABLE_MASTER_VESSELS , "vic" ,  mastervessels.getVic() )){

            try {

                db.insert(TABLE_MASTER_VESSELS, null, values);

            } catch(SQLException e) {
                // do some error handling
            }

        }else if( this.CheckIsVesselUpdated(TABLE_MASTER_VESSELS , "vic" ,  "versi" , mastervessels.getVic()  , mastervessels.getVersi() )){


            String updateVessel = "UPDATE " + TABLE_MASTER_VESSELS + " SET versi = '"+ mastervessels.getVersi() +"', nama_kapal = '"+mastervessels.getNama_kapal()+"' ,  jumlah_abk = '"+  mastervessels.getJumlah_abk() +"' ,  panjang_kapal = '" + mastervessels.getPanjang_kapal() +"'  , gross_tonnage = '"+ mastervessels.getGross_tonnage() +"' , pk = '"+ mastervessels.getPk() +"'    WHERE vic = '" + mastervessels.getVic() +"' " ;
            System.out.println(updateVessel);
            try {
                db.execSQL(updateVessel);
            }
            catch(SQLException e){

            }

        }else{
            //Do Nothing
        }

        db.close();
    }


    public List<MasterVessels> findAllVessels(){
        List<MasterVessels> lists=new ArrayList<MasterVessels>();
        String query="SELECT * FROM "+TABLE_MASTER_VESSELS;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterVessels master=new MasterVessels();
                master.setVic(String.valueOf(cursor.getString(0)));
                master.setVersi(cursor.getString(1));
                master.setNama_kapal(cursor.getString(2));
                master.setNama_pemilik(cursor.getString(3));
                master.setTahun_pembuatan(cursor.getString(4));
                master.setJumlah_abk(cursor.getString(5));
                master.setJenis_alat_tangkap(cursor.getString(6));
                master.setPanjang_kapal(cursor.getString(7));
                master.setLebar(cursor.getString(8));
                master.setDalam(cursor.getString(9));
                master.setGross_tonnage(cursor.getString(10));
                master.setPk(cursor.getString(11));
                master.setBahan_kapal(cursor.getString(12));
                master.setK_tpi(cursor.getString(13));
                master.setN_tpi(cursor.getString(14));
                master.setK_perusahaan(cursor.getString(15));
                master.setN_perusahaan(cursor.getString(16));
                master.setNama_kapten(cursor.getString(17));

                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }

    /*  END MASTER VESSELS */


    /* START MASTER KUALITAS  */

    public void saveMasterKualitas(MasterKualitas masterKualitas){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("k_tpi", masterKualitas.getKtpi());
        values.put("k_perusahaan", masterKualitas.getKperusahaan());
        values.put("tipe", masterKualitas.getTipe());
        values.put("kode", masterKualitas.getKode());
        values.put("deskripsi", masterKualitas.getDeskripsi());


        if(this.CheckIsDataAlreadyInDBorNot3(TABLE_MASTER_KUALITAS , "k_tpi" , "kode" ,  "k_perusahaan" ,  masterKualitas.getKtpi() ,  masterKualitas.getKode() , masterKualitas.getKperusahaan() )){

            try {

                db.insert(TABLE_MASTER_KUALITAS , null, values);

            } catch(SQLException e) {
                // do some error handling
            }
        }else{

            String updateKodeKualitas = "UPDATE " + TABLE_MASTER_KUALITAS + " SET kode = '"+ masterKualitas.getKode() +"', deskripsi = '"+ masterKualitas.getDeskripsi()+"'   WHERE k_tpi = '" + masterKualitas.getKtpi() +"' and k_perusahaan = '" + masterKualitas.getKperusahaan() +"' and tipe = '" + masterKualitas.getTipe() + "' and kode = '"+ masterKualitas.getKode() +"'" ;
            System.out.println(updateKodeKualitas);
            try {
                db.execSQL(updateKodeKualitas);
            }
            catch(SQLException e){

            }

        }
        db.close();
    }


    public List<MasterKualitas> findAllKualitas(){
        List<MasterKualitas> lists=new ArrayList<MasterKualitas>();
        String query="SELECT * FROM "+TABLE_MASTER_KUALITAS;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterKualitas master=new MasterKualitas();
                master.setKtpi(String.valueOf(cursor.getString(0)));
                master.setKperusahaan(cursor.getString(1));
                master.setTipe(cursor.getString(2));
                master.setKode(cursor.getString(3));
                master.setDeskripsi(cursor.getString(4));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }


    /* END MASTER KUALITAS  */

    /* START MASTER FT */
    public void saveMasterFT(MasterFT masterft) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", masterft.getId());
        values.put("k_tpi", masterft.getKtpi());
        values.put("nama_ft", masterft.getNamaFt());

        //db.insert(TABLE_MASTER_TPI, null, values);
        if (this.CheckIsDataAlreadyInDBorNot(TABLE_MASTER_FT, "id", masterft.getId())) {

            try {

                db.insert(TABLE_MASTER_FT, null, values);

            } catch (SQLException e) {
                // do some error handling
            }

        } else {

            String updateKode = "UPDATE " + TABLE_MASTER_FT + " SET k_tpi = '" + masterft.getKtpi() + "', nama_ft = '" + masterft.getNamaFt() + "'   WHERE id = '" + masterft.getId() + "'";
            System.out.println(updateKode);
            try {
                db.execSQL(updateKode);
            } catch (SQLException e) {

            }

            db.close();
        }

    }

    public List<MasterFT> findAllFt(){
        List<MasterFT> lists=new ArrayList<MasterFT>();
        String query="SELECT * FROM "+TABLE_MASTER_FT;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                MasterFT master=new MasterFT();
                master.setId(String.valueOf(cursor.getString(0)));
                master.setKtpi(cursor.getString(1));
                master.setNamaFt(cursor.getString(2));
                lists.add(master);
            }while(cursor.moveToNext());
        }

        return lists;
    }
    /*  END MASTER FT*/



    /* SELECT DATA MASTER AND PUT IT TO SPINNER  */

    public List<SpinnerObject> getAllLabels(String requestedTable){

        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + requestedTable;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add( new SpinnerObject (  cursor.getString(0) ,  cursor.getString(1) ));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

     /* END SELECT DATA MASTER AND PUT IT TO SPINNER  */


    /*  TRIP AND SAMPLING PROCESS*/
    public void insertTriplists( String k_tpi , String k_perusahaan  , String waktu   , String jam  , String notes  , String template_tipe , String create_time , String id_pengguna) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABEL_TRIP_LISTS + " (  k_tpi , k_perusahaan  , waktu   , jam  , notes  , template_tipe , create_time , id_pengguna , status ) " +
                "VALUES ('" + k_tpi + "', '" + k_perusahaan + "' , '"+ waktu +"' , '"+ jam +"' , '"+ notes +"' , '"+ template_tipe +"' , '"+ create_time +"' , '" + id_pengguna +"' , 'process')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }


    public void insertKualitas( String k_tpi , String k_perusahaan ){

        SQLiteDatabase readdatabase = this.getReadableDatabase();

        String selectQuery = "SELECT MAX(id) as id from " + TABEL_TRIP_LISTS + " ";

        Cursor cursor = readdatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                maxId =  cursor.getString(0) ;
            } while (cursor.moveToNext());
        }

        cursor.close();


        String selectQuery1 = "SELECT * FROM " + TABLE_MASTER_KUALITAS + " WHERE k_tpi = '"+ k_tpi +"' and k_perusahaan = '" + k_perusahaan +"' ";

        Log.e("search master kualitas ", "" + selectQuery1);

        String tipe , kode , deskripsi ;
        Cursor cursor1 = readdatabase.rawQuery(selectQuery1, null);
        if (cursor1.moveToFirst()) {
            do {
                tipe = cursor1.getString(2) ;
                kode = cursor1.getString(3) ;
                deskripsi = cursor1.getString(4) ;

                insertKodeKualitasToRingkasan(maxId , tipe , kode , deskripsi);
            } while (cursor1.moveToNext());
        }
        cursor1.close();

        readdatabase.close();


    }


    public void insertKodeKualitasToRingkasan(String maxId , String tipe ,String kode ,String deskripsi){
        SQLiteDatabase database = this.getWritableDatabase();

        if( tipe.equals("small") ) {

            String queryValues = "INSERT INTO " + TABEL_RINGKASAN_KECIL + " (  id_trip , kode , deskripsi , total_kg  ) " +
                    "VALUES ('" + maxId + "', '" + kode + "' , '" + deskripsi + "' , '0'  )";

            Log.e("insert sqlite ", "" + queryValues);
            database.execSQL(queryValues);

        }

        if( tipe.equals("large") ) {

            String queryValues = "INSERT INTO " + TABEL_RINGKASAN_BESAR + " (  id_trip , kode , deskripsi , total_kg  ) " +
                    "VALUES ('" + maxId + "', '" + kode + "' , '" + deskripsi + "' , '0'  )";

            Log.e("insert sqlite ", "" + queryValues);
            database.execSQL(queryValues);

        }

        database.close();

    }

        public ArrayList<HashMap<String, String>> getAllDataTripProcess() {
            ArrayList<HashMap<String, String>> wordList;
            wordList = new ArrayList<HashMap<String, String>>();
            String selectQuery = "SELECT * FROM " + TABEL_TRIP_LISTS;
            SQLiteDatabase database = this.getWritableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", cursor.getString(0));
                    map.put("k_tpi", cursor.getString(1));
                    map.put("k_perusahaan", cursor.getString(2));
                    wordList.add(map);
                } while (cursor.moveToNext());
            }

            Log.e("select sqlite ", "" + wordList);

            database.close();
            return wordList;
        }
    /* END TRIP AND SAMLING PROCESS */

}
