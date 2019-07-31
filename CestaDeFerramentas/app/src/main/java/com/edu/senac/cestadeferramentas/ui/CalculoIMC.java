package com.edu.senac.cestadeferramentas.ui;

import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculoIMC extends AppCompatActivity {
    EditText editPeso, editAltura, imc;
    TextView Resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editPeso=findViewById(R.id.edtPeso);
        editAltura=findViewById(R.id.edtAltura);

    }

    public void Calcular(View v){

        float altura=Float.parseFloat(editAltura.getText().toString());
        float peso=Float.parseFloat(editPeso.getText().toString());

        double imc = peso/(altura*altura);

        Resultado=(TextView)findViewById(R.id.Resultado);
        if (imc < 18.5){
            Resultado.setText(imc+". Abaixo do peso.");
        } else if (imc > 18.5 & imc < 24.9){
            Resultado.setText(imc + ". Peso normal.");
        } else if (imc > 25 & imc < 29.9){
            Resultado.setText(imc + ". Sobre peso.");
        } else {
            Resultado.setText(imc + ". Obesidade.");
        }
    }

}
