package jogo;

public class TestePeca {

    public static void main(String[] args) {

        Peca p1 = new Peca("HCl", "H2SO4");
        Peca p2 = new Peca("H2SO4", "HNO3");
        Peca p3 = new Peca("NaOH", "KOH");
        Peca p4 = new Peca("HNO3", "HCl");

        System.out.println("=== PEÇAS ===");

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        System.out.println("\n=== TESTES DE CONEXÃO ===");

        System.out.println(
                "p1 conecta p2? "
                + p1.conecta(p2)
        );

        System.out.println(
                "p2 conecta p3? "
                + p2.conecta(p3)
        );

        System.out.println(
                "p2 conecta p4? "
                + p2.conecta(p4)
        );

        System.out.println(
                "p4 conecta p1? "
                + p4.conecta(p1)
        );
    }
}