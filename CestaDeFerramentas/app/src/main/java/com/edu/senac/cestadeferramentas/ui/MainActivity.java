package com.edu.senac.cestadeferramentas.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.constantes.Request;
import com.edu.senac.cestadeferramentas.helper.DatabaseHelper;
import com.edu.senac.cestadeferramentas.model.Usuario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    EditText editSenha, editEmail;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CicloVidaActivity", "Passando pelo método onCreate...");
        setContentView(R.layout.activity_main);

        editSenha = findViewById(R.id.edtSenha);
        editEmail = findViewById(R.id.edtEmail);


    }


    public void irParaPrincipal(View v) {

        /*DatabaseHelper databaseHelper=new DatabaseHelper(this);

        //atribui o valor do campo da tela para a variavel do tipo String
        String strEmail = editEmail.getText().toString();
        String strSenha = editSenha.getText().toString();

        Usuario usuario=databaseHelper.validarUsuario(strEmail, strSenha);
        if (usuario != null){
            Toast.makeText(this, "Bem-vindo "+usuario.getLogin(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Principal.class));
            finish();
        } else {
            Toast.makeText(this, "Usuario e senha invalidos", Toast.LENGTH_SHORT).show();
        }*/


        String strLogin = editEmail.getText().toString();
        String strSenha = editSenha.getText().toString();

        Usuario usuario=new Usuario();
        usuario.setSenha(strSenha);
        usuario.setLogin(strLogin);

        new Login().execute(usuario);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("ClicloVidaActivity", "passando pelo método onStart...");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("ClicloVidaActivity", "passando pelo método onRestart...");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ClicloVidaActivity", "passando pelo método onPause...");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("ClicloVidaActivity", "passando pelo método onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ClicloVidaActivity", "passando pelo método onDestroy...");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ClicloVidaActivity", "passando pelo método onResume...");
    }










    private class Login extends AsyncTask<Usuario, Void, Usuario>{
        @Override
        protected void  onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
            progress.show();
            progress.setCancelable(false);
            progress.setContentView(R.layout.progres);



        }

        @Override //deve retornar um usuario para o metodo onPostExecute
                    //r3ecebe um objeto de usuario por parametro
        protected Usuario doInBackground(Usuario... usuarios){
            try {



                URL url = new URL(Request.URL_REQUEST+"/ferramentas/autenticacao");
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept","application/json");


                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);


                Gson gson=new Gson();
                DataOutputStream outputStream= new DataOutputStream(urlConnection.getOutputStream());
                String parametro = gson.toJson(usuarios[0]);
                Log.e("request", parametro);
                outputStream.writeBytes(parametro);
                outputStream.flush();
                outputStream.close();

                int codigoResposta = urlConnection.getResponseCode();

                Log.e("request", "erro tal:"+codigoResposta);
                if (codigoResposta==200){

                    String jsonResposta= "";
                    InputStreamReader inputStream=new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader reader=new BufferedReader(inputStream);

                    String line="";
                    while ((line=reader.readLine()) != null){
                        jsonResposta+=line;
                    }
                    Log.e("request", jsonResposta);
                    return gson.fromJson(jsonResposta,Usuario.class);

                } else {
                    return null;
                }




            }catch (Exception e){
                Log.e("request", "erro");

            }


            return null;
        }

        @Override /// usuario vem do parametro do metodo doIndBackground
        protected void onPostExecute(Usuario usuario) {
            progress.dismiss();



            AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
            if (usuario!= null){
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.salvarUsuario(usuario);

                alertDialog.setTitle("Bem vindo!");
                alertDialog.setMessage(usuario.getNome()+" é top e o Perico desumilde");
                alertDialog.create().show();

                Intent intent = new  Intent(MainActivity.this, Principal.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
                finish();

            } else {
                alertDialog.setTitle("Atenção!");
                alertDialog.setMessage("Falha ao logar");
                alertDialog.create().show();

            }

        }
    }



}
