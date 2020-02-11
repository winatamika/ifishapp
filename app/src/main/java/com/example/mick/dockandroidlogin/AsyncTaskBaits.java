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

public class AsyncTaskBaits extends AsyncTask<Void, Void, Void> {

    private String url = Server.URL + "masterBaits";

    String data="";
    String singleParsed = "";
    String dataParsed = "";


    Context context;

    public AsyncTaskBaits(Context context){
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

                    String category =  String.valueOf(val.get("category"));
                    String scientific_name =  String.valueOf(val.get("scientific_name"));
                    String species_name =  String.valueOf(val.get("species_name"));
                    String description =  String.valueOf(val.get("description"));

                    //System.out.println(category + " " + scientific_name +"  " + species_name + " " + description);
                    databaseHandler.saveMasterBaits(new MasterBaits( category , scientific_name , species_name  , description ));

                }
            }

            List<MasterBaits> lists=databaseHandler.findAllBaits();
            for(MasterBaits list:lists){
                Log.d("MasterDataBaits", "category :"+list.getCategory()+" | scientific_name :"+list.getScientific_name()+" | species_name:"+list.getSpecies_name() + "| description:"+list.getDescription());
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
