package goosetech.limitbreaker;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marte on 1/15/2018.
 */

public class user {
    //Helper class for device info
    public static class deviceInfo{
        String type, id;
        boolean permissions;
        public deviceInfo(){
            type="not provided";
            id="not provided";
            permissions=false;
        }
        public deviceInfo(String type, String id){
            this.type=type;
            this.id=id;
            permissions=false;
        }
        public String getType(){return type;}
        public String getID(){return id;}
    }
    final static String TAG="debugging_user";
    String fullName, username,email,password, phoneNumber;
    ArrayList<String> activityLog;
    ArrayList<Location> locationHistory;
    ArrayList<deviceInfo> devices;
    public user(){
        //Default constructor
        fullName ="Molten Cocoa";
        username ="MC_Fire";
        email = "lavatechbeats@gmail.com";
        password="adminPass";
        phoneNumber = "734.972.2049";
        activityLog = new ArrayList<>();
        locationHistory = new ArrayList<>();
        devices = new ArrayList<>();
    }
    public user(String fullName,String username,String email, String password, String phoneNumber){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        activityLog = new ArrayList<>();
        locationHistory = new ArrayList<>();
        devices = new ArrayList<>();
    }
    //User may need to change info
    public void updateInfo(int choice, String info){
        switch(choice){
            case 1:
                this.username=info;
                break;
            case 2:
                this.email=info;
                break;
            case 3:
                this.password=info;
                break;
            case 4:
                this.phoneNumber=info;
                break;
            case 5:
                this.fullName=info;
                break;
            default: Log.w(TAG,"Invalid choice");
        }

    }
    //activityLog needs a format for the logs it store
    public void updateActivityLog(String date, String summary){
        String logEntry = "Last active on "+date+":\n"+summary+"\n\n";
        activityLog.add(logEntry);
    }
    public void updateLocationHistory(Location lastLocation){
        locationHistory.add(lastLocation);
    }

}
