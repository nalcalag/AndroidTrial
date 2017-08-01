package com.nalcalag.androidtrial.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class Team {

    //Attributes
    @SerializedName("teamID")
    private Integer id;

    @SerializedName("teamName")
    private String name;

    @SerializedName("teamStadium")
    private String stadium;

    @SerializedName("isNation")
    private boolean isNation;

    @SerializedName("teamNationality")
    private String nationality;

    @SerializedName("teamCity")
    private String city;

    //Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStadium() {
        return stadium;
    }

    public boolean isNation() {
        return isNation;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCity() {
        return city;
    }
}
