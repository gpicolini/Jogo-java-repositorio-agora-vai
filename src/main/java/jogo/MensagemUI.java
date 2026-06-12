package jogo;

import javax.swing.*;
import java.awt.*;

public class MensagemUI extends JDialog {

    public MensagemUI(JFrame parent, String titulo, String mensagem) {

        super(parent, true);

        setUndecorated(true);
        setSize(380, 200);
        setLocationRelativeTo(parent);
        setLayout(null);

        JPanel fundo = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                GradientPaint gradiente = new GradientPaint(
                        0, 0,
                        new Color(25, 25, 25),
                        getWidth(), getHeight(),
                        new Color(120, 0, 25)
                );

                g2.setPaint(gradiente);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
            }
        };

        fundo.setBounds(0, 0, 380, 200);
        add(fundo);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setBounds(0, 25, 380, 32);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        fundo.add(lblTitulo);

        JLabel lblMensagem = new JLabel(
                "<html><center>" + mensagem + "</center></html>",
                SwingConstants.CENTER
        );
        lblMensagem.setBounds(35, 70, 310, 45);
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMensagem.setForeground(new Color(230, 230, 230));
        fundo.add(lblMensagem);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(130, 135, 120, 38);
        btnOk.setBackground(new Color(255, 50, 70));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnOk.setFocusPainted(false);
        btnOk.setOpaque(true);
        btnOk.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 135), 2));
        fundo.add(btnOk);

        btnOk.addActionListener(e -> dispose());

        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke("ENTER"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);
    }

    public static void mostrar(JFrame parent, String titulo, String mensagem) {
        new MensagemUI(parent, titulo, mensagem);
    }
}