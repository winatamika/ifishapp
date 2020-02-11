package com.example.mick.dockandroidlogin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Mick on 2/3/2018.
 */

public class AsyncTaskVessels extends AsyncTask<Void, Void, Void> {

    private String url = Server.URL + "masterVessels";

    String data="";
    String singleParsed = "";
    String dataParsed = "";


    Context context;

    public AsyncTaskVessels(Context context){
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        DatabaseHandler databaseHandler =new DatabaseHandler(context);

        try {
            //String geturl =  Server.URL ;
            String geturl = url;
            URL url = new URL(geturl) ;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            /* It Should Be Using This*/
            InputStreamReader responseBodyReader = new InputStreamReader(inputStream, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);

            String line =  "";
            if( httpURLConnection.getResponseCode() == 200) {
                while (line != null) {

                    line = bufferedReader.readLine();
                    data = data + line;

                }
                JSONObject JO = new JSONObject(data);
                JSONArray JA = JO.getJSONArray("data");
                JSONObject val = null;

                for (int i = 0; i < JA.length(); i++) {
                    val = JA.getJSONObject(i);

                    String vic =  String.valueOf(val.get("vic"));
                    String versi =  String.valueOf(val.get("versi"));
                    String nama_kapal =  String.valueOf(val.get("nama_kapal"));
                    String nama_pemilik =  String.valueOf(val.get("nama_pemilik"));
                    String tahun_pembuatan =  String.valueOf(val.get("tahun_pembuatan"));
                    String jumlah_abk =  String.valueOf(val.get("jumlah_abk"));
                    String jenis_alat_tangkap =  String.valueOf(val.get("jenis_alat_tangkap"));
                    String panjang_kapal =  String.valueOf(val.get("panjang_kapal"));
                    String lebar =  String.valueOf(val.get("lebar"));
                    String dalam =  String.valueOf(val.get("dalam"));
                    String gross_tonnage =  String.valueOf(val.get("gross_tonnage"));
                    String pk =  String.valueOf(val.get("pk"));
                    String bahan_kapal =  String.valueOf(val.get("bahan_kapal"));
                    String k_tpi =  String.valueOf(val.get("k_tpi"));
                    String n_tpi =  String.valueOf(val.get("n_tpi"));
                    String k_perusahaan =  String.valueOf(val.get("k_perusahaan"));
                    String n_perusahaan =  String.valueOf(val.get("n_perusahaan"));
                    String nama_kapten = String.valueOf(val.get("nama_kapten"));

                    //System.out.println(vic + " " + nama_kapal +"  " + panjang_kapal + " " + k_perusahaan);
                    databaseHandler.saveMasterVessels(new MasterVessels(  vic  ,  versi  ,  nama_kapal ,  nama_pemilik ,   tahun_pembuatan ,  jumlah_abk  ,  jenis_alat_tangkap ,  panjang_kapal  , lebar , dalam , gross_tonnage ,  pk , bahan_kapal ,  k_tpi , n_tpi ,  k_perusahaan ,  n_perusahaan , nama_kapten ));

                }
            }

            List<MasterVessels> lists=databaseHandler.findAllVessels();
            for(MasterVessels list:lists){
                Log.d("MasterDataVessels", "vic :"+list.getVic()+" | nama_kapal :"+list.getNama_kapal()+" | n_tpi : " +list.getN_tpi()  + " | kapten : " + list.getNama_kapten() );
            }


            httpURLConnection.disconnect();
            databaseHandler.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }




        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        //MainActivity.textJson.setText(this.dataParsed);


    }


}
