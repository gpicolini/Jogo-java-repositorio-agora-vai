package jogo;

public class Partida {

    private Jogador jogador;
    private Monte monte;
    private Tabuleiro tabuleiro;
    private SistemaPontuacao pontuacao;

    private Dificuldade dificuldade;

    private int fase = 1;
    private int comprasRestantes;

    // ===================== CONSTRUTOR =====================
    public Partida(String nomeJogador, Dificuldade dificuldade) {

        this.jogador = new Jogador(nomeJogador);
        this.dificuldade = dificuldade;

        this.pontuacao = new SistemaPontuacao();

        iniciarFase();
    }

    // ===================== INICIAR FASE =====================
    private void iniciarFase() {

        monte = new Monte(dificuldade);
        tabuleiro = new Tabuleiro();
        jogador.getMao().clear();

        if (fase == 1) {
            comprasRestantes = 5;
            distribuirFacil();
        }

        if (fase == 2) {
            comprasRestantes = 3;
            distribuirMedio();
        }

        if (fase == 3) {
            comprasRestantes = 2;
            distribuirDificil();
        }
    }

    // ===================== FACIL =====================
    private void distribuirFacil() {

        for (int i = 0; i < 3; i++) {
            Peca p = monte.comprarPeca();
            if (p != null) jogador.receberPeca(p);
        }
    }

    // ===================== MEDIO =====================
    private void distribuirMedio() {

        for (int i = 0; i < 5; i++) {

            if (i < 3) {
                Peca p = monte.comprarPeca();
                if (p != null) jogador.receberPeca(p);
            } else {
                jogador.receberPeca(new Peca("X", "Inútil", TipoQuimico.SAL));
            }
        }
    }

    // ===================== DIFICIL =====================
    private void distribuirDificil() {

        for (int i = 0; i < 7; i++) {

            if (i < 3) {
                Peca p = monte.comprarPeca();
                if (p != null) jogador.receberPeca(p);
            } else {
                jogador.receberPeca(new Peca("X", "Inútil", TipoQuimico.OXIDO));
            }
        }
    }

    // ===================== JOGAR PEÇA =====================
    public boolean jogarPeca(int indice, boolean direita) {

        if (indice < 0 || indice >= jogador.getMao().size())
            return false;

        Peca peca = jogador.getMao().get(indice);

        boolean sucesso;

        if (tabuleiro.vazio()) {

            tabuleiro.adicionarPrimeiraPeca(peca);
            jogador.jogarPeca(indice);

            pontuacao.adicionarAcerto();
            return true;
        }

        sucesso = direita
                ? tabuleiro.adicionarDireita(peca)
                : tabuleiro.adicionarEsquerda(peca);

        if (sucesso) {
            jogador.jogarPeca(indice);
            pontuacao.adicionarAcerto();
        } else {
            pontuacao.adicionarErro();
        }

        return sucesso;
    }

    // ===================== COMPRA =====================
    public void comprarPeca() {

        if (comprasRestantes <= 0) return;

        Peca p = monte.comprarPeca();

        if (p != null) {
            jogador.receberPeca(p);
            comprasRestantes--;
        }
    }

    // ===================== FIM =====================
    public boolean terminou() {

        if (jogador.quantidadePecas() == 0)
            return true;

        if (monte.vazio() && !existeJogadaPossivel())
            return true;

        return false;
    }

    // ===================== PRÓXIMA FASE =====================
    public boolean proximaFase() {

        if (fase >= 3) return false;

        fase++;
        iniciarFase();
        return true;
    }

    // ===================== JOGADA POSSÍVEL =====================
    public boolean existeJogadaPossivel() {

        if (tabuleiro.vazio()) return true;

        Peca primeira = tabuleiro.getPecas().get(0);
        Peca ultima = tabuleiro.getPecas().get(tabuleiro.getPecas().size() - 1);

        for (Peca p : jogador.getMao()) {
            if (p.conecta(primeira) || p.conecta(ultima)) {
                return true;
            }
        }

        return false;
    }

    // ===================== FINALIZAR =====================
    public void finalizar() {
        pontuacao.finalizarPartida();
    }

    // ===================== GETTERS =====================
    public Jogador getJogador() { return jogador; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public SistemaPontuacao getPontuacao() { return pontuacao; }
    public int getFase() { return fase; }
    public Dificuldade getDificuldade() { return dificuldade; }
}