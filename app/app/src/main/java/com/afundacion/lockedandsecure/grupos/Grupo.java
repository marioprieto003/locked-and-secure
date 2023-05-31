package com.afundacion.lockedandsecure.grupos;

import com.afundacion.lockedandsecure.contrasenas.Contraseña;

import java.util.ArrayList;

public class Grupo  {
    int id, tamaño;
    String nombre;
    ArrayList<Contraseña> listaContraseñas;

    public Grupo(int id, int tamaño, String nombre, ArrayList<Contraseña> listaContraseñas) {
        this.id = id;
        this.tamaño = tamaño;
        this.nombre = nombre;
        this.listaContraseñas = listaContraseñas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Contraseña> getListaContraseñas() {
        return listaContraseñas;
    }

    public void setListaContraseñas(ArrayList<Contraseña> listaContraseñas) {
        this.listaContraseñas = listaContraseñas;
    }
}
