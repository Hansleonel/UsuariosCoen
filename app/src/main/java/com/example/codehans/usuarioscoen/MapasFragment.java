package com.example.codehans.usuarioscoen;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapasFragment extends Fragment implements OnMapReadyCallback, AIListener {

    public static final String URL = "http://10.24.9.6:8080/sigem/api/predioffaasCustom";
    public static final String URLACOP = "http://10.24.9.6:8080/sigem/api/centroAcopioCustom";
    public static final String URLEVENTOS = "http://10.24.9.6:8080/sigem/api/eventoCustom";

    //todo: PARA EL USO DE MAPS RECUERDA EL USO DEL KEY ADEMAS DE IMPLEMENTAR
    //todo: el OnMapReadyCallB
    //todo: SI SE REQUIERE OBTENER MI UBICACION RECUERDA LOS PERMISOS
    GoogleMap MgoogleMap;
    MapView mapView;
    View view;
    public static final int LOCATION_REQUEST_CODE = 1;
    String TOKEN = " ";
    private FloatingActionButton floatingActionButton;
    private EditText editText_visualizar;

    AIService aiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mapas, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TOKEN = pref.getString("TOKENSTRING", "ERROR");
        //Toast.makeText(getContext(), " " + TOKEN, Toast.LENGTH_LONG).show();
        Log.d("MAPSFRAGMENT", "onCreateView: " + TOKEN);

        //todo AI configuration
        //todo RECORDAR IMPORTAR LA SEGUNDA LIBRERIA
        final AIConfiguration config = new AIConfiguration("7c004436321e46238cd852eaf3358767",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(getContext(), config);
        aiService.setListener(this);

        //editText_visualizar = (EditText) view.findViewById(R.id.edtV_ver);


        //todo FloatingButton dentro del fragment no es totalment recomendado
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_map);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "FUNCION FLOATING", Toast.LENGTH_LONG).show();
                aiService.startListening();
                //String predio = editText_visualizar.getText().toString();
                //if (predio.startsWith("MAR")) {
                //    Mostrar_Predios_MarinaGuerra();
                //} else if (predio.startsWith("EJE")) {
                //    Mostrar_Predios_Ejercito();
                //} else if (predio.startsWith("FU")) {
                //    Mostrar_Predios_FuerzaArea();
                //} else if (predio.startsWith("PRE")) {
                //    Mostrar_Predios();
                //} else if (predio.startsWith("EVE")) {
                //    Mostrar_Eventos();
                //} else if (predio.startsWith("CENT")) {
                //    Mostrar_Centro_Acopio();
                //}
            }
        });


        return view;

    }


    private void Mostrar_Eventos() {

        MgoogleMap.clear();
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo Para los JSONARRAY usamos este metodo
        //todo Verificar que no es igual al metodo de alerta donde solo se obtiene como respuesta un Jsonobjet
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URLEVENTOS, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < 200; i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String Descripicion = jrJsonObject.getString("descripcion");
                                Double lat = Double.parseDouble(latitud);
                                Double longit = Double.parseDouble(longitud);
                                MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
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
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        aiService.stopListening();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map_View);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        MgoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            MgoogleMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
                //DialogFragment.instantiate(getContext(), "Es  necesario el permiso", Bundle.EMPTY);
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
        //todo:USADO PARA EL ZOOM Y UBICACION DE LA REGION O LOCALIZACION
        CameraUpdate camupd = CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0431800, -77.0282400), 12);
        MgoogleMap.moveCamera(camupd);
        //todo:AGREGAR MARCADORES Y PERSONALIZARLOS
        //todo:PODEMOS AGRUPAR MARKERS POR CANTIDAD DENTRO DE UNA ZONA
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.046373, -71.042754)).title("Lima"));
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-14.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo MOSTRANDO PREDIOS
        //Mostrar_Predios();
        //todo MOSTRANDO CENTRO
        Mostrar_Centro_Acopio();
        //todo MOSTRANDO EVENTOS
        Mostrar_Eventos();
    }

    private void Mostrar_Centro_Acopio() {
        MgoogleMap.clear();
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo Para los JSONARRAY usamos este metodo
        //todo Verificar que no es igual al metodo de alerta donde solo se obtiene como respuesta un Jsonobjet
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URLACOP, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String predio = jrJsonObject.getString("predio");
                                String entidad = jrJsonObject.getString("entidad");
                                Double lat = Double.parseDouble(latitud);
                                Double longit = Double.parseDouble(longitud);
                                MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(predio).snippet(entidad).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_y)));
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
              //  Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        aiService.stopListening();
    }

    private void Mostrar_Predios() {
        MgoogleMap.clear();
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo Para los JSONARRAY usamos este metodo
        //todo Verificar que no es igual al metodo de alerta donde solo se obtiene como respuesta un Jsonobjet
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < 200; i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String Entidad = jrJsonObject.getString("entidad");
                                String Descripicion = jrJsonObject.getString("descripcion");
                                Double lat = Double.parseDouble(latitud);
                                Double longit = Double.parseDouble(longitud);
                                MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Entidad).snippet(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_g)));
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
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        aiService.stopListening();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "PERMISOS ASIGNADOS", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onResult(AIResponse response) {
        //todo USANDO AI PARA FILTRAR
        Log.d("RESPONSE", "onResult: " + response.toString());
        Result result01 = response.getResult();
        Toast.makeText(getContext(), "QUERY" + result01.getResolvedQuery() + " ACTION" + result01.getAction() + " RESULTADO" + result01.toString(), Toast.LENGTH_LONG).show();

        String actionResult = result01.getAction();
        if (actionResult.equals("show.place")) {
            Mostrar_Predios();
        } else if (actionResult.equals("show.gatheringplace")) {
            Mostrar_Centro_Acopio();
        } else if (actionResult.equals("show.eventsplace")) {
            Mostrar_Eventos();
        } else if (actionResult.equals("show.placearmy")) {
            Mostrar_Predios_Ejercito();
        } else if (actionResult.equals("show.placenavy")) {
            Mostrar_Predios_MarinaGuerra();
        } else if (actionResult.equals("show.placeFuerza")) {
            Mostrar_Predios_FuerzaArea();
        } //else if(actionResult.equals(" ")){
        //Enviar_Alerta();
        //}

    }

    private void Mostrar_Predios_FuerzaArea() {
        MgoogleMap.clear();
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo Para los JSONARRAY usamos este metodo
        //todo Verificar que no es igual al metodo de alerta donde solo se obtiene como respuesta un Jsonobjet
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < 200; i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String Entidad = jrJsonObject.getString("entidad");
                                String Descripicion = jrJsonObject.getString("descripcion");
                                if (Entidad.startsWith("FUERZA")) {
                                    Double lat = Double.parseDouble(latitud);
                                    Double longit = Double.parseDouble(longitud);
                                    MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Entidad).snippet(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_g)));
                                }
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
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        aiService.stopListening();
    }

    private void Mostrar_Predios_MarinaGuerra() {
        MgoogleMap.clear();
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo Para los JSONARRAY usamos este metodo
        //todo Verificar que no es igual al metodo de alerta donde solo se obtiene como respuesta un Jsonobjet
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < 200; i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String Entidad = jrJsonObject.getString("entidad");
                                String Descripicion = jrJsonObject.getString("descripcion");
                                if (Entidad.startsWith("MA")) {
                                    Double lat = Double.parseDouble(latitud);
                                    Double longit = Double.parseDouble(longitud);
                                    MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Entidad).snippet(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_g)));
                                }
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
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        aiService.stopListening();
    }

    private void Mostrar_Predios_Ejercito() {
        MgoogleMap.clear();
        //MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(-12.246373, -76.242754)).title("Lima").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        //todo Para los JSONARRAY usamos este metodo
        //todo Verificar que no es igual al metodo de alerta donde solo se obtiene como respuesta un Jsonobjet
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < 200; i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");
                                String longitud = jrJsonObject.getString("longitud");
                                String Entidad = jrJsonObject.getString("entidad");
                                String Descripicion = jrJsonObject.getString("descripcion");
                                if (Entidad.startsWith("EJE")) {

                                    Double lat = Double.parseDouble(latitud);
                                    Double longit = Double.parseDouble(longitud);
                                    MgoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(Entidad).snippet(Descripicion).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_g)));
                                }
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
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
        aiService.stopListening();
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {
        aiService.stopListening();
    }
}
