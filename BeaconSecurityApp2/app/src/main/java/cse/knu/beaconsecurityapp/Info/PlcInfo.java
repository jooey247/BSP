package cse.knu.beaconsecurityapp.Info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by juhee on 2016-08-03.
 */
public class PlcInfo implements Serializable {
    private String plcId;
    private String plcName;
    private int plcOption;
    private List<UserInfo> userinfos;
    private MngInfo mngInfo;
    private List<BeaconInfo> beaconInfos;

    public PlcInfo(String plcId, String plcName, int plcOption, MngInfo mngInfo) {
        super();
        this.plcId = plcId;
        this.plcName = plcName;
        this.plcOption = plcOption;
        this.mngInfo = mngInfo;
    }


    public PlcInfo(String plcId, String plcName, int plcOption, List<BeaconInfo> beaconInfos) {
        super();
        this.plcId = plcId;
        this.plcName = plcName;
        this.plcOption = plcOption;
        this.beaconInfos = beaconInfos;
    }


    public PlcInfo(String plcId, String plcName, int plcOption) {
        super();
        this.plcId = plcId;
        this.plcName = plcName;
        this.plcOption = plcOption;
    }



    public List<UserInfo> getUserinfos() {
        return userinfos;
    }


    public void addBeaconInfos(BeaconInfo beaconInfo){
        beaconInfos.add(beaconInfo);
    }


    public MngInfo getMngInfo() {
        return mngInfo;
    }


	/*
	 * DAN:INF LOOP
	 *
	public void setMngInfo(MngInfo mngInfo) {
		this.mngInfo = mngInfo;
	}
	*/


    public String getPlcId() {
        return plcId;
    }


    public void setPlcId(String plcId) {
        this.plcId = plcId;
    }


    public String getPlcName() {
        return plcName;
    }


    public void setPlcName(String plcName) {
        this.plcName = plcName;
    }


    public int getPlcOption() {
        return plcOption;
    }


    public void setPlcOption(int plcOption) {
        this.plcOption = plcOption;
    }


    public List<BeaconInfo> getBeaconInfos() {
        return beaconInfos;
    }


    public void setBeaconInfos(List<BeaconInfo> beaconInfos) {
        this.beaconInfos = beaconInfos;
    }


    public PlcInfo(){

    }
}
