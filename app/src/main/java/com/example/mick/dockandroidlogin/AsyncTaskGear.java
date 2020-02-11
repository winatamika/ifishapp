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

public class AsyncTaskGear extends AsyncTask<Void, Void, Void> {

    private String url = Server.URL + "masterGears";

    String data="";
    String singleParsed = "";
    String dataParsed = "";


    Context context;

    public AsyncTaskGear(Context context){
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

                    String k_alattangkap =  String.valueOf(val.get("k_alattangkap"));
                    String indonesia =  String.valueOf(val.get("indonesia"));
                    String english =  String.valueOf(val.get("english"));
                    String status =  String.valueOf(val.get("status"));

                    //System.out.println(k_alattangkap + " " + indonesia +"  " + english + " " + status);
                    databaseHandler.saveMasterGear(new MasterGear( k_alattangkap , indonesia , english  , status ));

                }
            }

            List<MasterGear> lists=databaseHandler.findAllGear();
            for(MasterGear list:lists){
                Log.d("MasterDataGear", "K_alat :"+list.getKalattangkap()+" | indonesia :"+list.getIndonesia()+" | english:"+list.getEnglish() + "| status:"+list.getStatus());
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
