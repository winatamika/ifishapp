package com.example.mick.dockandroidlogin.sampling;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.adapter.SectionsPageAdapter;
import com.example.mick.dockandroidlogin.fragment.Tab1FragmentRingkasanKecil;
import com.example.mick.dockandroidlogin.fragment.Tab2FragmentIkanKecil;
import com.example.mick.dockandroidlogin.fragment.Tab3FragmentRingkasanBesar;
import com.example.mick.dockandroidlogin.fragment.Tab4FragmentIkanBesar;

public class SamplingInfo extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private String idTrip , tipeTemplate, kode_tpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampling_sampling_info);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

          /* Get All Importtant Intent  */
        idTrip = getIntent().getStringExtra("idTrip");
        tipeTemplate = getIntent().getStringExtra("tipeTemplate");
        kode_tpi =  getIntent().getStringExtra("kode_tpi");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1FragmentRingkasanKecil(), "Sum Small");
        adapter.addFragment(new Tab2FragmentIkanKecil(), "Small Fish");
        adapter.addFragment(new Tab3FragmentRingkasanBesar(), "Sum Large");
        adapter.addFragment(new Tab4FragmentIkanBesar(), "Large Fish");
        viewPager.setAdapter(adapter);
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

    public String getIdTrip() {
        return idTrip;
    }

    public String getTipeTemplate() {
        return tipeTemplate;
    }

    public String getKode_tpi() {
        return kode_tpi;
    }

}
