package com.example.tp_ihc_android;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity<string> extends AppCompatActivity {

    Button btnTeste;
    TextView tvTeste;
    ImageView imgView;

    RequestQueue queue;
    String url = "https://dog.ceo/api/breeds/image/random/";
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTeste = findViewById(R.id.btnTeste);
        tvTeste = findViewById(R.id.tvTeste);
        imgView = findViewById(R.id.imageView);

        queue = Volley.newRequestQueue(this);
        url = "https://dog.ceo/api/breeds/image/random";
        // Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String test = jsonObject.optString("message");
                            Picasso.get().load(test).into(imgView);
                            tvTeste.setText(test);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Log.wtf("erro", e.getMessage());
                            Log.d("erro", e.getMessage());
                            tvTeste.setText("deu ruim.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvTeste.setText("deu ruim.");
                    }
                });

        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
