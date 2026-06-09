package jogo;

import java.util.ArrayList;

public class Tabuleiro {

    private ArrayList<Peca> pecas;

    public Tabuleiro() {
        pecas = new ArrayList<>();
    }

    public boolean vazio() {
        return pecas.isEmpty();
    }

    public boolean adicionarPrimeiraPeca(Peca peca) {

        if (!pecas.isEmpty()) {
            return false;
        }

        pecas.add(peca);
        return true;
    }

    public boolean adicionarEsquerda(Peca peca) {

        if (pecas.isEmpty()) {
            pecas.add(peca);
            return true;
        }

        Peca primeira = pecas.get(0);

        if (peca.conecta(primeira)) {
            pecas.add(0, peca);
            return true;
        }

        return false;
    }

    public boolean adicionarDireita(Peca peca) {

        if (pecas.isEmpty()) {
            pecas.add(peca);
            return true;
        }

        Peca ultima = pecas.get(pecas.size() - 1);

        if (peca.conecta(ultima)) {
            pecas.add(peca);
            return true;
        }

        return false;
    }

    public void mostrarTabuleiro() {

        for (Peca p : pecas) {
            System.out.print(p + " ");
        }

        System.out.println();
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }
}