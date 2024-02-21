package com.example.instadamfinal.controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordController {
    public  String get_SHA_512_SecurePassword(String passwordToHash, String salt){

        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean checkPassword(String password) {
        if (!password.isEmpty()){
            String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=<>?]).{8,}$";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(password);

            return matcher.matches();
        }else
            return false;

    }
}
