package com.example.codehans.usuarioscoen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class RankingEmpresasActivity extends AppCompatActivity {

    private RecyclerView recyclerView_actualizacionesE;
    private RecyclerView recyclerView_rankingE;
    private ArrayList<RankingEmpresasContainer> rankingEmpresasContainerArrayList;
    private RankingEmpresasAdapter adapter_actualizacionesE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_empresas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ranking de Empresas");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        recyclerView_actualizacionesE = (RecyclerView) findViewById(R.id.recView_rank_empresas_actualizaciones);
        recyclerView_rankingE = (RecyclerView) findViewById(R.id.recView_rank_empresas_puestos);

        recyclerView_actualizacionesE.setHasFixedSize(true);
        recyclerView_rankingE.setHasFixedSize(true);

        rankingEmpresasContainerArrayList = new ArrayList<RankingEmpresasContainer>();

        rellenar_actualizacionesE();
        rellenar_rankingE();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void rellenar_rankingE() {

    }

    private void rellenar_actualizacionesE() {

        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A01", "http://www.staffcreativa.pe/blog/wp-content/uploads/leche-gloria-logo.jpg", "Gloria", "Alimentacion"));
        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A02", "http://www.coca-colamexico.com.mx/content/dam/journey/mx/es/private/historia/2016/abril/Una%20historia%20de%20evolucion%20los%20cambios%20del%20logotipo%20de%20Coca-Cola_1.png", "Coca Cola", "Bebidas"));
        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A03", "https://www.telefonica.com/documents/23283/139117659/TEL_logo_color_neg_thumb.jpg", "Telefonica", "Abrigo"));
        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A04", "https://seeklogo.com/images/C/Claro-logo-C82D96482A-seeklogo.com.png", "Claro", "Vivienda"));
        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A05", "http://cl.gamabikes.com/wp-content/uploads/2016/08/logo-ripley.png", "Ripley", "Ropa"));
        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A06", "http://freevectorlogo.net/wp-content/uploads/2013/06/visa-us-vector-logo-400x400.png", "VISA", "Abrigo"));
        rankingEmpresasContainerArrayList.add(new RankingEmpresasContainer("A07", "https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/082012/logo-backus.png?itok=B9gQDhTT", "BACKUS", "Alimentacion"));

        adapter_actualizacionesE = new RankingEmpresasAdapter(rankingEmpresasContainerArrayList, getApplicationContext());
        recyclerView_actualizacionesE.setAdapter(adapter_actualizacionesE);
        recyclerView_actualizacionesE.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

    }

}
