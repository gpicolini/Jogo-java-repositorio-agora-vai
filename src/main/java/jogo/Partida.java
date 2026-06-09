package jogo;

public class Partida {

    private Jogador jogador;
    private Monte monte;
    private Tabuleiro tabuleiro;
    private SistemaPontuacao pontuacao;

    public Partida(String nomeJogador) {

        jogador = new Jogador(nomeJogador);

        monte = new Monte();

        tabuleiro = new Tabuleiro();

        pontuacao = new SistemaPontuacao();

        distribuirPecas();
    }

    private void distribuirPecas() {

        for(int i = 0; i < 5; i++) {
            jogador.receberPeca(monte.comprarPeca());
        }
    }

    public boolean jogarPeca(int indice, boolean direita) {

        if(indice < 0 || indice >= jogador.getMao().size())
            return false;

        Peca peca = jogador.getMao().get(indice);

        boolean sucesso;

        if(tabuleiro.vazio()) {

            jogador.jogarPeca(indice);

            tabuleiro.adicionarPrimeiraPeca(peca);

            pontuacao.adicionarAcerto();

            return true;
        }

        if(direita) {
            sucesso = tabuleiro.adicionarDireita(peca);
        }
        else {
            sucesso = tabuleiro.adicionarEsquerda(peca);
        }

        if(sucesso) {

            jogador.jogarPeca(indice);

            pontuacao.adicionarAcerto();
        }
        else {

            pontuacao.adicionarErro();
        }

        return sucesso;
    }

    public void comprarPeca() {

        if(!monte.vazio()) {
            jogador.receberPeca(monte.comprarPeca());
        }
    }

    public boolean terminou() {
        return jogador.quantidadePecas() == 0;
    }

    public void finalizar() {
        pontuacao.finalizarPartida();
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public SistemaPontuacao getPontuacao() {
        return pontuacao;
    }
}