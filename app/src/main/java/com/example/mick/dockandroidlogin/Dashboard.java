package com.example.mick.dockandroidlogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mick.dockandroidlogin.fragment.Call;
import com.example.mick.dockandroidlogin.fragment.Dash;
import com.example.mick.dockandroidlogin.fragment.Logout;
import com.example.mick.dockandroidlogin.fragment.Protocol;
import com.example.mick.dockandroidlogin.fragment.Sites;
import com.example.mick.dockandroidlogin.fragment.Trip;

import static com.example.mick.dockandroidlogin.R.id.call;

/**
 * Created by Mick on 2/4/2018.
 */

public class Dashboard extends AppCompatActivity  {


    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this , mDrawerlayout,R.string.open , R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

        View header = nvDrawer.getHeaderView(0);
        TextView user = (TextView) header.findViewById(R.id.username);
        TextView versions = (TextView) header.findViewById(R.id.versions);
        //String username = getIntent().getStringExtra("username");
        //user.setText(username);
        user.setText(Server.usernamae);
        versions.setText(Server.app_version) ;

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment = null ;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.db:
            fragmentClass = Dash.class;
                break;
            case R.id.trip:
                fragmentClass = Trip.class;
                break;
            case R.id.protocol:
                fragmentClass = Protocol.class;
                break;
            case R.id.sites:
                fragmentClass = Sites.class;
                break;
            case call:
                fragmentClass = Call.class;
                break;
            case R.id.logout:
                fragmentClass = Logout.class;
                break;
            default:
                fragmentClass = Dash.class;
        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent , myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();

    }

    private  void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

}
