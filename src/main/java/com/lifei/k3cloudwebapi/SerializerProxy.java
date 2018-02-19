package com.lifei.k3cloudwebapi;

public class SerializerProxy {

    ISerializerProxy proxy = null;

    public SerializerProxy() throws Exception {
        this.proxy = SerializerManager.Create();
    }

    public String Serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass().isPrimitive()) {
            return obj.toString();
        }
        if ((obj instanceof String)) {
            return obj.toString();
        }

        return this.proxy.Serialize(obj);
    }

    public Object Deserialize(String content, Class<?> type) {
        if ((content == null) || (content.length() == 0)) {
            if (!type.isPrimitive()) {
                return type.getClass();
            }
            if (type.getName().equalsIgnoreCase("java.lang.String")) {
                return content;
            }
            return null;
        }

        if (type.getName().equalsIgnoreCase("java.lang.string")) {
            return content;
        }

        return this.proxy.Deserialize(content, type);
    }
}
