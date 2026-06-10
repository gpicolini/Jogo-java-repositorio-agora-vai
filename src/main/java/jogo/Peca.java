package jogo;

public class Peca {

    private String ladoEsquerdo;
    private String ladoDireito;

    public Peca(String ladoEsquerdo, String ladoDireito) {
        this.ladoEsquerdo = ladoEsquerdo;
        this.ladoDireito = ladoDireito;
    }

    public String getLadoEsquerdo() {
        return ladoEsquerdo;
    }

    public String getLadoDireito() {
        return ladoDireito;
    }

    public boolean conecta(Peca outra) {
        if (outra == null) return false;

        return this.ladoEsquerdo.equalsIgnoreCase(outra.ladoEsquerdo)
            || this.ladoEsquerdo.equalsIgnoreCase(outra.ladoDireito)
            || this.ladoDireito.equalsIgnoreCase(outra.ladoEsquerdo)
            || this.ladoDireito.equalsIgnoreCase(outra.ladoDireito);
    }

    @Override
    public String toString() {
        return "[" + ladoEsquerdo + " | " + ladoDireito + "]";
    }
}