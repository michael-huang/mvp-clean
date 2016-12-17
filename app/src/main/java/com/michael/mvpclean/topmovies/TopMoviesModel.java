package com.michael.mvpclean.topmovies;

import com.michael.mvpclean.api.model.Result;

import io.reactivex.functions.BiFunction;
import io.reactivex.Observable;

/**
 * Created by huangyanzhen on 2016/12/15.
 */

public class TopMoviesModel implements TopMoviesMVP.Model {

    private Repository repository;

    public TopMoviesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(), new BiFunction<Result, String, ViewModel>() {
            @Override
            public ViewModel apply(Result result, String s) throws Exception {
                return new ViewModel(result.title, s);
            }
        });
    }

}
