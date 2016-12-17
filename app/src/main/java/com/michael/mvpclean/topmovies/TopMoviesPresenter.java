package com.michael.mvpclean.topmovies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huangyanzhen on 2016/12/14.
 */

public class TopMoviesPresenter implements TopMoviesMVP.Presenter {

    private TopMoviesMVP.View view;
    private Disposable disposable = null;
    private TopMoviesMVP.Model model;

    public TopMoviesPresenter(TopMoviesMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData() {
        model.result().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver());
    }

    public io.reactivex.Observer<ViewModel> getObserver() {
        return new io.reactivex.Observer<ViewModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ViewModel viewModel) {
                if (view != null) {
                    view.updateData(viewModel);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (view != null) {
                    view.showSnackbar("Something went wrong on getting movies!");
                }
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void rxUnsubscribe() {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void setView(TopMoviesMVP.View view) {
        this.view = view;
    }
}
