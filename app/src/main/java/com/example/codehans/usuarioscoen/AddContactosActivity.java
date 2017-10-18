package com.example.codehans.usuarioscoen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
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
    public static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1002;
    private TextView txtV_nombre_contacto;
    private TextView txtV_numero_contacto;
    private Button btn_registrar_contacto;
    private ProgressDialog progressDialog;
    private CircleImageView imageView_contacto;
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
        imageView_contacto = (CircleImageView) findViewById(R.id.imgV_photoContact);

        permisos();
        //permisos_sms();

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

    private void permisos() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //    if (!Settings.System.canWrite(this)) {
        //       requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //             Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
        //   } else {
        // continue with your code
        //   }
        //}

        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SEND_SMS);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddContactosActivity.this,
                    Manifest.permission.SEND_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(AddContactosActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1002);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "PERMISO OTORGADO", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "PERMISO NO OTORGADO", Toast.LENGTH_LONG).show();
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

    private void picker_photo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    private void registrar_contacto() {

        //String mensaje = "INSTALA LA APLICACION DIRECTAMENTE DESDE EL GOOGLE PLAY unasolafuerza.org";
        //String numero_sms = txtV_numero_contacto.getText().toString();


        String nombre_contacto = txtV_nombre_contacto.getText().toString();
        String numero_contacto = txtV_numero_contacto.getText().toString();

        enviar_sms();

        progressDialog = new ProgressDialog(AddContactosActivity.this);
        progressDialog.setMessage("Subiendo por favor espere...");
        progressDialog.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        Log.d("IMAGE", "registrar_contacto: " + imageString);


        JSONObject js = new JSONObject();
        try {
            js.put("alias", nombre_contacto);
            js.put("nroCelular", numero_contacto);
            js.put("photo", imageString);
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

    private void enviar_sms() {

        String nombre_contacto = txtV_nombre_contacto.getText().toString();
        String numero_contacto = txtV_numero_contacto.getText().toString();
        String mensaje = "Hola" + nombre_contacto + " Ingresa a mindef.gob.pe e instala la aplicacion en casos de emergencia yo ya tengo la aplicaicon";


        try {
            int permissionChek = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            if (permissionChek != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "NO SE TIENEN LOS PERMISOS NECESARIOS", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MENSAJE DE TEXTO", "enviar_sms: SE TIENE PERMISOS");
            }

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero_contacto, null, mensaje, null, null);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "MENSAJE NO ENVIADO ERROR", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
                        final String imageString = Base64.encodeToString(imageBytes, Base64.URL_SAFE);

                        Log.d("BITMAP TO BASE 64", "onActivityResult: " + imageString);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
