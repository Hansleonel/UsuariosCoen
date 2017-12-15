package com.example.codehans.usuarioscoen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ogtie05 on 14/12/2017.
 */

public class RankingEmpresasAdapter extends RecyclerView.Adapter<RankingEmpresasAdapter.RankingEmpresasViewHolder> {

    private ArrayList<RankingEmpresasContainer> rankingEmpresasContainerArrayList;
    private Context context;

    public RankingEmpresasAdapter(ArrayList<RankingEmpresasContainer> rankingEmpresasContainerArrayList, Context context) {
        this.rankingEmpresasContainerArrayList = rankingEmpresasContainerArrayList;
        this.context = context;
    }

    @Override
    public RankingEmpresasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_actualizandose, parent, false);
        RankingEmpresasAdapter.RankingEmpresasViewHolder rankingEmpresasViewHolder = new RankingEmpresasAdapter.RankingEmpresasViewHolder(itemView);
        return rankingEmpresasViewHolder;
    }

    @Override
    public void onBindViewHolder(RankingEmpresasViewHolder holder, int position) {
        RankingEmpresasContainer item = rankingEmpresasContainerArrayList.get(position);
        holder.bindRankingEmpresas(item);
    }

    @Override
    public int getItemCount() {
        return rankingEmpresasContainerArrayList.size();
    }

    public class RankingEmpresasViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView_empresa_photo;
        private TextView txtV_id_colaboracion;
        private TextView txtV_id_empresa_colaboracion;
        private TextView txtV_tipo_colaboracion;

        public RankingEmpresasViewHolder(View itemView) {
            super(itemView);
            circleImageView_empresa_photo = (CircleImageView) itemView.findViewById(R.id.imgV_item_actualizandose);
            txtV_id_colaboracion = (TextView) itemView.findViewById(R.id.txtV_item_id_colaboracion);
            txtV_id_empresa_colaboracion = (TextView) itemView.findViewById(R.id.txtV_item_Nombre);
            txtV_tipo_colaboracion = (TextView) itemView.findViewById(R.id.txtV_item_Colaboracion);
        }

        public void bindRankingEmpresas(RankingEmpresasContainer r) {
            txtV_id_colaboracion.setText(r.getId_actualizandose());
            txtV_id_empresa_colaboracion.setText(r.getNombre_empresa_actualizandose());
            txtV_tipo_colaboracion.setText(r.getTipo_ayuda_actualizandose());
            Glide.with(context)
                    .load(r.getPhoto_empresa_actualizandose())
                    .crossFade()
                    .centerCrop()
                    .into(circleImageView_empresa_photo);
        }
    }
}
