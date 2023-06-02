package com.afundacion.lockedandsecure.ajustes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorcontrasenas.R;

public class Ajustes extends AppCompatActivity {
    public Ajustes() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .replace(R.id.fragment_container, new AjustesFragment())
                .addToBackStack(null)
                .commit();
    }
}
