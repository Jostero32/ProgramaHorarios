/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS GAMER
 */
public class Reserva {

    private Object aulaLaboratorio;
    private Object responsable;
    private String observaciones;

    public Reserva(Object aulaLaboratorio, Object responsable, String observaciones) {
        this.aulaLaboratorio = aulaLaboratorio;
        this.responsable = responsable;
        this.observaciones = observaciones;
    }

    public Object getAulaLaboratorio() {
        return this.aulaLaboratorio;
    }

    public void setAulaLaboratorio(Object aulaLaboratorio) {
        this.aulaLaboratorio = aulaLaboratorio;
    }

    public Object getResponsable() {
        return this.responsable;
    }

    public void setResponsable(Object responsable) {
        this.responsable = responsable;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
