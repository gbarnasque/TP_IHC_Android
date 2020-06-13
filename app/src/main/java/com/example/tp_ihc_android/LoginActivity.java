package com.example.tp_ihc_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    Context appContext;

    EditText etEmail;
    EditText etSenha;
    TextView tvTeste;

    Button btnLogin;

    StringRequest stringRequest;
    String requestBody;

    //String teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = this.getApplicationContext();

        setContentView(R.layout.activity_login2);

        etEmail = (EditText)findViewById(R.id.tvEmail);
        etSenha = (EditText)findViewById(R.id.tvSenha);
        btnLogin = findViewById(R.id.btnLogin);
        //tvTeste = findViewById(R.id.tvTeste);
        //MainScreen = new Intent(this, LoginActivity.class);

        stringRequest = new StringRequest(Request.Method.POST, ConnectSingleton.getInstance(appContext).getEndpoint() + "user/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseObject = new JSONObject(response);

                            //tvTeste.setText(responseObject.optString("status"));
                            //teste = responseObject.optString("status");
                            if(responseObject.optString("status").equals("OK")) {
                                changeActivity();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            //Log.wtf("erro", e.getMessage());
                            //Log.d("erro", e.getMessage());
                            //tvTeste.setText(ConnectSingleton.getInstance(appContext).getEndpoint() + "user/login1");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.wtf("erro", error.getMessage());
                        //Log.d("erro", error.getMessage());
                       // tvTeste.setText(ConnectSingleton.getInstance(appContext).getEndpoint() + "user/login2");
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            //Log.wtf("aqui", "Unsupported Encoding while trying to get the bytes of using "  +requestBody);
                            //Log.d("aqui", "Unsupported Encoding while trying to get the bytes of using" +  requestBody);
                            return null;
                        }
                    }
                };


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSenha.getText().toString().equals("1111")){
                    showAlertDialogButtonClicked(v);
                }
                else {
                    changeActivity();
                }
                /*
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", etEmail.getText().toString());
                    jsonObject.put("senha", etSenha.getText().toString());
                }
                catch (JSONException e){

                }
                requestBody = jsonObject.toString();

                ConnectSingleton.getInstance(appContext).addToRequestQueue(stringRequest);
                */
            }
        });

    }

    private void changeActivity(){
        Intent intent = new Intent(this, MenuBar.class);
        startActivity(intent);
    }

    private void checkPassword(){

    }
    public void showAlertDialogButtonClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Não foi possível realizar o login.");
        builder.setMessage("Por gentileza, tente novamente.");


        builder.setPositiveButton("Tentar Novamente", null);


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //public String getEmail(){
        //String texto = email.getText(;

    //}
}
