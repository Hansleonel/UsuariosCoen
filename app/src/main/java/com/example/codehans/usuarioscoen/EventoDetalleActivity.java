package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventoDetalleActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView txtV_City;
    private TextView txtV_Prioridad;
    private TextView txtV_Estado;
    private TextView txtV_Descripcion;
    private TextView txtV_FechaI;
    private TextView txtV_FechaF;
    private TextView txtV_titulo;

    private RecyclerView recyclerView_informes_por_evento;
    public static final String URL_INFORMES_POR_EVENTO = "http://www.ocrm.gob.pe/sigem/api/informe-eventosByEvento/";

    private String idEvento = " ";
    private String idFuncionario = " ";
    private String descripcionEvento = " ";
    private String nameFuncionario = " ";
    private String LatPut = " ";
    private String LonPut = " ";

    private String TOKEN = " ";

    private String URL_EVENTOS_USER_FUNCIONARIO = "http://www.ocrm.gob.pe/sigem/api/eventoByFuncionario";
    private String URL_EVENTOS_USER_CIUDADANO = "http://www.ocrm.gob.pe/sigem/api/eventoByCiudadano";
    private String URL_CENTROS_ACOPIO_POR_EVENTO = "http://www.ocrm.gob.pe/sigem/api/centro-eventos-byidevento/";

    private GoogleMap mapa;

    private String SHAREDLATITUD = " ";
    private String SHAREDLONGITUD = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Detalles Evento");
        setSupportActionBar(toolbar);

        final TabHost tabs = (TabHost) findViewById(R.id.TabHostEventoDetalle);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab01_evento_detalle");
        spec.setContent(R.id.tab1_evento_detalle);
        spec.setIndicator("DETALLES");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab02_evento_detalle");
        spec.setContent(R.id.tab2_evento_detalle);
        spec.setIndicator("PREDIOS");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_predio_detalle1);
        mapFragment.getMapAsync(this);

        txtV_City = (TextView) findViewById(R.id.txtV_eventoDetalle_ciudad);
        txtV_Prioridad = (TextView) findViewById(R.id.txtV_eventoDetalle_prioridad);
        txtV_Estado = (TextView) findViewById(R.id.txtV_eventoDetalle_estado);
        txtV_Descripcion = (TextView) findViewById(R.id.txtV_eventoDetalle_descripcion);
        txtV_FechaI = (TextView) findViewById(R.id.txtV_eventoDetalle_fechai);
        txtV_FechaF = (TextView) findViewById(R.id.txtV_eventoDetalle_fechaf);
        txtV_titulo = (TextView) findViewById(R.id.txtV_Titulo_Evento);


        SharedPreferences pref = getApplication().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TOKEN = pref.getString("TOKENSTRING", "ERROR");

        Intent i = getIntent();
        idEvento = i.getStringExtra("idEvento");

        txtV_titulo.setText("Detalles del Evento ID " + idEvento);


        //todo recuperando valores desde shared LOCATION
        SharedPreferences prefLocation = getSharedPreferences("LOCATIONFILE", Context.MODE_PRIVATE);
        SHAREDLATITUD = prefLocation.getString("LOCATIONLATITUD", "ERROR");
        SHAREDLONGITUD = prefLocation.getString("LOCATIONLONGITUD", "ERROR");

        Toast.makeText(getApplicationContext(), "SHARED LONGITUD ES " + SHAREDLONGITUD, Toast.LENGTH_LONG).show();

        evento_detalle(idEvento);
        ver_acopio_por_evento(idEvento);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar Informe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void ver_acopio_por_evento(String idEvento) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_CENTROS_ACOPIO_POR_EVENTO + "/" + idEvento, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String idCentroAcopio = jrJsonObject.getString("idCentroAcopio");
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String entidad = jrJsonObject.getString("entidad");
                                Double lat = Double.parseDouble(latitud);
                                Double longit = Double.parseDouble(longitud);
                                String tag = "-2";
                                mapa.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(idCentroAcopio).snippet(entidad).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_centro_ayuda))).setTag(tag);
                            }
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        Toast.makeText(getApplicationContext(), "el id del Evento es " + idEvento, Toast.LENGTH_LONG).show();
        CameraUpdate camupd = CameraUpdateFactory.newLatLngZoom(new LatLng(-9.189967, -75.015152), 5);
        mapa.moveCamera(camupd);
        //evento_detalle(idEvento);

        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag().equals("-2")) {
                    Intent i = new Intent(getApplicationContext(), CentroAcopioActivity.class);
                    i.putExtra("idCentroAcopio", marker.getTitle());
                    startActivity(i);
                }
                return false;
            }
        });
    }

    private void evento_detalle(final String evento) {

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_EVENTOS_USER_CIUDADANO + "/" + SHAREDLATITUD + "/" + SHAREDLONGITUD + "/100", null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String idEventojs = jrJsonObject.getString("idEvento");
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String fechai = jrJsonObject.getString("fechaInicio");
                                String prioridad = jrJsonObject.getString("prioridad");
                                String Descripicion = jrJsonObject.getString("descripEvento");
                                String idTipoEvento = jrJsonObject.getString("idTipoEvento");
                                String Estado = jrJsonObject.getString("estado");
                                Double lat = Double.parseDouble(latitud);
                                Double longit = Double.parseDouble(longitud);

                                if (evento.equals(idEventojs)) {
                                    descripcionEvento = Descripicion;
                                    txtV_City.setText("  Ciudad: " + latitud + " " + longitud);
                                    LatPut = latitud;
                                    LonPut = longitud;
                                    txtV_Prioridad.setText("  Prioridad:" + " " + prioridad);
                                    txtV_Estado.setText("  Estado:" + " " + Estado);
                                    txtV_Descripcion.setText("  Descripcion:" + " " + Descripicion);
                                    txtV_FechaI.setText("  Fecha de Inicio" + " " + fechai);
                                    mapa.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(LatPut), Double.parseDouble(LonPut))).title("Tipo De Evento: " + idTipoEvento).snippet("Prioridad: " + prioridad).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)));
                                    CameraUpdate camupd = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LatPut), Double.parseDouble(LonPut)), 12);
                                    mapa.moveCamera(camupd);

                                    //mapa.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(LatPut), Double.parseDouble(LonPut))).title("Tipo De Evento: " + idTipoEvento).snippet("Prioridad: " + prioridad).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_centros_ayuda)));

                                }
                            }
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
}
