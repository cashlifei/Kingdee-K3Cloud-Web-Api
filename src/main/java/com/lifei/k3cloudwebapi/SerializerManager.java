package com.lifei.k3cloudwebapi;

public class SerializerManager {

    public static ISerializerProxy Create()
            throws Exception {
        return new JsonSerializerProxy();
    }
}
