package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JFrame {

    private String nome;

    public TelaMenu(String nome) {

        this.nome = nome;

        setTitle("Dominó Químico");
        setSize(700, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        Color vinho = new Color(125, 15, 35);

        JLabel titulo = new JLabel("DOMINÓ QUÍMICO", SwingConstants.CENTER);
        titulo.setBounds(0, 20, 700, 45);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(vinho);
        add(titulo);

        JPanel card = new JPanel(null);
        card.setBounds(95, 75, 510, 320);
        card.setBackground(Color.WHITE);
        add(card);

        JLabel domino = new JLabel("▯");
        domino.setFont(new Font("Arial", Font.BOLD, 58));
        domino.setForeground(Color.BLACK);
        domino.setBounds(55, 45, 90, 90);
        card.add(domino);

        JLabel frasco = new JLabel("⚗");
        frasco.setFont(new Font("Arial", Font.PLAIN, 60));
        frasco.setForeground(Color.BLACK);
        frasco.setBounds(70, 205, 90, 80);
        card.add(frasco);

        JButton btnJogar = criarBotao("▶  Jogar", "Um jogador ou multi-jogador");
        btnJogar.setBounds(220, 25, 230, 50);
        card.add(btnJogar);

        JButton btnTutorial = criarBotao("📘  Como Jogar", "");
        btnTutorial.setBounds(220, 95, 230, 50);
        card.add(btnTutorial);

        JButton btnConfig = criarBotao("⚙  Configurações", "");
        btnConfig.setBounds(220, 165, 230, 50);
        card.add(btnConfig);

        JButton btnSair = criarBotao("↩  Sair", "");
        btnSair.setBounds(220, 235, 230, 50);
        card.add(btnSair);

        btnJogar.addActionListener(e -> {
            dispose();
            new TelaSala(nome, Dificuldade.FACIL);
        });

        btnTutorial.addActionListener(e -> new TelaTutorial());

        btnConfig.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "⚙️ Em desenvolvimento")
        );

        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JButton criarBotao(String titulo, String subtitulo) {

        String html =
                "<html><center>" +
                        "<span style='font-size:18px;'>" + titulo + "</span>" +
                        (subtitulo.isEmpty()
                                ? ""
                                : "<br><span style='font-size:9px;'>" + subtitulo + "</span>") +
                        "</center></html>";

        JButton b = new JButton(html);

        b.setBackground(Color.WHITE);
        b.setForeground(new Color(125, 15, 35));
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(125, 15, 35), 2));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return b;
    }
}