package com.example.instadamfinal.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailController {

    public static boolean comprobarEmail(String email){
        if(!email.isEmpty()){

            String expresionRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
            Pattern pattern = Pattern.compile(expresionRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();

        }else
            return false;

    }
}
