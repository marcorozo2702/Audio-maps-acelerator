package com.edu.senac.cestadeferramentas.ui;

import android.content.Intent;
import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity (new Intent(ListaProdutos.this, ProdutoActivity.class)) ;
            }
        });

        databaseHelper=new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Produto>listraPro=databaseHelper.buscarTodos();
        for (Produto produto:listraPro){
            Log.e("produto", "Id " +produto.getCodigo()+" Nome: " +produto.getNome());
        }
    }
}
