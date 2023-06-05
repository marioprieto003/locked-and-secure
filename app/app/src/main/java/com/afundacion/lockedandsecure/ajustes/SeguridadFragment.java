package com.afundacion.lockedandsecure.ajustes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.afundacion.gestorcontrasenas.R;

public class SeguridadFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_seguridad, rootKey);
        // Conexion con los preferences
        Preference preferenceCambiarContraseña = getPreferenceManager().findPreference("cambiar_contraseña");
        Preference preferenceAcesoBiometrico = getPreferenceManager().findPreference("acceso_biometrico");

        preferenceCambiarContraseña.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                //startActivity(new Intent(getContext(), CambiarContraseña.class));
                return true;
            }
        });

        preferenceAcesoBiometrico.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                return true;
            }
        });
    }
}
