package com.edu.senac.cestadeferramentas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.senac.cestadeferramentas.R;
import com.edu.senac.cestadeferramentas.helper.JogoVelha;

public class JogoDaVelhaActivity extends AppCompatActivity {
    ImageView a1, a2, a3, b1, b2, b3, c1, c2, c3;


    boolean X = true;
    String[] tabuleiro = new String[9]; //criando o vetors das 9 posições

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_da_velha);

        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);


    }

    public void startgame(View v) {
        ImageView imagem = findViewById(v.getId()); //seleciona a imagem pelo id

        imagem.setImageResource(
                X?R.drawable.x:R.drawable.bola);

        tabuleiro[getPosition(imagem.getId())] = X ? "X" : "O";

        X = !X;

        String vencedor = JogoVelha.obtemVencedor(tabuleiro);
        if (vencedor!=null){
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle(null);
            dialog.setContentView(R.layout.resultado_jogo);

            TextView descricao=dialog.findViewById(R.id.descricao);
            descricao.setText("Parabéns! "+vencedor +" venceu!");

            dialog.show();
        }

    }

    private int getPosition(int id){
        switch (id){
            case R.id.a1:
                return 0;
            case R.id.a2:
                return 1;
            case R.id.a3:
                return 2;
            case R.id.b1:
                return 3;
            case R.id.b2:
                return 4;
            case R.id.b3:
                return 5;
            case R.id.c1:
                return 6;
            case R.id.c2:
                return 7;
            case R.id.c3:
                return 8;
        }
        return 0;
    }

}
