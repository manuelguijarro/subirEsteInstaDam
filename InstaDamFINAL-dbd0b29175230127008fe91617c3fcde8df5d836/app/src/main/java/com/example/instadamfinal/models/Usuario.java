package com.example.instadamfinal.models;

public class Usuario {
    private String nombreUsuario;
    private String emailUsuario;

    public Usuario(String nombreUsuario, String emailUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
