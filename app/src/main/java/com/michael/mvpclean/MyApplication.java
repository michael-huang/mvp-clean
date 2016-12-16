package com.michael.mvpclean;

import android.app.Application;

import com.michael.mvpclean.api.ApiModuleForInfo;
import com.michael.mvpclean.api.ApiModuleForName;
import com.michael.mvpclean.topmovies.TopMoviesModule;

/**
 * Created by huangyanzhen on 2016/12/15.
 */

public class MyApplication extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModuleForName(new ApiModuleForName())
                .topMoviesModule(new TopMoviesModule())
                .apiModuleForInfo(new ApiModuleForInfo())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
