package com.lifei.k3cloudwebapi;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonSerializerProxy
        implements ISerializerProxy {

    public String Serialize(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String strJson = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(baos, obj);
            strJson = new String(baos.toByteArray(), "UTF-8");
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strJson;
    }

    public Object Deserialize(String content, Class<?> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(content, type);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
