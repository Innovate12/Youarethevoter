package com.neil.swapnilparashar.youarethevoter.backend;

import java.util.Date;
import java.util.List;

/**
 * Created by swapnilparashar on 06/11/2016.
 */

public class VoteDetails {
    private int voteID;
    private String voteName;
    private String voteSummary;
    private String optionA;
    private String optionB;
    private String voteHashTag;
    private int countA;
    private int countB;
    private Date voteCloseDate;
    private String voteLink;

    // For Users vote count
    private int userID;
    private int voteA;
    private int voteB;

    // Message for Operation
    private String message;

    // Admin Screen
    private String adminUser;
    private String adminPassword;

    private List<Integer> listOfInterestGroups;

    public int getVoteID() {
        return voteID;
    }

    public void setVoteID(int voteID) {
        this.voteID = voteID;
    }

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getVoteSummary() {
        return voteSummary;
    }

    public void setVoteSummary(String voteSummary) {
        this.voteSummary = voteSummary;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getVoteHashTag() {
        return voteHashTag;
    }

    public void setVoteHashTag(String voteHashTag) {
        this.voteHashTag = voteHashTag;
    }

    public int getCountA() {
        return countA;
    }

    public void setCountA(int countA) {
        this.countA = countA;
    }

    public int getCountB() {
        return countB;
    }

    public void setCountB(int countB) {
        this.countB = countB;
    }

    public Date getVoteCloseDate() {
        return voteCloseDate;
    }

    public void setVoteCloseDate(Date voteCloseDate) {
        this.voteCloseDate = voteCloseDate;
    }

    public String getVoteLink() {
        return voteLink;
    }

    public void setVoteLink(String voteLink) {
        this.voteLink = voteLink;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVoteA() {
        return voteA;
    }

    public void setVoteA(int voteA) {
        this.voteA = voteA;
    }

    public int getVoteB() {
        return voteB;
    }

    public void setVoteB(int voteB) {
        this.voteB = voteB;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getListOfInterestGroups() {
        return listOfInterestGroups;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setListOfInterestGroups(List<Integer> listOfInterestGroups) {
        this.listOfInterestGroups = listOfInterestGroups;
    }
}
