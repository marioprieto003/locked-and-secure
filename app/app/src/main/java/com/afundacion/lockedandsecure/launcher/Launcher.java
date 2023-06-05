package com.afundacion.lockedandsecure.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.lockedandsecure.inicio.Inicio;
import com.afundacion.lockedandsecure.login.Login;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class Launcher extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        String sessionToken = preferences.getString("token", null);

        if (sessionToken == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        } else {
            Rest rest = Rest.getInstance(context);

            rest.auth(
                    response -> {
                        Intent intent = new Intent(context, Inicio.class);
                        startActivity(intent);
                        finish();
                    },
                    error -> {
                        if (error.networkResponse.statusCode == 401) {
                            Intent intent = new Intent(context, Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }
            );
        }
    }
}
