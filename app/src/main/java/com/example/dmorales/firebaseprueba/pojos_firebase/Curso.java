package com.example.dmorales.firebaseprueba.pojos_firebase;

public class Curso {
    private String id;
    private String eap;
    private int ciclo;
    private String nombre;
    private String coordinador;

    public Curso(String id, String eap, int ciclo, String nombre, String coordinador) {
        this.id = id;
        this.eap = eap;
        this.ciclo = ciclo;
        this.nombre = nombre;
        this.coordinador = coordinador;
    }

    public Curso() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEap() {
        return eap;
    }

    public void setEap(String eap) {
        this.eap = eap;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }
}
