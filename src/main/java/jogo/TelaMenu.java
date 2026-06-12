package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JFrame {

    private String nome;

    public TelaMenu(String nome) {

        this.nome = nome;

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JPanel fundo = new JPanel(null) {

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 =
                        (Graphics2D) g;

                GradientPaint gradiente =
                        new GradientPaint(
                                0,
                                0,
                                new Color(25,25,25),

                                getWidth(),
                                getHeight(),

                                new Color(
                                        120,
                                        0,
                                        25
                                )
                        );

                g2.setPaint(gradiente);

                g2.fillRect(
                        0,
                        0,
                        getWidth(),
                        getHeight()
                );
            }
        };

        add(fundo);

        JLabel icone =
                new JLabel(
                        "🧪",
                        SwingConstants.CENTER
                );

        icone.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        44
                )
        );

        fundo.add(icone);

        JLabel titulo =
                new JLabel(
                        "DOMINÓ QUÍMICO",
                        SwingConstants.CENTER
                );

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        titulo.setForeground(
                Color.WHITE
        );

        fundo.add(titulo);

        JLabel subtitulo =
                new JLabel(
                        "Bem-vindo, " + nome,
                        SwingConstants.CENTER
                );

        subtitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        subtitulo.setForeground(
                new Color(
                        230,
                        230,
                        230
                )
        );

        fundo.add(subtitulo);

        JPanel card =
                new JPanel(null);

        card.setBackground(
                new Color(
                        255,
                        255,
                        255,
                        235
                )
        );

        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                255,
                                255,
                                255,
                                80
                        ),
                        2
                )
        );

        fundo.add(card);

        JLabel lblMenu =
                new JLabel(
                        "MENU PRINCIPAL",
                        SwingConstants.CENTER
                );

        lblMenu.setBounds(
                0,
                20,
                490,
                30
        );

        lblMenu.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        lblMenu.setForeground(
                new Color(
                        125,
                        15,
                        35
                )
        );

        card.add(lblMenu);

        JButton btnJogar =
                criarBotao(
                        "Jogar",
                        "Multi-jogador"
                );

        btnJogar.setBounds(
                125,
                65,
                240,
                45
        );

        card.add(btnJogar);

        JButton btnBot =
                criarBotao(
                        "🤖 Contra Bot",
                        ""
                );

        btnBot.setBounds(
                125,
                120,
                240,
                45
        );

        card.add(btnBot);

        JButton btnTutorial =
                criarBotao(
                        "📘 Como Jogar",
                        ""
                );

        btnTutorial.setBounds(
                125,
                175,
                240,
                45
        );

        card.add(btnTutorial);

        JButton btnConfig =
                criarBotao(
                        "Configurações",
                        ""
                );

        btnConfig.setBounds(
                125,
                230,
                240,
                45
        );

        card.add(btnConfig);

        JButton btnSair =
                criarBotao(
                        "Sair",
                        ""
                );

        btnSair.setBounds(
                125,
                285,
                240,
                45
        );

        card.add(btnSair);

        btnJogar.addActionListener(e -> {

            dispose();

            new TelaSala(
                    nome,
                    Dificuldade.FACIL
            );
        });

        btnBot.addActionListener(e -> {

            dispose();

            new TelaJogoBot(
                    nome,
                    Dificuldade.FACIL
            );
        });

        btnTutorial.addActionListener(
                e ->
                        new TelaTutorial()
        );

        btnConfig.addActionListener(
                e ->
                        MensagemUI.mostrar(
        this,
        "Mensagem",
        "⚙️ Em desenvolvimento"
)
        );

        btnSair.addActionListener(
                e ->
                        System.exit(0)
        );

        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);

        SwingUtilities.invokeLater(() -> {

            int largura =
                    getWidth();

            int altura =
                    getHeight();

            fundo.setBounds(
                    0,
                    0,
                    largura,
                    altura
            );

            icone.setBounds(
                    0,
                    altura / 2 - 290,
                    largura,
                    60
            );

            titulo.setBounds(
                    0,
                    altura / 2 - 235,
                    largura,
                    50
            );

            subtitulo.setBounds(
                    0,
                    altura / 2 - 185,
                    largura,
                    30
            );

            card.setBounds(
                    (largura - 490) / 2,
                    (altura - 330) / 2 + 55,
                    490,
                    330
            );

            fundo.repaint();
        });
    }

    private JButton criarBotao(
            String titulo,
            String subtitulo
    ) {

        String html =
                "<html><center>" +
                        titulo +

                        (
                                subtitulo.isEmpty()
                                        ? ""
                                        : "<br><span style='font-size:9px'>"
                                        + subtitulo +
                                        "</span>"
                        )

                        +

                        "</center></html>";

        JButton b =
                new JButton(
                        html
                );

        Color vermelho =
                new Color(
                        255,
                        50,
                        70
                );

        Color hover =
                new Color(
                        255,
                        75,
                        95
                );

        b.setBackground(vermelho);

        b.setForeground(
                Color.WHITE
        );

        b.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        b.setFocusPainted(false);

        b.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        b.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        b.setBackground(
                                hover
                        );
                    }

                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        b.setBackground(
                                vermelho
                        );
                    }
                }
        );

        return b;
    }
}