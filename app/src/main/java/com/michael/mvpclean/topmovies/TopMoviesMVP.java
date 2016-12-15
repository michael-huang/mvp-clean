package com.michael.mvpclean.topmovies;

import io.reactivex.Observable;

/**
 * Created by huangyanzhen on 2016/12/14.
 */

public interface TopMoviesMVP {

    interface View {
        void updateData(ViewModel viewModel);

        void showSnackbar(String s);
    }

    interface Presenter {
        void loadData();

        void rxUnsubscribe();

        void setView(TopMoviesMVP.View view);
    }

    interface Model {
        Observable<ViewModel> result();
    }
}
