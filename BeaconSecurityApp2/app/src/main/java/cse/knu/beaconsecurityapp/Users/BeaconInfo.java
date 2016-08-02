package cse.knu.beaconsecurityapp.Users;

import java.io.Serializable;

/**
 * Created by juhee on 2016-07-29.
 */
public class BeaconInfo implements Serializable{
    private String beaMdadr;
    private String beaOption;
    private String beaAdr;

    public BeaconInfo() {
    }

    public BeaconInfo(String beaMdadr, String beaOption, String beaAdr) {
        this.beaMdadr = beaMdadr;
        this.beaOption = beaOption;
        this.beaAdr = beaAdr;
    }

    public String getBeaMdadr() {
        return beaMdadr;
    }

    public void setBeaMdadr(String beaMdadr) {
        this.beaMdadr = beaMdadr;
    }

    public String getBeaOption() {
        return beaOption;
    }

    public void setBeaOption(String beaOption) {
        this.beaOption = beaOption;
    }

    public String getBeaAdr() {
        return beaAdr;
    }

    public void setBeaAdr(String beaAdr) {
        this.beaAdr = beaAdr;
    }
}
