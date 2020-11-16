package com.example.gopoolit.Retrofit;

import com.example.gopoolit.model.ApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("everything")
    Call<ApiResponse> getDataList(@Query("q") String q,@Query("from") String from,@Query("sortBy") String sortBy,@Query("apiKey") String apiKey);
}
