package jogo;

public class TesteJogador {

    public static void main(String[] args) {

        // ===================== CONFIG =====================
        Dificuldade dificuldade = Dificuldade.FACIL;

        Monte monte = new Monte(dificuldade);
        Jogador jogador = new Jogador("Gabriel");

        // ===================== COMPRA PEÇAS =====================
        for (int i = 0; i < 5; i++) {
            Peca p = monte.comprarPeca();

            if (p != null) {
                jogador.receberPeca(p);
            }
        }

        // ===================== MÃO INICIAL =====================
        System.out.println("=== MÃO INICIAL ===");
        jogador.mostrarMao();

        // ===================== JOGADA =====================
        jogador.jogarPeca(0);

        System.out.println("\n=== DEPOIS DA JOGADA ===");
        jogador.mostrarMao();
    }
}