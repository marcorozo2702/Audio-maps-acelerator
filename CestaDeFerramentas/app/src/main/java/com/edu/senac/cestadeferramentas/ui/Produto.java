package com.edu.senac.cestadeferramentas.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.senac.cestadeferramentas.R;

import java.util.ArrayList;
import java.util.List;

public class Produto extends AppCompatActivity {
    ImageView AddImagem;
    EditText nomeProduto, Quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        AddImagem = findViewById(R.id.AddImagem);
        nomeProduto=findViewById(R.id.nomeProduto);
        Quantidade=findViewById(R.id.Quantidade);


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
        } if (Quantidade .getText().toString().trim().length() ==0){
            return "informe a quantidade";
        } else{
            int quantidade = Integer.parseInt(Quantidade.getText().toString());
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
            //chama a função para salvar os campos
            salvar();
        } else {
            //chama a função para exibir as mensagens
            mensagemErro(mensagem);
        }
    }

    public void salvar(){

    }

    public void mensagemErro(String mensagem){
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(mensagem);

        //define um botão como positivo
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(Produto.this, "positivo" +arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(Produto.this, "negativo=" +arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        builder.create().show();
    }
}