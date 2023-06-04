/*
 * *
 *  * Created by mprieto on 1/6/23 9:13
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 1/6/23 8:55
 *
 */

package com.afundacion.lockedandsecure.contrasenas;

import java.io.Serializable;

public class Contraseña implements Serializable {
    int id;
    String contraseña, email, usuario, fecha, plataforma;

    public Contraseña(int id, String contraseña, String email, String usuario, String fecha, String plataforma) {
        this.id = id;
        this.contraseña = contraseña;
        this.email = email;
        this.usuario = usuario;
        this.fecha = fecha;
        this.plataforma = plataforma;
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

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
