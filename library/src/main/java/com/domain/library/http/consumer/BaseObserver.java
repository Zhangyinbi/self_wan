package com.domain.library.http.consumer;

import com.domain.library.BaseApplicationController;
import com.domain.library.R;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;

import com.domain.library.utils.ToastUtils;
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
public abstract class BaseObserver<T extends BaseEntry> implements Observer<BaseEntry> {
  private CompositeDisposable compositeDisposable;

  public BaseObserver(CompositeDisposable compositeDisposable) {
    this.compositeDisposable = compositeDisposable;
  }

  @Override
  public void onSubscribe(Disposable d) {
    compositeDisposable.add(d);
  }

  @Override
  public void onNext(BaseEntry bean) {
    if (null == bean) {
      ToastUtils.showToast("服务器繁忙，请稍后");
    }
    if (bean.getStatus() != 0) {
      ToastUtils.showToast(bean.getMsg());
      return;
    }
    onSuss((T) bean);
  }

  @Override
  public void onError(Throwable e) {
    if (e instanceof BaseException) {
      onError((BaseException) e);
    } else {
      onError(new BaseException(e, String.valueOf(BaseException.UN_KNOWN_ERROR), BaseApplicationController.getContext()
                                                                                                          .getString(R.string.unKnow_error)));
    }
  }

  @Override
  public void onComplete() {

  }

  public abstract void onError(BaseException e);

  public abstract void onSuss(T tBaseEntry);
}
