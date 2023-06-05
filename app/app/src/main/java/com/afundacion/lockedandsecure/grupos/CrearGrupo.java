package com.afundacion.lockedandsecure.grupos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class CrearGrupo extends AppCompatActivity {
    private TextInputLayout nombreLayout;
    private TextInputEditText nombre;
    private Toolbar toolbar;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        setUpToolbar();
        nombre = findViewById(R.id.nombreTextInput);
        nombreLayout = findViewById(R.id.nombreTextInputLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crear_grupo, menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.guardar_toolbar) {
            if (nombre.getText().toString().length() != 0) {
                Rest rest = Rest.getInstance(context);
                JSONObject body = new JSONObject();
                try {
                    body.put("nombre", nombre.getText().toString());
                } catch (JSONException e) {e.printStackTrace();}
                
                rest.crearGrupo(
                        response -> {
                            Toast.makeText(context, "Usuario creado", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        },
                        error -> {
                            if (error.networkResponse.statusCode == 409) {
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        body
                );
            } else {
                nombreLayout.setHelperText(getResources().getText(R.string.campo_obligatorio));
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
