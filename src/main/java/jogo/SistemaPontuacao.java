package jogo;

public class SistemaPontuacao {

    private int acertos;
    private int erros;

    private long inicio;
    private long fim;

    public SistemaPontuacao() {
        acertos = 0;
        erros = 0;
        inicio = System.currentTimeMillis();
    }

    public void adicionarAcerto() {
        acertos++;
    }

    public void adicionarErro() {
        erros++;
    }

    public void finalizarPartida() {
        fim = System.currentTimeMillis();
    }

    public int getAcertos() {
        return acertos;
    }

    public int getErros() {
        return erros;
    }

    public int getTempoSegundos() {
        return (int)((fim - inicio) / 1000);
    }

    public int getBonusTempo() {

        int tempo = getTempoSegundos();

        if (tempo <= 120)
            return 25;

        if (tempo <= 240)
            return 10;

        return 0;
    }

    public int getBonusPerfeito() {

        if (erros == 0)
            return 20;

        return 0;
    }

    public int getPontuacaoTotal() {

        return (acertos * 10)
                - (erros * 3)
                + getBonusTempo()
                + getBonusPerfeito();
    }
}
