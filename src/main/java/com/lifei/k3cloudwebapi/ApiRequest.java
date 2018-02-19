package com.lifei.k3cloudwebapi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class ApiRequest<T> {

    private CookieStore _cookieStore;
    protected String _serverUrl;
    private static final String CHARSET = "UTF-8";
    private static String _sessionkey = "kdservice-sessionid";
    private static String _aspnetsessionkey = "ASP.NET_SessionId";
    private String _aspnetsessionid = "";
    private String _currentsessionid = "";
    private HttpClient _httpClient;
    Map<String, Object> _requestParams;
    private HttpRequestBase _request;
    private MultipartEntity _entity;
    private Header _header;
    private HttpResponse _response;
    private String _responseString;
    private InputStream _responseStream;
    ParaDictionary _txtParams;
    private Boolean _isAsynchronous = Boolean.valueOf(false);
    private IKDWebRequestLinstener<T> _listener;
    private Class<T> _returnType;

    public ApiRequest(String serverUrl, CookieStore cookieStore, Object[] objParams, Class<T> returnType) {
        this._cookieStore = cookieStore;
        this._serverUrl = serverUrl;
        this._returnType = returnType;
    }

    private HttpClient getHttpClient() {
        if (this._httpClient == null) {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");

            HttpConnectionParams.setConnectionTimeout(params, 300000);

            HttpConnectionParams.setSoTimeout(params, 300000);

            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http",
                    PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https",
                    SSLSocketFactory.getSocketFactory(), 443));

            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                    params, schReg);
            this._httpClient = new DefaultHttpClient(conMgr, params);

            if (this._cookieStore != null) {
                DefaultHttpClient dhttpclient = (DefaultHttpClient) this._httpClient;
                dhttpclient.setCookieStore(this._cookieStore);
            }
        }
        return this._httpClient;
    }

    public HttpPost getHttpPost() {
        if ((this._serverUrl != null) && (!this._serverUrl.equals(""))) {
            HttpPost httpPost = new HttpPost(getServerUrl());
            this._request = httpPost;
            if (this._header != null) {
                httpPost.setHeader(this._header);
                setHeaders();
            }
            return httpPost;
        }
        return null;
    }

    public void syncdoPost() {
        this._isAsynchronous = Boolean.valueOf(true);

        new Thread(new Runnable() {
            public void run() {
                ApiRequest.this.doPost();
            }
        }).start();
    }

    public void doPost() {
        HttpPost httpPost = getHttpPost();
        try {
            if (httpPost == null) {
                return;
            }

            httpPost.setEntity(getServiceParameters().toEncodeFormEntity());
            this._response = getHttpClient().execute(httpPost);

            if (this._response.getStatusLine().getStatusCode() == 200) {
                HttpEntity respEntity = this._response.getEntity();

                if (this._currentsessionid == "") {
                    CookieStore mCookieStore = ((AbstractHttpClient) getHttpClient())
                            .getCookieStore();
                    List cookies = mCookieStore.getCookies();
                    if (cookies.size() > 0) {
                        this._cookieStore = mCookieStore;
                    }
                    for (int i = 0; i < cookies.size(); i++) {
                        if (_aspnetsessionkey.equals(((Cookie) cookies.get(i)).getName())) {
                            this._aspnetsessionid = ((Cookie) cookies.get(i)).getValue();
                        }

                        if (_sessionkey.equals(((Cookie) cookies.get(i)).getName())) {
                            this._currentsessionid = ((Cookie) cookies.get(i)).getValue();
                        }
                    }

                }

                this._responseStream = respEntity.getContent();
                this._responseString = streamToString(this._responseStream);

                if (this._isAsynchronous.booleanValue()) {
                    this._listener.onRequsetSuccess(this);
                }
            }
        } catch (Exception e) {
            if (this._isAsynchronous.booleanValue()) {
                this._listener.onRequsetError(this, e);
            }
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }
    }

    private void setHeaders() {
        this._request.setHeader("Content-Type", "application/json;charset=utf-8");
        this._request.setHeader("AppVersion", "2.0");
        this._request.setHeader("User-Agent", "ApiClient");

        if (!this._aspnetsessionid.equalsIgnoreCase("")) {
            this._request.setHeader(_aspnetsessionkey, this._aspnetsessionid);
        }

        if (!this._currentsessionid.equalsIgnoreCase("")) {
            this._request.setHeader(_sessionkey, this._currentsessionid);
        }
    }

    private String streamToString(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }

    public ParaDictionary getServiceParameters() {
        UUID uuid = UUID.randomUUID();
        int rid = uuid.toString().hashCode();
        this._txtParams.put("nonce", "");
        this._txtParams.put("version", "1.0");
        this._txtParams.put("format", "1");
        this._txtParams.put("timestamp", new Date().toString());
        this._txtParams.put("rid", Integer.toString(rid));
        this._txtParams.put("useragent", "ApiClient");
        return this._txtParams;
    }

    public String chinaToUnicode(String str) {
        char[] chars = str.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            int chr1 = chars[i];
            if ((chr1 >= 19968) && (chr1 <= 171941)) {
                result = result + "\\u" + Integer.toHexString(chr1);
            } else {
                result = result + chars[i];
            }
        }
        return result;
    }

    public void setListener(IKDWebRequestLinstener<T> listener) {
        this._listener = listener;
    }

    public String getServerUrl() {
        return this._serverUrl;
    }

    public String getResponseString() {
        return this._responseString;
    }

    public CookieStore getCookieStore() {
        return this._cookieStore;
    }

    public Class<T> getReturnClassType() {
        return this._returnType;
    }
}
