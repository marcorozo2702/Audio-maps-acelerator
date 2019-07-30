package com.edu.senac.cestadeferramentas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
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
    }

    public void irParaIMC(View V){
        //Mandando para uma próxima tela
        Intent i=new Intent(this, CalculoIMC.class);
        startActivity(i);
    }

    public void irParaCalculo (View V){
        //Mandando para uma próxima tela
        Intent i=new Intent(this, CalculoBytes.class);
        startActivity(i);
    }

    public void irParaSobre(View V){
        //Mandando para uma próxima tela
        Intent i=new Intent(this, Sobre.class);
        startActivity(i);
    }

    public void irParaLista(View V){
        //Mandando para uma próxima tela
        Intent i=new Intent(this, ListaProdutos.class);
        startActivity(i);
    }

}
