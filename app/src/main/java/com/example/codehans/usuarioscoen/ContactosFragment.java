package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
    private String urlcontact2, urlcontact3, urlcontact4, urlcontact5, urlcenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_contactos, container, false);

        contact01 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact01);
        contact02 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact02);
        contact03 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact03);
        contact04 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact04);
        contact05 = (ImageView) linearLayout.findViewById(R.id.circleImageV_contact05);
        centerContact = (ImageView) linearLayout.findViewById(R.id.CircleImageV_Principal);
        floatingActionButton_contacts = (FloatingActionButton) linearLayout.findViewById(R.id.fab_contactos);

        floatingActionButton_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Enviar mensaje", Toast.LENGTH_LONG).show();
            }
        });

        recuperar_contactos();


        urlcontact = "";
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
                }
            }
        });

        contact02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext())
                        .load(urlcontact2)
                        .crossFade()
                        .centerCrop()
                        .into(centerContact);
            }
        });

        contact03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext())
                        .load(urlcontact3)
                        .crossFade()
                        .centerCrop()
                        .into(centerContact);
            }
        });

        contact04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext())
                        .load(urlcontact4)
                        .crossFade()
                        .centerCrop()
                        .into(centerContact);
            }
        });

        contact05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext())
                        .load(urlcontact5)
                        .crossFade()
                        .centerCrop()
                        .into(centerContact);
            }
        });



        return linearLayout;
    }

    private void recuperar_contactos() {

    }


}
