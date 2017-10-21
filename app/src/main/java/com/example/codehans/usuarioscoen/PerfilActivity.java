package com.example.codehans.usuarioscoen;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private EditText edtV_nombre_perfil;
    private EditText edtV_apellido_perfil;
    private EditText edtV_email_perfil;
    private ImageView photo_perfil;
    private Button btn_aceptar_perfil;
    //public static final String URL_CIUDADANOS = "http://10.24.9.6:8080/sigem/api/ciudadanos";
    public static final String URL_CIUDADANOS = "http://www.ocrm.gob.pe/sigem/api/ciudadanos";
    //public static final String URL_CIUDADANOS = "https://sistemas.mindef.gob.pe/sigem/api/ciudadanos";

    private String idUserPerfil;
    private String userNamePerfil;
    private String emailPerfil;
    private String nroCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        edtV_nombre_perfil = (EditText) findViewById(R.id.edtV_Nombre_Perfil);
        edtV_apellido_perfil = (EditText) findViewById(R.id.edtV_Apellidos_Perfil);
        edtV_email_perfil = (EditText) findViewById(R.id.edtV_Email_perfil);
        btn_aceptar_perfil = (Button) findViewById(R.id.Aceptar_perfil);
        photo_perfil = (ImageView) findViewById(R.id.imgV_PhotoPerfil);

        Intent in = getIntent();
        idUserPerfil = in.getStringExtra("idUser");
        userNamePerfil = in.getStringExtra("userName");
        //emailPerfil = in.getStringExtra("email");
        nroCelular = in.getStringExtra("nroCelular");
        //Toast.makeText(getApplicationContext()," "+TOKEN,Toast.LENGTH_LONG).show();

        //edtV_email_perfil.setText(emailPerfil);

        btn_aceptar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro_ciudadano();
                //todo verificar la necesidad de estas lineas
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    private void registro_ciudadano() {

        Log.d("ON PERFIL ID USER", "onCreate: " + idUserPerfil);
        Log.d("ON PERFIL USER NAME", "onCreate: " + userNamePerfil);
        //Log.d("ON PERFIL USER NAME", "onCreate: " + emailPerfil);
        Log.d("ON PERFIL USER NAME", "onCreate: " + nroCelular);
        final String apellido = edtV_apellido_perfil.getText().toString().trim();
        final String nombre = edtV_nombre_perfil.getText().toString().trim();
        final String email = edtV_email_perfil.getText().toString().trim();


        JSONObject js = new JSONObject();
        JSONObject js1 = new JSONObject();
        try {
            js1.put("idUser", Integer.parseInt(idUserPerfil));
            js1.put("username", userNamePerfil);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            js.put("apellidos", apellido);
            js.put("nombres", nombre);
            js.put("email", email);
            js.put("latitud", 0.1);
            js.put("longitud", 0.2);
            js.put("nroCelular", nroCelular);
            js.put("nrodni", userNamePerfil);
            js.put("ubigeoResidencia", "1111111");
            js.put("photo", "Aaaaa11a");
            js.put("user", js1);
            //js.put("langKey", "es");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL_CIUDADANOS, js,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ON RESPONSE CIUDADANOS", response.get("createdDate") + " i am queen");
                            Toast.makeText(PerfilActivity.this, "CIUDADANO REGISTRADO", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "HUBO UN ERROR CON LA INSCRIPCION", Toast.LENGTH_LONG).show();
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
                return headers;
            }
        };

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}
