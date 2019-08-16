package com.edu.senac.cestadeferramentas.helper;

public class JogoVelha {
    public static String obtemVencedor(String[] tabuleiro){
        if ((tabuleiro == null) || (tabuleiro.length !=9)){
            return null;
        }
        Integer[][] padroesVitoria = {
                {0, 1, 2},
                {0, 4, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {2, 4, 6},
                {3, 4, 5},
                {6, 7, 8},

        };
        for (Integer[] padraoVitoria: padroesVitoria){
            boolean haVencedor = tabuleiro[padraoVitoria[0]] != null
                    && tabuleiro[padraoVitoria[0]].equals(tabuleiro[padraoVitoria[1]])
                    && tabuleiro[padraoVitoria[0]].equals(tabuleiro[padraoVitoria[2]]);

            if (haVencedor){
                String vencedor = tabuleiro[padraoVitoria[0]];
                return vencedor;
            }
        }
        return null;
    }
}
