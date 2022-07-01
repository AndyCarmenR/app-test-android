package com.yobitelecom.demoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yobitelecom.demoapp.R;
import com.yobitelecom.demoapp.service.TestService;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lblPresentar;
    private EditText txtSaludo;
    private Button btnCallEndpoint, btnLimpiar;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblPresentar=findViewById(R.id.lblPresentar);
        btnCallEndpoint=findViewById(R.id.btnCallEndpoint);
        btnLimpiar=findViewById(R.id.btnLimpiar);
        txtSaludo=findViewById(R.id.txtSaluda);

        progress=new ProgressDialog(this);
        progress.setMessage("cargando...");
        progress.setCancelable(Boolean.FALSE);

        btnCallEndpoint.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btnCallEndpoint){
            HashMap<String, Object>controles=new HashMap<>();
            controles.put("lblPresentar",lblPresentar);
            controles.put("progressDialog",progress);
            controles.put("txtSaludo",txtSaludo);
            new TestService().callEndpoint(controles,this);
        }
        else if(v==btnLimpiar){
            lblPresentar.setText(R.string.textoInicio);
            txtSaludo.setText("");
            txtSaludo.setHint(R.string.hintTxt);
        }

    }
}