package com.donoso.easyflight.utils;

import com.donoso.easyflight.vista.Main;

public class EncriptarMain {

    public static void main(String[] args) {
       String encriptada = Utiles.encriptarAMD5("12345");
       System.out.println(encriptada);
    }

}
