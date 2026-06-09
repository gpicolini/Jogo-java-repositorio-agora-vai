package jogo;

public class TestePeca {

    public static void main(String[] args) {

        Peca p1 = new Peca("HCl", "Ácido", TipoQuimico.ACIDO);
        Peca p2 = new Peca("H2SO4", "Ácido", TipoQuimico.ACIDO);
        Peca p3 = new Peca("NaOH", "Base", TipoQuimico.BASE);
        Peca p4 = new Peca("NaCl", "Sal", TipoQuimico.SAL);

        System.out.println("=== PEÇAS ===");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        System.out.println("\n=== TESTES DE CONEXÃO ===");

        System.out.println("p1 conecta p2? " + p1.conecta(p2)); // ácido + ácido (false)
        System.out.println("p1 conecta p3? " + p1.conecta(p3)); // ácido + base (true)
        System.out.println("p1 conecta p4? " + p1.conecta(p4)); // ácido + sal (false)
        System.out.println("p3 conecta p1? " + p3.conecta(p1)); // base + ácido (true)
    }
}