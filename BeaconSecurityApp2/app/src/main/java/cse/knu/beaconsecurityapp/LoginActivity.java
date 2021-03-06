package cse.knu.beaconsecurityapp;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cse.knu.beaconsecurityapp.Users.PlaceInfo;
import cse.knu.beaconsecurityapp.Users.UserInfo;

/**
 * Created by juhee on 2016-07-29.
 */
public class LoginActivity extends Activity {
    EditText editId;
    EditText editpw;
    String macId;

    String request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("start", "start");

        editId=(EditText)findViewById(R.id.edit_id);
        editpw=(EditText)findViewById(R.id.edit_pw);

        macId=getMacId();

    }

    private String getMacId(){
        WifiManager mWifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();

        return mWifiInfo.getMacAddress();
    }

    public void onClick(View view){
        String id=editId.getText().toString();
        String pw=editpw.getText().toString();

        RequestParams params = new RequestParams();

        switch (view.getId()){
            case R.id.btn_user:
                request="user";
                editId.setText(macId);
                editId.setFocusable(false);
                editId.setClickable(false);
                break;
            case R.id.btn_place:
                editId.setText("");
                request="place";
                editId.setFocusable(true);
                editId.setClickable(true);
                break;
            case R.id.btn_login:
                if(request.equals("user"))
                    new Login(new UserInfo(id,pw)).execute();
                else if(request.equals("place"))
                    new Login(new PlaceInfo(id,pw)).execute();
                break;
            case R.id.btn_sign:
                if(id.length()>1){
                    if(pw.length()>4){
                        if(request.equals("user"))
                            new Signup(new UserInfo(id,pw)).execute();
                        else if(request.equals("place"))
                            new Signup(new PlaceInfo(id,pw)).execute();
                    }else {
                        Toast.makeText(this,"PW length must longer than 4",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"ID length musb longer thatn 1",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class Login extends AsyncTask<Void, Void, String> {
        private final String TAG="Login";

        private UserInfo userInfo;
        private PlaceInfo placeInfo;

        private String result;

        /*
        로그인
         */
        public Login(UserInfo userInfo) {this.userInfo=userInfo;}
        public Login(PlaceInfo placeInfo) {this.placeInfo=placeInfo;}

        @Override
        protected String doInBackground(Void... params) {
            String urlString = Static.baseURL + "users_login/";
            urlString += request;

            HttpURLConnection conn = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            URL url = null;
            try {
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2500 * 1000);
                conn.setReadTimeout(200 * 1000);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Gson gson = new Gson();
                JSONObject job = null;
                if(request.equals("user"))
                    job = new JSONObject(gson.toJson(userInfo));
                else if(request.equals("place"))
                    job = new JSONObject(gson.toJson(placeInfo));

                os = conn.getOutputStream();
                os.write(job.toString().getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();

                switch (responseCode) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        result = sb.toString();
//                        Log.d(TAG, result);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String param){
            super.onPostExecute(param);
            if(param.equals("login_success\n")){
                Log.d(TAG,result);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if(param.equals("login_fail\n")){
                Log.d(TAG,result);
                Toast.makeText(LoginActivity.this,"Wrong ID or PW",Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*
    회원가입
     */
    private class Signup extends AsyncTask<Void,Void,String>{
        private final String TAG="Signup";

        private UserInfo userInfo;
        private PlaceInfo placeInfo;

        private String result;
        /*
        회원가입
         */
        public Signup(UserInfo userInfo){this.userInfo=userInfo;}
        public Signup(PlaceInfo placeInfo) {this.placeInfo=placeInfo;}
        @Override
        protected String doInBackground(Void... params){
            String urlString = Static.baseURL + "users/";
            urlString += request;

            HttpURLConnection conn = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            URL url = null;
            try {
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2500 * 1000);
                conn.setReadTimeout(200 * 1000);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Gson gson = new Gson();
                JSONObject job = null;
                if(request.equals("user"))
                    job = new JSONObject(gson.toJson(userInfo));
                else if(request.equals("place"))
                    job = new JSONObject(gson.toJson(placeInfo));

                os = conn.getOutputStream();
                os.write(job.toString().getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();

                switch (responseCode) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        result = sb.toString();
//                        Log.d(TAG, result);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String param){
            super.onPostExecute(param);
            if(param.equals("signup_success\n")){
                Log.d(TAG,result);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if(param.equals("signup_duplicate\n")){
                Log.d(TAG,result);
                Toast.makeText(LoginActivity.this,"Same ID exists",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
