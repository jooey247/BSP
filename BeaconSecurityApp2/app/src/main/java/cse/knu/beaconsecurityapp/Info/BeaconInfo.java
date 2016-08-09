package cse.knu.beaconsecurityapp.Info;

import java.io.Serializable;

/**
 * Created by juhee on 2016-07-29.
 */
public class BeaconInfo implements Serializable{
    private String beaMcadrs;
    private String beaAdr;
    private PlcInfo plcInfo;

    public PlcInfo getPlcInfo() {
        return plcInfo;
    }

    public void setPlcInfo(PlcInfo plcInfo) {
        this.plcInfo = plcInfo;
    }

    public BeaconInfo() {
    }

    public BeaconInfo(String beaMcadrs, String beaAdr,PlcInfo plcInfo) {
        this.beaMcadrs = beaMcadrs;
        this.beaAdr = beaAdr;
        this.plcInfo=plcInfo;
    }

    public String getBeaMdadr() {
        return beaMcadrs;
    }

    public void setBeaMdadr(String beaMcadrs) {
        this.beaMcadrs = beaMcadrs;
    }

    public String getBeaAdr() {
        return beaAdr;
    }

    public void setBeaAdr(String beaAdr) {
        this.beaAdr = beaAdr;
    }
}
