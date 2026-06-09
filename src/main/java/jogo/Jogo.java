package jogo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jogo {

    private static final String URL = "jdbc:mysql://trolley.proxy.rlwy.net:52251/railway?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "TUEbJashZWrUFTRZyBeIYBXtUZEUILVG";

    public Connection conn;

    public TelaCadastro Cadastro;

    public TelaLogin Login;

    public Jogo() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado!");
        }

        conn = DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.println("Conexão estabelecida com sucesso!");

        Cadastro = new TelaCadastro(this);

        Cadastro.Show();
    }

    public void abrirJogo(String nome) {

        System.out.println("Jogador logado: " + nome);

        new TelaJogo(nome, Dificuldade.FACIL);

    }
}