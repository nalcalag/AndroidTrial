package com.nalcalag.androidtrial.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class Player {

    //Attributes
    @SerializedName("playerID")
    private int id;

    @SerializedName("playerFirstName")
    private String firstName;

    @SerializedName("playerSecondName")
    private String secondName;

    @SerializedName("playerNationality")
    private String nationality;

    @SerializedName("playerAge")
    private String age;

    @SerializedName("playerClub")
    private String club;

    private boolean isHeader;

    private boolean isFooter;

    // Getters
    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAge() {
        return age;
    }

    public String getClub() {
        return club;
    }

    //Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setClub(String club) {
        this.club = club;
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
