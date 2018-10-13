package com.domain.library.http.consumer;

import com.domain.library.BaseApplicationController;
import com.domain.library.R;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/9 23:36
 */
public abstract class BaseObserver<T> implements Observer<BaseEntry<T>> {
    private CompositeDisposable compositeDisposable;

    public BaseObserver(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onNext(BaseEntry<T> tBaseEntry) {
        onSuss(tBaseEntry);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof BaseException) {
            onError((BaseException) e);
        } else {
            onError(new BaseException(e, String.valueOf(BaseException.UN_KNOWN_ERROR), BaseApplicationController
                    .getContext().getString(R.string.unKnow_error)));
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(BaseException e);

    public abstract void onSuss(BaseEntry<T> tBaseEntry);
}
