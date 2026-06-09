package jogo;

public class TesteJogo {

    public static void main(String[] args) {

        Monte monte = new Monte();
        Jogador jogador = new Jogador("Gabriel");
        Tabuleiro tabuleiro = new Tabuleiro();

        
        for (int i = 0; i < 5; i++) {
            jogador.receberPeca(monte.comprarPeca());
        }

        jogador.mostrarMao();

       
        Peca primeira = jogador.jogarPeca(0);

        tabuleiro.adicionarPrimeiraPeca(primeira);

        System.out.println("\nTabuleiro:");

        tabuleiro.mostrarTabuleiro();

        System.out.println("\nMão após a jogada:");

        jogador.mostrarMao();
    }
}