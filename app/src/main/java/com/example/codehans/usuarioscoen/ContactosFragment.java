package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactosFragment extends Fragment {

    private ImageView contact01;
    private ImageView contact02;
    private ImageView contact03;
    private ImageView contact04;
    private ImageView contact05;
    private ImageView centerContact;
    private FloatingActionButton floatingActionButton_contacts;
    private String urlcontact = "";
    private String urlcontact2 = "";
    private String urlcontact3 = "";
    private String urlcontact4 = "";
    private String urlcontact5 = "";
    private String urlcenter;
    private String usernamemessage = " ";
    private String usernamemessage2 = " ";
    private String usernamemessage3 = " ";
    private String usernamemessage4 = " ";
    private String usernamemessage5 = " ";

    private TextView textView_UserNameContact;
    //private String URL_CONTACTOS_BY_CIUDADANO = "http://10.24.9.6:8080/sigem/api/contactosByCiudadano";
    private String URL_CONTACTOS_BY_CIUDADANO = "http://www.ocrm.gob.pe/sigem/api/contactosByCiudadano";
    private String TOKEN = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_contactos, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("TOKENSHAREFILE", Context.MODE_PRIVATE);
        TOKEN = pref.getString("TOKENSTRING", "ERROR");

        contact01 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact01);
        contact02 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact02);
        contact03 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact03);
        contact04 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact04);
        contact05 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact05);
        centerContact = (ImageView) linearLayout.findViewById(R.id.CircleImageV_Principal);
        textView_UserNameContact = (TextView) linearLayout.findViewById(R.id.txtV_UserNameContact);
        floatingActionButton_contacts = (FloatingActionButton) linearLayout.findViewById(R.id.fab_contactos);

        floatingActionButton_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Enviar mensaje", Toast.LENGTH_LONG).show();
                //enviar_mensaje_contactos();
            }
        });

        recuperar_contactos();


        /*urlcontact = "";
        Glide.with(getContext())
                .load(urlcontact)
                .crossFade()
                .centerCrop()
                .into(contact01);

        urlcontact2 = "https://i2.cdn.turner.com/cnnnext/dam/assets/140926165711-john-sutter-profile-image-large-169.jpg";
        Glide.with(getContext())
                .load(urlcontact2)
                .crossFade()
                .centerCrop()
                .into(contact02);

        urlcontact3 = "http://www.xsjjys.com/data/out/96/WHDQ-512397052.jpg";
        Glide.with(getContext())
                .load(urlcontact3)
                .crossFade()
                .centerCrop()
                .into(contact03);

        urlcontact4 = "http://www.horoscope.com/images-US/signs/profile-virgo.png";
        Glide.with(getContext())
                .load(urlcontact4)
                .crossFade()
                .centerCrop()
                .into(contact04);

        urlcontact5 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTj_HnXMKpKI30u2ZZ4BPg6qmx-mt12Vc-baHVdoELdpPqiIdNb6w";
        Glide.with(getContext())
                .load(urlcontact5)
                .crossFade()
                .centerCrop()
                .into(contact05);

        urlcenter = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTj_HnXMKpKI30u2ZZ4BPg6qmx-mt12Vc-baHVdoELdpPqiIdNb6w";
        Glide.with(getContext())
                .load(urlcenter)
                .crossFade()
                .centerCrop()
                .into(centerContact);
                */

        contact01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlcontact.isEmpty()) {
                    //Toast.makeText(getContext(), "ENVIAR", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), AddContactosActivity.class);
                    startActivity(i);
                } else {
                    Glide.with(getContext())
                            .load(urlcontact)
                            .crossFade()
                            .centerCrop()
                            .into(centerContact);
                    textView_UserNameContact.setText("El usuario " + usernamemessage + " se conecto por ultima vez a las: ");
                }
            }
        });

        contact02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlcontact2.isEmpty()) {
                    //Toast.makeText(getContext(), "ENVIAR", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), AddContactosActivity.class);
                    startActivity(i);
                } else {
                    Glide.with(getContext())
                            .load(urlcontact2)
                            .crossFade()
                            .centerCrop()
                            .into(centerContact);
                    textView_UserNameContact.setText("El usuario " + usernamemessage2 + " se conecto por ultima vez a las: ");
                }
            }
        });

        contact03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlcontact3.isEmpty()) {
                    //Toast.makeText(getContext(), "ENVIAR", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), AddContactosActivity.class);
                    startActivity(i);
                } else {
                    Glide.with(getContext())
                            .load(urlcontact3)
                            .crossFade()
                            .centerCrop()
                            .into(centerContact);
                    textView_UserNameContact.setText("El usuario " + usernamemessage3 + " se conecto por ultima vez a las: ");
                }
            }
        });

        contact04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlcontact4.isEmpty()) {
                    //Toast.makeText(getContext(), "ENVIAR", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), AddContactosActivity.class);
                    startActivity(i);
                } else {
                    Glide.with(getContext())
                            .load(urlcontact4)
                            .crossFade()
                            .centerCrop()
                            .into(centerContact);
                    textView_UserNameContact.setText("El usuario " + usernamemessage4 + " se conecto por ultima vez a las: ");
                }
            }
        });

        contact05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlcontact5.isEmpty()) {
                    //Toast.makeText(getContext(), "ENVIAR", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), AddContactosActivity.class);
                    startActivity(i);
                } else {
                    Glide.with(getContext())
                            .load(urlcontact5)
                            .crossFade()
                            .centerCrop()
                            .into(centerContact);
                    textView_UserNameContact.setText("El usuario " + usernamemessage5 + " se conecto por ultima vez a las: ");
                }
            }
        });


        return linearLayout;
    }

    private void recuperar_contactos() {

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL_CONTACTOS_BY_CIUDADANO, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jrJsonObject = response.getJSONObject(i);
                                String numero = jrJsonObject.getString("nroCelular");
                                String photo = jrJsonObject.getString("photo");
                                String alias = jrJsonObject.getString("alias");
                                Log.d("ON RESULT GETCONTACTS", "onResponse: " + numero + " " + photo);

                                if (i == 0) {
                                    urlcontact = "http://www.ocrm.gob.pe/sigem_files/" + photo;
                                    //urlcontact = "http://10.24.9.6:8080/sigem_files/" + photo;
                                    usernamemessage = alias;
                                    Glide.with(getContext())
                                            .load(urlcontact)
                                            .crossFade()
                                            .centerCrop()
                                            .into(contact01);
                                } else if (i == 1) {
                                    //urlcontact2 = "http://10.24.9.6:8080/sigem_files/" + photo;
                                    urlcontact2 = "http://www.ocrm.gob.pe/sigem_files/" + photo;
                                    usernamemessage2 = alias;
                                    Glide.with(getContext())
                                            .load(urlcontact2)
                                            .crossFade()
                                            .centerCrop()
                                            .into(contact02);
                                } else if (i == 2) {
                                    //urlcontact3 = "http://10.24.9.6:8080/sigem_files/" + photo;
                                    urlcontact3 = "http://www.ocrm.gob.pe/sigem_files/" + photo;
                                    usernamemessage3 = alias;
                                    Glide.with(getContext())
                                            .load(urlcontact3)
                                            .crossFade()
                                            .centerCrop()
                                            .into(contact03);
                                } else if (i == 3) {
                                    //urlcontact4 = "http://10.24.9.6:8080/sigem_files/" + photo;
                                    urlcontact4 = "http://www.ocrm.gob.pe/sigem_files/" + photo;
                                    usernamemessage4 = alias;
                                    Glide.with(getContext())
                                            .load(urlcontact4)
                                            .crossFade()
                                            .centerCrop()
                                            .into(contact04);
                                } else if (i == 4) {
                                    //urlcontact5 = "http://10.24.9.6:8080/sigem_files/" + photo;
                                    urlcontact5 = "http://www.ocrm.gob.pe/sigem_files/" + photo;
                                    usernamemessage5 = alias;
                                    Glide.with(getContext())
                                            .load(urlcontact5)
                                            .crossFade()
                                            .centerCrop()
                                            .into(contact05);
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


}
