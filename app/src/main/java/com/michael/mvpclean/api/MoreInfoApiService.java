package com.michael.mvpclean.api;

import com.michael.mvpclean.api.model.OmdbApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by huangyanzhen on 2016/12/15.
 */

public interface MoreInfoApiService {

    @GET("/")
    Observable<OmdbApi> getCountry(@Query("t") String title);

}
