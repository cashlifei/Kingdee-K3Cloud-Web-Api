package com.lifei.k3cloudwebapi;

public class ApiHttpClient<T>
        implements IKDWebRequestLinstener<T> {

    private IAsyncActionCallBack<T> _callback;

    public ApiHttpClient(IAsyncActionCallBack<T> callback) {
        this._callback = callback;
    }

    public ApiHttpClient() {
    }

    public T Send(ApiRequest<T> request, Class<T> type) throws Exception {
        request.doPost();
        String result = request.getResponseString();
        Object obj = new SerializerProxy().Deserialize(result, type);
        return (T) obj;
    }

    public void syncSend(ApiRequest<T> request) throws Exception {
        request.syncdoPost();
    }

    public void onRequsetSuccess(ApiRequest<T> request) {
        if (this._callback != null) {
            String returnValue = request.getResponseString();
            try {
                Object obj = new SerializerProxy().Deserialize(returnValue, request.getReturnClassType());
                AsyncResult asyncResult = AsyncResult.CreateSuccessfulResult(obj);
                this._callback.CallBack(asyncResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onRequsetFailed(ApiRequest<T> request) {
        if (this._callback != null) {
            AsyncResult result = AsyncResult.CreateUnSuccessfulResult(new Exception(request.getResponseString()));
            this._callback.CallBack(result);
        }
    }

    public void onRequsetError(ApiRequest<T> request, Exception e) {
        if (this._callback != null) {
            AsyncResult asyncResult = AsyncResult.CreateUnSuccessfulResult(e);
            this._callback.CallBack(asyncResult);
        }
    }
}
