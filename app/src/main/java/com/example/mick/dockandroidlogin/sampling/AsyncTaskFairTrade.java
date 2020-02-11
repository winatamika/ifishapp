package com.example.mick.dockandroidlogin.sampling;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.MasterFT;
import com.example.mick.dockandroidlogin.Server;

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
 * Created by Mick on 2/24/2019.
 */

public class AsyncTaskFairTrade extends AsyncTask<Void, Void, Void> {


    private String url = Server.URL + "masterFT";

    String data="";
    String singleParsed = "";
    String dataParsed = "";


    Context context;

    public AsyncTaskFairTrade(Context context){
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

                    String id =  String.valueOf(val.get("id"));
                    String k_tpi =  String.valueOf(val.get("k_tpi"));
                    String nama_ft =  String.valueOf(val.get("nama_ft"));

                    //System.out.println(category + " " + scientific_name +"  " + species_name + " " + description);
                    databaseHandler.saveMasterFT(new MasterFT( id , k_tpi , nama_ft   ));

                }
            }

            List<MasterFT> lists=databaseHandler.findAllFt();
            for(MasterFT list:lists){
                Log.d("MasterDataFT", "ID :"+list.getId()+" | nama_ft :"+list.getNamaFt()+" | k_tpi :"+list.getKtpi() );
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
