package cse.knu.beaconsecurityapp.Users;

import java.io.Serializable;

/**
 * Created by juhee on 2016-07-26.
 */
public class UserInfo implements Serializable{
    private String userId;
    private String userPw;
    private String plcName;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlcName() {
        return plcName;
    }

    public void setPlcName(String plcName) {
        this.plcName = plcName;
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

    public UserInfo(String userId, String userPw, String plcName,String userName) {
        this.userId = userId;
        this.userPw = userPw;
        this.plcName = plcName;
        this.userName= userName;
    }

    public UserInfo(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    public UserInfo(){

    }
}
