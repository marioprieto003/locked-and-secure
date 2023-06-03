package com.afundacion.lockedandsecure.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.inicio.Inicio;
import com.afundacion.lockedandsecure.registro.Registro;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;

public class Login extends AppCompatActivity {
    private TextInputLayout emailLayout, contraseñaLayout;
    private TextInputEditText email, contraseña;
    private Button inicioSesionBoton, registrarBoton;
    SharedPreferences sharedPreferencesEncrypted;
    private Rest rest = Rest.getInstance(this);
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.white));

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
                emailLayout.setHelperText("Campo obligatorio");
            } else if (contraseña.getText().length() <= 0) {
                contraseñaLayout.setError("");
                contraseñaLayout.setHelperText("Campo obligatorio");
            } else {
                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText().toString());
                    body.put("contraseña", contraseña.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                rest.login(
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    // Guardado del token de sesión en las Shared Preferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("token", response.getString("token")).apply();

                                    /*
                                    // Guardado de la clave de encriptado en las EncyptedSharedPreferences
                                    String masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                                    SharedPreferences sharedPreferencesEncrypted = EncryptedSharedPreferences.create(
                                            "clave",
                                            masterKey,
                                            context,
                                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                                    );
                                    sharedPreferencesEncrypted.edit().putString("clave", response.getString("clave")).apply();
                                    */
                                    Intent intent = new Intent(context, Inicio.class);
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse.statusCode == 403) {
                                    contraseñaLayout.setError("");
                                    contraseñaLayout.setHelperText(getResources().getString(R.string.contraseña_error));
                                } else if (error.networkResponse.statusCode == 404) {
                                    emailLayout.setError("");
                                    emailLayout.setHelperText("Usuario no registrado");
                                }
                            }
                        },
                        body
                );
            }


        }
    };

    View.OnClickListener registrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Registro.class);
            startActivity(intent);
        }
    };

    /*      MOVER A LAUNCHER     */
    // Metodo para mantener la configuracion de idioma
    private void setIdioma() {
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

            // Guardamos el idioma en las shared preferences para mantenerlo al volver a abrir la app


            // Reinicia la actividad para aplicar el cambio de idiomarecreate();
        }

    }
}
