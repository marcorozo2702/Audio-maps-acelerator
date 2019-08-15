package com.edu.senac.cestadeferramentas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.edu.senac.cestadeferramentas.R;

public class JogoDaVelhaActivity extends AppCompatActivity {
    ImageView a1, a2, a3, b1, b2, b3, c1, c2, c3;
    boolean X = true;

    int[][] matriz = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
    };

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
        if (X == true) {
            imagem.setImageResource(R.drawable.x); //muda a imagem
            X = !X; //modifica a variavel X (boolean) para o contrario (diferente do q era)
        } else {
            imagem.setImageResource(R.drawable.bola);
            X = !X;
        }



        imagem.setClickable(false); //desabilita o click da imagem se ela ja ter sido mudada
    }

}
