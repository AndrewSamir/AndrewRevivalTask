package com.samir.andrew.andrewsamirrevivaltask.retorfitconfig;


import com.samir.andrew.andrewsamirrevivaltask.googlePlacesApis.ModelGooglePlacesApis;
import com.samir.andrew.andrewsamirrevivaltask.utilities.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiCall {

    @GET(Constant.subUrl + "maps/api/place/nearbysearch/json?")
    Call<ModelGooglePlacesApis> getGooglePlacesCall(
            @Query("location") String location,
            @Query("radius") String radius,
            @Query("key") String key);

    /*@GET(Constant.subUrl + "maps/api/place/photo?")
    Call<String> getGooglePlacesPhotoCall(
            @Query("maxwidth") String maxwidth,
            @Query("photoreference") String photoreference,
            @Query("sensor") String sensor,
            @Query("key") String key);*/
}