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

        Color vinho = new Color(125, 15, 35);

        setTitle("Sala Online - Dominó Químico");
        setSize(700, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("SALA ONLINE", SwingConstants.CENTER);
        titulo.setBounds(0, 25, 700, 45);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(vinho);
        add(titulo);

        JPanel card = new JPanel(null);
        card.setBounds(120, 85, 460, 260);
        card.setBackground(Color.WHITE);
        add(card);

        lblCodigo = new JLabel("Código da sala: ---", SwingConstants.CENTER);
        lblCodigo.setBounds(30, 10, 400, 35);
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 18));
        lblCodigo.setForeground(vinho);
        card.add(lblCodigo);

        lblStatus = new JLabel("Crie ou entre em uma sala", SwingConstants.CENTER);
        lblStatus.setBounds(30, 48, 400, 30);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        lblStatus.setForeground(Color.DARK_GRAY);
        card.add(lblStatus);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(80, 88, 300, 38);
        txtCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        txtCodigo.setFont(new Font("Arial", Font.BOLD, 18));
        txtCodigo.setForeground(vinho);
        txtCodigo.setBorder(BorderFactory.createLineBorder(vinho, 2));
        card.add(txtCodigo);

        JButton btnCriar = criarBotao("Criar sala");
        btnCriar.setBounds(115, 138, 230, 35);
        card.add(btnCriar);

        JButton btnEntrar = criarBotao("Entrar na sala");
        btnEntrar.setBounds(115, 183, 230, 35);
        card.add(btnEntrar);

        JButton btnIniciar = criarBotao("Iniciar jogo");
        btnIniciar.setBounds(115, 228, 230, 35);
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

        setVisible(true);
    }

    private JButton criarBotao(String texto) {

    JButton botao = new JButton(texto);

    botao.setPreferredSize(
            new Dimension(
                    145,
                    52
            )
    );

    botao.setBackground(
            new Color(
                    220,
                    0,
                    35
            )
    );

    botao.setForeground(
            Color.WHITE
    );

    botao.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    16
            )
    );

    botao.setFocusPainted(false);

    botao.setBorder(
            BorderFactory.createCompoundBorder(

                    BorderFactory.createLineBorder(
                            new Color(
                                    255,
                                    50,
                                    70
                            ),
                            3
                    ),

                    BorderFactory.createEmptyBorder(
                            8,
                            20,
                            8,
                            20
                    )
            )
    );

    botao.setCursor(
            Cursor.getPredefinedCursor(
                    Cursor.HAND_CURSOR
            )
    );

    botao.setOpaque(true);

    botao.addMouseListener(
            new java.awt.event.MouseAdapter() {

                public void mouseEntered(
                        java.awt.event.MouseEvent e
                ) {

                    botao.setBackground(
                            new Color(
                                    255,
                                    30,
                                    60
                            )
                    );

                    botao.setBounds(
                            botao.getX(),
                            botao.getY() - 2,
                            botao.getWidth(),
                            botao.getHeight()
                    );
                }

                public void mouseExited(
                        java.awt.event.MouseEvent e
                ) {

                    botao.setBackground(
                            new Color(
                                    220,
                                    0,
                                    35
                            )
                    );

                    botao.setBounds(
                            botao.getX(),
                            botao.getY() + 2,
                            botao.getWidth(),
                            botao.getHeight()
                    );
                }
            }
    );

    return botao;
}

    private void iniciarTimer() {

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, e -> {

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


