package cse.knu.beaconsecurityapp.UserActivity;

import cse.knu.beaconsecurityapp.SetActivity;

import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.*;
import android.content.Intent;
import cse.knu.beaconsecurityapp.R;
import cse.knu.beaconsecurityapp.SetFragment;

import android.support.v4.view.GravityCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ToggleButton;

import com.estimote.sdk.SystemRequirementsChecker;

public class user_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    private WifiManager wifiManager;
    private AudioManager audioManager;
    private CameraManager cameraManager;

    android.hardware.Camera camera;
    android.hardware.Camera.Parameters p;
    private String user_original_option_info = new String();

    private String audiostate = new String();
    private String wifistate = new String();
    private String flashstate =new String();
    private String camerastate = new String("1");

    private int wifi_info = 0;
    private int sound_info = 1;
    private int camera_info = 2;
    private int flash_info = 3;

    ToggleButton toggle_Button;
    ToggleButton toggle_Button2;
    ToggleButton toggle_Button3;
    ToggleButton toggle_Button4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(
                this,dlDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        dlDrawer.setDrawerListener(dtToggle);
        dtToggle.syncState();

        camera = android.hardware.Camera.open();
        camera.release();
        p = camera.getParameters();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        toggle_Button=(ToggleButton)findViewById(R.id.toggleButton);
        toggle_Button2=(ToggleButton)findViewById(R.id.toggleButton2);
        toggle_Button3=(ToggleButton)findViewById(R.id.toggleButton3);
        toggle_Button4=(ToggleButton)findViewById(R.id.toggleButton4);

        toggle_Button.setChecked(true);

        getUsersOption();
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
    }


    public void setUsersOption(String opt){

        switch (opt.indexOf(wifi_info)){
            case 0 :
                //wifi끄기
                wifiManager.setWifiEnabled(false);
                toggle_Button.setChecked(false);
                break;
            case 1 :
                wifiManager.setWifiEnabled(true);
                toggle_Button.setChecked(true);
                break;
            default:
        }
        switch (opt.indexOf(sound_info)){
            case 0 :
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                toggle_Button2.setChecked(false);
                break;
            case 1 :
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                toggle_Button3.setChecked(true);
                break;
            default:
        }
        switch (opt.indexOf(camera_info)){
            case 0 :
                camera.lock();
                toggle_Button3.setChecked(false);
                break;
            case 1 :
                camera.release();
                toggle_Button4.setChecked(true);
                break;
            default:
        }
        switch (opt.indexOf(flash_info)){
            case 0 :
                p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                break;
            case 1 :
                p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                break;
            default:
        }
    }

    public void getUsersOption(){

        switch (audioManager.getRingerMode()){
            case AudioManager.RINGER_MODE_NORMAL:
                audiostate="1";
                toggle_Button2.setChecked(true);
                break;
            case AudioManager.RINGER_MODE_SILENT:
                audiostate="0";
                toggle_Button2.setChecked(false);
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                audiostate="0";
                toggle_Button2.setChecked(false);
                break;
            default:
        }

        switch (wifiManager.getWifiState()){
            case WifiManager.WIFI_STATE_ENABLED:
                wifistate="1";
                toggle_Button.setChecked(true);
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                wifistate="0";
                toggle_Button.setChecked(false);
                break;
            default:
        }

        switch (p.getFlashMode()){
            case android.hardware.Camera.Parameters.FLASH_MODE_TORCH:
                flashstate="1";
                toggle_Button4.setChecked(true);
                break;
            case android.hardware.Camera.Parameters.FLASH_MODE_OFF:
                flashstate="0";
                toggle_Button4.setChecked(false);
                break;
            default:
        }

        toggle_Button3.setChecked(true);

        user_original_option_info = wifistate+audiostate+camerastate+flashstate;
        System.out.println("***************"+user_original_option_info);
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
        getMenuInflater().inflate(R.menu.user_main, menu);
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume(){
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }
}
