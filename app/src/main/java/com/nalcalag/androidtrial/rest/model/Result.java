package com.nalcalag.androidtrial.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class Result {

    //Attributes
    @SerializedName("players")
    private List<Player> players;

    @SerializedName("teams")
    private List<Team> teams;

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("request_order")
    private Integer requesOrder;

    @SerializedName("searchType")
    private String searchType;

    @SerializedName("searchString")
    private String searchString;

    @SerializedName("minVer")
    private String minVer;

    @SerializedName("serverAlert")
    private String serverAlert;

    //Getters & Setters

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRequesOrder() {
        return requesOrder;
    }

    public void setRequesOrder(Integer requesOrder) {
        this.requesOrder = requesOrder;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getMinVer() {
        return minVer;
    }

    public void setMinVer(String minVer) {
        this.minVer = minVer;
    }

    public String getServerAlert() {
        return serverAlert;
    }

    public void setServerAlert(String serverAlert) {
        this.serverAlert = serverAlert;
    }
}
