

/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sisfaedcoh.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rogergcc.sisfaedcoh.R;
import com.rogergcc.sisfaedcoh.constants.Constans;
import com.rogergcc.sisfaedcoh.database.DatabaseHelper;
import com.rogergcc.sisfaedcoh.fragment.BarcodeFragment;
import com.rogergcc.sisfaedcoh.fragment.LicenseFragment;
import com.rogergcc.sisfaedcoh.fragment.AccesoriosListFragment;
import com.rogergcc.sisfaedcoh.generics.base.MyApplication;
import com.rogergcc.sisfaedcoh.model.Product;
import com.rogergcc.sisfaedcoh.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BarcodeFragment.ScanRequest {

    private Context context ;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String BARCODE_KEY = "BARCODE";
    private Barcode barcodeResult;
    private final String TAG = MainActivity.class.getSimpleName() ;
    private final int MY_PERMISSION_REQUEST_CAMERA = 1001;
    private ItemScanned itemScanned ;

    Dialog dialogSession;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Qr Inventario");
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BarcodeFragment(), "Scanear Codigo");
        adapter.addFragment(new AccesoriosListFragment(), "Accesorios ");
        viewPager.setAdapter(adapter);
    }

    public String getScanTime() {
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a" , Locale.getDefault());
        return  timeFormat.format(new Date());
    }

    public String getScanDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy",Locale.getDefault());
        return dateFormat.format(new Date());
    }

    @Override
    public void scanBarcode() {
        /** This method will listen the button clicked passed form the fragment **/
        checkPermission();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dialogSession = new Dialog(MainActivity.this);

        dialogSession.setContentView(R.layout.dialog_login);
        int id = item.getItemId();
        switch (id){

            case R.id.item_login:
                 session = new SessionManager(getApplicationContext());

                if (session.isLoggedIn()){

                    HashMap<String, String> user = session.getUserDetails();
                    String sesion_usuario = user.get(SessionManager.KEY_USUARIO_NOMBRE);
                    String sesion_id = user.get(SessionManager.KEY_USUARIO_ID);
                    Toast.makeText(context, "Usuario "+sesion_usuario, Toast.LENGTH_SHORT).show();

                }else {
                    openDialogLogin();
                }

                break;
            case R.id.item_logut:
                session = new SessionManager(getApplicationContext());
                if (session.isLoggedIn()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Estas Seguro? ")

                            .setTitle("Cerra Sesión");

                    builder.setPositiveButton(R.string.ok_title, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            session.logoutUser();
                            //AccesoriosListFragment.mRecyclerView.setAdapter(null);

                            Intent i = new Intent(getBaseContext(), MainActivity.class);

                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();

                    //session.logoutUser();

                }else {
                    Toast.makeText(context, "No se ha iniciado sesión", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.item_share:
                openShare();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialogLogin() {




        final EditText dialog_usuario = dialogSession.findViewById(R.id.dialog_usuario);
        final EditText dialog_contrasenia = dialogSession.findViewById(R.id.dialog_contrasenia);
        Button btnIniciarSesion = dialogSession.findViewById(R.id.btnAprobarIP);
        //botonCancelar = myDialog.findViewById(R.id.btnCancelar);

        dialogSession.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogSession.show();
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = dialog_usuario.getText().toString();
                final String password = dialog_contrasenia.getText().toString();

                //https://api.myjson.com/bins/wicz0
                //String url = "http://192.168.0.12/documentosLista.json";
                if (TextUtils.isEmpty(username)) {
                    dialog_usuario.setError("Ingrese su Usuario");
                    dialog_usuario.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    dialog_contrasenia.setError("Ingrese su Contraseña");
                    dialog_contrasenia.requestFocus();
                    return;
                }
                iniciarSesionWS(dialog_usuario.getText().toString(),dialog_contrasenia.getText().toString());


            }
        });
    }

    private void iniciarSesionWS(String usuario, String contrasenia) {
        Gson gson = new GsonBuilder().create();
        Map<String, String> params = new HashMap<>();
        params.put("nombre", usuario);
        params.put("clave", contrasenia);

        String json = gson.toJson(params);// obj is your object
        JSONObject jsonObj = null;;
        try {

            jsonObj = new JSONObject(json);
            //js.put("documentoId","A");

        }catch (JSONException e) {
            e.printStackTrace();
        }
        String URls = Constans.URL+ "api/login";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                URls,
                jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressBar.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        try {


                            //if no error in response
                            if (response.getBoolean("respuesta")) {

                                Toast.makeText(context, response.getString("mensaje"), Toast.LENGTH_SHORT).show();

                                JSONObject objectUser = response.getJSONObject("usuario");


                                String codusuario = objectUser.getString("codusuario");
                                String nombre = objectUser.getString("nombre");
                                String email = objectUser.getString("email");




                                //mensajeLogin = mensaje;

                                session.crearSesion(nombre, codusuario,email);
                                dialogSession.hide();

                            } else {
                                //Toast.makeText(getApplicationContext(), jsonObject.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            //DynamicToast.makeWarning(getBaseContext(), "Error Tiempo de Respuesta Inicio de Sesión", Toast.LENGTH_LONG).show();

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type","application/x-www-form-urlencoded");
                //params.put("nombre",edtNombreImagen);
                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }



    private void openShare() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        String appLink = " apps details?id="+context.getPackageName();
        sharingIntent.setType("text/plain");
        String shareBodyText = "Check Out The Cool Barcode Reader App \n Link: "+appLink +" \n" +
                " #Barcode #Android";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Barcode Reader Android App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }






    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Estas Seguro? ")
                .setTitle(R.string.exit_title);
        builder.setPositiveButton(R.string.ok_title, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                session.logoutUser();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG , getResources().getString(R.string.camera_permission_granted));
            startScanningBarcode();
        } else {
            requestCameraPermission();

        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            ActivityCompat.requestPermissions(MainActivity.this,  new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);

        } else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void startScanningBarcode() {
        /*
          Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(MainActivity.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scaneando...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;

//                        dialogo guardar lista y db local
//                        showDialog(barcode.rawValue , getScanTime(),getScanDate());
                        Intent intent = new Intent(MainActivity.this, TicketResultActivity.class);
                        intent.putExtra("code", barcode.rawValue);
                        startActivity(intent);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSION_REQUEST_CAMERA && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScanningBarcode();
        } else {
            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.sorry_for_not_permission), Snackbar.LENGTH_SHORT)
                    .show();
        }

    }


    public interface  ItemScanned{
        void itemUpdated();
    }

}
