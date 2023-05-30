package com.afundacion.lockedandsecure.grupos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.contrasenas.Contraseña;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaGrupos extends Fragment {
    private FloatingActionButton crearGrupo;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private boolean clicked = false;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView inicioTextView;

    public static ListaGrupos newInstance() {
        return new ListaGrupos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grupos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        shimmerFrameLayout = view.findViewById(R.id.preview);
        crearGrupo = view.findViewById(R.id.crearGrupoBoton);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        // Setup del recylerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        llenarRecyclerView();



        // listeners
        swipeRefresh.setOnRefreshListener(refreshListener);
        crearGrupo.setOnClickListener(crearGrupoListener);
    }

    View.OnClickListener crearGrupoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Bundle
            //Intent intent = new Intent(getContext(), CrearGrupos.class)
            //startActivity(intent)
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

        rest.inicio(
                response -> {
                    if (response.length() != 0) {
                        try {
                            JSONArray array = response.getJSONArray("lista");
                            ArrayList<Grupo> lista = new ArrayList<>();

                            for (int i=0; i< array.length(); i++) {
                                JSONArray arrayContraseñas = array.getJSONArray("contraseñas");
                                ArrayList<Contraseña> listaContraseñas = new ArrayList<>();

                                for (int j=0; j<arrayContraseñas.length(); j++) {
                                    listaContraseñas.add(new Contraseña(
                                            arrayContraseñas.getJSONObject(j).getInt("id"),
                                            arrayContraseñas.getJSONObject(i).getString("contraseña"),
                                            arrayContraseñas.getJSONObject(i).getString("email"),
                                            arrayContraseñas.getJSONObject(i).getString("usuario"),
                                            arrayContraseñas.getJSONObject(i).getString("fecha")
                                    ));
                                }
                                lista.add(new Grupo(
                                        array.getInt("id"),
                                        array.getInt("tamaño"),
                                        response.getString("grupo"),
                                        listaContraseñas
                                ));
                            }
                            GruposAdapter adapter = new GruposAdapter(lista, new GruposAdapter.RecyclerItemClick() {
                                @Override
                                public void itemClick(Grupo item) {
                                    Toast.makeText(getContext(), "Click a -> " + item.getNombre(), Toast.LENGTH_SHORT).show();
                                }
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
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    }
}
