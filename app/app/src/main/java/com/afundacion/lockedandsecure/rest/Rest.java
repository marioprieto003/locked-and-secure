package com.afundacion.lockedandsecure.rest;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class Rest {
    private static Rest INSTANCE;

    private final String ANDROID_LOCALHOST = "http://10.0.2.2:8000"; // locahost del emulador de Android studio
    private final String MOVIL_IP = "http://192.168.0.11:8000";
    private String BASE_URL = MOVIL_IP;
    private Context context;
    private RequestQueue queue;

    private Rest(Context context) {
        this.context = context;
    }

    public static Rest getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Rest(context);
        }
        return INSTANCE;
    }

    public void health(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL + "/health",
                null,
                onResponse,
                onErrorResponse
        ));
    }

    public void auth(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/auth",
                null,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void login(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.POST,
                BASE_URL + "/login",
                body,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void registro(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/registro",
                body,
                onResponse,
                onErrorResponse
        ));
    }

    public void crearGrupo(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.POST,
                BASE_URL + "/grupo",
                body,onResponse,
                onErrorResponse,
                context
        ));
    }

    public void crearContraseña(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.POST,
                BASE_URL + "/contraseña",
                body,
                onResponse,
                onErrorResponse,
                context
        ));
    }
  
    public void inicio(Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonArrayRequestWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/inicio",
                null,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void getContraseñas(Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse, int idGrupo) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonArrayRequestWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/grupo/" + idGrupo,
                null,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void generarContraseña(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL + "/contraseña",
                null,
                onResponse,
                onErrorResponse
        ));
    }

    public void getCuenta(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/ajustes/cuenta",
                null,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void editarContraseña(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.PUT,
                BASE_URL + "/contraseña",
                body,
                onResponse,
                onErrorResponse,
                context
        ));
    }


    class JsonObjectRequestWithCustomAuth extends JsonObjectRequest {
        private Context context;

        public JsonObjectRequestWithCustomAuth(int method,
                                               String url,
                                               @Nullable JSONObject jsonRequest,
                                               Response.Listener<JSONObject> listener,
                                               @Nullable Response.ErrorListener errorListener,
                                               Context context) {
            super(method, url, jsonRequest, listener, errorListener);
            this.context = context;
        }

        @Override
        public Map<String, String> getHeaders() {
            SharedPreferences preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            String tokenSesion = preferences.getString("token", null);

            HashMap<String, String> myHeaders = new HashMap<>();
            myHeaders.put("token", tokenSesion);
            return myHeaders;
        }
    }

    class JsonArrayRequestWithCustomAuth extends JsonArrayRequest {
        private Context context;

        public JsonArrayRequestWithCustomAuth(int method,
                                              String url,
                                              @Nullable JSONArray jsonRequest,
                                              Response.Listener<JSONArray> listener,
                                              @Nullable Response.ErrorListener errorListener,
                                              Context context) {
            super(method, url, jsonRequest, listener, errorListener);
            this.context = context;
        }

        @Override
        public Map<String, String> getHeaders() {
            SharedPreferences preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            String tokenSesion = preferences.getString("token", null);

            HashMap<String, String> myHeaders = new HashMap<>();
            myHeaders.put("token", tokenSesion);
            return myHeaders;
        }
    }
}
