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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "votesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.youarethevoter.swapnilparashar.neil.com",
                ownerName = "backend.youarethevoter.swapnilparashar.neil.com",
                packagePath = ""
        )
)
public class VotesEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hello back
     */
    @ApiMethod(name = "sayHello", path = "sayHello")
    public MyBean sayHello(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hello, " + name);

        return response;
    }

    private String databaseURL() {
        String url="";
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

    /**
     * A simple endpoint method that accepts new vote question
     */
    @ApiMethod(name = "newVoteQuestion")
    public GenericBean newVoteQuestion(VoteDetails vd) {
        GenericBean response = new GenericBean();
        String sReply = "";
        String url = databaseURL();
        List<Integer> interestList = new ArrayList<Integer>(vd.getListOfInterestGroups());
        if(!("Admin".equals(vd.getAdminUser()) && "Admin01".equals(vd.getAdminPassword()))) {
            response.setData("Incorrect Admin details: "+vd.getAdminUser()+"/"+vd.getAdminPassword());
            return response;
        }
        if(url == null) {
            response.setData("ERROR - DB URL exception");
            return response;
        }
        java.util.Date dVoteClodeDate = vd.getVoteCloseDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sqlDate = sdf.format(dVoteClodeDate);
        String sQuery = "SELECT * FROM voter.vote where voteName = \'"+vd.getVoteName()+"\'";
        int iVoteID = 0;
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                int i = 0;
                while (rs.next()) {
                    ++i;
                }
                if (i == 1) {
                    sReply = "ERROR - This vote already exists";
                } else {
                    String statement = "INSERT INTO voter.vote (voteName, voteSummary, optionA, optionB, voteHashTag, countA, countB, voteCloseDate, voteLink) VALUES " +
                            "(\'" + vd.getVoteName() + "\', " +
                            "\'" + vd.getVoteSummary() + "\', " +
                            "\'" + vd.getOptionA() + "\', " +
                            "\'" + vd.getOptionB() + "\', " +
                            "\'" + vd.getVoteHashTag() + "\', " +
                            "" + vd.getCountA() + ", " +
                            "" + vd.getCountB() + ", " +
                            "\'" + sqlDate + "\', " +
                            "\'" + vd.getVoteLink() + "\')";
                    PreparedStatement stmt = conn.prepareStatement(statement);
                    stmt.executeUpdate();
                    ResultSet rs2 = conn.createStatement().executeQuery(sQuery);
                    while (rs2.next()) {
                        iVoteID = rs2.getInt("voteID");
                    }
                    if (iVoteID == 0) {
                        sReply = "ERROR - Failed to post new Vote";
                    } else {
                        String statement2 = "INSERT INTO voter.vote_bridge_interest (voteID, interestGroupID) VALUES ";
                        for (int j = 0; j < interestList.size(); ++j) {
                            if (j == 0) {
                                statement2 = statement2 + "(" + iVoteID + "," + interestList.get(j) + ")";
                            } else {
                                statement2 = statement2 + ", (" + iVoteID + "," + interestList.get(j) + ")";
                            }
                        }
                        PreparedStatement stmt2 = conn.prepareStatement(statement2);
                        stmt2.executeUpdate();
                        sReply = "Interest groups updated for vote: " + vd.getVoteName();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setData("ERROR - SQL Exception: " + e.getMessage());
                return response;
            } finally{
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
     * A simple endpoint method that retrieves a Bill information
     */
    @ApiMethod(name = "voteDetails", path = "voteDetails")
    public VoteDetails voteDetails(@Named("voteID") int voteID, @Named("userID") int userID) {
        VoteDetails vd = new VoteDetails();
        String url = databaseURL();
        if(url == null) {
            return null;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                String sQuery = "SELECT * FROM voter.vote where voteID = "+voteID+"";
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                while (rs.next()) {
                    vd.setVoteID(voteID);
                    vd.setVoteName(rs.getString("voteName"));
                    vd.setVoteSummary(rs.getString("voteSummary"));
                    vd.setOptionA(rs.getString("optionA"));
                    vd.setOptionB(rs.getString("optionB"));
                    vd.setCountA(rs.getInt("countA"));
                    vd.setCountB(rs.getInt("countB"));
                    vd.setVoteHashTag(rs.getString("voteHashTag"));
                    vd.setVoteCloseDate(rs.getDate("voteCloseDate"));
                    vd.setVoteLink(rs.getString("voteLink"));
                    vd.setVoteA(0);
                    vd.setVoteB(0);
                }
                String sQuery2 = "SELECT * FROM voter.users_bridge_vote WHERE voteID = " + voteID + " and userID = " + userID + "";
                ResultSet rs2 = conn.createStatement().executeQuery(sQuery2);
                while (rs2.next()) {
                    vd.setVoteA(rs2.getInt("voteA"));
                    vd.setVoteB(rs2.getInt("voteB"));
                    vd.setUserID(userID);
                }
                vd.setMessage("PASS - Vote details extracted successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                vd.setMessage("ERROR - SQL Exception: " + e.getMessage());
                return vd;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            vd.setMessage("ERROR - SQL Exception: " + e.getMessage());
            return vd;
        }
        return vd;
    }

    /**
     * A simple endpoint method that pushes Agent data
     */
    @ApiMethod(name = "voteList", path = "voteList")
    public List<VoteDetails> voteList(@Named("interestGroupID") int interestGroupID, @Named("active") Boolean active) {
        List<VoteDetails> results = new ArrayList<VoteDetails>();
        String url = databaseURL();
        if(url == null) {
            return null;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                String sQuery = "SELECT * FROM voter.vote_bridge_interest WHERE interestGroupID = "+interestGroupID;
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                List<Integer> voteIDlist = new ArrayList<Integer>();
                while (rs.next()) {
                    voteIDlist.add(rs.getInt("voteID"));
                }
                String sQuery2 = "SELECT * FROM voter.vote WHERE voteID IN (";
                Iterator<Integer> voteIDListIterator = voteIDlist.iterator();
                int i=0;
                while (voteIDListIterator.hasNext()) {
                    if(i==0) {
                        sQuery2 = sQuery2 + voteIDListIterator.next();
                    } else {
                        sQuery2 = sQuery2 + ", " + voteIDListIterator.next();
                    }
                    ++i;
                }
                sQuery2 = sQuery2 + ")";
                if(active) {
                    sQuery2 = sQuery2 + " AND voteCloseDate >= CURDATE()";
                } else {
                    sQuery2 = sQuery2 + " AND voteCloseDate < CURDATE()";
                }
                ResultSet rs2 = conn.createStatement().executeQuery(sQuery2);
                while (rs2.next()) {
                    VoteDetails vd = new VoteDetails();
                    vd.setVoteID(rs2.getInt("voteID"));
                    vd.setVoteName(rs2.getString("voteName"));
                    vd.setVoteSummary(rs2.getString("voteSummary"));
                    vd.setOptionA(rs2.getString("optionA"));
                    vd.setOptionB(rs2.getString("optionB"));
                    vd.setCountA(rs2.getInt("countA"));
                    vd.setCountB(rs2.getInt("countB"));
                    vd.setVoteHashTag(rs2.getString("voteHashTag"));
                    vd.setVoteCloseDate(rs2.getDate("voteCloseDate"));
                    vd.setVoteLink(rs2.getString("voteLink"));
                    /*dp.setUserA(0);
                    dp.setUserB(0);
                    dp.setUserID(userDeviceID);*/
                    results.add(vd);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                VoteDetails vde = new VoteDetails();
                vde.setMessage("ERROR - SQL Exception: "+ e.getMessage());
                results.add(vde);
                return results;
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            VoteDetails vde = new VoteDetails();
            vde.setMessage("ERROR - SQL Exception: "+ e.getMessage());
            results.add(vde);
            return results;
        }
        return results;
    }
    /**
     * A simple endpoint method that pushes Agent data
     */
    @ApiMethod(name = "voteAction", path = "voteAction")
    public GenericBean voteAction(VoteDetails vd) {
        GenericBean response = new GenericBean();
        String sReply = "";
        String url = databaseURL();
        if(url == null) {
            return null;
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                sReply = "Before select query";
                String sQuery = "SELECT * FROM voter.vote WHERE voteID = "+vd.getVoteID();
                ResultSet rs = conn.createStatement().executeQuery(sQuery);
                int iCountA = 0, iCountB = 0;
                sReply = "Before Increment";
                while (rs.next()) {
                    iCountA = rs.getInt("countA")+vd.getVoteA();
                    iCountB = rs.getInt("countB")+vd.getVoteB();
                }
                sReply = "Before update query";
                String statement = "UPDATE voter.vote SET " +
                        "countA = "+iCountA+", " +
                        "countB = "+iCountB+" " +
                        "where voteID = "+vd.getVoteID();
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.executeUpdate();
                sReply = "Before update query 2";
                String statement2 = "INSERT INTO users_bridge_vote (userID, voteID, voteA, voteB) VALUES ("
                        + vd.getUserID() + ", "
                        + vd.getVoteID() + ", "
                        + vd.getVoteA() + ", "
                        + vd.getVoteB() + ") " +
                        "ON DUPLICATE KEY UPDATE " +
                        "voteA = "+vd.getVoteA()+", " +
                        "voteB = "+vd.getVoteB()+"";
                sReply = statement2;
                PreparedStatement stmt2 = conn.prepareStatement(statement2);
                stmt2.executeUpdate();
                sReply = "PASS - Voted Successfully";
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

}
