package com.nalcalag.androidtrial.rest.service;

import com.nalcalag.androidtrial.rest.model.Player;
import com.nalcalag.androidtrial.rest.model.DataResult;
import com.nalcalag.androidtrial.rest.model.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nalcalag on 31/07/2017.
 */

public interface APIService {

    @GET("search")
    Call<DataResult> getResults(
            @Query("searchString") String search,
            @Query("searchType") String type,
            @Query("offset") String offset,
            @Query("requestOrder") String order);

//    @GET("search")
//    Call<DataResult> getResults2(
//            @Query("searchString") String search);

}
