package com.afundacion.lockedandsecure.contrasenas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.grupos.Grupo;

import java.util.ArrayList;

public class ContraseñasAdapter extends RecyclerView.Adapter<ContraseñasAdapter.ViewHolder> {
    private ArrayList<Contraseña> lista;
    private RecyclerItemClick listener;

    public ContraseñasAdapter(ArrayList<Contraseña> lista, RecyclerItemClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContraseñasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contrasena,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContraseñasAdapter.ViewHolder holder, int position) {
        final Contraseña item  = lista.get(position);
        if (lista.get(position).getEmail() != null) {
            holder.emailTextView.setText(lista.get(position).getEmail());
        } else {
            holder.emailTextView.setText("--");
        }

        if (lista.get(position).getUsuario() != null) {
            holder.usuarioTextView.setText(lista.get(position).getUsuario());
        } else {
            holder.usuarioTextView.setText("--");
        }

        holder.itemView.setOnClickListener(v -> listener.itemClick(item));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView emailTextView, usuarioTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emailTextView = itemView.findViewById(R.id.emailTextView);
            usuarioTextView = itemView.findViewById(R.id.usuarioTextView);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Contraseña item);
    }
}
