package cse.knu.beaconsecurityapp.Users;

import java.io.Serializable;

/**
 * Created by juhee on 2016-07-15.
 */
public class PlaceInfo implements Serializable{
    private String plcId;
    private String plcName;
    private String plcPw;
    private String plcAdrs;
    /*
    2016-07-29
    서버 디비 수정후 unserinfo 리스트 추가하기
     */
    public PlaceInfo(){

    }

    public PlaceInfo(String plcId, String plcPw) {
        this.plcId = plcId;
        this.plcPw = plcPw;
    }

    public PlaceInfo(String plcId, String plcPw, String plcName, String plcAdrs) {
        this.plcId = plcId;
        this.plcName = plcName;
        this.plcPw = plcPw;
        this.plcAdrs = plcAdrs;

    }

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

    public String getPlcPw() {
        return plcPw;
    }

    public void setPlcPw(String plcPw) {
        this.plcPw = plcPw;
    }

    public String getPlcAdrs() {
        return plcAdrs;
    }

    public void setPlcAdrs(String plcAdrs) {
        this.plcAdrs = plcAdrs;
    }

}
