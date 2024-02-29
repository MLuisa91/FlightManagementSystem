package com.planemanagement.planemanagement.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificarDatos {

    public static boolean verificarIdVuelo(String cadena) {
        // Expresión regular para el patrón alfanumérico
        String patron = "^[A-Z]{2}\\d{4}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        // Verificar si la cadena, en este caso el Id del vuelo, coincide con el patrón
        return matcher.matches();
    }

}
