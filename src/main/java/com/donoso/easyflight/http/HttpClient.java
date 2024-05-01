package com.donoso.easyflight.http;

import com.donoso.easyflight.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class HttpClient<U,T> {

    private CloseableHttpClient httpClient = null;
    private final String url;
    private static final String PROTOCOLO = "http";
    private static final String DOMINIO = "192.168.56.1";
    private static final String PUERTO = "9999";
    private static final String URLAPI = "easyflight";
    private final Class<T> tClass;


    public HttpClient(Class<T> tClass) {
        this.url = PROTOCOLO.concat("://")
                .concat(DOMINIO).concat(":")
                .concat(PUERTO).concat("/")
                .concat(URLAPI);
        this.httpClient = HttpClients.createDefault();
        this.tClass = tClass;

    }

    public HttpGet getHttpGet(String service) {
        return new HttpGet(url.concat("/").concat(service));
    }

    public HttpPost getHttpPost(String service, U object) throws JsonProcessingException {
        JsonConverter<T> converter = new JsonConverter<>(tClass);
        final StringEntity entity = new StringEntity(converter.objectToJson(object));
        final HttpPost httpPost = new HttpPost(url.concat("/").concat(service));

        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        return httpPost;
    }

    public HttpPut getHttpPut(String service, U object) throws JsonProcessingException {
        JsonConverter<T> converter = new JsonConverter<>(tClass);
        final StringEntity entity = new StringEntity(converter.objectToJson(object));
        final HttpPut httpPut = new HttpPut(url.concat("/").concat(service));

        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        return httpPut;
    }

    public HttpDelete getHttpDelete(String service) throws JsonProcessingException {
        return new HttpDelete(url.concat("/").concat(service));

    }

    public T execute(String url, U object, String method) throws Exception {
        JsonConverter<T> converter = new JsonConverter<>(tClass);
        if (method.equals("GET")) {
            HttpEntity entity = httpClient.execute(this.getHttpGet(url)).getEntity();
            return converter.jsonToObject(EntityUtils.toString(entity));
        }
        if (method.equals("POST")) {
            HttpEntity entity = httpClient.execute(this.getHttpPost(url, object)).getEntity();
            return converter.jsonToObject(EntityUtils.toString(entity));
        }

        if (method.equals("PUT")) {
            HttpEntity entity = httpClient.execute(this.getHttpPut(url, object)).getEntity();
            return converter.jsonToObject(EntityUtils.toString(entity));
        }

        if (method.equals("DELETE")) {
            httpClient.execute(this.getHttpDelete(url)).getEntity();
            return null;
        }

        return null;
    }
}
