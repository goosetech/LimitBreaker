package goosetech.limitbreaker;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marte on 1/15/2018.
 */

public class user {
    //Helper class for device info
    public class deviceInfo{
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
        public String getType(){
            return type;
        }


        public String getId() {
            return id;
        }

        public boolean hasPermissions() {
            return permissions;
        }
    }
    final static String TAG="debugging_user";
    String fullName, username,email, phoneNumber;
    ArrayList<String> activityLog;
    ArrayList<Location> locationHistory;
    ArrayList<deviceInfo> devices;
    public user(){
        //Default constructor
        fullName ="Molten Cocoa";
        username ="MC_Fire";
        email = "lavatechbeats@gmail.com";
        phoneNumber = "734.972.2049";
        activityLog = new ArrayList<>();
        locationHistory = new ArrayList<>();
        devices = new ArrayList<>();
    }
    public user(String fullName,String username,String email, String phoneNumber){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        activityLog = new ArrayList<>();
        locationHistory = new ArrayList<>();
        devices = new ArrayList<>();
    }

    //activityLog needs a format for the logs it store
    public void updateActivityLog(String date, String summary){
        String logEntry = "Last active on "+date+":\n"+summary+"\n\n";
        activityLog.add(logEntry);
    }
    public void updateLocationHistory(Location lastLocation){
        locationHistory.add(lastLocation);
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getActivityLog() {
        String log="";
        for (String logEntry:activityLog){
            log +=logEntry;
        }
        return log;
    }

    public String getLocationHistory() {
        String log="";
        for (Location logEntry:locationHistory){
            log +=logEntry.toString();
        }
        return log;
    }

    public String getDevices() {
        String log="";
        for(deviceInfo dev:devices){
            String building=dev.getType()+" "+dev.getId()+dev.hasPermissions();
            log+=building+"\n";
        }
        return log;
    }
}
