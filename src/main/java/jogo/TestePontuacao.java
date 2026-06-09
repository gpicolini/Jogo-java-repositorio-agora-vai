package jogo;

public class TestePontuacao {

    public static void main(String[] args)
            throws InterruptedException {

        SistemaPontuacao p = new SistemaPontuacao();

        p.adicionarAcerto();
        p.adicionarAcerto();
        p.adicionarAcerto();

        p.adicionarErro();

        Thread.sleep(3000);

        p.finalizarPartida();

        System.out.println("Acertos: " + p.getAcertos());
        System.out.println("Erros: " + p.getErros());
        System.out.println("Tempo: " + p.getTempoSegundos());
        System.out.println("Bonus Tempo: " + p.getBonusTempo());
        System.out.println("Pontuação: " + p.getPontuacaoTotal());
    }
}
