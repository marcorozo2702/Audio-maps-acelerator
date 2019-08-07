package com.edu.senac.cestadeferramentas.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Auto;
import com.edu.senac.cestadeferramentas.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoActivity extends AppCompatActivity {


    EditText edtReferencia, edtQuantidade, edtPeca, edtDescricao, edtValor;
    ImageView imagemPeca;
    Spinner edtUnidade;
    Button btnSalvar, btnDeletar;
    Auto atm;
    final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtReferencia = findViewById(R.id.edtReferencia);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        edtPeca = findViewById(R.id.edtPeca);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtValor = findViewById(R.id.edtValor);
        imagemPeca = findViewById(R.id.imagemPeca);
        edtUnidade = findViewById(R.id.edtUnidade);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnDeletar = findViewById(R.id.btnDeletar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void selecionarImagem(View view) {
        if (checkAndRequestPermissions()) {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        imagemPeca.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    public boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionNeeded = new ArrayList<>();
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                    1);
            return false;
        }
        return true;
    }



    public String validarCampos(){
        if (edtPeca.getText().toString().trim().length() ==0){
            return "Informe a peça";
        } if (edtQuantidade.getText().toString().trim().length() ==0){
            return "informe a quantidade";
        } else{
            int quantidade = Integer.parseInt(edtQuantidade.getText().toString());
            if (quantidade ==0){
                return "informe a quantidade";
            }
        }
        return "";
    }

    public void salvarCadastroPeca(View v){
        //verifica os campos
        String mensagem=validarCampos();
        //caso seja verdadeiro salvar os campos
        if(mensagem.length()==0){

            /*if ( atm!=null){
                //função a função que faz o update nos produtos
                atualizarCadastro(v);
            }*/
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
            DatabaseHelper databaseHelper = new DatabaseHelper(this);

            Auto atm=new Auto();
            atm.setImagem(getImagem());
            atm.setNome(edtPeca.getText().toString());
            atm.setReferencia(edtReferencia.getText().toString());
            atm.setDescricao(edtDescricao.getText().toString());
            atm.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
            atm.setUnidade(edtUnidade.getSelectedItem().toString());
            atm.setValor(Float.parseFloat(edtValor.getText().toString()));

            databaseHelper.salvarPeca(atm);
            finish();
        } else {
            mensagemErro(mensagem);
        }
    }





    public String getImagem(){
        Bitmap bitmap = ((BitmapDrawable)imagemPeca.getDrawable()).getBitmap();

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
                Toast.makeText(AutoActivity.this, "positivo" +arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(AutoActivity.this, "negativo=" +arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        builder.create().show();
    }

}
