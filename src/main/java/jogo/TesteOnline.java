package jogo;

public class TesteOnline {

    public static void main(String[] args) {

        PartidaOnlineDAO dao = new PartidaOnlineDAO();

        String codigo = dao.criarSala(Dificuldade.FACIL);

        System.out.println("Sala criada: " + codigo);
    }
}