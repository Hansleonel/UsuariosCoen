package com.example.codehans.usuarioscoen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddContactosActivity extends AppCompatActivity {

    private FloatingActionButton fab_add;
    private Bitmap bitmap;
    public static final int RESULT_PICK_CONTACT = 1001;
    public static final int PICK_IMAGE_REQUEST = 111;
    private TextView txtV_nombre_contacto;
    private TextView txtV_numero_contacto;
    private Button btn_registrar_contacto;
    private ProgressDialog progressDialog;
    private ImageView imageView_contacto;
    public static final String URL_ADD_CONTACT = "http://10.24.9.6:8080/sigem/api/contactos";
    String TOKEN = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contactos);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_addContact);
        txtV_nombre_contacto = (TextView) findViewById(R.id.txtV_NameContact);
        txtV_numero_contacto = (TextView) findViewById(R.id.txtV_NumberContact);
        btn_registrar_contacto = (Button) findViewById(R.id.btn_registrar_contacto);
        imageView_contacto = (ImageView) findViewById(R.id.imgV_photoContact);

        imageView_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker_photo();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker_contacto();
            }
        });

        btn_registrar_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_contacto();
            }
        });

        SharedPreferences pref = getApplication().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TOKEN = pref.getString("TOKENSTRING", "ERROR");
        Toast.makeText(getApplicationContext(), " ESTE ES EL TOKEN " + TOKEN, Toast.LENGTH_LONG).show();
    }

    private void picker_photo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    private void registrar_contacto() {
        String nombre_contacto = txtV_nombre_contacto.getText().toString();
        String numero_contacto = txtV_numero_contacto.getText().toString();

        progressDialog = new ProgressDialog(AddContactosActivity.this);
        progressDialog.setMessage("Subiendo por favor espere...");
        progressDialog.show();


        //Log.d("IMAGE", "registrar_contacto: "+imageString);


        JSONObject js = new JSONObject();
        try {
            js.put("alias", nombre_contacto);
            js.put("nroCelular", numero_contacto);
            //js.put("",imageString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, URL_ADD_CONTACT, js,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            Log.d("RESPONSE CORRECTO", response.get("idContacto") + " i am queen");
                            Toast.makeText(getApplicationContext(), "Contacto Agregado", Toast.LENGTH_LONG).show();
                            //Snackbar.make(v,"CONFIRMA TU PASSWORD CON",Snackbar.LENGTH_INDEFINITE).setAction("LOGIN",new View.OnClickListener(){
                            //  @Override
                            //public void onClick(View v) {
                            //  finish();
                            //}
                            //}).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), " error" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("eRRor Response", "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), "" + error.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
    }

    private void picker_contacto() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String contactNumber = null;
                        String contactName = null;

                        Uri uri = data.getData();

                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();

                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);

                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);

                        txtV_nombre_contacto.setText(contactName);
                        txtV_numero_contacto.setText(contactNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PICK_IMAGE_REQUEST:
                    Uri filePath = data.getData();

                    try {
                        //getting image from gallery
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                        //Setting image to ImageView
                        imageView_contacto.setImageBitmap(bitmap);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        Log.d("BITMAP TO BASE 64", "onActivityResult: "+imageString);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
