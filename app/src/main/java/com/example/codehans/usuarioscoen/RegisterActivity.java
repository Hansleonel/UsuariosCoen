package com.example.codehans.usuarioscoen;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //public static final String URL = "http://10.24.9.6:8080/sigem/api/registerMobil";
    //public static final String URL = "https://sistemas.mindef.gob.pe/sigem/api/registerMobil";
    public static final String URL = "http://www.ocrm.gob.pe/sigem/api/registerMobil";
    public static final String KEY_MAIL = "email";
    public static final String KEY_LENGUAGE = "langKey";
    public static final String KEY_DNI = "login";
    public static final String KEY_PASSWORD = "password";

    private EditText edtV_user, edtV_numero, edtV_DNI, edtV_mail, edtV_Password;
    private Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtV_DNI = (EditText) findViewById(R.id.edtV_DNI);
        edtV_numero = (EditText) findViewById(R.id.edtV_numero_telefonico);
        edtV_mail = (EditText) findViewById(R.id.edtV_mail);
        edtV_Password = (EditText) findViewById(R.id.edtV_password);

        btn_registrar = (Button) findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_user(v);
            }
        });

    }

    private void registrar_user(final View v) {

        final String dni = edtV_DNI.getText().toString().trim();
        final String numero = edtV_numero.getText().toString().trim();
        final String mail = edtV_mail.getText().toString().trim();
        final String password = edtV_Password.getText().toString().trim();
        final String nacimiento = edtV_mail.getText().toString().trim();
        String nacimiento_formato_register;

        String[] parts = nacimiento.split("/");
        String dia = parts[0];
        String mes = parts[1];
        String anho = parts[2];

        nacimiento_formato_register = anho+"-"+mes+"-"+dia;

        final String[] idUser = {" "};
        final String[] userName = {" "};
        final String[] nroCelular = {" "};
        //final String[] email = {" "};
        String id = " ";

        final String lenguage = "es";

        JSONObject js = new JSONObject();
        try {
            js.put("username", dni);
            js.put("nroCelular", numero);
            //js.put("email", mail);
            js.put("fechaNacimiento", nacimiento_formato_register);
            js.put("password", password);
            //js.put("langKey", "es");
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
                            Log.d("onResponse Register", response.get("authorities") + " i am queen");
                            idUser[0] = response.get("idUser").toString();
                            userName[0] = response.get("username").toString();
                            nroCelular[0] = response.get("nroCelular").toString();
                            //email[0] = response.get("email").toString();
                            Log.d("TO ID USER", "onResponse: " + idUser[0]);
                            Log.d("USER NAME", "onResponse: " + userName[0]);
                            Log.d("NRO CELULAR", "onResponse: " + nroCelular[0]);
                            //Log.d("EMAIL", "onResponse: " + email[0]);
                            Log.d("Register", "onResponse: " + response);
                            Toast.makeText(RegisterActivity.this, "USUARIO REGISTRADO", Toast.LENGTH_LONG).show();
                            //Snackbar.make(v, "CONFIRMA TU PASSWORD CON TU MAIL", Snackbar.LENGTH_INDEFINITE).setAction("LOGIN", new View.OnClickListener() {
                            //   @Override
                            //     finish();
                            //   }
                            //}).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intentPerfil = new Intent(RegisterActivity.this, PerfilActivity.class);
                        intentPerfil.putExtra("idUser", idUser[0]);
                        intentPerfil.putExtra("userName", userName[0]);
                        intentPerfil.putExtra("nroCelular", nroCelular[0]);
                        intentPerfil.putExtra("nacimiento", nacimiento);
                        //intentPerfil.putExtra("email",email[0]);
                        //Toast.makeText(getApplicationContext(),idUser[0],Toast.LENGTH_LONG).show();
                        startActivity(intentPerfil);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("EEEEEE", "Error: " + error.toString());
                Toast.makeText(RegisterActivity.this, "" + error, Toast.LENGTH_LONG).show();
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
