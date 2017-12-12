package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HerramientasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HerramientasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HerramientasFragment extends Fragment {


    private Button btnVer_t;
    private Button btnVer_i;
    private Button btnVer_ts;
    private Button btnVer_h;
    private Button btnCompartir_t;
    private Button btnCompartir_i;
    private Button btnComparotir_ts;
    private Button btnCompartir_h;
    private ImageView imageView_t;
    private ImageView imageView_i;
    private ImageView imageView_ts;
    private ImageView imageView_h;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_herramientas, container, false);
        btnVer_t = (Button) relativeLayout.findViewById(R.id.btn_verHerramientasConsejos);
        btnVer_i = (Button) relativeLayout.findViewById(R.id.btn_verHerramientasConsejosI);
        btnVer_ts = (Button) relativeLayout.findViewById(R.id.btn_verHerramientasConsejosTs);
        btnVer_h = (Button) relativeLayout.findViewById(R.id.btn_verHerramientasConsejosH);

        btnCompartir_t = (Button) relativeLayout.findViewById(R.id.btn_compartirHerramientasConsejos);
        btnCompartir_i = (Button) relativeLayout.findViewById(R.id.btn_compartirHerramientasConsejosI);
        btnComparotir_ts = (Button) relativeLayout.findViewById(R.id.btn_compartirHerramientasConsejosTs);
        btnCompartir_h = (Button) relativeLayout.findViewById(R.id.btn_compartirHerramientasConsejosH);

        imageView_t = (ImageView) relativeLayout.findViewById(R.id.img_HerramientasConsejosT);
        imageView_i = (ImageView) relativeLayout.findViewById(R.id.img_HerramientasConsejosI);
        imageView_ts = (ImageView) relativeLayout.findViewById(R.id.img_HerramientasConsejosTs);
        imageView_h = (ImageView) relativeLayout.findViewById(R.id.img_HerramientasConsejosH);

        Glide.with(getContext()).
                load("https://img.elcomercio.pe/files/ec_article_multimedia_gallery/uploads/2017/03/22/58d29d2cab985.jpeg").
                centerCrop().
                crossFade().
                into(imageView_t);

        Glide.with(getContext()).
                load("https://cdn3.uvnimg.com/dims4/default/a7a920d/2147483647/resize/1093x820%3E/quality/75/?url=https%3A%2F%2Fcdn2.uvnimg.com%2Ff0%2Feb%2F5c5222bb4f4e9af3f157459a01a2%2Fgettyimages-654940404.jpg").
                centerCrop().
                crossFade().
                into(imageView_i);

        Glide.with(getContext()).
                load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTrGZ-94EFUsezBVl9ItgSjJkq5IDHa-pUHXtua7kRPh49nwpshZw").
                centerCrop().
                crossFade().
                into(imageView_ts);

        Glide.with(getContext()).
                load("https://i.cbc.ca/1.4031136.1489830567!/fileImage/httpImage/image.jpg_gen/derivatives/original_620/peru-floods.jpg").
                centerCrop().
                crossFade().
                into(imageView_h);

        btnVer_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVer_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVer_ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVer_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return relativeLayout;
    }

}
