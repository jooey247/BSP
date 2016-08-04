package cse.knu.beaconsecurityapp;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

//import com.estimote.sdk.BeaconManager;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import cse.knu.beaconsecurityapp.Users.UserInfo;

public class ConnectActivity extends AppCompatActivity implements BeaconConsumer{

    private BeaconManager beaconManager;
    private Beacon temp_beacon;
    private UserInfo current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }

    public ConnectActivity(){}

    @Override
    public void onBeaconServiceConnect(){
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if(beacons.size()>0){
                    for(Beacon beacon : beacons){
                        //테스트위해서 1미터 내의 비콘만 감지하도록
                        if(beacon.getDistance()<1){
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ConnectActivity.this);
                            dialog.setTitle("알림").setMessage("비콘과 연결되었습니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).create().show();
                            temp_beacon = beacon;

                            //비콘정보 + 유저정보 서버로 보냄
                            //beacon.getBluetoothAddress()
                        }

                    }
                }
            }
        });
        try{
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId",null,null,null));
        }catch (RemoteException e){}
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        beaconManager.unbind(this);
    }
}
