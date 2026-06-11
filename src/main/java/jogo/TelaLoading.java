package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaLoading extends JFrame {

    private JProgressBar barra;
    private JLabel lblStatus;

    private Timer timer;
    private int progresso = 0;

    private Jogo jogo;

    public TelaLoading(Jogo jogo) {

        this.jogo = jogo;

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
                        0,
                        0,
                        new Color(25, 25, 25),
                        getWidth(),
                        getHeight(),
                        new Color(120, 0, 25)
                );

                g2.setPaint(gradiente);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        add(fundo);

        JLabel icone = new JLabel("🧪", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        fundo.add(icone);

        JLabel titulo = new JLabel("DOMINÓ QUÍMICO", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 46));
        titulo.setForeground(Color.WHITE);
        fundo.add(titulo);

        JLabel subtitulo = new JLabel("Classificação de Funções Inorgânicas", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        subtitulo.setForeground(new Color(230, 230, 230));
        fundo.add(subtitulo);

        lblStatus = new JLabel("Carregando laboratório...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblStatus.setForeground(new Color(255, 230, 120));
        fundo.add(lblStatus);

        barra = new JProgressBar();
        barra.setMinimum(0);
        barra.setMaximum(100);
        barra.setStringPainted(true);
        barra.setForeground(new Color(255, 50, 70));
        barra.setBackground(new Color(40, 40, 40));
        barra.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fundo.add(barra);

        JLabel rodape = new JLabel("Preparando peças, monte e tabuleiro...", SwingConstants.CENTER);
        rodape.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rodape.setForeground(new Color(220, 220, 220));
        fundo.add(rodape);

        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        timer = new Timer(45, e -> {

            progresso += 2;
            barra.setValue(progresso);

            if (progresso == 24) {
                lblStatus.setText("Misturando ácidos...");
            }

            if (progresso == 46) {
                lblStatus.setText("Organizando bases...");
            }

            if (progresso == 68) {
                lblStatus.setText("Separando sais e óxidos...");
            }

            if (progresso == 88) {
                lblStatus.setText("Finalizando sistema...");
            }

            if (progresso >= 100) {
                timer.stop();
                dispose();
                jogo.abrirCadastro();
            }
        });

        timer.start();

        setVisible(true);

        SwingUtilities.invokeLater(() -> {

            int largura = getWidth();
            int altura = getHeight();

            fundo.setBounds(0, 0, largura, altura);

            icone.setBounds(0, altura / 2 - 220, largura, 75);
            titulo.setBounds(0, altura / 2 - 145, largura, 60);
            subtitulo.setBounds(0, altura / 2 - 85, largura, 30);
            lblStatus.setBounds(0, altura / 2 - 25, largura, 30);

            barra.setBounds(
                    (largura - 430) / 2,
                    altura / 2 + 35,
                    430,
                    26
            );

            rodape.setBounds(0, altura / 2 + 80, largura, 25);

            fundo.revalidate();
            fundo.repaint();
        });
    }
}