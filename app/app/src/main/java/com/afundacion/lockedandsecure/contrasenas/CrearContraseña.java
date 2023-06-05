/*
 * *
 *  * Created by mprieto on 1/6/23 9:12
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 1/6/23 9:09
 *
 */

package com.afundacion.lockedandsecure.contrasenas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.afundacion.gestorcontrasenas.R;
import com.afundacion.lockedandsecure.rest.Rest;
import com.android.volley.Response;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class CrearContraseña extends AppCompatActivity {
    private TextInputLayout contraseñaLayout, emailLayout, usuarioLayout;
    private TextInputEditText email, contraseñaTextInput, usuario, plataforma;
    private Button generarContraseña, qrBoton;
    private View fortalezaContraseña;
    private Toolbar toolbar;
    private Context context = this;
    //private int color = getResources().getColor(R.color.weak);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contrasena);
        setUpToolbar();

        generarContraseña = findViewById(R.id.generarContraseña);
        generarContraseña.setOnClickListener(generarContraseñaListener);

        email = findViewById(R.id.emailTextInput);
        usuario = findViewById(R.id.usuarioTextInput);
        plataforma = findViewById(R.id.plataformaTextInput);

        qrBoton = findViewById(R.id.qrBoton);
        qrBoton.setOnClickListener(qrListener);

        emailLayout = findViewById(R.id.emailTextInputLayout);
        usuarioLayout = findViewById(R.id.usuarioTextInputLayout);

        contraseñaTextInput = findViewById(R.id.contraseñaTextInput);
        contraseñaLayout = findViewById(R.id.contraseñaTextInputLayout);
        fortalezaContraseña = findViewById(R.id.fortaleza_contrasena);

        // Con TextWatcher podemos detectar cambios al momento en un EditTexxt
        contraseñaTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    analyzeString(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    View.OnClickListener generarContraseñaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Rest rest = Rest.getInstance(context);

            rest.generarContraseña(
                    response ->  {
                        try {
                            contraseñaTextInput.setText(response.getString("contraseña"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error ->  {

                    }
            );
        }
    };

    View.OnClickListener qrListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog builder = new Dialog(context);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });

            ImageView imageView = new ImageView(context);
            try {
                imageView.setImageBitmap(encodeAsBitmap(contraseñaTextInput.getText().toString()));
            } catch (WriterException e) {
                e.printStackTrace();
            }
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            builder.show();
        }
    };

    private void analyzeString(CharSequence contraseña) {
        // Si la contraseña cumple los requisitos la consideramos "fuerte" y se lo mostramos al usuario
        if (hasLength(contraseña) && hasSymbol(contraseña) && hasUpperCase(contraseña) && hasLowerCase(contraseña)) {
            contraseñaLayout.setBoxStrokeColor(getResources().getColor(R.color.bulletproof));
            fortalezaContraseña.setBackgroundColor(getResources().getColor(R.color.bulletproof));
        } else {
            contraseñaLayout.setBoxStrokeColor(getResources().getColor(R.color.weak));
            fortalezaContraseña.setBackgroundColor(getResources().getColor(R.color.weak));
        }
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crear_contrasena, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.guardar) {

            if (email.getText().toString().length() < 1) {
                emailLayout.setError(getResources().getString(R.string.campo_obligatorio));
            } else if (usuario.getText().toString().length() < 1) {
                usuarioLayout.setError(getResources().getString(R.string.campo_obligatorio));
            } else if (contraseñaTextInput.getText().toString().length() < 1) {
                contraseñaLayout.setError(getResources().getString(R.string.campo_obligatorio));
            } else {

                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText().toString());
                    body.put("usuario", usuario.getText().toString());
                    body.put("contraseña", contraseñaTextInput.getText().toString());
                    body.put("plataforma", plataforma.getText().toString());
                    body.put("grupo", getIntent().getIntExtra("idGrupo", 1));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Rest rest = Rest.getInstance(context);
                rest.crearContraseña(
                        (Response.Listener<JSONObject>) response -> {
                            Toast.makeText(context, getResources().getString(R.string.contraseña_creada), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        },
                        error -> {
                            Toast.makeText(context, error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                        },
                        body
                );
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private static boolean hasLength(CharSequence data) {
        return String.valueOf(data).length() >= 8;
    }

    private static boolean hasSymbol(CharSequence data) {
        String password = String.valueOf(data);
        return !password.matches("[A-Za-z0-9 ]*");
    }

    private static boolean hasUpperCase(CharSequence data) {
        String password = String.valueOf(data);
        return !password.equals(password.toLowerCase());
    }

    private static boolean hasLowerCase(CharSequence data) {
        String password = String.valueOf(data);
        return !password.equals(password.toUpperCase());
    }

    // Metodo de creacion del QR
    Bitmap encodeAsBitmap(String str) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 600, 600);

        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }

    // Metodo para que la flecha superior funcione igual que pulsa atras
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
