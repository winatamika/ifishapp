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

public class AsyncTaskLanding extends AsyncTask<Void, Void, Void> {

    private String url = Server.URL + "masterLanding";

    String data="";
    String singleParsed = "";
    String dataParsed = "";

    Context context;
    public AsyncTaskLanding(Context context){
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

                    String k_tpi =  String.valueOf(val.get("k_tpi"));
                    String n_tpi =  String.valueOf(val.get("n_tpi"));
                    String fake =  String.valueOf(val.get("fake"));

                    //System.out.println(val.get("k_tpi") + " " + val.get("n_tpi") +"  " + val.get("fake"));
                    databaseHandler.saveMasterTpi(new MasterTpi( k_tpi , n_tpi , fake  ));

                }
            }

            List<MasterTpi> lists=databaseHandler.findAll();
            for(MasterTpi list:lists){
                Log.d("MasterDataTpi", "ID :"+list.getKtpi()+" | Nama :"+list.getNtpi()+" | Fake:"+list.getFake());
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
