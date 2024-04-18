package com.donoso.easyflight.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter<T> {

    ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> tClass;

    public JsonConverter(Class<T> tClass) {
        super();
        objectMapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        this.tClass = tClass;
    }

    /**
     * Convierte una cadena json de formato String a array de Objetos
     *
     * @param response
     * @return
     * @throws Exception
     */
    public T[] jsonToObjectList(String response) throws Exception {
        return (T[]) objectMapper.readValue(response, tClass);
    }

    /**
     * Convierte una cadena json de formato String a un único Objeto
     *
     * @param response
     * @return
     * @throws Exception
     */
    public T jsonToObject(String response) throws Exception {
        return objectMapper.readValue(response, tClass);
    }

    /**
     * Convierte un Objeto a una una cadena String con formato json
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
