package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaTutorial extends JFrame {

    public TelaTutorial() {

        setTitle("Como Jogar - Dominó Químico");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel fundo = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                java.net.URL url =
                        getClass().getResource("/jogo/images/tutorial.png");

                if (url != null) {
                    ImageIcon img = new ImageIcon(url);
                    g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(240, 240, 240));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        add(fundo, BorderLayout.CENTER);

        JPanel card = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(255, 255, 255, 238));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        card.setBounds(80, 55, 840, 520);
        card.setOpaque(false);
        fundo.add(card);

        JButton btnVoltar = new JButton("← VOLTAR");
        btnVoltar.setBounds(25, 20, 110, 32);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setForeground(new Color(125, 15, 35));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        card.add(btnVoltar);

        JLabel titulo = new JLabel("COMO JOGAR", SwingConstants.CENTER);
        titulo.setBounds(0, 25, 840, 45);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.BLACK);
        card.add(titulo);

        JTextArea esquerda = criarTexto("""
                1. REGRAS BÁSICAS

                OBJETIVO DO JOGO
                Ser o primeiro jogador a ficar sem peças
                ou ter a menor quantidade ao final.

                DISTRIBUIÇÃO E MONTE
                Cada jogador começa com 7 peças.
                As restantes ficam no Monte.

                O TURNO
                Escolha uma peça da sua mão.
                Depois clique em Esquerda ou Direita.
                Se não tiver peça compatível, compre do Monte.
                """);

        esquerda.setBounds(50, 95, 330, 350);
        card.add(esquerda);

        JTextArea direita = criarTexto("""
                2. MECÂNICA DE ENCAIXE QUÍMICO

                A REGRA MAIS IMPORTANTE:
                Conecte peças da MESMA FUNÇÃO
                INORGÂNICA ou por correspondência
                entre NOME e FÓRMULA.
                """);

        direita.setBounds(455, 95, 340, 145);
        card.add(direita);

        JLabel lblCorreto = new JLabel("A. ENCAIXE CORRETO:");
        lblCorreto.setBounds(455, 245, 300, 25);
        lblCorreto.setForeground(new Color(20, 150, 60));
        lblCorreto.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(lblCorreto);

        JTextArea textoCorreto = criarTexto("""
                HCl combina com Ácido.
                NaOH combina com Base.
                NaCl combina com Sal.
                CaO combina com Óxido.
                """);
        textoCorreto.setBounds(455, 272, 330, 85);
        card.add(textoCorreto);

        JPanel exemploCorreto = new JPanel(null);
        exemploCorreto.setBounds(455, 355, 340, 75);
        exemploCorreto.setOpaque(false);
        card.add(exemploCorreto);

        desenharPecaExemplo(exemploCorreto, 20, 10, "Ácido", "HCl");

        JLabel lblIncorreto = new JLabel("B. ENCAIXE INCORRETO:");
        lblIncorreto.setBounds(455, 430, 300, 25);
        lblIncorreto.setForeground(new Color(200, 40, 40));
        lblIncorreto.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(lblIncorreto);

        JPanel exemploIncorreto = new JPanel(null);
        exemploIncorreto.setBounds(455, 455, 340, 65);
        exemploIncorreto.setOpaque(false);
        card.add(exemploIncorreto);

        desenharPecaExemplo(exemploIncorreto, 20, 5, "Sal", "NaOH");

        btnVoltar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JTextArea criarTexto(String texto) {

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setOpaque(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.BOLD, 16));
        area.setForeground(Color.BLACK);

        return area;
    }

    private void desenharPecaExemplo(JPanel painel, int x, int y, String lado1, String lado2) {

        JPanel peca = new JPanel(null);
        peca.setBounds(x, y, 130, 55);
        peca.setBackground(new Color(255, 238, 185));
        peca.setBorder(BorderFactory.createLineBorder(new Color(45, 25, 10), 3));

        JLabel l1 = new JLabel(lado1, SwingConstants.CENTER);
        l1.setBounds(0, 0, 65, 55);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l1.setForeground(new Color(35, 20, 10));

        JLabel l2 = new JLabel(lado2, SwingConstants.CENTER);
        l2.setBounds(65, 0, 65, 55);
        l2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l2.setForeground(new Color(35, 20, 10));

        JPanel linha = new JPanel();
        linha.setBounds(64, 5, 2, 45);
        linha.setBackground(new Color(70, 45, 22));

        peca.add(l1);
        peca.add(l2);
        peca.add(linha);

        painel.add(peca);
    }
}