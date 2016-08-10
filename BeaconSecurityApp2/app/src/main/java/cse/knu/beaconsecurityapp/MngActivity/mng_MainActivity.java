package cse.knu.beaconsecurityapp.MngActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cse.knu.beaconsecurityapp.Info.MngInfo;
import cse.knu.beaconsecurityapp.R;

public class mng_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    String mng="mng";
    String TAG="Main";

    TextView mngName;

    private Fragment beaconmngFragment;
    MngInfo mngInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        beaconmngFragment = new BeaconMngFragment();
        setSupportActionBar(toolbar);

        // param key : mng
        // result : 0
        Intent intent = getIntent();
         mngInfo = (MngInfo)intent.getSerializableExtra(mng);
        intent.putExtra("결과","매니져 로그인성공");
        setResult(0,intent);
        Log.v(TAG,mngInfo.getMngId()+"manager login success");

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(
                this,dlDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dlDrawer.setDrawerListener(dtToggle);
        dtToggle.syncState();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mngName=(TextView)findViewById(R.id.mngname);
//        mngName.setText(mngInfo.getMngId());

        /*
        기본화면 설정
         */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mng_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     //   if (id == R.id.action_settings) {
     //       return true;
     //   }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mngplc){
            //transaction.replace(R.id.container,beaconmngFragment);
            replaceFragment(1);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(int fragmentId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragmentId==1){
            //Manage Beacon Fragment
            beaconmngFragment=BeaconMngFragment.newInstance(mngInfo.getMngId());
            transaction.replace(R.id.container,new BeaconMngFragment());
        }

//        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof BeaconMngFragment){
            beaconmngFragment=fragment;
            transaction.replace(R.id.container, beaconmngFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();

    }
}
