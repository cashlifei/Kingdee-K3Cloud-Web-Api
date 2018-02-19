package com.lifei.k3cloudwebapi;

public abstract interface ISerializerProxy {

    public abstract String Serialize(Object paramObject);

    public abstract Object Deserialize(String paramString, Class<?> paramClass);
}
