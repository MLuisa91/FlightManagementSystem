package com.donoso.easyflight.utils;

public class URLApi {

    // URLS PARA LOS SERVICIOS DE USUARIOS
    public static final String API_USER_BY_ID_PASSWORD = "usuarios/";
    public static final String API_USER_CREATE = "usuarios/insertar";
    public static final String API_USER_UPDATE = "usuarios/actualizar";
    public static final String API_USER_BY_ID = "usuarios/{id}"; //Replace {id} por id de usuario
    public static final String API_USER_SEARCH = "usuarios/search";
    public static final String API_USER_DELETE = "usuarios/{id}";

    //URLS PARA LOS PAISES
    public static final String API_PAIS_SEARCH = "paises/search";

    //URLS PARA LOS AVIONES
    public static final String API_AVION_CREATE = "aviones/insertar";
    public static final String API_AVION_UPDATE = "aviones/actualizar";
    public static final String API_AVION_BY_ID = "aviones/{id}";
    public static final String API_AVION_SEARCH = "aviones/search";
    public static final String API_AVION_DELETE = "aviones/{id}";

    //URLS PARA LAS OFERTAS
    public static final String API_OFERTA_CREATE = "ofertas/insertar";
    public static final String API_OFERTA_UPDATE = "ofertas/actualizar";
    public static final String API_OFERTA_BY_ID = "ofertas/{id}";
    public static final String API_OFERTA_SEARCH = "ofertas/search";
    public static final String API_OFERTA_DELETE = "ofertas/{id}";

    //URLS PARA LOS AEROPUERTOS
    public static final String API_AEROPUERTO_SEARCH = "aeropuertos/search";

    //URLS PARA LOS VUELOS
    public static final String API_VUELO_SEARCH = "vuelos/search";
    public static final String API_VUELO_BY_ID = "vuelos/{id}";
    public static final String API_VUELO_UPDATE = "vuelos/actualizar";
    public static final String API_VUELO_CREATE = "vuelos/insertar";
    public static final String API_VUELO_DELETE = "vuelos/{id}";

}
