package com.example.instadamfinal.controllers;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.instadamfinal.db.DataBaseHelper;
import com.example.instadamfinal.db.FirebaseDataBaseHelper;

public class DBController {

    public String loginUser(Context context,String email,String password){

        return checkUserDB(context,email,password);

    }

    private String checkUserDB(Context context,String email,String password) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.checkEmailAndPasswordHelper(email,password);

    }
    public boolean registrarUsuario(Context context, String nombreUsuario, String emailUsuario, String passwordUsuario){

        boolean existeEmailUsuario = verificarEmailUsuarioDB(context, emailUsuario);

        if (existeEmailUsuario)
            return false;//porque existe el email en la base de datos

        boolean resultadoCrearNuevoUsuario = crearNuevoUsuarioDB(context, nombreUsuario, emailUsuario, passwordUsuario);

        return resultadoCrearNuevoUsuario;
    }
    private boolean verificarEmailUsuarioDB(Context context, String emailUsuario) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        boolean resultadoExisteUsuario = dataBaseHelper.verificarExisteEmailUsuarioHelper(emailUsuario);

        return resultadoExisteUsuario;
    }
    @SuppressLint("RestrictedApi")
    private boolean crearNuevoUsuarioDB(Context context, String nombreUsuario, String emailUsuario, String passwordUsuario) {

        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            boolean resultado = dataBaseHelper.crearNuevoUsuarioHelper(nombreUsuario,emailUsuario,passwordUsuario);
            if (resultado){
                FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
                firebaseDataBaseHelper.crearNuevoUsuarioFirebaseHelper(nombreUsuario,emailUsuario);
            }
            return resultado;

        }catch (Exception e){
            return false;
        }
    }
}