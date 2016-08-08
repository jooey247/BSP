package cse.knu.beaconsecurityapp;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

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

    ToggleButton toggle_Button;
    ToggleButton toggle_Button2;
    ToggleButton toggle_Button3;
    ToggleButton toggle_Button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main); //activity_set);

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

        getUsersOption();
    }

    public SetActivity(){}

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
    }
}
