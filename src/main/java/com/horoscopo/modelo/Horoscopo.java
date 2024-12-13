package com.horoscopo.modelo;

import java.util.Date;

public class Horoscopo {
    private String animal;
    private Date fechaInicio;
    private Date fechaFin;

    // Getters y Setters
    public String getAnimal() { return animal; }
    public void setAnimal(String animal) { this.animal = animal; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}
