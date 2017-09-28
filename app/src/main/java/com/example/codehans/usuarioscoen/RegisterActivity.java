package com.example.codehans.usuarioscoen;

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

    public static final String URL = "http://10.24.9.6:8080/coen/api/register";
    public static final String KEY_MAIL = "email";
    public static final String KEY_LENGUAGE = "langKey";
    public static final String KEY_DNI = "login";
    public static final String KEY_PASSWORD = "password";

    private EditText edtV_user, edtV_password, edtV_DNI;
    private Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtV_DNI = (EditText) findViewById(R.id.edtV_DNI);
        edtV_password = (EditText) findViewById(R.id.edtV_password);
        edtV_user = (EditText) findViewById(R.id.edtV_username);

        btn_registrar = (Button) findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_user(v);
            }
        });

    }

    private void registrar_user(final View v) {

        final String mail = edtV_user.getText().toString().trim();
        final String password = edtV_password.getText().toString().trim();
        final String dni = edtV_DNI.getText().toString().trim();
        final String lenguage = "es";

        JSONObject js = new JSONObject();
        try {
            js.put("login", dni);
            js.put("email", mail);
            js.put("password", password);
            js.put("langKey", "es");
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
                            Log.d("AAAAAAA", response.get("message") + " i am queen");
                            Toast.makeText(RegisterActivity.this, "USUARIO REGISTRADO", Toast.LENGTH_LONG).show();
                            Snackbar.make(v, "CONFIRMA TU PASSWORD CON TU MAIL", Snackbar.LENGTH_INDEFINITE).setAction("LOGIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            }).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
