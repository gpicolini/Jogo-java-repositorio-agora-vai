package jogo;

import java.util.ArrayList;

public class Jogador {

    private String nome;
    private ArrayList<Peca> mao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void receberPeca(Peca peca) {

        if (peca != null) {
            mao.add(peca);
        }
    }

    public Peca jogarPeca(int indice) {

        if (indice < 0 || indice >= mao.size()) {
            return null;
        }

        return mao.remove(indice);
    }

    public void mostrarMao() {

        System.out.println("\nMão de " + nome + ":");

        for (int i = 0; i < mao.size(); i++) {
            System.out.println(i + " - " + mao.get(i));
        }
    }

    public int quantidadePecas() {
        return mao.size();
    }

    public ArrayList<Peca> getMao() {
        return mao;
    }
}