package com.donoso.easyflight.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConverter<T> {

    ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> tClass;

    public JsonConverter(Class<T> tClass) {
        super();
        //Configuraciones para propiedades de los JSON
        objectMapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

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
        return "".equals(response) ? null : objectMapper.readValue(response, tClass);
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
