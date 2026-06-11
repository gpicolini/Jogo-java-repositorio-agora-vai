package jogo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class TelaRelatorioProfessor extends JFrame {

    private Connection conn;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaRelatorioProfessor(Connection conn) {

        this.conn = conn;

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JPanel fundo = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gradiente = new GradientPaint(
                        0, 0,
                        new Color(25, 25, 25),
                        getWidth(), getHeight(),
                        new Color(120, 0, 25)
                );

                g2.setPaint(gradiente);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        add(fundo);

        JLabel icone = new JLabel("📊", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        fundo.add(icone);

        JLabel titulo = new JLabel(
                "RELATÓRIO DE DESEMPENHO",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);
        fundo.add(titulo);

        JLabel subtitulo = new JLabel(
                "Acompanhamento individual dos alunos",
                SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subtitulo.setForeground(new Color(230, 230, 230));
        fundo.add(subtitulo);

        JPanel card = new JPanel(null);
        card.setBackground(new Color(255, 255, 255, 235));
        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255, 255, 255, 80),
                        2
                )
        );
        fundo.add(card);

        modelo = new DefaultTableModel();

        modelo.addColumn("Aluno");
        modelo.addColumn("Partidas");
        modelo.addColumn("Acertos");
        modelo.addColumn("Erros");
        modelo.addColumn("Média Pontos");
        modelo.addColumn("Melhor Pontuação");

        tabela = new JTable(modelo);
        tabela.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.setRowHeight(30);
        tabela.setForeground(new Color(35, 35, 35));
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(new Color(220, 220, 220));
        tabela.setSelectionBackground(new Color(255, 50, 70));
        tabela.setSelectionForeground(Color.WHITE);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(125, 15, 35));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220),
                        2
                )
        );
        card.add(scroll);

        JButton btnAtualizar = criarBotao("Atualizar");
        fundo.add(btnAtualizar);

        JButton btnSair = criarBotao("Sair");
        fundo.add(btnSair);

        btnAtualizar.addActionListener(e -> carregarRelatorio());

        btnSair.addActionListener(e -> System.exit(0));

        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        carregarRelatorio();

        setVisible(true);

        SwingUtilities.invokeLater(() -> {

            int largura = getWidth();
            int altura = getHeight();

            fundo.setBounds(0, 0, largura, altura);

            icone.setBounds(0, altura / 2 - 320, largura, 55);
            titulo.setBounds(0, altura / 2 - 265, largura, 50);
            subtitulo.setBounds(0, altura / 2 - 215, largura, 28);

            card.setBounds(
                    (largura - 900) / 2,
                    (altura - 330) / 2 + 20,
                    900,
                    330
            );

            scroll.setBounds(20, 20, 860, 290);

            btnAtualizar.setBounds(
                    largura / 2 - 170,
                    altura / 2 + 220,
                    150,
                    42
            );

            btnSair.setBounds(
                    largura / 2 + 20,
                    altura / 2 + 220,
                    150,
                    42
            );

            fundo.revalidate();
            fundo.repaint();
        });
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

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                modelo.addRow(
                        new Object[]{
                                rs.getString("aluno"),
                                rs.getInt("partidas"),
                                rs.getInt("acertos"),
                                rs.getInt("erros"),
                                String.format("%.2f", rs.getDouble("media")),
                                rs.getInt("melhor")
                        }
                );
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar relatório."
            );
        }
    }

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        Color vermelho = new Color(255, 50, 70);
        Color hover = new Color(255, 75, 95);

        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setBackground(vermelho);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255, 120, 135),
                        2
                )
        );
        botao.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        botao.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(vermelho);
            }
        });

        return botao;
    }
}