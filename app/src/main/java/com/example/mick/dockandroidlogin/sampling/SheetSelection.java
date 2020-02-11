package com.example.mick.dockandroidlogin.sampling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.R;

import static com.example.mick.dockandroidlogin.R.id.baitInfo;

public class SheetSelection extends AppCompatActivity {

    TextView idTrip, n_tpi, n_perusahaan , tipe_template , textDatetime  ;
    String id , tpi , perusahaan , template, datetime , kode_tpi;
    Intent i , j , k , l , m ;
    CardView tripInfoCardView , BaitInfoCardView, BycatchInfoCardView , SamplingInfoCardView , EtpInfoCardView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_sheet_selection);

        id = getIntent().getStringExtra("idTrip");
        tpi = getIntent().getStringExtra("n_tpi");
        perusahaan = getIntent().getStringExtra("n_perusahaan");
        template =  getIntent().getStringExtra("tipeTemplate");
        datetime = getIntent().getStringExtra("waktu") + " " + getIntent().getStringExtra("jam") ;
        kode_tpi = getIntent().getStringExtra("kode_tpi");

        idTrip = (TextView) findViewById(R.id.idTrip);
        n_tpi = (TextView) findViewById(R.id.n_tpi);
        n_perusahaan = (TextView) findViewById(R.id.n_perusahaan);
        tipe_template = (TextView) findViewById(R.id.tipe_template);
        textDatetime = (TextView) findViewById(R.id.datetime);

        idTrip.setText(id);
        n_tpi.setText(tpi);
        n_perusahaan.setText(perusahaan);
        tipe_template.setText(template);
        textDatetime.setText(datetime);

        i = new Intent(SheetSelection.this,TripInfo.class);
        i.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
        i.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
        i.putExtra("n_tpi",getIntent().getStringExtra("n_tpi"));
        i.putExtra("n_perusahaan",getIntent().getStringExtra("n_perusahaan"));
        tripInfoCardView = (CardView) findViewById(R.id.tripInfo);
        tripInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        j =   new Intent(SheetSelection.this,BaitInfo.class);
        j.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
        j.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
        j.putExtra("tpi",getIntent().getStringExtra("n_tpi"));
        j.putExtra("n_perusahaan",getIntent().getStringExtra("perusahaan"));
        BaitInfoCardView = (CardView) findViewById(baitInfo);
        BaitInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(j);
            }
        });

        k = new Intent(SheetSelection.this,BycatchInfo.class);
        k.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
        k.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
        k.putExtra("tpi",getIntent().getStringExtra("n_tpi"));
        k.putExtra("n_perusahaan",getIntent().getStringExtra("perusahaan"));
        BycatchInfoCardView = (CardView) findViewById(R.id.bycatchInfo);
        BycatchInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(k);
            }
        });

        l = new Intent(SheetSelection.this,SamplingInfo.class);
        l.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
        l.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
        l.putExtra("tpi",getIntent().getStringExtra("n_tpi"));
        l.putExtra("n_perusahaan",getIntent().getStringExtra("perusahaan"));
        l.putExtra("kode_tpi",getIntent().getStringExtra("kode_tpi"));
        SamplingInfoCardView = (CardView) findViewById(R.id.samplingInfo);
        SamplingInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(l);
            }
        });

        m = new Intent(SheetSelection.this,EtpInfo.class);
        m.putExtra("idTrip",getIntent().getStringExtra("idTrip"));
        m.putExtra("tipeTemplate",getIntent().getStringExtra("tipeTemplate"));
        m.putExtra("tpi",getIntent().getStringExtra("n_tpi"));
        m.putExtra("n_perusahaan",getIntent().getStringExtra("perusahaan"));
        EtpInfoCardView = (CardView) findViewById(R.id.etpInfo);
        EtpInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(m);
            }
        });


        /*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        */

        //back to trip lists
        Button buttonBack=(Button)findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(SheetSelection.this, TripProcess.class);
                startActivity(intent);
            }
        });

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
