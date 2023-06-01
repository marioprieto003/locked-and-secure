package com.afundacion.lockedandsecure.contrasenas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.rest.Rest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;

public class ListaContraseñas extends Fragment {

    private FloatingActionButton crearContraseña;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private boolean clicked = false;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView inicioTextView;
    private static int idGrupo;

    // Constructor vacío
    public ListaContraseñas() {}

    // "Constructor" del fragment por el que recibe el id del grupo del que tiene que buscar las contraseñas
    public static ListaContraseñas newInstance(int id) {
        idGrupo = id;
        return new ListaContraseñas();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contrasenas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        shimmerFrameLayout = view.findViewById(R.id.preview);
        crearContraseña = view.findViewById(R.id.crearContraseñaBoton);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        inicioTextView = view.findViewById(R.id.textViewInicio);
        // Setup del recylerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        llenarRecyclerView();



        // listeners
        swipeRefresh.setOnRefreshListener(refreshListener);
        crearContraseña.setOnClickListener(crearContraseñaListener);
    }

    View.OnClickListener crearContraseñaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), CrearContraseña.class);
            startActivity(intent);
        }
    };

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // Hacemos desaparecer el recyclerView
            recyclerView.setVisibility(View.GONE);
            inicioTextView.setVisibility(View.GONE);
            // Hacemos aparecer la preview de carga
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();

            llenarRecyclerView();
        }
    };

    private void llenarRecyclerView() {
        Rest rest = Rest.getInstance(getContext());

        rest.getContraseñas(
                response -> {
                    if (response.length() != 0) {
                        try {
                            ArrayList<Contraseña> listaContraseñas = new ArrayList<>();

                            for (int i=0; i<response.length(); i++) {
                                listaContraseñas.add(new Contraseña(
                                        response.getJSONObject(i).getInt("id"),
                                        response.getJSONObject(i).getString("contraseña"),
                                        response.getJSONObject(i).getString("email"),
                                        response.getJSONObject(i).getString("usuario"),
                                        response.getJSONObject(i).getString("fecha")
                                ));
                            }


                            // ItemClickListener del recyclerView de contraseñas
                            ContraseñasAdapter adapter = new ContraseñasAdapter(listaContraseñas, item -> {
                                Intent intent = new Intent(getContext(), CrearContraseña.class);
                                intent.putExtra("contraseña", item);
                                startActivity(intent);
                            });

                            recyclerView.setVisibility(View.VISIBLE);

                            // Parar la preview de carga de datos y hacerla desaparecer
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);

                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        inicioTextView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    }

                    swipeRefresh.setRefreshing(false);
                },
                error -> {

                },
                idGrupo
        );
    }
}
