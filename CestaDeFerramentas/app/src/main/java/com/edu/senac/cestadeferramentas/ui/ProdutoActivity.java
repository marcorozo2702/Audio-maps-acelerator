package com.edu.senac.cestadeferramentas.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.constantes.Request;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.edu.senac.cestadeferramentas.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.query.In;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {
    ImageView AddImagem;
    EditText nomeProduto, quantidadeProduto;
    Spinner statusProduto;
    Button btnExcluir;
    Produto pro;
    ProgressDialog progress;

    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        AddImagem = findViewById(R.id.AddImagem);
        nomeProduto=findViewById(R.id.nomeProduto);
        quantidadeProduto=findViewById(R.id.quantidadeProduto);
        statusProduto=findViewById(R.id.statusProduto);
        btnExcluir=findViewById(R.id.btnExcluir);



        Intent thisintent = getIntent();
        usuario=(Usuario) thisintent.getSerializableExtra("usuario");


        Intent i = getIntent();

        pro = (Produto) i.getSerializableExtra("produto");
        Produto pro = (Produto) i.getSerializableExtra("produto");
        if (pro!=null){
            btnExcluir.setVisibility(View.VISIBLE);
            nomeProduto.setText(pro.getNome());
            quantidadeProduto.setText(Integer.toString(pro.getQuantidade()));
            statusProduto.setSelection(pro.getStatus().equals("C")?0:1);

            byte[] decodedString = Base64.decode(pro.getFoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            AddImagem.setImageBitmap(decodedByte);
        } else {
            btnExcluir.setVisibility(View.GONE);
        }
        //GONE = oculto//VISIBLE= visivel
    }

    public void onClickWhatsApp(View view) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Por favor compre" +
                    " "+pro.getQuantidade()+" unidade do produto "+pro.getNome();

            PackageInfo info=pm.getPackageInfo("com.whatsapp",
                    PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        List<String> listPermissionNeeded = new ArrayList<>();
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                    1);
            return false;
        }
        return true;
    }

    public void irParaCameraProduto(View v) {
        Log.e("camera", "inicio foto");

        if (checkAndRequestPermissions()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("camera", "erro foto" + requestCode + " - " + RESULT_OK);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            AddImagem.setImageBitmap(imageBitmap);
        }
    }

    public String validarCampos(){
        if (nomeProduto.getText().toString().trim().length() ==0){
            return "Informe o nome";
        } if (quantidadeProduto .getText().toString().trim().length() ==0){
            return "informe a quantidade";
        } else{
            int quantidade = Integer.parseInt(quantidadeProduto.getText().toString());
            if (quantidade ==0){
                return "informe a quantidade";
            }
        }
        return "";
    }

    public void salvarCadastro(View v){
        //verifica os campos
        String mensagem=validarCampos();
        //caso seja verdadeiro salvar os campos
        if(mensagem.length()==0){

            if ( pro!=null){
                //função a função que faz o update nos produtos
                atualizarCadastro(v);
            }
            //chama a função para salvar os campos
            salvar(v);

        } else {

            //chama a função para exibir as mensagens
            mensagemErro(mensagem);
        }
    }

    public void salvar(View v){
        String mensagem = validarCampos();
        if (mensagem.equals("")){
          // DatabaseHelper databaseHelper = new DatabaseHelper(this);

            Produto pro=new Produto();
            pro.setFoto(getImagem());
            pro.setNome(nomeProduto.getText().toString());
            pro.setQuantidade(Integer.parseInt(quantidadeProduto.getText().toString()));
            pro.setStatus(statusProduto.getSelectedItem().toString().equals("COMPRADO")?"C":"N");

            new salvarProduto().execute(pro);

            //databaseHelper.salvarProduto(pro);
            //finish();
        } else {
            mensagemErro(mensagem);
        }
    }

    public void atualizarCadastro(View v){
        DatabaseHelper databaseHelper=new DatabaseHelper(this);

        pro.setFoto(getImagem());
        pro.setNome(nomeProduto.getText().toString());
        pro.setQuantidade(Integer.parseInt(quantidadeProduto.getText().toString()));
        pro.setStatus(statusProduto.getSelectedItem().toString().equals("COMPRADO")?"C":"N");
        databaseHelper.update(pro);
        finish();
    }

    public void excluirCadastro(View v){
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        databaseHelper.removerProduto(pro);
        finish();
    }

    public String getImagem(){
        Bitmap bitmap = ((BitmapDrawable)AddImagem.getDrawable()).getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void mensagemErro(String mensagem){
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(mensagem);

        //define um botão como positivo
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ProdutoActivity.this, "positivo" +arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ProdutoActivity.this, "negativo=" +arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        builder.create().show();
    }



    private class salvarProduto extends AsyncTask<Produto, Void, List<Produto>> {

        @Override
        protected void  onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(ProdutoActivity.this);
            progress.show();
            progress.setCancelable(false);
            progress.setContentView(R.layout.progres);

        }

        @Override
        protected List<Produto> doInBackground(Produto... produtos) {
            try {



                URL url = new URL(Request.URL_REQUEST+"/ferramentas/salvarProduto");
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("codigo",usuario.getCodigo().toString());
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);


                Gson gson=new Gson();
                DataOutputStream outputStream= new DataOutputStream(urlConnection.getOutputStream());
                String parametro = gson.toJson(produtos[0]);
                Log.e("request", parametro);
                outputStream.writeBytes(parametro);
                outputStream.flush();
                outputStream.close();

                int codigoResposta = urlConnection.getResponseCode();

                Log.e("request", "erro tal asiasoaksoa:"+codigoResposta);
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
                Log.e("request", "erro 666");

            }


            return null;
        }

        @Override /// usuario vem do parametro do metodo doIndBackground
        protected void onPostExecute(List<Produto> produto) {
            progress.dismiss();



            AlertDialog.Builder alertDialog=new AlertDialog.Builder(ProdutoActivity.this);
            if (produto!= null){

                alertDialog.setTitle("Atencao!");
                alertDialog.setMessage("Sucesso");









                finish();

            }else
            {   alertDialog.setTitle("Atencao!");
                alertDialog.setMessage("Erro");}
            alertDialog.create().show();

        }



    }




}