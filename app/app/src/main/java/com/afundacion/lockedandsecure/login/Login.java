package com.afundacion.lockedandsecure.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.crearContrasena.CrearContraseña;
import com.afundacion.lockedandsecure.inicio.Inicio;
import com.afundacion.lockedandsecure.registro.Registro;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private TextInputLayout emailLayout, contraseñaLayout;
    private TextInputEditText email, contraseña;
    private Button inicioSesionBoton, registrarBoton;
    private Rest rest = Rest.getInstance(this);
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        //getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        email = findViewById(R.id.emailTextInput);
        emailLayout = findViewById(R.id.emailTextInputLayout);

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
                                    SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("token", response.getString("token")).apply();
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
}
