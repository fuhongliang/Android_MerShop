package cn.ifhu.base;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import cn.ifhu.bean.BaseEntity;
import cn.ifhu.utils.ToastHelper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    /**
     * 用于标识服务端返回异常时是否弹出错误toast
     */
    private boolean needShowErroToast = false;

    public BaseObserver(boolean showErrorToast) {
        this.needShowErroToast = showErrorToast;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }


    @Override
    public void onNext(BaseEntity<T> tLinkBaseEntity) {
        if (tLinkBaseEntity.isSuccess()) {
            try {
                onSuccees(tLinkBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onAPIError();
                onCodeError(tLinkBaseEntity);
                if (needShowErroToast) {
                    if (tLinkBaseEntity != null && !TextUtils.isEmpty(tLinkBaseEntity.getMessage())) {
                        ToastHelper.makeText(tLinkBaseEntity.getMessage(), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.w(TAG, "onError: " + e.toString());
        onAPIError();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException
                    || e instanceof SocketTimeoutException) {

                onFailure(e, true);
                if (needShowErroToast) {
//                    ToastHelper.makeText(MyApplication.getApplication().getResources().getString(R.string.network_not_connect), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                }
            } else {
                if (e instanceof HttpException) {
                    HttpException error = (HttpException) e;
                    if (400 <= error.code() && error.code() < 500) {
                        Gson gson = new Gson();
                        BaseEntity<T> linkBaseEntity = gson.fromJson(error.response().errorBody().string(), BaseEntity.class);
                        if (linkBaseEntity != null && linkBaseEntity.getCode() != null && linkBaseEntity.getCode().equals("authentication_failed")) {
//                            UserLogic.isLinkTokenUpdate.set(!UserLogic.isLinkTokenUpdate.get());
                        }
                        if (linkBaseEntity != null && linkBaseEntity.getCode() != null && (linkBaseEntity.getCode().equals("token_missing")
                                || linkBaseEntity.getCode().equals("token_expired") || linkBaseEntity.getCode().equals("token_invalid"))) {
//                            UserLogic.apiDataToken.set(false);
//                            UserLogic.fetchToken();
                        }
                        onCodeError(linkBaseEntity);
                        if (needShowErroToast) {
                            if (linkBaseEntity != null && !TextUtils.isEmpty(linkBaseEntity.getMessage())) {
                                ToastHelper.makeText(linkBaseEntity.getMessage(), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                            }
                        }
                    } else if (error.code() >= 500) {
                        onFailure(e, false);
                        if (needShowErroToast) {
                            ToastHelper.makeText("Server 500 error", Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                        }
                    }
                } else {
                    ToastHelper.makeText(e.getMessage(), Toast.LENGTH_LONG, ToastHelper.WARNWITHICONTOAST).show();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        onApiComplete();
    }

    @Override
    public void onComplete() {
        onApiComplete();
    }

    /**
     * 接口处理无论成功与否页面对用的操作，需复写此方法
     *
     * @param
     * @throws Exception
     */
    protected abstract void onApiComplete();

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception {
    }


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
    }

    /**
     * 任何类型的错误返回:网络连接异常，后端返回异常，后端业务返回非成功，主要用于重置提交按钮状态
     */
    protected void onAPIError() {
    }
}
