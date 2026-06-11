package jogo;

import javax.swing.*;
import java.awt.*;

public class EstiloUI {

    public static final Color VINHO = new Color(125, 15, 35);
    public static final Color VERMELHO = new Color(255, 50, 70);

    public static JLabel criarTitulo(String texto, int largura) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setBounds(0, 55, largura, 55);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);
        return titulo;
    }

    public static JLabel criarSubtitulo(String texto, int largura) {
        JLabel sub = new JLabel(texto, SwingConstants.CENTER);
        sub.setBounds(0, 110, largura, 30);
        sub.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sub.setForeground(new Color(230, 230, 230));
        return sub;
    }

    public static JPanel criarCard(int x, int y, int w, int h) {
        JPanel card = new JPanel(null);
        card.setBounds(x, y, w, h);
        card.setBackground(new Color(255, 255, 255, 235));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2));
        return card;
    }

    public static JButton criarBotao(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(255, 50, 70));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 135), 2));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}