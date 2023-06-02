package com.afundacion.lockedandsecure.ajustes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.afundacion.gestorcontrasenas.R;

public class AjustesFragment extends PreferenceFragmentCompat {
    public AjustesFragment() {}

    public static Fragment newInstance() {
        return new AjustesFragment();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences_main, rootKey);

        Preference preferenceCuenta = getPreferenceManager().findPreference("cuenta");
        Preference preferenceSeguridad = getPreferenceManager().findPreference("seguridad");
        Preference preferenceRecursosAdicionales = getPreferenceManager().findPreference("recursos_adicionales");

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment)fragment;
        NavController navController = navHostFragment.getNavController();

        /*
        // Listeners
        preferenceCuenta.setOnPreferenceClickListener(preference -> {
            navController.navigate(R.id.action_ajustes_to_cuentaFragment);
            //cambiarFragment(preference.getKey());
            return true;
        });

        preferenceSeguridad.setOnPreferenceClickListener(preference -> {
            navController.navigate(R.id.action_ajustes_to_seguridadFragment);
            //cambiarFragment(preference.getKey());
            return true;
        });


        preferenceRecursosAdicionales.setOnPreferenceClickListener(preference -> {
            navController.navigate(R.id.action_ajustes_to_recursosFragment);
            //cambiarFragment(preference.getKey());
            return true;
        });
    }

    private void cambiarFragment(String key) {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment)fragment;
        NavController navController = navHostFragment.getNavController();

        switch (key) {
            case "cuenta":
                navController.navigate(R.id.cuentaFragment);
                break;
            case "seguridad":
                navController.navigate(R.id.seguridadFragment);
                break;
            case "notificaciones":
                navController.navigate(R.id.notificacionesFragment);
                break;
            case "recursos_adicionales":
                navController.navigate(R.id.recursosAdicionalesFragment);
                break;
        }

         */
    }


}