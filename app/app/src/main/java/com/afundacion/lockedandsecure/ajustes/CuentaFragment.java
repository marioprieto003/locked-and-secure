package com.afundacion.lockedandsecure.ajustes;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class CuentaFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences_cuenta, rootKey);

        Preference preferenceNombre = getPreferenceManager().findPreference("nombre");
        Preference preferenceApellidos = getPreferenceManager().findPreference("apellidos");
        ListPreference preferenceCambiarIdioma = getPreferenceManager().findPreference("idioma");

        preferenceCambiarIdioma.setValue(getActivity().getResources().getConfiguration().locale.getLanguage());
        preferenceCambiarIdioma.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                setIdioma(newValue.toString());
                return true;
            }
        });

        // Peticion para obetener el nombre completo del usuario
        Rest rest = Rest.getInstance(getContext());

        rest.getCuenta(
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            preferenceNombre.setTitle(response.getString("nombre"));
                            preferenceApellidos.setTitle(response.getString("apellidos"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    private void setIdioma(String languageCode) {
        // Obtiene la configuración actual
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();

        // Crea un objeto Locale con el idioma especificado
        Locale locale = new Locale(languageCode);

        // Actualiza la configuración con el nuevo idioma
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Guardamos el idioma en las shared preferences para mantenerlo al volver a abrir la app
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("idioma", Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("idioma", languageCode).apply();

        // Reinicia la actividad para aplicar el cambio de idioma
        getActivity().recreate();
    }

}
