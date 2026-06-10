package jogo;

import java.util.ArrayList;

public class Partida {

    private Monte monte;
    private Tabuleiro tabuleiro;
    private SistemaPontuacao pontuacao;

    private ArrayList<Jogador> jogadores;
    private ArrayList<Integer> pontos;

    private int indiceJogadorAtual = 0;

    private String vencedorFase = "";

    private Dificuldade dificuldade;

    private int fase = 1;
    private int comprasRestantes;

    // Compatibilidade: se não passar quantidade, começa com 2 jogadores
    public Partida(String nomeJogador, Dificuldade dificuldade) {
        this(nomeJogador, dificuldade, 2);
    }

    public Partida(String nomeJogador, Dificuldade dificuldade, int quantidadeJogadores) {

        if (quantidadeJogadores < 2) quantidadeJogadores = 2;
        if (quantidadeJogadores > 4) quantidadeJogadores = 4;

        this.dificuldade = dificuldade;
        this.pontuacao = new SistemaPontuacao();

        jogadores = new ArrayList<>();
        pontos = new ArrayList<>();

        jogadores.add(new Jogador(nomeJogador));

        for (int i = 2; i <= quantidadeJogadores; i++) {
            jogadores.add(new Jogador("Jogador " + i));
        }

        for (int i = 0; i < jogadores.size(); i++) {
            pontos.add(0);
        }

        iniciarFase();
    }

    private void iniciarFase() {

        monte = new Monte(dificuldade);
        tabuleiro = new Tabuleiro();

        for (Jogador j : jogadores) {
            j.getMao().clear();
        }

        if (fase == 1) comprasRestantes = 5;
        else if (fase == 2) comprasRestantes = 3;
        else comprasRestantes = 2;

        distribuir();
    }

    private void distribuir() {

        int qtd = fase == 1 ? 3 : fase == 2 ? 5 : 7;

        for (Jogador jogador : jogadores) {
            for (int i = 0; i < qtd; i++) {
                Peca p = monte.comprarPeca();

                if (p != null) {
                    jogador.receberPeca(p);
                } else {
                    jogador.receberPeca(new Peca("X" + i, "X" + (i + 1)));
                }
            }
        }
    }

    private void adicionarPontosJogadorAtual(int valor) {
        int atual = pontos.get(indiceJogadorAtual);
        pontos.set(indiceJogadorAtual, atual + valor);
    }

    public boolean jogarPeca(int indice, boolean direita) {

        Jogador jogadorAtual = getJogadorAtual();

        if (indice < 0 || indice >= jogadorAtual.getMao().size()) {
            return false;
        }

        Peca peca = jogadorAtual.getMao().get(indice);
        boolean sucesso;

        if (tabuleiro.vazio()) {

            tabuleiro.adicionarPrimeiraPeca(peca);
            jogadorAtual.jogarPeca(indice);

            pontuacao.adicionarAcerto();
            adicionarPontosJogadorAtual(10);

            trocarTurno();
            return true;
        }

        sucesso = direita
                ? tabuleiro.adicionarDireita(peca)
                : tabuleiro.adicionarEsquerda(peca);

        if (sucesso) {

            jogadorAtual.jogarPeca(indice);

            pontuacao.adicionarAcerto();
            adicionarPontosJogadorAtual(10);

            trocarTurno();

        } else {

            pontuacao.adicionarErro();
            adicionarPontosJogadorAtual(-2);
        }

        return sucesso;
    }

    public boolean comprarPeca() {

        if (comprasRestantes <= 0) {
            return false;
        }

        Peca p = monte.comprarPeca();

        if (p != null) {
            getJogadorAtual().receberPeca(p);
            comprasRestantes--;
            trocarTurno();
            return true;
        }

        return false;
    }

    public void trocarTurno() {
        indiceJogadorAtual++;

        if (indiceJogadorAtual >= jogadores.size()) {
            indiceJogadorAtual = 0;
        }
    }

    public boolean terminouFase() {

        for (Jogador j : jogadores) {
            if (j.quantidadePecas() == 0) {
                return true;
            }
        }

        return false;
    }

    public boolean terminouJogo() {
        return fase >= 3 && terminouFase();
    }

    public boolean atualizarFaseSeNecessario() {

        if (!terminouFase()) return false;
        if (fase >= 3) return false;

        vencedorFase = getVencedorFase();

        fase++;
        indiceJogadorAtual = 0;

        iniciarFase();

        return true;
    }

    public boolean existeJogadaPossivel() {

        if (tabuleiro.vazio()) return true;

        Peca primeira = tabuleiro.getPecas().get(0);
        Peca ultima = tabuleiro.getPecas().get(tabuleiro.getPecas().size() - 1);

        for (Peca p : getJogadorAtual().getMao()) {
            if (p.conecta(primeira) || p.conecta(ultima)) {
                return true;
            }
        }

        return false;
    }

    public void finalizar() {
        pontuacao.finalizarPartida();
    }

    public String getVencedorFase() {

        for (Jogador j : jogadores) {
            if (j.quantidadePecas() == 0) {
                return j.getNome();
            }
        }

        return "";
    }

    public String getUltimoVencedorFase() {
        return vencedorFase;
    }

    public String getNomeVencedor() {

        int maior = pontos.get(0);
        int indiceVencedor = 0;
        boolean empate = false;

        for (int i = 1; i < pontos.size(); i++) {
            if (pontos.get(i) > maior) {
                maior = pontos.get(i);
                indiceVencedor = i;
                empate = false;
            } else if (pontos.get(i) == maior) {
                empate = true;
            }
        }

        if (empate) return "Empate";

        return jogadores.get(indiceVencedor).getNome();
    }

    public int getComprasRestantes() {
        return comprasRestantes;
    }

    public Jogador getJogador() {
        return getJogadorAtual();
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(indiceJogadorAtual);
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public int getPontosJogador(int indice) {
        return pontos.get(indice);
    }

    public Jogador getJogador1() { return jogadores.get(0); }
    public Jogador getJogador2() { return jogadores.get(1); }

    public int getPontosJogador1() { return pontos.get(0); }
    public int getPontosJogador2() { return pontos.get(1); }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public SistemaPontuacao getPontuacao() {
        return pontuacao;
    }

    public int getFase() {
        return fase;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }
}