package com.example.codehans.usuarioscoen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CentroAcopioActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String URL_CENTROSACOPIO_BY_ID = "http://www.ocrm.gob.pe/sigem/api/centroAcopioCustom/";
    private static final String URL_INSUMOS_BY_CENTRO_ACOPIO = "http://www.ocrm.gob.pe/sigem/api/insumo-centroacopio/";
    private String TOKEN = " ";
    private GoogleMap mapa;

    private String idAcopio = " ";
    private TextView txtV_TipoAyuda;
    private TextView txtV_Contacto;
    private TextView txtV_Telefono;
    private TextView txtV_Direccion;
    private TextView txtV_Entidad;

    private RecyclerView recyclerView_centro_acopio_insumos;
    private ArrayList<CentroAcopioInsumosContainer> ArrayListInsumos;

    private String TELEFONO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centro_acopio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Centro de Ayuda");
        setSupportActionBar(toolbar);


        final TabHost tabs = (TabHost) findViewById(R.id.TabHostCentroAcopio);
        tabs.setup();

        txtV_TipoAyuda = (TextView) findViewById(R.id.txtV_CentroAcopio_Tipo);
        txtV_Contacto = (TextView) findViewById(R.id.txtV_CentroAcopio_Contacto);
        txtV_Telefono = (TextView) findViewById(R.id.txtV_CentroAcopio_Telefono);
        txtV_Direccion = (TextView) findViewById(R.id.txtV_CentroAcopio_Direccion);
        txtV_Entidad = (TextView) findViewById(R.id.txtV_CentroAcopio_Entidad);
        recyclerView_centro_acopio_insumos = (RecyclerView) findViewById(R.id.RecView_CentroAcopio_Insumos);
        recyclerView_centro_acopio_insumos.setHasFixedSize(true);

        ArrayListInsumos = new ArrayList<CentroAcopioInsumosContainer>();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab01");
        spec.setContent(R.id.tab1_centroAcopio);
        spec.setIndicator("DETALLES");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab02");
        spec.setContent(R.id.tab2_centroAcopio);
        spec.setIndicator("INSUMOS");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_centroAcopio);
        mapFragment.getMapAsync(this);

        SharedPreferences pref = getApplication().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TOKEN = pref.getString("TOKENSTRING", "ERROR");
        Intent i = getIntent();
        idAcopio = i.getStringExtra("idCentroAcopio");

        permisos();

        ver_insumos(idAcopio);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Realizando llamada", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                making_cll();
            }
        });
    }

    private void ver_insumos(String idAcopio) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_INSUMOS_BY_CENTRO_ACOPIO + idAcopio, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                int idInsumo = jrJsonObject.getInt("idInsumo");
                                int stock = jrJsonObject.getInt("stock");
                                String descripcion = jrJsonObject.getString("descripcion");


                                ArrayListInsumos.add(new CentroAcopioInsumosContainer(idInsumo, stock, descripcion));
                                Log.d("ON CENTRO DE ACOPIO", "onResponse: " + descripcion);

                            }
                            CentroAcopioInsumosAdapter adaptador = new CentroAcopioInsumosAdapter(ArrayListInsumos);
                            recyclerView_centro_acopio_insumos.setAdapter(adaptador);
                            recyclerView_centro_acopio_insumos.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            //recyclerView_centro_acopio_insumos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
                            //JSONObject jresponse = response.getJSONObject(0);
                            //String descripcion = jresponse.getString("latitud");
                            //Double latitud = Double.parseDouble(descripcion);
                            //Log.d("DESCRIPCION", latitud + " size " + response.length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("eRRor Response", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), "ON RESPONSE" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + TOKEN);
                return headers;
            }
        };

        // Adding request to request queue
        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }

    private void permisos() {
        final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CentroAcopioActivity.this,
                        android.Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions(CentroAcopioActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    making_cll();
                } else {
                    System.out.println("El usuario ha rechazado el permiso");
                }
                return;
            }
        }
    }

    private void making_cll() {
        String dial = "tel:" + TELEFONO;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        Toast.makeText(getApplicationContext(), "el id del predio es " + idAcopio, Toast.LENGTH_LONG).show();
        CameraUpdate camupd = CameraUpdateFactory.newLatLngZoom(new LatLng(-9.189967, -75.015152), 5);
        mapa.moveCamera(camupd);
        centro_acopio_descripcion(idAcopio);
    }

    private void centro_acopio_descripcion(final String idAcopio) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, URL_CENTROSACOPIO_BY_ID + idAcopio, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txtV_TipoAyuda.setText("  Tipo de Ayuda Ofrecida: " + response.get("tipo").toString());
                            txtV_Contacto.setText("  Contacto: " + response.get("contacto").toString());
                            txtV_Telefono.setText("  Telefono: " + response.get("telefono").toString());
                            TELEFONO = response.get("telefono").toString();
                            txtV_Direccion.setText("  Direccion: " + response.get("direccion").toString());
                            txtV_Entidad.setText("  Entidad del Local: " + response.get("entidad").toString());
                            Double latitud = Double.parseDouble(response.get("latitud").toString());
                            Double longitud = Double.parseDouble(response.get("longitud").toString());
                            String direccion = response.get("direccion").toString();
                            mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(idAcopio).snippet(direccion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_centro_ayuda)));

                            CameraUpdate camupd = CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 12);
                            mapa.moveCamera(camupd);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("EEEEEE", "Error: " + error.toString());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + TOKEN);
                return headers;
            }
        };

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

}
