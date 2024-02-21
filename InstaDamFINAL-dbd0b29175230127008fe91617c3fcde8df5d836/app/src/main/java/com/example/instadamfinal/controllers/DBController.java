package com.example.instadamfinal.controllers;

import android.content.Context;

import com.example.instadamfinal.db.DataBaseHelper;

public class DBController {

    public String loginUser(Context context,String email,String password){

        return checkUserDB(context,email,password);

    }
    public boolean registerUser(Context context,String userName,String email,String password){

        boolean exits = checkEmailUserDB(context, email);

        return (exits)?false:createNewUserDB(context, userName, email, password);
    }
    private String checkUserDB(Context context,String email,String password) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.checkEmailAndPasswordHelper(email,password);

    }
    private boolean checkEmailUserDB(Context context,String email) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.checkEmailHelper(email);

    }
    private boolean createNewUserDB(Context context,String userName,String email,String password) {
        boolean result = false;

        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            result = dataBaseHelper.createNewUserHelper(userName,email,password);
            return result;

        }catch (Exception e){
            return result;
        }
    }
}
