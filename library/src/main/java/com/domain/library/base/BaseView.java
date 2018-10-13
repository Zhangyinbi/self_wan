package com.domain.library.base;

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);

    void showToast(String msg);

    /**
     * This is a general method used for showing some kind of progress during a background task. For example, this
     * method should show a progress bar and/or disable buttons before some background work starts.
     */
    void showProgress();

    /**
     * This is a general method used for hiding progress information after a background task finishes.
     */
    void hideProgress();

    void showEmptyView();
}
