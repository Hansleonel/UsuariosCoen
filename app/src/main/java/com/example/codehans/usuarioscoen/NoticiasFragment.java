package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticiasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoticiasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticiasFragment extends Fragment {

    private CircleImageView circleImageView_empresa_ranking;
    private CircleImageView circleImageView_ayudar;
    private CircleImageView circleImageView_logros;
    private CircleImageView circleImageView_streaming;
    private CardView cardView_empresa_ranking;
    private CardView cardView_eventos_ranking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_noticias, container, false);

        circleImageView_empresa_ranking = (CircleImageView) relativeLayout.findViewById(R.id.imgV_Rank_Empresas);
        circleImageView_ayudar = (CircleImageView) relativeLayout.findViewById(R.id.imgV_howHelp);
        circleImageView_logros = (CircleImageView) relativeLayout.findViewById(R.id.imgV_howHelp_Logros);
        circleImageView_streaming = (CircleImageView) relativeLayout.findViewById(R.id.imgV_howHelp_Rank_Eventos);
        cardView_empresa_ranking = (CardView) relativeLayout.findViewById(R.id.cardV_rank_empresa);
        cardView_eventos_ranking = (CardView) relativeLayout.findViewById(R.id.cardV_rank_eventos);

        Glide.with(getContext())
                .load("https://ep01.epimg.net/verne/imagenes/2017/09/20/articulo/1505896772_761780_1505900865_noticia_normal.jpg")
                .crossFade()
                .centerCrop()
                .into(circleImageView_ayudar);

        Glide.with(getContext())
                .load("https://media.deseretdigital.com/file/71a80d6626.jpg")
                .crossFade()
                .centerCrop()
                .into(circleImageView_empresa_ranking);

        Glide.with(getContext())
                .load("http://noticias.universia.es/net/images/educacion/m/me/mej/mejores-escuelas-universidades-tecnologicas-estados-unidos.jpg")
                .crossFade()
                .centerCrop()
                .into(circleImageView_logros);

        Glide.with(getContext())
                .load("http://cdn3.upsocl.com/wp-content/uploads/2017/03/ddd-2.jpg")
                .crossFade()
                .centerCrop()
                .into(circleImageView_streaming);

        cardView_empresa_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), RankingEmpresasActivity.class);
                startActivity(i);
            }
        });
        cardView_eventos_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LiveStreamingActivity.class);
                startActivity(i);
            }
        });

        return relativeLayout;
    }
}
