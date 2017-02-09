package com.neil.swapnilparashar.youarethevoter.backend;

import java.util.Date;
import java.util.List;

/**
 * Created by swapnilparashar on 08/10/15.
 */
public class User {
    private int userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userDeviceID;
    private Date userDOB;
    private int locationID;

    // Other parameters
    private List<Integer> listOfInterestGroups;
    private String message;

    public String getUserDeviceID() {
        return userDeviceID;
    }

    public void setUserDeviceID(String userDeviceID) {
        this.userDeviceID = userDeviceID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public List<Integer> getListOfInterestGroups() {
        return listOfInterestGroups;
    }

    public void setListOfInterestGroups(List<Integer> listOfInterestGroups) {
        this.listOfInterestGroups = listOfInterestGroups;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
