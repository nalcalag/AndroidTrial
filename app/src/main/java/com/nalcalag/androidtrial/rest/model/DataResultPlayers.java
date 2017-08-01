package com.nalcalag.androidtrial.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nalcalag on 01/08/2017.
 */

public class DataResultPlayers {

    //Attributes
    @SerializedName("result")
    private Result result;

    //Getters
    public List<Player> getPlayers() {
        return result.getPlayers();
    }


    private class Result {

        //Attributes
        @SerializedName("players")
        private List<Player> players;

        @SerializedName("teams")
        private String teams;

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

    }
}
