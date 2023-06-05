package com.afundacion.lockedandsecure.ajustes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.afundacion.gestorcontrasenas.BuildConfig;
import com.afundacion.gestorcontrasenas.R;

public class RecursosFragment extends PreferenceFragmentCompat {
    public RecursosFragment() {}

    public static RecursosFragment newInstance() {
        return new RecursosFragment();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_recursos, rootKey);
        // Conexion con los preferences
        Preference preferenceVersion = getPreferenceManager().findPreference("version");
        Preference preferenceInformeFallos = getPreferenceManager().findPreference("informe_fallos");
        Preference preferenceCondicionesUso = getPreferenceManager().findPreference("condiciones_de_uso");
        Preference preferencePoliticaPrivacidad = getPreferenceManager().findPreference("politica_privacidad");
        Preference preferenceAvisosLegales = getPreferenceManager().findPreference("avisos_legales");

        // Obtenemos la versión de la app que el usuario tiene instalada
        preferenceVersion.setSummary(BuildConfig.VERSION_NAME);

        preferenceInformeFallos.setOnPreferenceClickListener(preference -> {
            return true;
        });

        // Redirección a página de condiciones de uso
        preferenceCondicionesUso.setOnPreferenceClickListener(preference -> {
            //Uri uri = Uri.parse("");
            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //startActivity(intent);
            return true;
        });

        // Redirección a página de política de privacidad
        preferencePoliticaPrivacidad.setOnPreferenceClickListener(preference -> {
            //Uri uri = Uri.parse("https://www.santamariadelmar.es/?page_id=19782");
            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //startActivity(intent);
            return false;
        });

        // Redirección a página de avisos legales
        preferenceAvisosLegales.setOnPreferenceClickListener(preference -> {
            //Uri uri = Uri.parse("https://www.santamariadelmar.es/?page_id=19781");
            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //startActivity(intent);
            return true;
        });
    }
}
