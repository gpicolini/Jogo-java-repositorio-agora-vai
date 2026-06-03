package jogo;

public class Peca {

    private String lado1;
    private String lado2;

    public Peca(String lado1, String lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
    }

    public void inverter() {
        String temp = lado1;
        lado1 = lado2;
        lado2 = temp;
    }

    public boolean conecta(String conceito) {
        return lado1.equals(conceito) || lado2.equals(conceito);
    }

    @Override
    public String toString() {
        return "[" + lado1 + " | " + lado2 + "]";
    }
}   