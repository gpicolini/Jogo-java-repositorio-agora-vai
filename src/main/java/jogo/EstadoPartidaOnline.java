package jogo;

import java.util.ArrayList;

public class EstadoPartidaOnline {

    private String codigoSala;

    private ArrayList<Peca> mesa;
    private ArrayList<Peca> minhaMao;
    private ArrayList<Peca> monte;

    private int jogadorAtual;
    private int minhaOrdem;
    private int qtdJogadores;

    public EstadoPartidaOnline(
            String codigoSala,
            int minhaOrdem,
            int qtdJogadores
    ) {
        this.codigoSala = codigoSala;
        this.minhaOrdem = minhaOrdem;
        this.qtdJogadores = qtdJogadores;

        this.mesa = new ArrayList<>();
        this.minhaMao = new ArrayList<>();
        this.monte = new ArrayList<>();

        this.jogadorAtual = 0;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public ArrayList<Peca> getMesa() {
        return mesa;
    }

    public ArrayList<Peca> getMinhaMao() {
        return minhaMao;
    }

    public ArrayList<Peca> getMonte() {
        return monte;
    }

    public int getJogadorAtual() {
        return jogadorAtual;
    }

    public void setJogadorAtual(int jogadorAtual) {
        this.jogadorAtual = jogadorAtual;
    }

    public int getMinhaOrdem() {
        return minhaOrdem;
    }

    public int getQtdJogadores() {
        return qtdJogadores;
    }

    public boolean ehMinhaVez() {
        return jogadorAtual == minhaOrdem;
    }

    public void carregarMao(ArrayList<Peca> pecas) {
        minhaMao.clear();
        minhaMao.addAll(pecas);
    }

    public void carregarMesa(ArrayList<Peca> pecas) {
        mesa.clear();
        mesa.addAll(pecas);
    }

    public void carregarMonte(ArrayList<Peca> pecas) {
        monte.clear();
        monte.addAll(pecas);
    }

    public Peca comprarDoMonteLocal() {

        if (monte.isEmpty()) {
            return null;
        }

        return monte.remove(0);
    }

    public void adicionarNaMao(Peca p) {

        if (p != null) {
            minhaMao.add(p);
        }
    }

    public void removerDaMao(Peca p) {

        minhaMao.remove(p);
    }

    public void jogarNaMesa(Peca p, boolean direita) {

        if (p == null) {
            return;
        }

        if (direita) {
            mesa.add(p);
        } else {
            mesa.add(0, p);
        }

        minhaMao.remove(p);
    }

    public void passarTurnoLocal() {

        jogadorAtual++;

        if (jogadorAtual >= qtdJogadores) {
            jogadorAtual = 0;
        }
    }
}
