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

        // loop até acabar
        while (!partida.terminou()) {

            boolean jogou = false;

            // tenta jogar todas peças da mão
            for (int i = 0; i < partida.getJogador().getMao().size(); i++) {

                if (partida.jogarPeca(i, true) || partida.jogarPeca(i, false)) {
                    jogou = true;
                    jogadas++;
                    break;
                }
            }

            // se não conseguiu jogar, compra
            if (!jogou) {
                partida.comprarPeca();
                compras++;
            }

            // segurança pra evitar loop infinito
            if (compras > 50) {
                System.out.println("⚠️ travou em compras, abortando...");
                break;
            }
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