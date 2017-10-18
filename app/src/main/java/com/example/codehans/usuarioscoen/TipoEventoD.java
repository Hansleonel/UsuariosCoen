package com.example.codehans.usuarioscoen;

/**
 * Created by ogtie05 on 18/10/2017.
 */

public class TipoEventoD {

    private String createdDate;
    private String estado;
    private String idTipoEvento;
    private String descripcion;

    public TipoEventoD(String createdDate, String estado, String idTipoEvento, String descripcion) {
        this.createdDate = createdDate;
        this.estado = estado;
        this.idTipoEvento = idTipoEvento;
        this.descripcion = descripcion;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(String idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
