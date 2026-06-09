package jogo;

public class TesteMonte {

    public static void main(String[] args) {

        // ===================== CONFIG =====================
        Dificuldade dificuldade = Dificuldade.FACIL;

        Monte monte = new Monte(dificuldade);

        System.out.println("Peças no monte: " + monte.quantidadePecas());

        // ===================== COMPRA TODAS AS PEÇAS =====================
        while (!monte.vazio()) {

            Peca p = monte.comprarPeca();

            if (p != null) {
                System.out.println(p);
            }
        }

        System.out.println("\nMonte vazio!");
    }
}