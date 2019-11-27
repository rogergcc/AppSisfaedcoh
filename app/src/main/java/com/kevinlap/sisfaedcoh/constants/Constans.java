/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.kevinlap.sisfaedcoh.constants;

import android.content.Context;
import android.content.SharedPreferences;

public class Constans {

//    public static final String URL ="http://192.168.0.23/SISFAEDCOH/";

    public static final String URL = "http://192.168.0.23/SISFAEDCOH/";
    public static final String PROJECTNAME = "/SISFAEDCOH/";

    public static final String LOGIN = PROJECTNAME + "api/login";

    public static final String OBTENERACCESORIOS = PROJECTNAME + "api/accesorios";
    public static final String BUSCARBARCODE = PROJECTNAME + "api/accesorio/";
    public static final String IMAGENES = PROJECTNAME + "inventario/accesorios/";


    /*
    * metodo para obtener la ip configurada
    * */
    public static String obtenerIp(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Protocol", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");
        return ip;
    }

}
