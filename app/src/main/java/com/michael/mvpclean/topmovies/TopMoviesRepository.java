package com.michael.mvpclean.topmovies;

import com.michael.mvpclean.api.MoreInfoApiService;
import com.michael.mvpclean.api.MovieApiService;
import com.michael.mvpclean.api.model.OmdbApi;
import com.michael.mvpclean.api.model.Result;
import com.michael.mvpclean.api.model.TopRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by huangyanzhen on 2016/12/14.
 */

public class TopMoviesRepository implements Repository {

    private MovieApiService movieApiService;
    private MoreInfoApiService moreInfoApiService;
    private List<String> countries;
    private List<Result> results;
    private long timestamp;

    private static final long STALE_MS = 20 * 1000; // Data would be stale after 20 seconds

    public TopMoviesRepository(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService) {
        this.moreInfoApiService = moreInfoApiService;
        this.timestamp = System.currentTimeMillis();
        this.movieApiService = movieApiService;
        countries = new ArrayList<>();
        results = new ArrayList<>();

    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

    @Override
    public Observable<Result> getResultsFromMemory() {

        if (isUpToDate()) {
            return Observable.fromIterable(results);
        } else {
            timestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getResultsFromNetwork() {

        Observable<TopRated> topRatedObservable = movieApiService.getTopRatedMovies(1).concatWith(movieApiService.getTopRatedMovies(2)).concatWith(movieApiService.getTopRatedMovies(3));

        return topRatedObservable.concatMap(new Function<TopRated, Observable<Result>>() {
            @Override
            public Observable<Result> apply(TopRated topRated) throws Exception {
                return Observable.fromIterable(topRated.results);
            }
        }).doOnNext(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                results.add(result);
            }
        });
    }

    @Override
    public Observable<String> getCountriesFromMemory() {

        if (isUpToDate()) {
            return Observable.fromIterable(countries);
        } else {
            timestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountriesFromNetwork() {

        return getResultsFromNetwork().concatMap(new Function<Result, Observable<OmdbApi>>() {
            @Override
            public Observable<OmdbApi> apply(Result result) throws Exception {
                return moreInfoApiService.getCountry(result.title);
            }
        }).concatMap(new Function<OmdbApi, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbApi omdbApi) throws Exception {
                return Observable.just(omdbApi.getCountry());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                countries.add(s);
            }
        });
    }

    @Override
    public Observable<String> getCountryData() {
       return getCountriesFromMemory().switchIfEmpty(getCountriesFromNetwork());
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultsFromMemory().switchIfEmpty(getResultsFromNetwork());
    }
}
