package com.domain.library.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseView> {
    public V mView;

    private CompositeDisposable compositeDisposable;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable == null ? new CompositeDisposable() : compositeDisposable;
    }

    /**
     * This is a general method used for showing some kind of progress during a background task. For example, this
     * method should show a progress bar and/or disable buttons before some background work starts.
     */
    public void showProgress() {
        if (mView != null) {
            mView.showProgress();
        }
    }

    ;

    /**
     * This is a general method used for hiding progress information after a background task finishes.
     */
    public void hideProgress() {
        if (mView != null) {
            mView.hideProgress();
        }
    }

    ;

    public abstract void create();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    public abstract void resume();

    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    public abstract void pause();

    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onStop() method.
     */
    public abstract void stop();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    public void destroy() {
        getCompositeDisposable().dispose();
    }

}
