package jogo;

public class TestePartida {

    public static void main(String[] args) {

        Partida partida = new Partida("Gabriel");

        System.out.println("Mão inicial:");

        partida.getJogador().mostrarMao();

        boolean resultado =
                partida.jogarPeca(0, true);

        System.out.println("\nJogada: " + resultado);

        System.out.println("\nTabuleiro:");

        partida.getTabuleiro().mostrarTabuleiro();

        System.out.println("\nPontuação:");

        System.out.println(
                partida.getPontuacao()
                        .getPontuacaoTotal()
        );
    }
}