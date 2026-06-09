package jogo;

public class TesteMonte {

    public static void main(String[] args) {

        Monte monte = new Monte();

        System.out.println("Peças no monte: "
                + monte.quantidadePecas());

        while (!monte.vazio()) {

            Peca p = monte.comprarPeca();

            System.out.println(p);
        }
    }
}