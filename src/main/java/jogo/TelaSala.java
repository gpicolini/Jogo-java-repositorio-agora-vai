package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaSala extends JFrame {

    private String nomeJogador;
    private Dificuldade dificuldade;

    private PartidaOnlineDAO dao;

    private String codigoSala;
    private int minhaOrdem = -1;

    private boolean jogoAberto = false;

    private JLabel lblCodigo;
    private JLabel lblStatus;
    private JTextField txtCodigo;

    private Timer timer;

    public TelaSala(String nomeJogador, Dificuldade dificuldade) {

        this.nomeJogador = nomeJogador;
        this.dificuldade = dificuldade;
        this.dao = new PartidaOnlineDAO();

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

        JLabel icone = new JLabel("🌐", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 44));
        fundo.add(icone);

        JLabel titulo = new JLabel("SALA ONLINE", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);
        fundo.add(titulo);

        JLabel subtitulo = new JLabel("Crie uma sala ou entre pelo código", SwingConstants.CENTER);
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

        lblCodigo = new JLabel("Código da sala: ---", SwingConstants.CENTER);
        lblCodigo.setBounds(25, 18, 380, 32);
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCodigo.setForeground(new Color(125, 15, 35));
        card.add(lblCodigo);

        lblStatus = new JLabel("Crie ou entre em uma sala", SwingConstants.CENTER);
        lblStatus.setBounds(25, 52, 380, 28);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setForeground(new Color(50, 50, 50));
        card.add(lblStatus);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(65, 92, 300, 38);
        txtCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        txtCodigo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        txtCodigo.setForeground(new Color(125, 15, 35));
        txtCodigo.setBackground(Color.WHITE);
        txtCodigo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );
        card.add(txtCodigo);

        JButton btnCriar = criarBotao("Criar sala");
        btnCriar.setBounds(100, 145, 230, 36);
        card.add(btnCriar);

        JButton btnEntrar = criarBotao("Entrar na sala");
        btnEntrar.setBounds(100, 190, 230, 36);
        card.add(btnEntrar);

        JButton btnIniciar = criarBotao("Iniciar jogo");
        btnIniciar.setBounds(100, 235, 230, 36);
        btnIniciar.setEnabled(false);
        card.add(btnIniciar);

        btnCriar.addActionListener(e -> {

            codigoSala = dao.criarSala(dificuldade);

            if (codigoSala == null) {
                JOptionPane.showMessageDialog(this, "Erro ao criar sala!");
                return;
            }

            minhaOrdem = dao.entrarSala(codigoSala, nomeJogador);

            lblCodigo.setText("Código da sala: " + codigoSala);
            lblStatus.setText("Você criou a sala. Aguardando jogadores...");

            btnIniciar.setEnabled(true);
            iniciarTimer();
        });

        btnEntrar.addActionListener(e -> {

            codigoSala = txtCodigo.getText().trim();

            if (codigoSala.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o código da sala!");
                return;
            }

            minhaOrdem = dao.entrarSala(codigoSala, nomeJogador);

            if (minhaOrdem == -1) {
                JOptionPane.showMessageDialog(this, "Sala não encontrada!");
                return;
            }

            if (minhaOrdem == -2) {
                JOptionPane.showMessageDialog(this, "Sala cheia!");
                return;
            }

            if (minhaOrdem == -3) {
                JOptionPane.showMessageDialog(this, "Erro ao entrar na sala!");
                return;
            }

            lblCodigo.setText("Código da sala: " + codigoSala);
            lblStatus.setText("Jogador " + (minhaOrdem + 1) + " conectado. Aguardando início...");

            btnIniciar.setEnabled(false);
            iniciarTimer();
        });

        btnIniciar.addActionListener(e -> {

            dao.distribuirPecasSala(codigoSala);
            boolean iniciou = dao.iniciarSala(codigoSala);

            if (iniciou) {
                lblStatus.setText("Jogo iniciado!");
                abrirJogoOnline();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao iniciar sala!");
            }
        });

        getRootPane().registerKeyboardAction(
                e -> {
                    if (timer != null) timer.stop();
                    dispose();
                    new TelaMenu(nomeJogador);
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);

        SwingUtilities.invokeLater(() -> {

            int largura = getWidth();
            int altura = getHeight();

            fundo.setBounds(0, 0, largura, altura);

            icone.setBounds(0, altura / 2 - 255, largura, 55);
            titulo.setBounds(0, altura / 2 - 200, largura, 50);
            subtitulo.setBounds(0, altura / 2 - 150, largura, 28);

            card.setBounds(
                    (largura - 430) / 2,
                    (altura - 275) / 2 + 45,
                    430,
                    275
            );

            fundo.revalidate();
            fundo.repaint();
        });
    }

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        Color vermelho = new Color(255, 50, 70);
        Color hover = new Color(255, 75, 95);

        botao.setBackground(vermelho);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
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
                if (botao.isEnabled()) {
                    botao.setBackground(hover);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                if (botao.isEnabled()) {
                    botao.setBackground(vermelho);
                }
            }
        });

        return botao;
    }

    private void iniciarTimer() {

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(500, e -> {

            String status = dao.getStatusSala(codigoSala);
            int qtd = dao.getQuantidadeJogadores(codigoSala);

            lblStatus.setText("Jogadores: " + qtd + " | Status: " + status);

            if ("JOGANDO".equals(status)) {
                timer.stop();
                abrirJogoOnline();
            }
        });

        timer.start();
    }

    private void abrirJogoOnline() {

        if (jogoAberto) return;

        jogoAberto = true;

        if (timer != null) {
            timer.stop();
        }

        dispose();

        new TelaJogoOnline(
                nomeJogador,
                dificuldade,
                codigoSala,
                minhaOrdem
        );
    }
}