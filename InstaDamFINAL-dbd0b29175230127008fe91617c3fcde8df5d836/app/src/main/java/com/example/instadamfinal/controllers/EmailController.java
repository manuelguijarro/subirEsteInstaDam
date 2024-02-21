package com.example.instadamfinal.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailController {


    public boolean checkEmail(String email){
        if(!email.isEmpty()){

            String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(email);

            return matcher.matches();

        }else
            return false;

    }
}
