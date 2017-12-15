package com.example.codehans.usuarioscoen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class HowHelpActivity extends AppCompatActivity {

    private CircleImageView circleImageView_howHelp;
    private CardView cardView_ranking_empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        circleImageView_howHelp = (CircleImageView) findViewById(R.id.imgV_howHelp);
        cardView_ranking_empresas = (CardView) findViewById(R.id.cardV_rank_empresa);

        cardView_ranking_empresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HowHelpActivity.this, RankingEmpresasActivity.class);
                startActivity(i);
            }
        });
        Glide.with(this)
                .load("https://media.deseretdigital.com/file/71a80d6626.jpg")
                .crossFade()
                .centerCrop()
                .into(circleImageView_howHelp);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
