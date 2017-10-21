package com.example.codehans.usuarioscoen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaAlertaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaAlertaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaAlertaFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final String TAG = "LOCALIZACION";
    //public static final String URL = "http://10.24.9.6:8080/sigem/api/reporte-ciudadanos";
    //public static final String URL_CONTACTOS = "http://10.24.9.6:8080/sigem/api/contactos";
    //public static final String URL_CONTACTOS_BY_USER = "http://10.24.9.6:8080/sigem/api/contactosByCiudadano";


    public static final String URL = "http://www.ocrm.gob.pe/sigem/api/reporte-ciudadanos";
    public static final String URL_CONTACTOS = "http://www.ocrm.gob.pe/sigem/api/contactos";
    public static final String URL_CONTACTOS_BY_USER = "http://www.ocrm.gob.pe/sigem/api/contactosByCiudadano";
    public static final String URL_TIPOEVENTO = "http://www.ocrm.gob.pe/sigem/api/tipo-eventos";


    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private EditText editText_ubicacion;
    private EditText editText_message;
    private Spinner spinner_tipoE;
    private Spinner spinner_tipoC;
    private Button btn_alert;

    private GoogleApiClient apiClient;

    public String LATI = " ";
    public String LONGI = " ";
    String TOKEN = " ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_nueva_alerta, container, false);
        //todo uso de sharedpreference para la recuperacion del token
        SharedPreferences pref = getActivity().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TOKEN = pref.getString("TOKENSTRING", "ERROR");

        //tipos_eventos();
        permisos();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat nformat = new SimpleDateFormat("dd-MM-yyyy");
        String date = nformat.format(c.getTime());


        editText_ubicacion = (EditText) relativeLayout.findViewById(R.id.edtV_Ubicacion);
        editText_message = (EditText) relativeLayout.findViewById(R.id.edtV_Message);
        spinner_tipoE = (Spinner) relativeLayout.findViewById(R.id.cmbx_TipoE);
        //spinner_tipoC = (Spinner) relativeLayout.findViewById(R.id.cmbx_TipoC);
        btn_alert = (Button) relativeLayout.findViewById(R.id.btn_accept_alert);
        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getContext(), ReceiptActivity.class);
                //startActivity(i);
                envio_reporte_alerta(v);
            }
        });


        String[] emergency = new String[]{"TIPO EVENTO 1", "TIPO EVENTO 2", "TIPO EVENTO 3", "TIPO EVENTO 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, emergency);
        spinner_tipoE.setAdapter(adapter);

        //String[] concecuency = new String[]{"Puente Caido","Colegio Colapsado","Plaza Inundada"};
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,concecuency);


        apiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage((FragmentActivity) getContext(), this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API).build();


        return relativeLayout;
    }

    private void tipos_eventos() {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_TIPOEVENTO, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String latitud = jrJsonObject.getString("latitud");


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
    }

    private void envio_reporte_alerta(final View v) {

        String message = editText_message.getText().toString();
        String lugar = editText_ubicacion.getText().toString();
        Double lat = Double.parseDouble(LATI);
        Double lng = Double.parseDouble(LONGI);
        //Toast.makeText(getApplicationContext()," "+TOKEN,Toast.LENGTH_LONG).show();

        JSONObject jsTipoEvento = new JSONObject();
        try {
            jsTipoEvento.put("createdDate", "2017-09-29T15:27:25Z");
            jsTipoEvento.put("estado", "ACTIVO");
            jsTipoEvento.put("idTipoEvento", 1);
            jsTipoEvento.put("descripcion", "TIPO EVENTO 1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject js = new JSONObject();
        try {
            js.put("descripcion", message);
            js.put("lugar", lugar);
            js.put("estado", "ACTIVO");
            js.put("fecha", "2017-08-19");
            js.put("latitud", lat);
            js.put("longitud", lng);
            js.put("tipo", jsTipoEvento);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL, js,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("RESPONSE CORRECTO", response.get("idRepCiudadano") + " i am queen");
                            Toast.makeText(getContext(), "LA ALERTA HA SIDO ENVIADA", Toast.LENGTH_LONG).show();
                            enviar_mensajes_contactos();
                            //Snackbar.make(v,"CONFIRMA TU PASSWORD CON",Snackbar.LENGTH_INDEFINITE).setAction("LOGIN",new View.OnClickListener(){
                            //  @Override
                            //public void onClick(View v) {
                            //  finish();
                            //}
                            //}).show();
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
        Volley.newRequestQueue(getContext()).add(jsonObjReq);
    }

    private void enviar_mensajes_contactos() {

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_CONTACTOS_BY_USER, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String numero = jrJsonObject.getString("nroCelular");
                                String photo = jrJsonObject.getString("photo");
                                String alias = jrJsonObject.getString("alias");
                                Log.d("ON RESULT GETCONTACTS", "onResponse: " + numero + photo);
                                enviar_sms(numero, alias);
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
                //Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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

    }

    private void enviar_sms(String numero, String alias) {
        String nombre_contacto = alias;
        String numero_contacto = numero;
        String mensaje = "Hola" + nombre_contacto + " Eh enviado un mensaje de emergencia desde" + editText_ubicacion ;


        try {
            int permissionChek = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
            if (permissionChek != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "NO SE TIENEN LOS PERMISOS NECESARIOS", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MENSAJE DE TEXTO", "enviar_sms: SE TIENE PERMISOS");
            }

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero_contacto, null, mensaje, null, null);
        } catch (Exception e) {
            Toast.makeText(getContext(), "MENSAJE NO ENVIADO ERROR", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void permisos() {

        int permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS);

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.SEND_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        1002);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 2909: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                } else {
                    Log.e("Permission", "Denied");
                }
                break;
            }
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "PERMISO OTORGADO", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "PERMISO NO OTORGADO", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case 1002: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }

    }

    private void updateUI(Location location) {


        if (location != null) {
            Geocoder geocoder = new Geocoder(getContext());
            String address = " ";
            String city = " ";
            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                //List<Address> addressList = geocoder.getFromLocation(-12.0651300, -75.2048600, 1);
                address = addressList.get(0).getCountryName();
                city = addressList.get(0).getLocality();
                Log.d(TAG, "updateUI: COUNTRY " + address);
                Log.d(TAG, "updateUI: COUNTRY CODE" + addressList.get(0).getCountryCode());
                Log.d(TAG, "updateUI: CIUDAD " + city);
                Log.d(TAG, "updateUI: " + addressList.get(0).getMaxAddressLineIndex());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //editText_ubicacion.setText("Latitud: " + String.valueOf(location.getLatitude()) + " Longitud" + String.valueOf(location.getLongitude()));
            editText_ubicacion.setText(" " + address + " " + city);
            Toast.makeText(getContext(), "DIRECCION " + address, Toast.LENGTH_LONG).show();
            LATI = String.valueOf(location.getLongitude());
            LONGI = String.valueOf(location.getLatitude());
        } else {
            editText_ubicacion.setText("Latitud: (desconocida) " + "Longitud: (desconocida)");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended: Se ha interrumpido la conexión con Google Play Services");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "SE AH PRODUCIDO UN ERROR CON PLAY SERVICES");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apiClient.stopAutoManage(getActivity());
        apiClient.disconnect();
    }
}
