/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sisfaedcoh.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.rogergcc.sisfaedcoh.activity.MainActivity;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file usuarioNombre
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User usuarioNombre (make variable public to access from outside)
    public static final String KEY_USUARIO_NOMBRE = "usuarioNombre";

    // Email address (make variable public to access from outside)
    public static final String KEY_USUARIO_ID = "usuarioId";

    public static final String KEY_EMPLEADO_ID = "empleadoId";

    public static final String KEY_USUARIO_EMPLEADO = "usuarioEmpleado";

    public static final String KEY_USUARIO_CORREO = "usuarioCorreo";

    public static final String KEY_USUARIO_ROL = "usuarioRol";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String usuarioNombre,String empleadoId, String usuarioId,String empleado, String correo, String rol){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing usuarioNombre in pref
        editor.putString(KEY_USUARIO_NOMBRE, usuarioNombre);

        editor.putString(KEY_EMPLEADO_ID, empleadoId);
        // Storing usuarioId in pref
        editor.putString(KEY_USUARIO_ID, usuarioId);

        editor.putString(KEY_USUARIO_EMPLEADO, empleado);

        editor.putString(KEY_USUARIO_CORREO, correo);

        editor.putString(KEY_USUARIO_ROL, rol);
        // commit changes
        editor.commit();
    }

    public void crearSesion(String usuarioNombre, String usuarioId, String correo){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing usuarioNombre in pref
        editor.putString(KEY_USUARIO_NOMBRE, usuarioNombre);

        // Storing usuarioId in pref
        editor.putString(KEY_USUARIO_ID, usuarioId);


        editor.putString(KEY_USUARIO_CORREO, correo);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user usuarioNombre
        user.put(KEY_USUARIO_NOMBRE, pref.getString(KEY_USUARIO_NOMBRE, null));

        // user usuarioId id
        user.put(KEY_USUARIO_ID, pref.getString(KEY_USUARIO_ID, null));

        user.put(KEY_EMPLEADO_ID, pref.getString(KEY_EMPLEADO_ID, null));

        user.put(KEY_USUARIO_EMPLEADO, pref.getString(KEY_USUARIO_EMPLEADO, null));

        user.put(KEY_USUARIO_CORREO, pref.getString(KEY_USUARIO_CORREO, null));

        user.put(KEY_USUARIO_ROL, pref.getString(KEY_USUARIO_ROL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        //_context.startActivity(i);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
