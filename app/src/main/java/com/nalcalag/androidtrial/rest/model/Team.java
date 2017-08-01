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

    private boolean isHeader;

    private boolean isFooter;

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

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isFooter() {
        return isFooter;
    }

    public void setFooter(boolean footer) {
        isFooter = footer;
    }
}
