package com.afundacion.lockedandsecure.registro;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {
    private TextInputLayout emailLayout, contraseñaLayout, repetirContraseñaLayout, nombreLayout, apellido1Layout, apellido2Layout;
    private TextInputEditText email, contraseña, repetirContraseña, nombre, apellido1, apellido2;
    private Button registrarBoton;
    private Rest rest = Rest.getInstance(this);
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        emailLayout = findViewById(R.id.emailTextInputLayout);
        email = findViewById(R.id.emailTextInput);

        contraseñaLayout = findViewById(R.id.contraseñaTextInputLayout);
        contraseña = findViewById(R.id.contraseñaTextInput);

        repetirContraseñaLayout = findViewById(R.id.repetirContraseñaTextInputLayout);
        repetirContraseña = findViewById(R.id.repetirContraseñaTextInput);

        nombreLayout = findViewById(R.id.nombreTextInputLayout);
        nombre = findViewById(R.id.nombreTextInput);

        apellido1Layout = findViewById(R.id.apellido1TextInputLayout);
        apellido1 = findViewById(R.id.apellido1TextInput);

        apellido2Layout = findViewById(R.id.apellido2TextInputLayout);
        apellido2 = findViewById(R.id.apellido2TextInput);

        registrarBoton = findViewById(R.id.registrarBoton);
        registrarBoton.setOnClickListener(registrarListener);
    }

    View.OnClickListener registrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            emailLayout.setHelperText("");
            contraseñaLayout.setHelperText("");
            repetirContraseñaLayout.setHelperText("");
            nombreLayout.setHelperText("");
            apellido1Layout.setHelperText("");
            apellido2Layout.setHelperText("");

            if (email.getText().length() <= 0) {
                emailLayout.setError("");
                emailLayout.setHelperText("Campo obligatorio");
                // FALTA COMPROBACION DE EMAIL
            } else if (contraseña.getText().length() <= 0) {
                contraseñaLayout.setError("");
                contraseñaLayout.setHelperText("Campo obligatorio");
            } else if (repetirContraseña.getText().length() <= 0) {
                repetirContraseñaLayout.setError("");
                repetirContraseñaLayout.setHelperText("Campo obligatorio");
            } else if (nombre.getText().length() <= 0) {
                nombreLayout.setError("");
                nombreLayout.setHelperText("Campo obligatorio");
            } else if (apellido1.getText().length() <= 0) {
                apellido1Layout.setError("");
                apellido1Layout.setHelperText("Campo obligatorio");
            } else if (apellido2.getText().length() <= 0) {
                apellido2Layout.setError("");
                apellido2Layout.setHelperText("Campo obligatorio");
            } else if (!contraseña.getText().toString().equals(repetirContraseña.getText().toString())) {
                contraseñaLayout.setError("");
                repetirContraseñaLayout.setError("");
                contraseñaLayout.setHelperText("Las contraseñas no coinciden");
                repetirContraseñaLayout.setHelperText("Las contraseñas no coinciden");
            } else {
                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText().toString());
                    body.put("contraseña", contraseña.getText().toString());
                    body.put("nombre", nombre.getText().toString());
                    body.put("apellido1", apellido1.getText().toString());
                    body.put("apellido2", apellido2.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                
                rest.registro(
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Usuario Registrado", Snackbar.LENGTH_LONG);
                                onBackPressed();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();  
                            }
                        },
                        body
                );
            }
        }
    };
}
