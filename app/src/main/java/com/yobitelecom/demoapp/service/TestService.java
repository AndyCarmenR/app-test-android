package com.yobitelecom.demoapp.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yobitelecom.demoapp.method.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestService extends Activity {

    public void callEndpoint(HashMap<String, Object> componentes, Context context) {
        ProgressDialog progressDialog = ((ProgressDialog) componentes.get("progressDialog"));
        progressDialog.setCancelable(Boolean.FALSE);
        progressDialog.show();

        EditText txtSaludo = ((EditText) componentes.get("txtSaludo"));

        JSONObject request = new JSONObject();
        try {
            request.put("textoSaludo", txtSaludo.getText().toString());

            RestClient.post("https://test-spring-boot-tlc.herokuapp.com/api/test/hola",
                    request.toString(),
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(() -> {
                                progressDialog.dismiss();
                            });

                            e.printStackTrace();

                            Toast.makeText(context, "ha ocurrido un error: " + e.getMessage(), Toast.LENGTH_LONG);
                        }

                        //android.os.NetworkOnMainThreadException
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String responseString = response.body().string();
                                Log.i("TAG", "run: " + responseString);
                                JSONObject jsonResponse;
                                try {
                                    jsonResponse = new JSONObject(responseString);

                                    runOnUiThread(() -> {
                                        progressDialog.dismiss();
                                        try {
                                            ((TextView) componentes.get("lblPresentar")).setText(jsonResponse.getString("description"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "ocurrio un error: " + e.getMessage(), Toast.LENGTH_LONG);
                                }

                            } else {
                                runOnUiThread(() -> {
                                    progressDialog.dismiss();
                                });
                                Toast.makeText(context, "ocurrio un error: " + response.code(), Toast.LENGTH_LONG);
                            }
                        }
                    });
        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(context, "ocurrio un error: " + e.getMessage(), Toast.LENGTH_LONG);
        }
    }
}
