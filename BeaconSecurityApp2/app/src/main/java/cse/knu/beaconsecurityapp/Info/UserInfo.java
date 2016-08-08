package cse.knu.beaconsecurityapp.Info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhee on 2016-07-26.
 */
public class UserInfo implements Serializable{
    private String userId;
    private String userPw;

    private String userName;
    private List<PlcInfo> plcInfos = new ArrayList<>();

    public void addplcInfos(PlcInfo plcInfo){
        plcInfos.add(plcInfo);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfo(String userId, String userPw,String userName) {
        this.userId = userId;
        this.userPw = userPw;

        this.userName= userName;
    }

    public UserInfo(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    public UserInfo(){

    }
}
