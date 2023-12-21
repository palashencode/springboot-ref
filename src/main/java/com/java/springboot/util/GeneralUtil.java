package com.java.springboot.util;

public class GeneralUtil {

    public static String[] toUpper(String[] arr){
        String[] response = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == null) continue;

            response[i] = arr[i].toUpperCase();
        }
        return response;
    }

}
