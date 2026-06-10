package jogo;

public class TestePartida {

    public static void main(String[] args) {

        testar(Dificuldade.FACIL);
        testar(Dificuldade.MEDIO);
        testar(Dificuldade.DIFICIL);
    }

    private static void testar(Dificuldade dificuldade) {

        System.out.println("\n==============================");
        System.out.println("TESTANDO DIFICULDADE: " + dificuldade);
        System.out.println("==============================\n");

        Partida partida = new Partida("Bot", dificuldade);

        int jogadas = 0;
        int compras = 0;

        int seguranca = 0;

        // roda até o jogo acabar (não só fase)
        while (!partida.terminouJogo() && seguranca < 500) {

            boolean jogou = false;

            int tamanhoMao = partida.getJogador().getMao().size();

            for (int i = 0; i < tamanhoMao; i++) {

                // tenta direita primeiro (mais comum)
                if (partida.jogarPeca(i, true)) {
                    jogadas++;
                    jogou = true;
                    break;
                }

                // tenta esquerda se falhar
                if (partida.jogarPeca(i, false)) {
                    jogadas++;
                    jogou = true;
                    break;
                }
            }

            if (!jogou) {
                partida.comprarPeca();
                compras++;
            }

            seguranca++;
        }

        partida.finalizar();

        System.out.println("\nRESULTADO FINAL:");
        System.out.println("Jogadas: " + jogadas);
        System.out.println("Compras: " + compras);
        System.out.println("Pontuação: " + partida.getPontuacao().getPontuacaoTotal());

        System.out.println("Fim da dificuldade: " + dificuldade);
        System.out.println("==============================\n");
    }
}