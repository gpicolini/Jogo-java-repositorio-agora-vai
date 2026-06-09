package jogo;

import java.util.ArrayList;
import java.util.Collections;

public class Monte {

    private ArrayList<Peca> pecas;

    public Monte() {

        pecas = new ArrayList<>();

        
        pecas.add(new Peca("HCl", "Ácido"));
        pecas.add(new Peca("H2SO4", "Ácido"));
        pecas.add(new Peca("HNO3", "Ácido"));
        pecas.add(new Peca("H3PO4", "Ácido"));

        
        pecas.add(new Peca("NaOH", "Base"));
        pecas.add(new Peca("KOH", "Base"));
        pecas.add(new Peca("Ca(OH)2", "Base"));
        pecas.add(new Peca("Mg(OH)2", "Base"));

        
        pecas.add(new Peca("CO2", "Óxido"));
        pecas.add(new Peca("CaO", "Óxido"));
        pecas.add(new Peca("SO3", "Óxido"));
        pecas.add(new Peca("MgO", "Óxido"));

      
        pecas.add(new Peca("NaCl", "Sal"));
        pecas.add(new Peca("KBr", "Sal"));
        pecas.add(new Peca("CaCO3", "Sal"));
        pecas.add(new Peca("Na2SO4", "Sal"));

        embaralhar();
    }

    public void embaralhar() {
        Collections.shuffle(pecas);
    }

    public Peca comprarPeca() {

        if (pecas.isEmpty()) {
            return null;
        }

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