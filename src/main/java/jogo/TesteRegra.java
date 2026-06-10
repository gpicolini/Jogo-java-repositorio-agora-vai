package jogo;

public class TesteRegra {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro();

        // conecta
        Peca p1 = new Peca("NaCl", "KBr");

        // conecta com p1
        Peca p2 = new Peca("KBr", "CaCO3");

        // NÃO conecta
        Peca p3 = new Peca("HCl", "H2SO4");

        tabuleiro.adicionarPrimeiraPeca(p1);

        System.out.println(
                "Adicionar peça 2: " +
                tabuleiro.adicionarDireita(p2)
        );

        System.out.println(
                "Adicionar peça 3: " +
                tabuleiro.adicionarDireita(p3)
        );

        System.out.println();

        tabuleiro.mostrarTabuleiro();
    }
}