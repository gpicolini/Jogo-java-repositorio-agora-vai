package jogo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaRelatorioProfessor extends JFrame {

    private Connection conn;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaRelatorioProfessor(Connection conn) {

        this.conn = conn;

        setTitle("Relatórios - Professor");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("RELATÓRIO DE DESEMPENHO DOS ALUNOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(170, 0, 0));

        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel();

        modelo.addColumn("Aluno");
        modelo.addColumn("Partidas");
        modelo.addColumn("Acertos");
        modelo.addColumn("Erros");
        modelo.addColumn("Média Pontos");
        modelo.addColumn("Melhor Pontuação");

        tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnSair = new JButton("Sair");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarRelatorio());

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin(null).Show();
        });

        carregarRelatorio();

        setVisible(true);
    }

    private void carregarRelatorio() {

        modelo.setRowCount(0);

        try {
            String sql = """
                    SELECT 
                        u.nome AS aluno,
                        COUNT(p.id) AS partidas,
                        COALESCE(SUM(p.acertos), 0) AS acertos,
                        COALESCE(SUM(p.erros), 0) AS erros,
                        COALESCE(AVG(p.pontuacao_final), 0) AS media,
                        COALESCE(MAX(p.pontuacao_final), 0) AS melhor
                    FROM usuarios u
                    LEFT JOIN partida p ON p.id_usuario = u.id
                    WHERE u.tipo = 'ALUNO'
                    GROUP BY u.id, u.nome
                    ORDER BY u.nome
                    """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("aluno"),
                        rs.getInt("partidas"),
                        rs.getInt("acertos"),
                        rs.getInt("erros"),
                        String.format("%.2f", rs.getDouble("media")),
                        rs.getInt("melhor")
                });
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatório.");
        }
    }
}