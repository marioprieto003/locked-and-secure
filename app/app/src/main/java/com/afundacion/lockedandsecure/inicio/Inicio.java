package com.afundacion.lockedandsecure.inicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.ajustes.Ajustes;
import com.afundacion.lockedandsecure.grupos.ListaGrupos;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Inicio extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_inicio);

        // Prohibir capturas de pantalla en la activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationListener);



        // Configuración del elemento default para que aparezca al iniciarse la activity y salga check
    }

    NavigationBarView.OnItemSelectedListener bottomNavigationListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_item_inicio:
                    cambiarFragment(ListaGrupos.newInstance());
                    return true;
                case R.id.nav_item_ajustes:
                    startActivity(new Intent(context, Ajustes.class));
                    return true;
            }
            return false;
        }
    };

    private void cambiarFragment(Fragment fragment) {
        Toast.makeText(this, "Transicion", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}