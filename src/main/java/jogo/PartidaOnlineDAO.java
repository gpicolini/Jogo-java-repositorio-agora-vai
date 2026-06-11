package jogo;

import java.sql.*;

public class PartidaOnlineDAO {
    
    
    
    private Connection conn;

    private Connection getConn() throws Exception {

    if (
        conn == null
        ||
        conn.isClosed()
    ) {

        conn =
                BancoDados.conectar();
    }

    return conn;
}

    public String criarSala(Dificuldade dificuldade) {

        String codigo =
                String.valueOf(
                        (int)(Math.random() * 900000) + 100000
                );

        String sql = """
                INSERT INTO salas
                (
                    codigo,
                    dificuldade,
                    fase,
                    indice_jogador_atual,
                    status
                )
                VALUES
                (?, ?, 1, 0, 'AGUARDANDO')
                """;

        try {
            Connection conn = BancoDados.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, codigo);
            stmt.setString(2, dificuldade.name());

            stmt.executeUpdate();

            stmt.close();
            // conn.close();

            return codigo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int entrarSala(String codigo, String nomeJogador) {

        try {
            Connection conn = BancoDados.conectar();

            int salaId = buscarIdSala(conn, codigo);

            if (salaId == -1) {
                // conn.close();
                return -1;
            }

            String contar = """
                    SELECT COUNT(*)
                    FROM jogadores_sala
                    WHERE sala_id=?
                    """;

            PreparedStatement countStmt = conn.prepareStatement(contar);
            countStmt.setInt(1, salaId);

            ResultSet rsCount = countStmt.executeQuery();
            rsCount.next();

            int ordem = rsCount.getInt(1);

            rsCount.close();
            countStmt.close();

            if (ordem >= 4) {
                // conn.close();
                return -2;
            }

            String inserir = """
                    INSERT INTO jogadores_sala
                    (
                        sala_id,
                        nome,
                        ordem,
                        pontos
                    )
                    VALUES
                    (?, ?, ?, 0)
                    """;

            PreparedStatement insert = conn.prepareStatement(inserir);

            insert.setInt(1, salaId);
            insert.setString(2, nomeJogador);
            insert.setInt(3, ordem);

            insert.executeUpdate();

            insert.close();
            // conn.close();

            return ordem;

        } catch (Exception e) {
            e.printStackTrace();
            return -3;
        }
    }

    public int getIndiceJogadorAtual(String codigo) {

        String sql = """
                SELECT indice_jogador_atual
                FROM salas
                WHERE codigo=?
                """;

        try {
            Connection conn = BancoDados.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int indice = rs.getInt("indice_jogador_atual");

                rs.close();
                stmt.close();
                // conn.close();

                return indice;
            }

            rs.close();
            stmt.close();
            // conn.close();

            return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getQuantidadeJogadores(String codigo) {

        try {
            Connection conn = BancoDados.conectar();

            int salaId = buscarIdSala(conn, codigo);

            if (salaId == -1) {
                // conn.close();
                return 0;
            }

            String sql = """
                    SELECT COUNT(*)
                    FROM jogadores_sala
                    WHERE sala_id=?
                    """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, salaId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            int quantidade = rs.getInt(1);

            rs.close();
            stmt.close();
            // conn.close();

            return quantidade;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean ehMinhaVez(String codigo, int minhaOrdem) {
        return getIndiceJogadorAtual(codigo) == minhaOrdem;
    }

    public boolean passarTurno(String codigo) {

        try {
            Connection conn = BancoDados.conectar();

            int atual = getIndiceJogadorAtual(codigo);
            int quantidade = getQuantidadeJogadores(codigo);

            if (atual == -1 || quantidade <= 0) {
                // conn.close();
                return false;
            }

            int proximo = atual + 1;

            if (proximo >= quantidade) {
                proximo = 0;
            }

            String sql = """
                    UPDATE salas
                    SET indice_jogador_atual=?
                    WHERE codigo=?
                    """;

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, proximo);
            stmt.setString(2, codigo);

            stmt.executeUpdate();

            stmt.close();
            // conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean iniciarSala(String codigo) {

        String sql = """
                UPDATE salas
                SET status='JOGANDO'
                WHERE codigo=?
                """;

        try {
            Connection conn = BancoDados.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);

            stmt.executeUpdate();

            stmt.close();
            // conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getStatusSala(String codigo) {

        String sql = """
                SELECT status
                FROM salas
                WHERE codigo=?
                """;

        try {
            Connection conn = BancoDados.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");

                rs.close();
                stmt.close();
                // conn.close();

                return status;
            }

            rs.close();
            stmt.close();
            // conn.close();

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public java.util.ArrayList<Peca> buscarPecasJogador(
        String codigo,
        int ordem
) {
    java.util.ArrayList<Peca> pecas = new java.util.ArrayList<>();

    try {
        Connection conn = getConn();

        int salaId = buscarIdSala(conn, codigo);

        String sql = """
                SELECT lado_esquerdo, lado_direito
                FROM pecas_jogador
                WHERE sala_id=?
                AND ordem_jogador=?
                AND usada=FALSE
                """;

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, salaId);
        stmt.setInt(2, ordem);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            pecas.add(
                    new Peca(
                            rs.getString("lado_esquerdo"),
                            rs.getString("lado_direito")
                    )
            );
        }

        rs.close();
        stmt.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return pecas;
}

    private int buscarIdSala(Connection conn, String codigo) throws SQLException {

        String buscaSala =
                "SELECT id FROM salas WHERE codigo=?";

        PreparedStatement salaStmt =
                conn.prepareStatement(buscaSala);

        salaStmt.setString(1, codigo);

        ResultSet rs =
                salaStmt.executeQuery();

        if (!rs.next()) {
            rs.close();
            salaStmt.close();
            return -1;
        }

        int idSala = rs.getInt("id");

        rs.close();
        salaStmt.close();

        return idSala;
    }
public boolean distribuirPecasSala(String codigo) {

    try {

        Connection conn =
        getConn();

        int salaId =
                buscarIdSala(conn, codigo);

        if (salaId == -1) {
            // conn.close();
            return false;
        }

        Monte monte =
                new Monte(Dificuldade.FACIL);

        int qtd =
                getQuantidadeJogadores(codigo);

        String sqlJogador = """
            INSERT INTO pecas_jogador
            (
                sala_id,
                ordem_jogador,
                lado_esquerdo,
                lado_direito
            )
            VALUES
            (?, ?, ?, ?)
        """;

        PreparedStatement stmtJogador =
                conn.prepareStatement(sqlJogador);

        for (int jogador = 0; jogador < qtd; jogador++) {

            for (int i = 0; i < 7; i++) {

                Peca p =
                        monte.comprarPeca();

                if (p == null) {
                    break;
                }

                stmtJogador.setInt(1, salaId);
                stmtJogador.setInt(2, jogador);
                stmtJogador.setString(3, p.getLadoEsquerdo());
                stmtJogador.setString(4, p.getLadoDireito());

                stmtJogador.executeUpdate();
            }
        }

        stmtJogador.close();

        String sqlMonte = """
            INSERT INTO monte_online
            (
                codigo_sala,
                lado_esquerdo,
                lado_direito,
                comprada
            )
            VALUES
            (?, ?, ?, false)
        """;

        PreparedStatement stmtMonte =
                conn.prepareStatement(sqlMonte);

        while (true) {

            Peca p =
                    monte.comprarPeca();

            if (p == null) {
                break;
            }

            stmtMonte.setString(1, codigo);
            stmtMonte.setString(2, p.getLadoEsquerdo());
            stmtMonte.setString(3, p.getLadoDireito());

            stmtMonte.executeUpdate();
        }

        stmtMonte.close();
        // conn.close();

        return true;

    } catch (Exception e) {

        e.printStackTrace();
        return false;
    }
}

public ResultSet buscarMinhaMao(
        String codigo,
        int ordem
) {

    try {

        Connection conn =
        getConn();

        int salaId =
                buscarIdSala(
                        conn,
                        codigo
                );

        String sql =
                """
                SELECT *
                FROM pecas_jogador
                WHERE sala_id=?
                AND ordem_jogador=?
                AND usada=FALSE
                """;

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(
                1,
                salaId
        );

        stmt.setInt(
                2,
                ordem
        );

        return stmt.executeQuery();

    } catch (Exception e) {

        e.printStackTrace();

        return null;
    }
}


public boolean jogarPecaOnline(
        int idPeca
) {

    try {


        Connection conn =
        getConn();

        String sql =
                """
                UPDATE pecas_jogador
                SET usada=TRUE
                WHERE id=?
                """;

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(
                1,
                idPeca
        );

        stmt.executeUpdate();

        stmt.close();
        // conn.close();

        return true;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}

public java.util.ArrayList<Peca> buscarMesa(String codigo) {

    java.util.ArrayList<Peca> pecas =
            new java.util.ArrayList<>();

    try {


        Connection conn =
        getConn();

        int salaId =
                buscarIdSala(
                        conn,
                        codigo
                );

        String sql =
                """
                SELECT lado_esquerdo,
                       lado_direito
                FROM mesa_online
                WHERE sala_id=?
                ORDER BY posicao
                """;

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(
                1,
                salaId
        );

        ResultSet rs =
                stmt.executeQuery();

        while (rs.next()) {

            pecas.add(

                    new Peca(

                            rs.getString(
                                    "lado_esquerdo"
                            ),

                            rs.getString(
                                    "lado_direito"
                            )
                    )
            );
        }

        rs.close();
        stmt.close();
        // conn.close();

    } catch (Exception e) {

        e.printStackTrace();
    }

    return pecas;
}


public boolean jogarPecaNaMesa(
        String codigo,
        int idPeca,
        boolean direita
) {
    try {
        Connection conn = getConn();
        
        int salaId = buscarIdSala(conn, codigo);

        if (salaId == -1) {
            // conn.close();
            return false;
        }

        
        String buscarPeca = """
                SELECT lado_esquerdo, lado_direito
                FROM pecas_jogador
                WHERE id=?
                AND usada=FALSE
                """;

        PreparedStatement stmtBuscar = conn.prepareStatement(buscarPeca);
        stmtBuscar.setInt(1, idPeca);

        ResultSet rs = stmtBuscar.executeQuery();

        if (!rs.next()) {
            rs.close();
            stmtBuscar.close();
            // conn.close();
            return false;
        }

        String ladoEsquerdo = rs.getString("lado_esquerdo");
        String ladoDireito = rs.getString("lado_direito");

        rs.close();
        stmtBuscar.close();

        
        String contarMesa = """
                SELECT COUNT(*)
                FROM mesa_online
                WHERE sala_id=?
                """;

        PreparedStatement stmtCount = conn.prepareStatement(contarMesa);
        stmtCount.setInt(1, salaId);

        ResultSet rsCount = stmtCount.executeQuery();
        rsCount.next();

        int quantidadeMesa = rsCount.getInt(1);

        rsCount.close();
        stmtCount.close();

        int posicao;

       
        if (quantidadeMesa == 0) {
            posicao = 0;
        } else {

            String pontaSql;

            if (direita) {
                pontaSql = """
                        SELECT lado_direito, posicao
                        FROM mesa_online
                        WHERE sala_id=?
                        ORDER BY posicao DESC
                        LIMIT 1
                        """;
            } else {
                pontaSql = """
                        SELECT lado_esquerdo, posicao
                        FROM mesa_online
                        WHERE sala_id=?
                        ORDER BY posicao ASC
                        LIMIT 1
                        """;
            }

            PreparedStatement stmtPonta = conn.prepareStatement(pontaSql);
            stmtPonta.setInt(1, salaId);

            ResultSet rsPonta = stmtPonta.executeQuery();

            if (!rsPonta.next()) {
                rsPonta.close();
                stmtPonta.close();
                // conn.close();();
                return false;
            }

            String ponta = direita
                    ? rsPonta.getString("lado_direito")
                    : rsPonta.getString("lado_esquerdo");

            int posicaoPonta = rsPonta.getInt("posicao");

            rsPonta.close();
            stmtPonta.close();

            if (direita) {

                
                if (ponta.equalsIgnoreCase(ladoEsquerdo)) {
                    posicao = posicaoPonta + 1;
                }

                // precisa inverter a peça
                else if (ponta.equalsIgnoreCase(ladoDireito)) {
                    String temp = ladoEsquerdo;
                    ladoEsquerdo = ladoDireito;
                    ladoDireito = temp;

                    posicao = posicaoPonta + 1;
                }

                else {
                    // conn.close();
                    return false;
                }

            } else {

               
                if (ponta.equalsIgnoreCase(ladoDireito)) {
                    posicao = posicaoPonta - 1;
                }

               
                else if (ponta.equalsIgnoreCase(ladoEsquerdo)) {
                    String temp = ladoEsquerdo;
                    ladoEsquerdo = ladoDireito;
                    ladoDireito = temp;

                    posicao = posicaoPonta - 1;
                }

               
                else {
                    // conn.close();
                    return false;
                }
            }
        }

       
        String inserirMesa = """
                INSERT INTO mesa_online
                (
                    sala_id,
                    lado_esquerdo,
                    lado_direito,
                    posicao
                )
                VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement stmtMesa = conn.prepareStatement(inserirMesa);
        stmtMesa.setInt(1, salaId);
        stmtMesa.setString(2, ladoEsquerdo);
        stmtMesa.setString(3, ladoDireito);
        stmtMesa.setInt(4, posicao);
        stmtMesa.executeUpdate();
        stmtMesa.close();

        String marcarUsada = """
                UPDATE pecas_jogador
                SET usada=TRUE
                WHERE id=?
                """;

        PreparedStatement stmtUsada = conn.prepareStatement(marcarUsada);
        stmtUsada.setInt(1, idPeca);
        stmtUsada.executeUpdate();
        stmtUsada.close();

        // conn.close();

        passarTurno(codigo);

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public Peca comprarDoMonte(String codigo) {

    try {

        Connection conn =
        getConn();

        String buscar = """
                SELECT id, lado_esquerdo, lado_direito
                FROM monte_online
                WHERE codigo_sala=?
                AND comprada=false
                ORDER BY id
                LIMIT 1
                """;

        PreparedStatement stmtBuscar =
                conn.prepareStatement(buscar);

        stmtBuscar.setString(1, codigo);

        ResultSet rs =
                stmtBuscar.executeQuery();

        if (!rs.next()) {
            rs.close();
            stmtBuscar.close();
            // conn.close();
            return null;
        }

        int idMonte =
                rs.getInt("id");

        String ladoEsquerdo =
                rs.getString("lado_esquerdo");

        String ladoDireito =
                rs.getString("lado_direito");

        rs.close();
        stmtBuscar.close();

        String atualizar = """
                UPDATE monte_online
                SET comprada=true
                WHERE id=?
                """;

        PreparedStatement stmtAtualizar =
                conn.prepareStatement(atualizar);

        stmtAtualizar.setInt(1, idMonte);
        stmtAtualizar.executeUpdate();

        stmtAtualizar.close();
        // conn.close();

        return new Peca(
                ladoEsquerdo,
                ladoDireito
        );

    } catch (Exception e) {

        e.printStackTrace();
        return null;
    }
}


public boolean adicionarPecaJogador(
        String codigo,
        int ordem,
        Peca p
) {

    try {

        Connection conn =
        getConn();

        int salaId =
                buscarIdSala(
                        conn,
                        codigo
                );

        String sql = """
                INSERT INTO pecas_jogador
                (
                    sala_id,
                    ordem_jogador,
                    lado_esquerdo,
                    lado_direito
                )
                VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setInt(1, salaId);

        stmt.setInt(
                2,
                ordem
        );

        stmt.setString(
                3,
                p.getLadoEsquerdo()
        );

        stmt.setString(
                4,
                p.getLadoDireito()
        );

        stmt.executeUpdate();

        stmt.close();
    // conn.close();

        return true;

    } catch (Exception e) {

        e.printStackTrace();

        return false;
    }
}



}

