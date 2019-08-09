package com.edu.senac.cestadeferramentas.ui;

import android.content.Intent;
import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.helper.AdapterAuto;
import com.edu.senac.cestadeferramentas.helper.AdapterList;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Auto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaAutoActivity extends AppCompatActivity {

    ListView autoList;
    List<Auto> autos;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_auto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent=new Intent(this, AutoActivity.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.removeExtra("auto");
                startActivity (intent);
            }
        });

        databaseHelper=new DatabaseHelper(this);

        autos = new ArrayList<>();
        AdapterAuto adapterAuto=new AdapterAuto(autos, this);
        autoList= findViewById(R.id.listaAuto);
        autoList.setAdapter(adapterAuto);



        autoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("auto", autos.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        autos=null;
        autos=databaseHelper.buscarTodasPecas();
        AdapterAuto adapterAuto = (AdapterAuto) autoList.getAdapter();

        adapterAuto.atualizarAutos(autos);



    }


}
