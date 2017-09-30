package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    public static String TOKEN = "";

    private TextView txtv_tittle;
    private Button btn_register;
    private Button btn_login;
    private EditText edtV_user;
    private EditText edtV_password;

    Context context = this;

    public static final String URL = "http://10.24.9.6:8080/sigem/api/authenticate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("shared", context.MODE_PRIVATE);

        txtv_tittle = (TextView) findViewById(R.id.lbl_tittle);
        Typeface m = Typeface.createFromAsset(getAssets(), "fonts/serious.ttf");
        txtv_tittle.setTypeface(m);

        edtV_user = (EditText) findViewById(R.id.txt_user);
        edtV_password = (EditText) findViewById(R.id.txt_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_accept);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    private void login_user() {

        final String user = edtV_user.getText().toString().trim();
        final String password = edtV_password.getText().toString().trim();

        JSONObject js = new JSONObject();
        try {
            js.put("password", password);
            js.put("remeberMe", "true");
            js.put("username", user);
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
                            Log.d("AAAAAAA", response.get("id_token") + " i am queen");
                            TOKEN = response.get("id_token").toString();
                            //Toast.makeText(LoginActivity.this,"EL TOKEN ES "+TOKEN,Toast.LENGTH_LONG).show();
                            SharedPreferences prefs = getSharedPreferences("TOKENSHAREFILE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("TOKENSTRING", TOKEN);
                            editor.commit();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("TOKENLOGIN", TOKEN);
                            startActivity(i);
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
                return headers;
            }
        };

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);

    }

}


