package com.michael.mvpclean.topmovies;

import com.michael.mvpclean.api.model.Result;

import io.reactivex.Observable;

/**
 * Created by huangyanzhen on 2016/12/14.
 */

public interface Repository {

    Observable<Result> getResultsFromMemory();

    Observable<Result> getResultsFromNetwork();

    Observable<String> getCountriesFromMemory();

    Observable<String> getCountriesFromNetwork();

    Observable<String> getCountryData();

    Observable<Result> getResultData();

}
