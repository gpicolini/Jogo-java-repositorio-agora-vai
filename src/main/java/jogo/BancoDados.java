package jogo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BancoDados {

    private static final String URL =
            "jdbc:mysql://root:TUEbJashZWrUFTRZyBeIYBXtUZEUILVG@trolley.proxy.rlwy.net:52251/railway";

    private static final String USUARIO =
            "root";

    private static final String SENHA =
            "TUEbJashZWrUFTRZyBeIYBXtUZEUILVG";

    public static Connection conectar() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL,
                    USUARIO,
                    SENHA
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String autenticar(Connection conn, String usuario, String senha) {

        try {
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");

                if (nome == null || nome.trim().isEmpty()) {
                    nome = usuario;
                }

                return nome;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean cadastrar(
        Connection conn,
        String nome,
        String usuario,
        String senha,
        String tipo
) {
    try {
        String sql = """
                INSERT INTO usuarios
                (nome, usuario, senha, tipo)
                VALUES (?, ?, ?, ?)
                """;

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, nome);
        stmt.setString(2, usuario);
        stmt.setString(3, senha);
        stmt.setString(4, tipo);

        stmt.executeUpdate();

        stmt.close();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public static boolean salvarResultado(
            Connection conn,
            int idUsuario,
            int acertos,
            int erros,
            int pontuacaoFinal,
            int bonusTempo,
            int tempoSegundos,
            String nivelJogado) {

        try {

            String sql = "INSERT INTO partida "
                    + "(id_usuario, acertos, erros, pontuacao_final, "
                    + "bonus_tempo, tempo_segundos, nivel_jogado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, acertos);
            stmt.setInt(3, erros);
            stmt.setInt(4, pontuacaoFinal);
            stmt.setInt(5, bonusTempo);
            stmt.setInt(6, tempoSegundos);
            stmt.setString(7, nivelJogado);

            stmt.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String buscarTipo(
        Connection conn,
        String usuario
) {

    try {

        String sql =
                """
                SELECT tipo
                FROM usuarios
                WHERE usuario=?
                """;

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setString(
                1,
                usuario
        );

        ResultSet rs =
                stmt.executeQuery();

        if (rs.next()) {

            String tipo =
                    rs.getString("tipo");

            rs.close();
            stmt.close();

            return tipo;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return "ALUNO";
}
}