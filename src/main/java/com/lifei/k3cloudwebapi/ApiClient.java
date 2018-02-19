package com.lifei.k3cloudwebapi;

import org.apache.http.client.CookieStore;
import org.json.JSONObject;

public class ApiClient {

    private String _serverUrl;
    private CookieStore _cookieStore;

    public ApiClient(String serverUrl) {
        this._serverUrl = serverUrl;
    }

    public <T> ApiRequest<T> createRequest(String servicename, Object[] parameters, Class<T> returnType) {
        return new ApiServiceRequest(this._serverUrl, this._cookieStore, servicename, parameters, returnType);
    }

    public <T> T execute(String servicename, Object[] parameters, Class<T> returnType) throws Exception {
        ApiRequest request = createRequest(servicename, parameters, returnType);
        ApiHttpClient httpClient = new ApiHttpClient();
        request.setListener(httpClient);
        return (T) httpClient.Send(request, returnType);
    }

    public <T> ApiRequest<T> executeAsync(String servicename, Object[] parameters, Class<T> returnType, IAsyncActionCallBack<T> callback) throws Exception {
        ApiRequest request = createRequest(servicename, parameters, returnType);
        ApiHttpClient httpClient = new ApiHttpClient(callback);
        request.setListener(httpClient);
        httpClient.syncSend(request);
        return request;
    }

    public Boolean login(String dbId, String userName, String password, int lcid) throws Exception {
        Object[] loginInfo = {dbId, userName, password, Integer.valueOf(lcid)};
        ApiRequest request = createRequest("Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser", loginInfo, String.class);
        ApiHttpClient httpClient = new ApiHttpClient();
        request.setListener(httpClient);
        String ret = (String) httpClient.Send(request, String.class);
        int result = new JSONObject(ret).getInt("LoginResultType");
        if (result == 1) {
            this._cookieStore = request.getCookieStore();
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }
}
