package com.lifei.k3cloudwebapi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

public class ParaDictionary extends Hashtable<String, String> {

    private static final long serialVersionUID = 1L;
    SerializerProxy ser = null;

    public ParaDictionary() {
        try {
            this.ser = new SerializerProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ParaDictionary(Map<String, Object> dicts) {
        try {
            this.ser = new SerializerProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Iterator localIterator = dicts.keySet().iterator(); localIterator.hasNext();) {
            Object element = localIterator.next();
            String key = (String) element;
            Object value = dicts.get(key);
            put(key, this.ser.Serialize(value));
        }
    }

    public void putitem(String key, Object value) {
        try {
            this.ser = new SerializerProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String itemValue = this.ser.Serialize(value);

        put(key, itemValue);
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

    public ParaDictionary(Hashtable<String, String> dicts) {
        for (Iterator localIterator = dicts.keySet().iterator(); localIterator.hasNext();) {
            Object element = localIterator.next();
            String key = (String) element;
            String value = (String) dicts.get(key);
            put(key, value);
        }
    }

    public UrlEncodedFormEntity toEncodeFormEntity() throws UnsupportedEncodingException {
        List list = new ArrayList();
        try {
            for (Iterator localIterator = keySet().iterator(); localIterator.hasNext();) {
                Object element = localIterator.next();
                String key = (String) element;
                String value = (String) get(key);
                list.add(new BasicNameValuePair(key, value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        return entity;
    }

    public MultipartEntity toHttpEntity() {
        MultipartEntity entityMultiForm = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            for (Iterator localIterator = keySet().iterator(); localIterator.hasNext();) {
                Object element = localIterator.next();
                String key = (String) element;
                String value = (String) get(key);
                entityMultiForm.addPart(key, new StringBody(value, Charset.forName("UTF-8")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityMultiForm;
    }
}
