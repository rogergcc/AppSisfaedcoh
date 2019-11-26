/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sisfaedcoh.model;



import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Accesorio {


    @Nullable
    @SerializedName("estado")
    private String estado;
    @Nullable
    @SerializedName("codbarra")
    private String codbarra;
    @Nullable
    @SerializedName("observacion")
    private String observacion;
    @Nullable
    @SerializedName("serie")
    private String serie;
    @Nullable
    @SerializedName("modelo")
    private String modelo;
    @Nullable
    @SerializedName("marca")
    private String marca;
    @Nullable
    @SerializedName("denominacion")
    private String denominacion;
    @Nullable
    @SerializedName("codigo")
    private String codigo;
    @Nullable
    @SerializedName("codigobandeja")
    private String codigobandeja;
    @Nullable
    @SerializedName("codigoaccesorio")
    private String codigoaccesorio;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodbarra() {
        return codbarra;
    }

    public void setCodbarra(String codbarra) {
        this.codbarra = codbarra;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigobandeja() {
        return codigobandeja;
    }

    public void setCodigobandeja(String codigobandeja) {
        this.codigobandeja = codigobandeja;
    }

    public String getCodigoaccesorio() {
        return codigoaccesorio;
    }

    public void setCodigoaccesorio(String codigoaccesorio) {
        this.codigoaccesorio = codigoaccesorio;
    }
}
