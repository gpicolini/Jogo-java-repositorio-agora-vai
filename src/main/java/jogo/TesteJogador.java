package jogo;

public class TesteJogador {

    public static void main(String[] args) {

        Monte monte = new Monte();

        Jogador jogador = new Jogador("Gabriel");

        for (int i = 0; i < 5; i++) {
            jogador.receberPeca(monte.comprarPeca());
        }

        jogador.mostrarMao();

        jogador.jogarPeca(0);

        System.out.println("\nDepois da jogada:");

        jogador.mostrarMao();
    }
}