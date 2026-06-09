package jogo;

public class TesteRegra {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro();

        Peca sal = new Peca("NaCl", "Sal");
        Peca outroSal = new Peca("KBr", "Sal");
        Peca acido = new Peca("HCl", "Ácido");

        tabuleiro.adicionarPrimeiraPeca(sal);

        System.out.println(
            "Adicionar outro sal: "
            + tabuleiro.adicionarDireita(outroSal)
        );

        System.out.println(
            "Adicionar ácido: "
            + tabuleiro.adicionarDireita(acido)
        );

        tabuleiro.mostrarTabuleiro();
    }
}
