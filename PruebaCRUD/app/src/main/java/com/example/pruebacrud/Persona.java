package com.example.pruebacrud;

public class Persona {
    private String uid;
    private String Titulo;
    private String Descri;
    private String Fecha;
    private String Password;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescri() {
        return Descri;
    }

    public void setDescri(String descri) {
        Descri = descri;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return Titulo;
    }
}

