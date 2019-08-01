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
import android.graphics.drawable.BitmapDrawable;
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
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Produto;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {
    ImageView AddImagem;
    EditText nomeProduto, quantidadeProduto;
    Spinner statusProduto;
    Button btnExcluir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        AddImagem = findViewById(R.id.AddImagem);
        nomeProduto=findViewById(R.id.nomeProduto);
        quantidadeProduto=findViewById(R.id.quantidadeProduto);
        statusProduto=findViewById(R.id.statusProduto);
        btnExcluir=findViewById(R.id.btnExcluir);

        //OCULTA
        btnExcluir.setVisibility(View.GONE);

        //VISIVEL
        //btnExcluir.setVisibility(View.VISIBLE);


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

            Produto pro=new Produto();
            pro.setFoto(getImagem());
            pro.setNome(nomeProduto.getText().toString());
            pro.setQuantidade(Integer.parseInt(quantidadeProduto.getText().toString()));
            pro.setStatus(statusProduto.getSelectedItem().toString().equals("COMPRADO")?"C":"N");

            databaseHelper.salvarProduto(pro);
            finish();
        } else {
            mensagemErro(mensagem);
        }
    }

    public void excluirCadastro(View v){
        DatabaseHelper databaseHelper=new DatabaseHelper(this);

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
}