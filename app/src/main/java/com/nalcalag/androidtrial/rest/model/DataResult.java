package com.nalcalag.androidtrial.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class DataResult {

    //Attributes
    @SerializedName("result")
    private Result result;

    //Getters
    public List<Player> getPlayers() {
        return result.getPlayers();
    }

    public List<Team> getTeams() {
        return result.getTeams();
    }
}
