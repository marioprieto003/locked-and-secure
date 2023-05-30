package com.afundacion.lockedandsecure.grupos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorcontrasenas.R;

import java.util.ArrayList;

public class GruposAdapter extends RecyclerView.Adapter<GruposAdapter.ViewHolder> {
    private ArrayList<Grupo> lista;
    private RecyclerItemClick listener;

    public GruposAdapter(ArrayList<Grupo> lista, RecyclerItemClick listener) {
        this.lista = lista;
        this.listener = listener;
        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GruposAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_grupo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GruposAdapter.ViewHolder holder, int position) {
        final Grupo item = lista.get(position);
        holder.nombreTextView.setText(lista.get(position).getNombre());
        holder.tamañoTextView.setText(lista.get(position).getTamaño());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreTextView, tamañoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            tamañoTextView = itemView.findViewById(R.id.tamañoTextView);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Grupo item);
    }
}
