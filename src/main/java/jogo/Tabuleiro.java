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

    // ===================== PRIMEIRA PEÇA =====================
    public boolean adicionarPrimeiraPeca(Peca peca) {

        if (peca == null || !pecas.isEmpty()) {
            return false;
        }

        pecas.add(peca);
        return true;
    }

    // ===================== LADO ESQUERDO =====================
    public boolean adicionarEsquerda(Peca peca) {

        if (peca == null) return false;

        if (pecas.isEmpty()) {
            pecas.add(peca);
            return true;
        }

        Peca primeira = pecas.get(0);

        if (conecta(peca, primeira)) {
            pecas.add(0, peca);
            return true;
        }

        return false;
    }

    // ===================== LADO DIREITO =====================
    public boolean adicionarDireita(Peca peca) {

        if (peca == null) return false;

        if (pecas.isEmpty()) {
            pecas.add(peca);
            return true;
        }

        Peca ultima = pecas.get(pecas.size() - 1);

        if (conecta(peca, ultima)) {
            pecas.add(peca);
            return true;
        }

        return false;
    }

    // ===================== REGRA CENTRAL =====================
    private boolean conecta(Peca a, Peca b) {

        if (a == null || b == null) return false;

        // regra atual: mesmo tipo químico (Ácido, Base, Sal, Óxido)
        return a.getFuncao().equalsIgnoreCase(b.getFuncao());
    }

    // ===================== DEBUG =====================
    public void mostrarTabuleiro() {

        if (pecas.isEmpty()) {
            System.out.println("[TABULEIRO VAZIO]");
            return;
        }

        for (Peca p : pecas) {
            System.out.print(p + " ");
        }

        System.out.println();
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }
}