package com.michael.mvpclean;

import com.michael.mvpclean.api.ApiModuleForInfo;
import com.michael.mvpclean.api.ApiModuleForName;
import com.michael.mvpclean.topmovies.TopMoviesActivity;
import com.michael.mvpclean.topmovies.TopMoviesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by huangyanzhen on 2016/12/15.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModuleForName.class, ApiModuleForInfo.class, TopMoviesModule.class})
public interface ApplicationComponent {

    void inject(TopMoviesActivity target);

}
