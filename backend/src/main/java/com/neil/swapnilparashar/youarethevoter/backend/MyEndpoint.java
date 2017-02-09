/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.neil.swapnilparashar.youarethevoter.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.youarethevoter.swapnilparashar.neil.com",
                ownerName = "backend.youarethevoter.swapnilparashar.neil.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi", path = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    private String databaseURL() {
        String url;
        if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            url = System.getProperty("ae-cloudsql.local-database-url");
        }
        return url;
    }

    private int databaseInsertUpdateQuery(String sInsertUpdateQuery) {
        int successFlag = -2;
        String url = databaseURL();
        if(url == null) {
            return successFlag;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                PreparedStatement stmt = conn.prepareStatement(sInsertUpdateQuery);
                successFlag = stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return successFlag;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return successFlag;
        }
        return successFlag;
    }

    /**
     * A simple endpoint method that registers User
     */
    @ApiMethod(name = "registerUser", path = "registerUser")
    public MyBean registerUser(User u) {
        MyBean response = new MyBean();
        String sReply = "";
        String sUserName, sUserEmail, sUserPassword, sUserDeviceID;
        sUserName = u.getUserName();
        sUserEmail = u.getUserEmail();
        sUserPassword = u.getUserPassword();
        sUserDeviceID = u.getUserDeviceID();
        java.util.Date dUserDOB = u.getUserDOB();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sqlDate = sdf.format(dUserDOB);
        int iLocationID = u.getLocationID();
        String sQuery = "SELECT * FROM voter.users where userDeviceId = \'" + sUserDeviceID
                + "\' OR userEmail = \'" + sUserEmail +"\'";
        String url = databaseURL();
        if(url == null) {
            response.setData("ERROR - DB URL exception");
            return response;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                int i = 0;
                while (rs.next()) {
                    ++i;
                }
                if (i==1) {
                    sReply = "ERROR - Device already registered";
                } else {
                    String statement = "INSERT INTO voter.users (userName, userEmail, userPassword, userDeviceID, userDOB, locationID) VALUES " +
                            "(\'" + sUserName + "\', " +
                            "\'" + sUserEmail + "\', " +
                            "\'" + sUserPassword + "\', " +
                            "\'" + sUserDeviceID + "\', " +
                            "\'" + sqlDate + "\', " +
                            iLocationID + ")";
                    PreparedStatement stmt = conn.prepareStatement(statement);
                    stmt.executeUpdate();
                    sReply = "PASS - User Registered with email: "+sUserEmail;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setData("ERROR - SQL Exception: " + e.getMessage());
                return response;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setData("ERROR - SQL Exception: " + e.getMessage());
            return response;
        }
        response.setData(sReply);
        return response;
    }
    /**
     * A simple endpoint method that updates User's interest groups
     */
    @ApiMethod(name = "updateUserInterests", path = "updateUserInterests")
    public MyBean updateUserInterests(User user) {
        MyBean response = new MyBean();
        String sReply = "";
        String sUserEmail = user.getUserEmail();
        List<Integer> interestList = new ArrayList<Integer>(user.getListOfInterestGroups());
        String sQuery = "SELECT * FROM voter.users where userEmail = \'" + sUserEmail + "\'";
        String url = databaseURL();
        int iUserID = 0;
        if(url == null) {
            response.setData("ERROR - DB URL exception");
            return response;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                while (rs.next()) {
                    iUserID = rs.getInt("userID");
                }
                if (iUserID==0) {
                    sReply = "ERROR - No such email registered";
                } else {
                    String statement = "DELETE FROM voter.users_bridge_interest WHERE userID = " + iUserID;
                    PreparedStatement stmt = conn.prepareStatement(statement);
                    stmt.executeUpdate();
                    sReply = "Interest deleted for user with email: "+sUserEmail;

                    String statement2 = "INSERT INTO voter.users_bridge_interest (userID, interestGroupID) VALUES ";
                    for(int i=0; i<interestList.size(); ++i) {
                        if (i == 0) {
                            statement2 = statement2 + "(" + iUserID + "," + interestList.get(i) + ")";
                        } else {
                            statement2 = statement2 + ", (" + iUserID + "," + interestList.get(i) + ")";
                        }
                    }
                    PreparedStatement stmt2 = conn.prepareStatement(statement2);
                    stmt2.executeUpdate();
                    sReply = "Interest groups updated for user with email: "+sUserEmail;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setData("ERROR - SQL Exception: " + e.getMessage());
                return response;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setData("ERROR - SQL Exception: " + e.getMessage());
            return response;
        }
        response.setData(sReply);
        return response;
    }
    /**
     * A simple endpoint method that validates user email and password for login
     */
    @ApiMethod(name = "loginUser", path = "loginUser")
    public User loginUser(User u) {
        User returnUser = u;
        String sUserEmail = "", sUserPassword = "", sPassword = "";
        sUserEmail = u.getUserEmail();
        sUserPassword = u.getUserPassword();
        String url = databaseURL();
        if(url == null) {
            returnUser.setMessage("ERROR - DB URL exception");
            return returnUser;
        }
        try {
            String sQuery = "SELECT * FROM voter.users where userEmail = \'"+sUserEmail+"\'";
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                returnUser.setMessage("ERROR - No such Email Registered");
                while(rs.next()) {
                    sPassword = rs.getString("userPassword");
                    int iUserID = rs.getInt("userID");
                    returnUser.setUserID(iUserID);
                    if (sPassword.equals(sUserPassword)) {
                        returnUser.setUserName(rs.getString("userName"));
                        returnUser.setUserDOB(rs.getDate("userDOB"));
                        returnUser.setLocationID(rs.getInt("locationID"));
                        String sQuery2 = "SELECT * FROM voter.users_bridge_interest where userID = "+iUserID;
                        ResultSet rs2 = conn.createStatement().executeQuery(sQuery2);
                        List<Integer> listInteresetGroups = new ArrayList<Integer>();
                        while(rs2.next()) {
                            listInteresetGroups.add(rs2.getInt("interestGroupID"));
                        }
                        returnUser.setListOfInterestGroups(listInteresetGroups);
                        returnUser.setMessage("PASS - User Logged In");
                    } else {
                        returnUser.setMessage("ERROR - Incorrect Password");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                returnUser.setMessage("ERROR - SQL exception: " + e.getMessage());
                return returnUser;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            returnUser.setMessage("ERROR - SQL exception: " + e.getMessage());
            return returnUser;
        }
        return returnUser;
    }

    /**
     * A simple endpoint method that validates user email and password for login
     */
    @ApiMethod(name = "updateUser", path = "updateUser")
    public MyBean updateUser(User u) {
        MyBean response = new MyBean();
        String sReply = "";
        String sUserEmail = "", sUserPassword = "", sPassword = "";
        sUserEmail = u.getUserEmail();
        sUserPassword = u.getUserPassword();
        String url = databaseURL();
        if(url == null) {
            response.setData("ERROR - DB URL exception");
            return response;
        }
        try {
            String sQuery = "SELECT * FROM voter.users where userEmail = \'"+sUserEmail+"\'";
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                sReply = "ERROR - No such Email Registered";
                while(rs.next()) {
                    sPassword = rs.getString("userPassword");
                    java.util.Date dUserDOB = u.getUserDOB();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String sqlDate = sdf.format(dUserDOB);
                    int iUserID = rs.getInt("userID");
                    if (sPassword.equals(sUserPassword)) {
                        String statement = "UPDATE voter.users SET " +
                                "userName = \'" + u.getUserName() + "\', " +
                                "userDOB = \'" + sqlDate + "\', " +
                                "locationID = " + u.getLocationID() +
                                " WHERE userID = " + iUserID + "";
                        PreparedStatement stmt = conn.prepareStatement(statement);
                        int success = stmt.executeUpdate();
                        sReply = "PASS - User details updated for email: "+sUserEmail;
                    } else {
                        sReply = "ERROR - Incorrect Password";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setData("ERROR - SQL Exception: " + e.getMessage());
                return response;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setData("ERROR - SQL exception: " + e.getMessage());
            return response;
        }
        response.setData(sReply);
        return response;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "selectSQL", path = "selectSQL")
    public MyBean selectSQL(@Named("selectSQL") String selectSQL) {
        MyBean response = new MyBean();
        String sReply="Row Number, ";
        String url = databaseURL();
        if(url == null) {
            response.setData("ERROR - DB URL exception");
            return response;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet rs = conn.createStatement().executeQuery(selectSQL);
                ResultSetMetaData metadata = rs.getMetaData();
                int columnCount = metadata.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    sReply = sReply +  metadata.getColumnName(i) + ", ";
                }
                while (rs.next()) {
                    String row = "";
                    for (int i = 1; i <= columnCount; i++) {
                        row += rs.getString(i) + ", ";
                    }
                    sReply = sReply+row;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setData("ERROR - SQL Exception: " + e.getMessage());
                return response;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setData("ERROR - SQL Exception: " + e.getMessage());
            return response;
        }
        response.setData(sReply);
        return response;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "insertSQL", path = "insertSQL")
    public MyBean insertSQL(@Named("insertSQL") String insertSQL) {
        MyBean response = new MyBean();
        String sReply;
        int iSuccess = databaseInsertUpdateQuery(insertSQL);
        if(iSuccess==-2) {
            sReply = "ERROR - SQL Insert, Update or Delete failed";
        } else {
            sReply = "PASS - SQL Insert, Update or Delete executed";
        }
        response.setData(sReply);
        return response;
    }
}
