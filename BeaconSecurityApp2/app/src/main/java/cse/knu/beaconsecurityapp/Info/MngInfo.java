package cse.knu.beaconsecurityapp.Info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by juhee on 2016-08-03.
 */
public class MngInfo implements Serializable {
    private String mngId;
    private String mngPw;
    private String mngName;
    private String mngAdrs;
    private List<PlcInfo> plcinfos;

    public MngInfo(String mngId, String mngPw) {
        super();
        this.mngId = mngId;
        this.mngPw = mngPw;
    }

    public MngInfo() {
        super();
    }

    public MngInfo(String mngId, String mngPw, String mngName, String mngAdrs, List<PlcInfo> plcinfos) {
        super();
        this.mngId = mngId;
        this.mngPw = mngPw;
        this.mngName = mngName;
        this.mngAdrs = mngAdrs;
        this.plcinfos = plcinfos;
    }

    public String getMngId() {
        return mngId;
    }

    public void setMngId(String mngId) {
        this.mngId = mngId;
    }

    public String getMngPw() {
        return mngPw;
    }

    public void setMngPw(String mngPw) {
        this.mngPw = mngPw;
    }

    public String getMngName() {
        return mngName;
    }

    public void setMngName(String mngName) {
        this.mngName = mngName;
    }

    public String getMngAdrs() {
        return mngAdrs;
    }

    public void setMngAdrs(String mngAdrs) {
        this.mngAdrs = mngAdrs;
    }

    public List<PlcInfo> getPlcinfos() {
        return plcinfos;
    }

    public void setPlcinfos(List<PlcInfo> plcinfos) {
        this.plcinfos = plcinfos;
    }

    public void addPlcinofs(PlcInfo plcinfo){
        plcinfos.add(plcinfo);
    }
}
