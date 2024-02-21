package com.example.instadamfinal.models;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;

public class Usuario {
    private String userName;
    private String  email;
    private String urlImagenPerfil;
    private Timestamp fechaRegistro;
    private List<Publicacion> publicaciones;

    public Usuario() {
    }

    public Usuario(String userName, String email, String urlImagenPerfil, Timestamp fechaRegistro, List<Publicacion> publicaciones) {
        this.userName = userName;
        this.email = email;
        this.urlImagenPerfil = urlImagenPerfil;
        this.fechaRegistro = fechaRegistro;
        this.publicaciones = publicaciones;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImagenPerfil() {
        return urlImagenPerfil;
    }

    public void setUrlImagenPerfil(String urlImagenPerfil) {
        this.urlImagenPerfil = urlImagenPerfil;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", urlImagenPerfil='" + urlImagenPerfil + '\'' +
                ", fechaRegistro=" + fechaRegistro.toDate() +
                ", publicaciones=" + publicaciones +
                '}';
    }
}
