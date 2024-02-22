package com.example.instadamfinal.controllers;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.instadamfinal.db.DataBaseHelper;
import com.example.instadamfinal.db.FirebaseDataBaseHelper;

public class DBController {

    public boolean logearUsuarioController(Context context, String emailUsuario, String passwordUsuario){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.comprobarEmailPasswordUsuarioDB(emailUsuario,passwordUsuario);

    }

    public boolean registrarUsuario(Context context, String nombreUsuario, String emailUsuario, String passwordUsuario){

        boolean existeEmailUsuario = verificarExisteEmailUsuarioDB(context, emailUsuario);

        if (existeEmailUsuario)
            return false;//porque existe el email en la base de datos

        boolean resultadoCrearNuevoUsuario = crearNuevoUsuarioDB(context, nombreUsuario, emailUsuario, passwordUsuario);

        return resultadoCrearNuevoUsuario;
    }
    private boolean verificarExisteEmailUsuarioDB(Context context, String emailUsuario) {

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
                crearNuevoUsuarioFirebaseDB(nombreUsuario,emailUsuario);
            }
            return resultado;

        }catch (Exception e){
            return false;
        }
    }
    private void crearNuevoUsuarioFirebaseDB(String nombreUsuario, String emailUsuario){
        FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
        firebaseDataBaseHelper.crearNuevoUsuarioFirebaseHelper(nombreUsuario,emailUsuario);
    }
}