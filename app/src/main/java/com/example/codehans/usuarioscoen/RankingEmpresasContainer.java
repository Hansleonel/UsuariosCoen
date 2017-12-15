package com.example.codehans.usuarioscoen;

/**
 * Created by ogtie05 on 14/12/2017.
 */

public class RankingEmpresasContainer {
    String id_actualizandose;
    String photo_empresa_actualizandose;
    String nombre_empresa_actualizandose;
    String tipo_ayuda_actualizandose;

    public RankingEmpresasContainer() {
    }

    public RankingEmpresasContainer(String id_actualizandose, String photo_empresa_actualizandose, String nombre_empresa_actualizandose, String tipo_ayuda_actualizandose) {
        this.id_actualizandose = id_actualizandose;
        this.photo_empresa_actualizandose = photo_empresa_actualizandose;
        this.nombre_empresa_actualizandose = nombre_empresa_actualizandose;
        this.tipo_ayuda_actualizandose = tipo_ayuda_actualizandose;
    }

    public String getId_actualizandose() {
        return id_actualizandose;
    }

    public void setId_actualizandose(String id_actualizandose) {
        this.id_actualizandose = id_actualizandose;
    }

    public String getPhoto_empresa_actualizandose() {
        return photo_empresa_actualizandose;
    }

    public void setPhoto_empresa_actualizandose(String photo_empresa_actualizandose) {
        this.photo_empresa_actualizandose = photo_empresa_actualizandose;
    }

    public String getNombre_empresa_actualizandose() {
        return nombre_empresa_actualizandose;
    }

    public void setNombre_empresa_actualizandose(String nombre_empresa_actualizandose) {
        this.nombre_empresa_actualizandose = nombre_empresa_actualizandose;
    }

    public String getTipo_ayuda_actualizandose() {
        return tipo_ayuda_actualizandose;
    }

    public void setTipo_ayuda_actualizandose(String tipo_ayuda_actualizandose) {
        this.tipo_ayuda_actualizandose = tipo_ayuda_actualizandose;
    }
}
