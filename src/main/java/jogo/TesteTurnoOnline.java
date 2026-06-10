package jogo;

public class TesteTurnoOnline {

    public static void main(String[] args) {

        PartidaOnlineDAO dao = new PartidaOnlineDAO();

        String codigo = "120175";

        System.out.println("Jogador atual: " + dao.getIndiceJogadorAtual(codigo));
        System.out.println("Quantidade: " + dao.getQuantidadeJogadores(codigo));

        dao.passarTurno(codigo);

        System.out.println("Jogador atual depois: " + dao.getIndiceJogadorAtual(codigo));
    }
}