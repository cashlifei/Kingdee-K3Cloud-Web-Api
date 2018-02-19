package com.lifei.k3cloudwebapi;

public abstract interface IAsyncActionCallBack<T> {

    public abstract void CallBack(AsyncResult<T> paramAsyncResult);
}
