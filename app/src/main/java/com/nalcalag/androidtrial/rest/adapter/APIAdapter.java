package com.nalcalag.androidtrial.rest.adapter;

import com.nalcalag.androidtrial.rest.constants.ApiConstants;
import com.nalcalag.androidtrial.rest.model.DataResult;
import com.nalcalag.androidtrial.rest.model.Player;
import com.nalcalag.androidtrial.rest.model.Team;
import com.nalcalag.androidtrial.rest.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class APIAdapter extends BaseAdapter implements APIService {

    private APIService apiService;

    public APIAdapter() {
        super(ApiConstants.BASE_API_URL);
        apiService = createService(APIService.class);
    }

    @Override
    public Call<DataResult> getResults(@Query("searchString") String search, @Query("searchType") String type, @Query("offset") String offset, @Query("requestOrder") String order) {
        return apiService.getResults(search, type, offset, order);
    }

//    @Override
//    public Call<DataResult> getResults2(@Path("search") String search) {
//        return apiService.getResults2(search);
//    }
}
