package com.michael.mvpclean.topmovies;

import com.michael.mvpclean.api.ApiModuleForName;
import com.michael.mvpclean.api.MoreInfoApiService;
import com.michael.mvpclean.api.MovieApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huangyanzhen on 2016/12/15.
 */

@Module(includes = {ApiModuleForName.class})
public class TopMoviesModule {

    @Provides
    public TopMoviesMVP.Presenter provideTopMoviesActivityPresenter(TopMoviesMVP.Model topMoviesModel) {
        return new TopMoviesPresenter(topMoviesModel);
    }

    @Provides
    public TopMoviesMVP.Model provideTopMoviesActivityModel(Repository repository) {
        return new TopMoviesModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideRepo(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService) {
        return new TopMoviesRepository(movieApiService, moreInfoApiService);
    }


}
