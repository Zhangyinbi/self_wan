package com.domain.library.http.exception;


import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 异常处理转换
 *
 * @param <> the type parameter
 */
public class ExceptionFunction implements Function<Throwable, Observable> {
    @Override
    public Observable apply(@NonNull Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandler.handleException(throwable));
    }
}
