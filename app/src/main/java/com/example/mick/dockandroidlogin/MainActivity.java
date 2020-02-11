package com.example.mick.dockandroidlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mick.dockandroidlogin.sampling.AsyncTaskFairTrade;

import static com.example.mick.dockandroidlogin.Server.isConnectingToInternet;

public class MainActivity extends AppCompatActivity {

    Button btn_logout , goTo;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static TextView textJson;

    Intent intent;

    ConnectivityManager conMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler=new DatabaseHandler(this);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        goTo = (Button) findViewById(R.id.goTo);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        txt_id.setText("ID : " + id);
        txt_username.setText("USERNAME : " + username);
        //textJson = (TextView)findViewById(R.id.textJson);

            if(isConnectingToInternet(MainActivity.this))
            {
                Toast.makeText(getApplicationContext(),"internet is available",Toast.LENGTH_LONG).show();

                AsyncTaskLanding masterTpi = new AsyncTaskLanding(MainActivity.this);
                masterTpi.execute();

                AsyncTaskSupplier masterSupplier = new AsyncTaskSupplier(MainActivity.this);
                masterSupplier.execute();

                AsyncTaskSetup masterSetup = new AsyncTaskSetup(MainActivity.this);
                masterSetup.execute();

                AsyncTaskSpecies masterSpecies = new AsyncTaskSpecies(MainActivity.this);
                masterSpecies.execute();

                AsyncTaskGear masterGear = new AsyncTaskGear(MainActivity.this);
                masterGear.execute();

                AsyncTaskBaits masterBaits = new AsyncTaskBaits(MainActivity.this);
                masterBaits.execute();

                AsyncTaskVessels masterVessels = new AsyncTaskVessels(MainActivity.this);
                masterVessels.execute();

                AsyncTaskKualitas masterKualitas = new AsyncTaskKualitas(MainActivity.this);
                masterKualitas.execute();

                AsyncTaskFairTrade masterFT = new AsyncTaskFairTrade(MainActivity.this);
                masterFT.execute();

            }
            else {
                System.out.print("internet is not available");
            }


        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                finish();
                System.exit(0);

                /*
                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
                */
            }
        });


        goTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                intent = new Intent(MainActivity.this, Dashboard.class);
                id = getIntent().getStringExtra(TAG_ID);
                username = getIntent().getStringExtra(TAG_USERNAME);
                Server.usernamae = username ;
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                finish();

                startActivity(intent);
            }
        });


    }
}
