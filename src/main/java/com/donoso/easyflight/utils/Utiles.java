package com.donoso.easyflight.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utiles {

    public static boolean validarIdVuelo(String cadena) {
        // Expresión regular para el patrón alfanumérico
        String patron = "^[A-Z]{2}\\d{4}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        // Verificar si la cadena, en este caso el Id del vuelo, coincide con el patrón
        return matcher.matches();
    }

    public static boolean validarHora(String cadena){
        String patron = "^([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    public static LocalTime convertirATime(String horas){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            return LocalTime.parse(horas, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validarHoras(LocalTime horaSalida, LocalTime horaLlegada) {
        return horaSalida.isBefore(horaLlegada);
    }

    public static boolean validarFecha(LocalDate fechaSeleccionada) {
        LocalDate fechaActual = LocalDate.now();
        return !fechaSeleccionada.isBefore(fechaActual);
    }

    public static boolean validarIdAvion(String cadena) {

        String patron = "^[A-Z]\\d{3}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }
}
