package com.afundacion.lockedandsecure.contrasenas;

public class Contraseña {
    int id;
    String contraseña, email, usuario, fecha;

    public Contraseña(int id, String contraseña, String email, String usuario, String fecha) {
        this.id = id;
        this.contraseña = contraseña;
        this.email = email;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
