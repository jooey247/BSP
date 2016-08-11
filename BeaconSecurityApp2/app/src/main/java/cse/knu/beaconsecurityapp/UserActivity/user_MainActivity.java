package cse.knu.beaconsecurityapp.UserActivity;

import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.*;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;

import cse.knu.beaconsecurityapp.Info.BeaconInfo;
import cse.knu.beaconsecurityapp.Info.MngInfo;
import cse.knu.beaconsecurityapp.Info.PlcInfo;
import cse.knu.beaconsecurityapp.Info.UserInfo;
import cse.knu.beaconsecurityapp.MngActivity.mng_MainActivity;
import cse.knu.beaconsecurityapp.R;
import cse.knu.beaconsecurityapp.Static;

import android.support.v4.view.GravityCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.estimote.sdk.SystemRequirementsChecker;
/*import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
*/
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.lang.String;
import java.util.UUID;

public class user_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    int temp;
    String request;

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

    private BeaconManager beaconManager;
    private Region region;
    private boolean isConnected;

    private List<Beacon> beaconList = new ArrayList<>();
    List<String> beaconPlaceList = new ArrayList<String>();
    //String beaconPlaceList[];

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

        toggle_Button=(ToggleButton)findViewById(R.id.toggleButton);
        toggle_Button2=(ToggleButton)findViewById(R.id.toggleButton2);
        toggle_Button3=(ToggleButton)findViewById(R.id.toggleButton3);
        toggle_Button4=(ToggleButton)findViewById(R.id.toggleButton4);

        toggle_Button.setClickable(false);
        toggle_Button2.setClickable(false);
        toggle_Button3.setClickable(false);
        toggle_Button4.setClickable(false);

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener(){
            @Override
            public void onBeaconsDiscovered(Region region,List<Beacon> beacons){
                if(!beacons.isEmpty()){
                    final Beacon nearestBeacon = beacons.get(0);

                    if(!isConnected&&nearestBeacon.getRssi()>-70){
                        isConnected=true;

                        final BeaconInfo temp = new BeaconInfo();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(user_MainActivity.this);
                        dialog.setTitle("Beacon Connect")
                                .setMessage("Do you want to connect?")
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog,int which){

                                        System.out.println("12#!asdfasdfasdfasdfasdfasdf@%#!$^#$$T%QWEFASDGADFASD");

                                        setUsersOption("1000");

                                        temp.setBeaAdr(null);
                                        temp.setBeaMdadr(nearestBeacon.getMacAddress().toString());
                                        temp.setPlcInfo(null);
                                        new getPlaceInfo(temp).execute();

                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog,int which){
                                        isConnected=false;
                                    }
                                }).create().show();
                        if(!isConnected)
                            Toast.makeText(user_MainActivity.this,"연결 종료",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        region = new Region("ranged region", /*UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D")*/null,null,null);

        camera=android.hardware.Camera.open();
        p = camera.getParameters();
        camera.release();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        getUsersOption();
    }

    public void setUsersOption(String opt){

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

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
        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startRanging(region);
            }
        });
    }

    /*
    @Override
    @Deprecated
    public void onBeaconServiceConnect(){
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if(beacons.size()>0){
                    beaconList.clear();
                    beaconPlaceList.clear();
                    for(Beacon beacon : beacons){
                        beaconList.add(beacon);
                        beaconPlaceList.add(beacon.getId2().toString());

                        if(beacon.getDistance()<0.1) {

                            System.out.println("***************************************");


                            AlertDialog.Builder builder = new AlertDialog.Builder(user_MainActivity.this);
                            builder.setTitle("비콘 연결").setMessage("비콘이 감지되었습니다.")
                                    .setPositiveButton("연결",null)
                                    .setNegativeButton("취소",null)
                                    .show();

                            try {

                                AlertDialog.Builder alert = new AlertDialog.Builder(user_MainActivity.this);

                                alert.setTitle("비콘 연결")
                                        .setMessage("비콘이 감지되었습니다")
                                        .setPositiveButton("연결", null)
                                        .setNegativeButton("취소",null)
                                        .show();
                            }catch (Exception e){}
                        }
                    }
                }
            }
        });
        try{
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId",null,null,null));
        }catch (RemoteException e){}
    }

    */

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public void onPause(){
        super.onPause();
    }




    private class getPlaceInfo extends AsyncTask<Void, Void, String> {

        private final String TAG="getPlaceInfo";

        private BeaconInfo beaconInfo;
        private String result;

        public getPlaceInfo(BeaconInfo beaconInfo){
            this.beaconInfo = beaconInfo;
        }

        @Override
        protected String doInBackground(Void... params) {
            String urlString = Static.baseURL + "/place/bcon/";
            urlString += beaconInfo.getBeaMdadr();
            System.out.println("//////////////////////////////////////////////////////////"+urlString);
            //+= request;

            HttpURLConnection conn = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            URL url = null;
            try {
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2500 * 1000);
                conn.setReadTimeout(200 * 1000);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Gson gson = new Gson();
                JSONObject job = null;
                    job = new JSONObject(gson.toJson(beaconInfo.getBeaMdadr()));

                os = conn.getOutputStream();
                os.write(job.toString().getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();

                switch (responseCode) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        result = sb.toString();
                        System.out.println("**********QEFASDVDFVSD***************************??//"+result);
            /*String urlString = Static.baseURL + "/place/search";
            urlString += request;

            HttpURLConnection conn = null;
            //ObjectOutputStream os = null;
            //ObjectInputStream is = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            URL url = null;
            try {
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2500 * 1000);
                conn.setReadTimeout(200 * 1000);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Gson gson = new Gson();
                JSONObject job = null;
                    job = new JSONObject(gson.toJson(beaconInfo));

                os = conn.getOutputStream();
                //os = (ObjectOutputStream) conn.getOutputStream();
                os.write(job.toString().getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();

                switch (responseCode) {
                    case 200:
                    case 201:
                        Object plc = new ObjectInputStream(conn.getInputStream());
                        //PlcInfo plc_temp = (PlcInfo)plc;
                        result = (PlcInfo)plc;
                        */
                        System.out.println("***********************78768687687**************************");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        /*
        @Override
        protected void onPostExecute(String param) {
            super.onPostExecute(param);

        }
        */
    }
}
