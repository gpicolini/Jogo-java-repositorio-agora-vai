package jogo;

public class TesteJogo {

    public static void main(String[] args) {

        // ===================== CONFIG =====================
        Dificuldade dificuldade = Dificuldade.FACIL;

        Monte monte = new Monte(dificuldade);
        Jogador jogador = new Jogador("Gabriel");
        Tabuleiro tabuleiro = new Tabuleiro();

        // ===================== DISTRIBUIÇÃO =====================
        for (int i = 0; i < 5; i++) {
            Peca p = monte.comprarPeca();
            if (p != null) {
                jogador.receberPeca(p);
            }
        }

        System.out.println("=== MÃO INICIAL ===");
        jogador.mostrarMao();

        // ===================== PRIMEIRA JOGADA =====================
        Peca primeira = jogador.getMao().get(0);
        jogador.jogarPeca(0);

        tabuleiro.adicionarPrimeiraPeca(primeira);

        // ===================== ESTADO DO TABULEIRO =====================
        System.out.println("\n=== TABULEIRO ===");
        tabuleiro.mostrarTabuleiro();

        // ===================== ESTADO FINAL DA MÃO =====================
        System.out.println("\n=== MÃO APÓS JOGADA ===");
        jogador.mostrarMao();
    }
}