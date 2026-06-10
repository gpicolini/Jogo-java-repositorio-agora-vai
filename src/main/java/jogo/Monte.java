package jogo;

import java.util.ArrayList;
import java.util.Collections;

public class Monte {

    private ArrayList<Peca> pecas;

    public Monte(Dificuldade dificuldade) {

        pecas = new ArrayList<>();

        montarPorDificuldade(dificuldade);
        embaralhar();
    }

    // ===================== CONSTRUÇÃO POR NÍVEL =====================
    private void montarPorDificuldade(Dificuldade dificuldade) {

        adicionarQuimicosBasicos();

        if (dificuldade == Dificuldade.FACIL) {
            return;
        }

        if (dificuldade == Dificuldade.MEDIO) {
            adicionarLixo(3);
            return;
        }

        if (dificuldade == Dificuldade.DIFICIL) {
            adicionarLixo(6);
        }
    }

    // ===================== PEÇAS VÁLIDAS =====================
    private void adicionarQuimicosBasicos() {

        // ÁCIDOS
        pecas.add(new Peca("HCl", "H2SO4"));
        pecas.add(new Peca("H2SO4", "HNO3"));
        pecas.add(new Peca("HNO3", "HCl"));

        // BASES
        pecas.add(new Peca("NaOH", "KOH"));
        pecas.add(new Peca("KOH", "LiOH"));
        pecas.add(new Peca("LiOH", "NaOH"));

        // ÓXIDOS
        pecas.add(new Peca("CO2", "SO3"));
        pecas.add(new Peca("SO3", "CaO"));
        pecas.add(new Peca("CaO", "CO2"));

        // SAIS
        pecas.add(new Peca("NaCl", "KBr"));
        pecas.add(new Peca("KBr", "CaCO3"));
        pecas.add(new Peca("CaCO3", "NaCl"));
    }

    // ===================== LIXO CONTROLADO =====================
    private void adicionarLixo(int quantidade) {

        for (int i = 0; i < quantidade; i++) {
            pecas.add(new Peca("X" + (i + 1), "X" + (i + 2)));
        }
    }

    // ===================== EMBARALHAR =====================
    public void embaralhar() {
        Collections.shuffle(pecas);
    }

    // ===================== COMPRA =====================
    public Peca comprarPeca() {

        if (pecas.isEmpty()) return null;

        return pecas.remove(0);
    }

    // ===================== ESTADO =====================
    public boolean vazio() {
        return pecas.isEmpty();
    }

    public int quantidadePecas() {
        return pecas.size();
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }
}