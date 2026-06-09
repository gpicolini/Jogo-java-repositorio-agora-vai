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

        // ===== BASE DO JOGO =====
        adicionarQuimicosBasicos();

        if (dificuldade == Dificuldade.FACIL) {
            // só peças boas
            return;
        }

        if (dificuldade == Dificuldade.MEDIO) {
            adicionarLixo(3); // peças inúteis
            return;
        }

        if (dificuldade == Dificuldade.DIFICIL) {
            adicionarLixo(6); // muito mais ruído
        }
    }

    // ===================== PEÇAS VÁLIDAS =====================
    private void adicionarQuimicosBasicos() {

    pecas.add(new Peca("HCl", "Ácido", TipoQuimico.ACIDO));
    pecas.add(new Peca("H2SO4", "Ácido", TipoQuimico.ACIDO));

    pecas.add(new Peca("NaOH", "Base", TipoQuimico.BASE));
    pecas.add(new Peca("KOH", "Base", TipoQuimico.BASE));

    pecas.add(new Peca("CO2", "Óxido", TipoQuimico.OXIDO));
    pecas.add(new Peca("SO3", "Óxido", TipoQuimico.OXIDO));

    pecas.add(new Peca("NaCl", "Sal", TipoQuimico.SAL));
    pecas.add(new Peca("KBr", "Sal", TipoQuimico.SAL));
    }

    // ===================== LIXO CONTROLADO =====================
    private void adicionarLixo(int quantidade) {

        for (int i = 0; i < quantidade; i++) {
            pecas.add(new Peca("X" + i, "Inútil", TipoQuimico.SAL));
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