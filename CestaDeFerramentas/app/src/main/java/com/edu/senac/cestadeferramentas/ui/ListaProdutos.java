package com.edu.senac.cestadeferramentas.ui;

import android.content.Intent;
import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.helper.AdapterList;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    ListView listaProdutos;
    List<Produto> produtos;
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

        produtos = new ArrayList<>();
        AdapterList adapterList=new AdapterList(produtos, this);
        listaProdutos= findViewById(R.id.lista);
        listaProdutos.setAdapter(adapterList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        produtos=null;
        produtos=databaseHelper.buscarTodos();
        AdapterList adapterList = (AdapterList) listaProdutos.getAdapter();

        adapterList.atualizarProdutos(produtos);


    }
}
