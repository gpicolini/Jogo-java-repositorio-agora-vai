package jogo;

public class TestePeca {

    public static void main(String[] args) {

        Peca p1 = new Peca("HCl", "Ácido");
        Peca p2 = new Peca("H2SO4", "Ácido");
        Peca p3 = new Peca("NaOH", "Base");
        Peca p4 = new Peca("NaCl", "Sal");

        System.out.println(p1);
        System.out.println(p2);

        System.out.println("p1 conecta p2? " + p1.conecta(p2));
        System.out.println("p1 conecta p3? " + p1.conecta(p3));
        System.out.println("p1 conecta p4? " + p1.conecta(p4));
    }
}