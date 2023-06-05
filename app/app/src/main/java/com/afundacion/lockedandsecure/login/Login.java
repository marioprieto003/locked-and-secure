package com.afundacion.lockedandsecure.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.inicio.Inicio;
import com.afundacion.lockedandsecure.registro.Registro;
import com.afundacion.lockedandsecure.rest.Rest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Login extends AppCompatActivity {
    private TextInputLayout emailLayout, contraseñaLayout;
    private TextInputEditText email, contraseña;
    private Button inicioSesionBoton, registrarBoton;
    private Rest rest = Rest.getInstance(this);
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Metodo para comprobar si hay una sesion activa
        auth();

        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailTextInput);
        emailLayout = findViewById(R.id.emailTextInputLayout);

        setIdioma();

        contraseña = findViewById(R.id.contraseñaTextInput);
        contraseñaLayout = findViewById(R.id.contraseñaTextInputLayout);
        inicioSesionBoton = findViewById(R.id.loginBoton);
        inicioSesionBoton.setOnClickListener(inicioSesionListener);
        registrarBoton = findViewById(R.id.registrarBoton);
        registrarBoton.setOnClickListener(registrarListener);
    }

    View.OnClickListener inicioSesionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            emailLayout.setHelperText("");
            contraseñaLayout.setHelperText("");
            if (email.getText().length() <= 0) {
                emailLayout.setError("");
                emailLayout.setHelperText(getResources().getText(R.string.campo_obligatorio));
            } else if (contraseña.getText().length() <= 0) {
                contraseñaLayout.setError("");
                contraseñaLayout.setHelperText(getResources().getText(R.string.campo_obligatorio));
            } else {
                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText().toString());
                    body.put("contraseña", contraseña.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                rest.login(
                        response -> {
                            try {
                                // Guardado del token de sesión en las Shared Preferences
                                SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                                sharedPreferences.edit().putString("token", response.getString("token")).apply();

                                // Redireccion a la pantalla de inicio
                                Intent intent = new Intent(context, Inicio.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        error -> {
                            if (error.networkResponse.statusCode == 401) {
                                contraseñaLayout.setError("");
                                contraseñaLayout.setHelperText(getResources().getString(R.string.contraseña_error));
                            } else if (error.networkResponse.statusCode == 404) {
                                emailLayout.setError("");
                                emailLayout.setHelperText(getResources().getText(R.string.no_registrado));
                            }
                        },
                        body
                );
            }


        }
    };

    View.OnClickListener registrarListener = v -> {
        Intent intent = new Intent(context, Registro.class);
        startActivity(intent);
    };

    /*      MOVER A LAUNCHER     */
    // Metodo para mantener la configuracion de idioma
    private void setIdioma() {
        // Recuperamos las shared preferences de idioma
        // En caso de ser nulas cogeria el idioma predefinido de la app (Español)
        SharedPreferences sharedPreferences = getSharedPreferences("idioma", Context.MODE_PRIVATE);
        String codigoIdioma = sharedPreferences.getString("idioma", null);

        if (codigoIdioma != null) {
            // Obtiene la configuración actual
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();

            // Crea un objeto Locale con el idioma especificado
            Locale locale = new Locale(codigoIdioma);

            // Actualiza la configuración con el nuevo idioma
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

    }

    private void auth() {
        Rest rest1 = Rest.getInstance(context);


    }
}
