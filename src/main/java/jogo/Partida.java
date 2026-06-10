package jogo;

public class Partida {

    private Jogador jogador;
    private Monte monte;
    private Tabuleiro tabuleiro;
    private SistemaPontuacao pontuacao;

    private Dificuldade dificuldade;

    private int fase = 1;
    private int comprasRestantes;

    public Partida(String nomeJogador, Dificuldade dificuldade) {

        this.jogador = new Jogador(nomeJogador);
        this.dificuldade = dificuldade;

        this.pontuacao = new SistemaPontuacao();

        iniciarFase();
    }

    private void iniciarFase() {

        monte = new Monte(dificuldade);
        tabuleiro = new Tabuleiro();
        jogador.getMao().clear();

        if (fase == 1) comprasRestantes = 5;
        if (fase == 2) comprasRestantes = 3;
        if (fase == 3) comprasRestantes = 2;

        distribuir();
    }

    private void distribuir() {

        if (fase == 1) {
            distribuirFacil();
        }
        else if (fase == 2) {
            distribuirMedio();
        }
        else {
            distribuirDificil();
        }
    }

    private void distribuirFacil() {

        jogador.receberPeca(
                new Peca("HCl", "Ácido", TipoQuimico.ACIDO));

        jogador.receberPeca(
                new Peca("H2SO4", "Ácido", TipoQuimico.ACIDO));

        jogador.receberPeca(
                new Peca("HNO3", "Ácido", TipoQuimico.ACIDO));
    }

    private void distribuirMedio() {

        // corretas
        jogador.receberPeca(
                new Peca("NaOH", "Base", TipoQuimico.BASE));

        jogador.receberPeca(
                new Peca("KOH", "Base", TipoQuimico.BASE));

        jogador.receberPeca(
                new Peca("LiOH", "Base", TipoQuimico.BASE));

        // armadilhas
        jogador.receberPeca(
                new Peca("CO2", "Óxido", TipoQuimico.OXIDO));

        jogador.receberPeca(
                new Peca("NaCl", "Sal", TipoQuimico.SAL));
    }

    private void distribuirDificil() {

        // corretas
        jogador.receberPeca(
                new Peca("CO2", "Óxido", TipoQuimico.OXIDO));

        jogador.receberPeca(
                new Peca("SO3", "Óxido", TipoQuimico.OXIDO));

        jogador.receberPeca(
                new Peca("CaO", "Óxido", TipoQuimico.OXIDO));

        // armadilhas
        jogador.receberPeca(
                new Peca("HCl", "Ácido", TipoQuimico.ACIDO));

        jogador.receberPeca(
                new Peca("NaOH", "Base", TipoQuimico.BASE));

        jogador.receberPeca(
                new Peca("NaCl", "Sal", TipoQuimico.SAL));

        jogador.receberPeca(
                new Peca("X1", "Inútil", TipoQuimico.SAL));
    }

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

    public void comprarPeca() {

        if (comprasRestantes <= 0)
            return;

        Peca p = monte.comprarPeca();

        if (p != null) {
            jogador.receberPeca(p);
            comprasRestantes--;
        }
    }

    // ===================== FASE TERMINA =====================
    public boolean terminouFase() {
        return jogador.quantidadePecas() == 0;
    }

    // ===================== JOGO TERMINA =====================
    public boolean terminouJogo() {
        return fase >= 3 && jogador.quantidadePecas() == 0;
    }

    // ===================== PROGRESSÃO =====================
    public boolean atualizarFaseSeNecessario() {

        if (!terminouFase()) {
            return false;
        }

        if (fase >= 3) {
            return false;
        }

        fase++;

        iniciarFase();

        return true;
    }

    // ===================== JOGADA POSSÍVEL =====================
    public boolean existeJogadaPossivel() {

        if (tabuleiro.vazio())
            return true;

        Peca primeira = tabuleiro.getPecas().get(0);
        Peca ultima = tabuleiro.getPecas().get(
                tabuleiro.getPecas().size() - 1);

        for (Peca p : jogador.getMao()) {

            if (p.conecta(primeira)
                    || p.conecta(ultima)) {

                return true;
            }
        }

        return false;
    }

    public void finalizar() {
        pontuacao.finalizarPartida();
    }

    // GETTERS
    public Jogador getJogador() {
        return jogador;
    }

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