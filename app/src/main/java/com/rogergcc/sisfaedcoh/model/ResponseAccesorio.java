/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sisfaedcoh.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ResponseAccesorio {


    @Nullable
    @SerializedName("mensaje")
    private String mensaje;
    @Nullable
    @SerializedName("data")
    private Accesorio accesorio;
    @Nullable
    @SerializedName("cod")
    private String cod;
    @Nullable
    @SerializedName("status")
    private int status;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Accesorio getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(Accesorio accesorio) {
        this.accesorio = accesorio;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
