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

    //URLS PARA RESERVAS
    public static final String API_RESERVA_SEARCH = "reservas/search";
    public static final String API_RESERVA_BY_ID = "reservas/{id}";
    public static final String API_RESERVA_UPDATE = "reservas/actualizar";
    public static final String API_RESERVA_CREATE = "reservas/insertar";
    public static final String API_RESERVA_DELETE = "reservas/{id}";

    //URLS PARA EXTRAS
    public static final String API_EXTRA_SEARCH = "extras/search";
    public static final String API_EXTRA_BY_ID = "extras/{id}";
    public static final String API_EXTRA_UPDATE = "extras/actualizar";
    public static final String API_EXTRA_CREATE = "extras/insertar";
    public static final String API_EXTRA_DELETE = "extras/{id}";

    //URLS PARA VIAJEROS
    public static final String API_VIAJERO_SEARCH = "viajeros/search";
    public static final String API_VIAJERO_BY_ID = "viajeros/{id}";
    public static final String API_VIAJERO_UPDATE = "viajeros/actualizar";
    public static final String API_VIAJERO_CREATE = "viajeros/insertar";
    public static final String API_VIAJERO_DELETE = "viajeros/{id}";

}
