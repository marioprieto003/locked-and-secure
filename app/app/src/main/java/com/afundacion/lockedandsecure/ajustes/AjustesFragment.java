package com.afundacion.lockedandsecure.ajustes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.login.Login;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
        Preference preferenceCerrarSesion = getPreferenceManager().findPreference("cerrar_sesion");

        // Listeners
        preferenceCuenta.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });

        preferenceSeguridad.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });


        preferenceRecursosAdicionales.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });

        // Borramos el token de sesion y la clave de cifrado y mandamos la app a la acitivity de login
        preferenceCerrarSesion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                SharedPreferences preferences = getContext().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                preferences.edit().clear().apply();
                try {
                    String masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                    SharedPreferences sharedPreferencesEncrypted = EncryptedSharedPreferences.create(
                            "clave",
                            masterKey,
                            getContext().getApplicationContext(),
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
                    sharedPreferencesEncrypted.edit().clear().apply();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
                return true;
            }
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
            case "recursos_adicionales":
                navController.navigate(R.id.recursosFragment);
                break;
        }


    }


}