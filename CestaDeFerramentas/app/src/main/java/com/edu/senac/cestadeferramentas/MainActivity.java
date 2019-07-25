package com.edu.senac.cestadeferramentas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editSenha, editEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CicloVidaActivity", "Passando pelo método onCreate...");
        setContentView(R.layout.activity_main);

        editSenha=findViewById(R.id.edtSenha);
        editEmail=findViewById(R.id.edtEmail);
    }


    public void irParaPrincipal (View v){

        //atribui o valor do campo da tela para a variavel do tipo String
        String email=editEmail.getText().toString();
        String senha=editSenha.getText().toString();

        if (email.equals("marco@senac.br") & senha.equals("123")){

            //Mandando para uma próxima tela
            Intent i=new Intent(this, Principal.class);
            startActivity(i);

            //mensagem para exibição de informações
            Toast.makeText(this, "Logado.",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Falha ao logar.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("ClicloVidaActivity", "passando pelo método onStart...");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.i("ClicloVidaActivity", "passando pelo método onRestart...");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("ClicloVidaActivity", "passando pelo método onPause...");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("ClicloVidaActivity", "passando pelo método onStop...");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("ClicloVidaActivity", "passando pelo método onDestroy...");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("ClicloVidaActivity", "passando pelo método onResume...");
    }
}
