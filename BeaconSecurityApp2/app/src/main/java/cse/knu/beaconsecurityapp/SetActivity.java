package cse.knu.beaconsecurityapp;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SetActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        camera = android.hardware.Camera.open();
        camera.release();
        p = camera.getParameters();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
    }

    public SetActivity(){}

    public void setUsersOption(String opt){

        switch (opt.indexOf(wifi_info)){
            case 0 :
                //wifi끄기
                wifiManager.setWifiEnabled(false);
                break;
            case 1 :
                wifiManager.setWifiEnabled(true);
                break;
            default:
        }
        switch (opt.indexOf(sound_info)){
            case 0 :
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case 1 :
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            default:
        }
        switch (opt.indexOf(camera_info)){
            case 0 :
                camera.lock();
                break;
            case 1 :
                camera.release();
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
                break;
            case AudioManager.RINGER_MODE_SILENT:
                audiostate="0";
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                audiostate="0";
                break;
            default:
        }

        switch (wifiManager.getWifiState()){
            case WifiManager.WIFI_STATE_ENABLED:
                wifistate="1";
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                wifistate="0";
                break;
            default:
        }

        switch (p.getFlashMode()){
            case android.hardware.Camera.Parameters.FLASH_MODE_TORCH:
                flashstate="1";
                break;
            case android.hardware.Camera.Parameters.FLASH_MODE_OFF:
                flashstate="0";
                break;
            default:
        }

        user_original_option_info = wifistate+audiostate+camerastate+flashstate;
    }
}
