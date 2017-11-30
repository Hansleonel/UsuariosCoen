package com.example.codehans.usuarioscoen;

/**
 * Created by ogtie05 on 17/11/2017.
 */

public class CentroAcopioInsumosContainer {
    int idInsumo;
    int StockInsumo;
    String descripcionInsumo;

    public CentroAcopioInsumosContainer() {
    }

    public CentroAcopioInsumosContainer(int idInsumo, int stockInsumo, String descripcionInsumo) {
        this.idInsumo = idInsumo;
        StockInsumo = stockInsumo;
        this.descripcionInsumo = descripcionInsumo;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getStockInsumo() {
        return StockInsumo;
    }

    public void setStockInsumo(int stockInsumo) {
        StockInsumo = stockInsumo;
    }

    public String getDescripcionInsumo() {
        return descripcionInsumo;
    }

    public void setDescripcionInsumo(String descripcionInsumo) {
        this.descripcionInsumo = descripcionInsumo;
    }
}
