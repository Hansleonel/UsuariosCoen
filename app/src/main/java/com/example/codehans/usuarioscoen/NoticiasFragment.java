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
    private CardView cardView_empresa_ranking;
    private CardView cardView_eventos_ranking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_noticias, container, false);

        circleImageView_empresa_ranking = (CircleImageView) relativeLayout.findViewById(R.id.imgV_Rank_Empresas);
        cardView_empresa_ranking = (CardView) relativeLayout.findViewById(R.id.cardV_rank_empresa);
        cardView_eventos_ranking = (CardView) relativeLayout.findViewById(R.id.cardV_rank_eventos);

        Glide.with(getContext())
                .load("https://media.deseretdigital.com/file/71a80d6626.jpg")
                .crossFade()
                .centerCrop()
                .into(circleImageView_empresa_ranking);

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

            }
        });

        return relativeLayout;
    }
}
