package com.edu.senac.cestadeferramentas.ui;

import android.content.Intent;
import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_aplicativos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sobre:
                Intent i=new Intent(this, Sobre.class);
                startActivity(i);
                default:
                    return super.onOptionsItemSelected(item);
        }
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

    /*public void irParaSobre(View V){
        //Mandando para uma próxima tela
        Intent i=new Intent(this, Sobre.class);
        startActivity(i);
    }*/

    public void irParaLista(View V){
        //Mandando para uma próxima tela
        Intent i=new Intent(this, ListaProdutos.class);
        startActivity(i);
    }

}
