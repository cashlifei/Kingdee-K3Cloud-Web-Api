package com.lifei.k3cloudwebapi;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.http.client.CookieStore;

public class ApiServiceRequest<T> extends ApiRequest<T> {

    private String _serverName;
    private List<Object> _parameters;

    public ApiServiceRequest(String serverUrl, CookieStore cookieStore, String serverName, Object[] objParams, Class<T> returnType) {
        super(serverUrl, cookieStore, objParams, returnType);

        this._serverName = serverName;
        this._parameters = Arrays.asList(objParams);
    }

    public String getServerUrl() {
        return this._serverUrl + this._serverName + ".common.kdsvc";
    }

    public ParaDictionary getServiceParameters() {
        if (this._txtParams == null) {
            this._txtParams = new ParaDictionary();
        }

        UUID uuid = UUID.randomUUID();
        int rid = uuid.toString().hashCode();
        this._txtParams.put("nonce", "");
        this._txtParams.put("version", "1.0");
        this._txtParams.put("format", "1");
        this._txtParams.put("timestamp", new Date().toString());
        this._txtParams.put("rid", Integer.toString(rid));
        this._txtParams.put("useragent", "ApiClient");
        this._txtParams.putitem("parameters", this._parameters);
        return this._txtParams;
    }
}
