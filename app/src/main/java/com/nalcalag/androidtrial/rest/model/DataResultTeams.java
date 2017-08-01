package com.nalcalag.androidtrial.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nalcalag on 01/08/2017.
 */

public class DataResultTeams {

    //Attributes
    @SerializedName("result")
    private Result result;

    //Getters
    public List<Team> getTeams() {
        return result.getTeams();
    }

    private class Result {

        //Attributes
        @SerializedName("players")
        private String players;

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
        public List<Team> getTeams() {
            return teams;
        }

        public void setTeams(List<Team> teams) {
            this.teams = teams;
        }

    }
}
