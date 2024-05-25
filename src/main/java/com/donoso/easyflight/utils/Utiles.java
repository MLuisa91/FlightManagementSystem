package com.donoso.easyflight.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utiles {

    private static final String SECRETKEY = "EasyFlight";

    /**
     * Método para validar si el parámetro que llega es un número
     * @param cadena
     * @return
     */
    public static boolean validarSiNumero(String cadena){
        String patron = "[0-9]+";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    /**
     * Método para validar si el parámetro es de tipo double
     * @param cadena
     * @return
     */
    public static boolean validarSiDouble(String cadena){
        try{
            Double.parseDouble(cadena);
        } catch(Exception ex){
        return false;
        }
        return true;
    }

    /**
     * Método para validar el ID del Vuelo (XX0000)
     * @param cadena
     * @return
     */
    public static boolean validarIdVuelo(String cadena) {
        // Expresión regular para el patrón alfanumérico
        String patron = "^[A-Z]{2}\\d{4}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        // Verificar si la cadena, en este caso el Id del vuelo, coincide con el patrón
        return matcher.matches();
    }

    /**
     * Método para validar si el parámetro cumple el patrón asignado a las horas
     * @param cadena
     * @return
     */
    public static boolean validarHora(String cadena){
        String patron = "^([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    /**
     * Método para comprobar que la fecha de inicio sea anterior a la fecha final de las ofertas.
     * @param fechaInicio
     * @param fechaFinal
     * @return
     */
    public static boolean compararFechas(LocalDate fechaInicio, LocalDate fechaFinal){
        return fechaInicio.isBefore(fechaFinal);
    }

    /**
     * Método que convierte el parámetro a Time
     * @param horas
     * @return
     */
    public static LocalTime convertirATime(String horas){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            return LocalTime.parse(horas, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Convierte a Local Date el parámetro
     * @param fecha
     * @return
     */
    public static LocalDate convertirADate(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");

        try {
            return LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Método que comprueba que la hora de salida sea anterior a la hora de llegada de un vuelo
     * @param horaSalida
     * @param horaLlegada
     * @return
     */
    public static boolean validarHoras(LocalTime horaSalida, LocalTime horaLlegada) {
        return horaSalida.isBefore(horaLlegada);
    }

    /**
     * Método que comprueba que la fecha seleccionada sea anterior a la fecha actual
     * @param fechaSeleccionada
     * @return
     */
    public static boolean validarFecha(LocalDate fechaSeleccionada) {
        LocalDate fechaActual = LocalDate.now();
        return !fechaSeleccionada.isBefore(fechaActual);
    }

    /**
     * Método que comprueba que el ID del Avión siga el patrón A000
     * @param cadena
     * @return
     */
    public static boolean validarIdAvion(String cadena) {

        String patron = "^[A-Z]\\d{3}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    /**
     * Método para comprobar que el parámetro sigue el patrón asignado a los números de Teléfono
     * @param cadena
     * @return
     */
    public static boolean validarTelefono(String cadena) {

        String patron = "^[6|7|9][0-9]{8}$";

        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    /**
     * Método que valida si el parámetro es una fecha
     * @param cadena
     * @return
     */
    public static boolean validarSiFecha(String cadena){
        String patron = "([0-9]{4,})([-])([0-9]{2,})([-])([0-9]{2,})";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(cadena);

        return matcher.matches();
    }

    /**
     * Método para comprobar que el parámetro sigue el patrón asignado a los Emails
     * @param cadena
     * @return
     */
    public static Boolean validaEmail (String cadena) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    /**
     * Método que comprueba que el parámetro sigue el patrón asignado a los DNI
     * @param cadena
     * @return
     */
    public static boolean validarDNI(String cadena) {

        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(cadena.length() != 9 || Character.isLetter(cadena.charAt(8)) == false ) {
            return false;
        }


        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (cadena.substring(8)).toUpperCase();

        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(soloNumeros(cadena) == true && letraDNI(cadena).equals(letraMayuscula)) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean soloNumeros(String cadena) {

        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for(i = 0; i < cadena.length() - 1; i++) {
            numero = cadena.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if(miDNI.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }

    private static String letraDNI(String cadena) {
               // pasar miNumero a integer
        int miDNI = Integer.parseInt(cadena.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;
    }

    /**
     *Método que genera de forma automática un código a las reservas realizadas
     * @return
     */
    public static String generarCodigoReservas() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numeros = "0123456789";
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            codigo.append(letras.charAt(random.nextInt(letras.length())));
        }

        for (int i = 0; i < 2; i++) {
            codigo.append(numeros.charAt(random.nextInt(numeros.length())));
        }

        return codigo.toString();
    }

    /**
     * Método que verifica que el parámetro siga el patrón asignado al código de la reserva
     * @param codigo
     * @return
     */
    public static boolean verificarCodigoReserva (String codigo) {
        if (codigo.length() != 6) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (!Character.isLetter(codigo.charAt(i)) || !Character.isUpperCase(codigo.charAt(i))) {
                return false;
            }
        }
        for (int i = 4; i < 6; i++) {
            if (!Character.isDigit(codigo.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Método para encriptar las contraseñas de los usuarios en MD5
     * @param input
     * @return
     */
    public static String encriptarAMD5(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(SECRETKEY.getBytes("utf-8"));
            byte[] mensajeDiggester = md5.digest(input.getBytes());
            BigInteger numero = new BigInteger(1, mensajeDiggester);
            String tieneTexto = numero.toString(16);

            while (tieneTexto.length() < 32) {
                tieneTexto = "0" + tieneTexto;
            }
            return tieneTexto;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que desencripta las contraseñas encriptadas en MD5
     * @param passwordBD
     * @return
     */
    public static String desencriptarMD5(String passwordBD){
        try {
            byte[] mensaje= Base64.getDecoder().decode(passwordBD.getBytes("utf-8"));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] password = md5.digest(SECRETKEY.getBytes("utf-8"));
            byte[] keyPassword = Arrays.copyOf(password,24);
            SecretKey key = new SecretKeySpec(keyPassword, "DESede");
            Cipher descipher = Cipher.getInstance("DESede");
            descipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plaintext = descipher.doFinal(mensaje);

            return new String(plaintext, "UTF-8");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
