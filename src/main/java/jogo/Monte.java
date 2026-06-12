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

    private void montarPorDificuldade(Dificuldade dificuldade) {

        adicionarQuimicosBasicos();
        adicionarPecasDuplas();
        adicionarCoringas();

        if (dificuldade == Dificuldade.FACIL) {
            return;
        }

        if (dificuldade == Dificuldade.MEDIO) {
            adicionarLixo(5);
            return;
        }

        if (dificuldade == Dificuldade.DIFICIL) {
            adicionarLixo(10);
        }
    }

    private void adicionarQuimicosBasicos() {

        // ÁCIDOS
        pecas.add(new Peca("Ácido", "HCl"));
        pecas.add(new Peca("Ácido", "H2SO4"));
        pecas.add(new Peca("Ácido", "HNO3"));
        pecas.add(new Peca("Ácido", "H3PO4"));
        pecas.add(new Peca("HCl", "H2SO4"));
        pecas.add(new Peca("H2SO4", "HNO3"));
        pecas.add(new Peca("HNO3", "H3PO4"));

        // BASES
        pecas.add(new Peca("Base", "NaOH"));
        pecas.add(new Peca("Base", "KOH"));
        pecas.add(new Peca("Base", "LiOH"));
        pecas.add(new Peca("Base", "Ca(OH)2"));
        pecas.add(new Peca("NaOH", "KOH"));
        pecas.add(new Peca("KOH", "LiOH"));
        pecas.add(new Peca("LiOH", "Ca(OH)2"));

        // ÓXIDOS
        pecas.add(new Peca("Óxido", "CO2"));
        pecas.add(new Peca("Óxido", "SO3"));
        pecas.add(new Peca("Óxido", "CaO"));
        pecas.add(new Peca("Óxido", "MgO"));
        pecas.add(new Peca("CO2", "SO3"));
        pecas.add(new Peca("SO3", "CaO"));
        pecas.add(new Peca("CaO", "MgO"));

        // SAIS
        pecas.add(new Peca("Sal", "NaCl"));
        pecas.add(new Peca("Sal", "KBr"));
        pecas.add(new Peca("Sal", "CaCO3"));
        pecas.add(new Peca("Sal", "Na2SO4"));
        pecas.add(new Peca("NaCl", "KBr"));
        pecas.add(new Peca("KBr", "CaCO3"));
        pecas.add(new Peca("CaCO3", "Na2SO4"));
    }

    private void adicionarPecasDuplas() {

        pecas.add(new Peca("Ácido", "Ácido"));
        pecas.add(new Peca("Base", "Base"));
        pecas.add(new Peca("Óxido", "Óxido"));
        pecas.add(new Peca("Sal", "Sal"));

        pecas.add(new Peca("HCl", "HCl"));
        pecas.add(new Peca("H2SO4", "H2SO4"));
        pecas.add(new Peca("HNO3", "HNO3"));
        pecas.add(new Peca("NaOH", "NaOH"));
        pecas.add(new Peca("KOH", "KOH"));
        pecas.add(new Peca("CO2", "CO2"));
        pecas.add(new Peca("CaO", "CaO"));
        pecas.add(new Peca("NaCl", "NaCl"));
    }

    private void adicionarCoringas() {

        pecas.add(new Peca("Coringa", "Ácido"));
        pecas.add(new Peca("Coringa", "Base"));
        pecas.add(new Peca("Coringa", "Óxido"));
        pecas.add(new Peca("Coringa", "Sal"));

        pecas.add(new Peca("Coringa", "HCl"));
        pecas.add(new Peca("Coringa", "NaOH"));
        pecas.add(new Peca("Coringa", "CaO"));
        pecas.add(new Peca("Coringa", "NaCl"));

        pecas.add(new Peca("Coringa", ""));
        pecas.add(new Peca("Coringa", ""));
        pecas.add(new Peca("Coringa", ""));
        pecas.add(new Peca("Coringa", ""));
}
    

   


    private void adicionarLixo(int quantidade) {

        for (int i = 0; i < quantidade; i++) {
            pecas.add(new Peca("X" + (i + 1), "X" + (i + 2)));
        }
    }

    public void embaralhar() {
        Collections.shuffle(pecas);
    }

    public Peca comprarPeca() {

        if (pecas.isEmpty()) return null;

        return pecas.remove(0);
    }

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