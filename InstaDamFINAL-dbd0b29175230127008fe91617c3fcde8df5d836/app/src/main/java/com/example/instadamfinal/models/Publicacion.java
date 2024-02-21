package com.example.instadamfinal.models;

public class Publicacion {
    private int numeroDeLikes;
    private String urlImagenPublicacion;

    public Publicacion() {
    }

    public Publicacion(int numeroDeLikes, String urlImagenPublicacion) {
        this.numeroDeLikes = numeroDeLikes;
        this.urlImagenPublicacion = urlImagenPublicacion;
    }

    public int getNumeroDeLikes() {
        return numeroDeLikes;
    }

    public void setNumeroDeLikes(int numeroDeLikes) {
        this.numeroDeLikes = numeroDeLikes;
    }

    public String getUrlImagenPublicacion() {
        return urlImagenPublicacion;
    }

    public void setUrlImagenPublicacion(String urlImagenPublicacion) {
        this.urlImagenPublicacion = urlImagenPublicacion;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "numeroDeLikes=" + numeroDeLikes +
                ", urlImagenPublicacion='" + urlImagenPublicacion + '\'' +
                '}';
    }
}
