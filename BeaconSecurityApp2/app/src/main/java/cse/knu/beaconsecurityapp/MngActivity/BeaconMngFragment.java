package cse.knu.beaconsecurityapp.MngActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cse.knu.beaconsecurityapp.Info.BeaconInfo;
import cse.knu.beaconsecurityapp.Info.MngInfo;
import cse.knu.beaconsecurityapp.R;

/**
 * Created by juhee on 2016-08-04.
 */
public class BeaconMngFragment extends Fragment {
    private static final String ARG_PARAM1="MngID";
    private static final String ARG_PARAM2="PlcID";

    private String mParam1;
    private String mParam2;

    Button btn_wifi;

    ArrayList<BeaconInfo> beaconInfos;
    MngInfo mngInfo;

    public BeaconMngFragment(){
    }

    public static BeaconMngFragment newInstance(String mparam1){
        BeaconMngFragment fragment = new BeaconMngFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1,mparam1);
  //      args.putString(ARG_PARAM2,mparam2);
        fragment.setArguments(args);
        Log.v("BeaMngFrag rcv : ",mparam1);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            //매니저 아이디
            mParam1=getArguments().getString(ARG_PARAM1);
            //플레이스 아이디
           // mParam1=getArguments().getString(ARG_PARAM2);
        }
        else Log.e("ARG","null");
    }

    private TextView textmngid;
    private TextView textplcid;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstace){
        View view = inflater.inflate(R.layout.mng_fragment_beaconmng,container,false);
        getActivity().setTitle("Manage your place");

        //wifi btn
        switch (view.getId()){
            case R.id.btn_wifi :
                Log.v("BeaconMngFrag","WiFi btn");
                break;
        }
        return inflater.inflate(R.layout.mng_fragment_beaconmng,container,false);
    }


}
