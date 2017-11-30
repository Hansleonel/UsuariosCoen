package com.example.codehans.usuarioscoen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ogtie05 on 17/11/2017.
 */

public class CentroAcopioInsumosAdapter extends RecyclerView.Adapter<CentroAcopioInsumosAdapter.InformesInsumosViewHolder> {

    private ArrayList<CentroAcopioInsumosContainer> datosInsumos;

    public CentroAcopioInsumosAdapter(ArrayList<CentroAcopioInsumosContainer> datosInformes) {
        this.datosInsumos = datosInformes;
    }

    @Override
    public InformesInsumosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_insumos, parent, false);
        //itemView.setOnClickListener(this);
        //usando el constructuror de la clase para inicializar el opvh
        CentroAcopioInsumosAdapter.InformesInsumosViewHolder InformesInsumosViewHolder = new CentroAcopioInsumosAdapter.InformesInsumosViewHolder(itemView);
        return InformesInsumosViewHolder;
    }

    @Override
    public void onBindViewHolder(InformesInsumosViewHolder holder, int position) {
        CentroAcopioInsumosContainer item = datosInsumos.get(position);
        holder.bindInsumos(item);
    }

    @Override
    public int getItemCount() {
        return datosInsumos.size();
    }

    public class InformesInsumosViewHolder extends RecyclerView.ViewHolder {

        private TextView txtV_stock;
        private TextView txtV_Descripcion;

        public InformesInsumosViewHolder(View itemView) {
            super(itemView);

            txtV_stock = (TextView) itemView.findViewById(R.id.tv_item_insumo_stock);
            txtV_Descripcion = (TextView) itemView.findViewById(R.id.tv_item_insumo_descripcion);
        }

        public void bindInsumos(CentroAcopioInsumosContainer p) {
            txtV_stock.setText(String.valueOf(p.getStockInsumo()));
            txtV_Descripcion.setText(p.getDescripcionInsumo());
        }
    }
}
