package com.edu.senac.cestadeferramentas.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.constantes.Request;
import com.edu.senac.cestadeferramentas.helper.AdapterList;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    ListView listaProdutos;
    List<Produto> produtos;
    ProgressDialog progress;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent=new Intent(this, ProdutoActivity.class);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.removeExtra("produto");
                startActivity (intent);
            }
        });

        databaseHelper=new DatabaseHelper(this);

        produtos = new ArrayList<>();
        AdapterList adapterList=new AdapterList(produtos, this);
        listaProdutos= findViewById(R.id.lista);
        listaProdutos.setAdapter(adapterList);



        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("produto", produtos.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*produtos=null;
        produtos=databaseHelper.buscarTodos();
        AdapterList adapterList = (AdapterList) listaProdutos.getAdapter();

        adapterList.atualizarProdutos(produtos);*/

        new adquirirProduto().execute();


    }



    private class adquirirProduto extends AsyncTask<Void, Void, List<Produto>> {

        @Override
        protected void  onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(ListaProdutos.this);
            progress.show();
            progress.setCancelable(false);
            progress.setContentView(R.layout.progres);

        }

        @Override
        protected List<Produto> doInBackground(Void... produtos) {
            try {

                Thread.sleep(3000);


                URL url = new URL(Request.URL_REQUEST+"/ferramentas/adquirirProduto");
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("Codigo","1");
                /*urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);*/


                Gson gson=new Gson();

                int codigoResposta = urlConnection.getResponseCode();

                Log.e("request", "erro XXX:"+codigoResposta);
                if (codigoResposta==200){

                    String jsonResposta= "";
                    InputStreamReader inputStream=new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader reader=new BufferedReader(inputStream);

                    String line="";
                    while ((line=reader.readLine()) != null){
                        jsonResposta+=line;
                    }
                    Log.e("request", jsonResposta);

                    Type listType = new TypeToken<ArrayList<Produto>>(){}.getType();

                    return gson.fromJson(jsonResposta, listType);

                } else {
                    return null;
                }




            }catch (Exception e){
                Log.e("request", "erro");

            }


            return null;
        }

        @Override /// usuario vem do parametro do metodo doIndBackground
        protected void onPostExecute(List<Produto> produto) {
            progress.dismiss();
            AdapterList adapterList = (AdapterList) listaProdutos.getAdapter();

            adapterList.atualizarProdutos(produto);

        }



    }
}
