package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaJogoOnline extends JFrame {

    private String nomeJogador;
    private String codigoSala;
    private int minhaOrdem;
    private Dificuldade dificuldade;

    private PainelMaoOnline painelMao;
    private PainelMesaOnline painelMesa;
    private PartidaOnlineDAO dao;

    private EstadoPartidaOnline estado;

    private boolean atualizando = false;

    private JLabel lblInfo;
    private JLabel lblVez;

    private Timer timer;

    public TelaJogoOnline(String nomeJogador, Dificuldade dificuldade, String codigoSala, int minhaOrdem) {

        this.nomeJogador = nomeJogador;
        this.dificuldade = dificuldade;
        this.codigoSala = codigoSala;
        this.minhaOrdem = minhaOrdem;
        this.dao = new PartidaOnlineDAO();

        this.estado =
        new EstadoPartidaOnline(
                codigoSala,
                minhaOrdem,
                dao.getQuantidadeJogadores(codigoSala)
        );

        

        setTitle("");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        painelMesa = new PainelMesaOnline(
        dao,
        codigoSala,
        minhaOrdem,
        nomeJogador
);

painelMao = new PainelMaoOnline(
        dao,
        codigoSala,
        minhaOrdem
);

// NOVO
estado.carregarMesa(
        dao.buscarMesa(
                codigoSala
        )
);

estado.carregarMao(
        dao.buscarPecasJogador(
                codigoSala,
                minhaOrdem
        )
);

painelMao.setOpaque(false);

        painelMao.setBackground(
        new Color(
                0,
                0,
                0,
                0
        )
);

        JLayeredPane camada = new JLayeredPane();

        camada.add(painelMesa, Integer.valueOf(0));
        camada.add(painelMao, Integer.valueOf(1));
        
        JPanel painelSul = new JPanel(new GridBagLayout());
        painelSul.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 18, 0, 18);

        JButton btnEsquerda = criarBotao("Esquerda");
        JButton btnDireita = criarBotao("Direita");
        JButton btnComprar = criarBotao("Comprar");
        JButton btnSair = criarBotao("Sair");

        painelSul.add(btnEsquerda, gbc);
        painelSul.add(btnDireita, gbc);
        painelSul.add(btnComprar, gbc);
        painelSul.add(btnSair, gbc);

        camada.add(painelSul, Integer.valueOf(2));

        JPanel painelTopo = new JPanel(new GridLayout(2, 1));
        painelTopo.setOpaque(false);

        lblInfo = new JLabel("", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblInfo.setForeground(Color.CYAN);

        lblVez = new JLabel("", SwingConstants.CENTER);
        lblVez.setFont(new Font("Segoe UI", Font.BOLD, 20));

        painelTopo.add(lblInfo);
        painelTopo.add(lblVez);

        camada.add(painelTopo, Integer.valueOf(3));

        add(camada, BorderLayout.CENTER);

        camada.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                ajustarCamadas(camada, painelTopo, painelSul);
            }
        });

        btnEsquerda.addActionListener(e -> jogar(false));
        btnDireita.addActionListener(e -> jogar(true));

        btnComprar.addActionListener(e -> {

    if (!dao.ehMinhaVez(codigoSala, minhaOrdem)) {

        JOptionPane.showMessageDialog(
                this,
                "Ainda não é sua vez!"
        );

        return;
    }

    lblVez.setText("🃏 Comprando...");

    new Thread(() -> {

        Peca p =
                dao.comprarDoMonte(
                        codigoSala
                );

        SwingUtilities.invokeLater(() -> {

            if (p == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Monte vazio!"
                );

                return;
            }

                dao.adicionarPecaJogador(
                codigoSala,
            minhaOrdem,
                        p
);

        painelMao.atualizarMao();

        painelMao.revalidate();

        painelMao.repaint();

        lblVez.setText(
        "✅ Peça comprada"
);

        });

    }).start();

});

        btnSair.addActionListener(e -> {

    if (timer != null) {
        timer.stop();
    }

            

    dispose();
    });

        getRootPane().registerKeyboardAction(

        e -> {

            

            dispose();

        },

        KeyStroke.getKeyStroke("ESCAPE"),

        JComponent.WHEN_IN_FOCUSED_WINDOW
);

        atualizarTela();
        
        painelMao.atualizarMao();

        timer = new Timer(700, e -> atualizarTela());
        timer.start();

        setVisible(true);

        SwingUtilities.invokeLater(() ->
                ajustarCamadas(camada, painelTopo, painelSul)
        );
    }

    private void jogar(boolean direita) {

    int idPeca =
            painelMao.getIdPecaSelecionada();

    Peca pecaSelecionada =
            painelMao.getPecaSelecionada();

    if (
            idPeca == -1
            ||
            pecaSelecionada == null
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Selecione uma peça!"
        );

        return;
    }

    if (
            !dao.ehMinhaVez(
                    codigoSala,
                    minhaOrdem
            )
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Ainda não é sua vez!"
        );


    

   

        return;
    }

    // UI INSTANTÂNEA
    painelMao.limparSelecao();

    painelMao.removerPecaSelecionadaLocal();

    

    painelMesa.adicionarPecaLocal(
            pecaSelecionada
    );

    painelMesa.repaint();

    lblVez.setText(
            "⏳ Salvando..."
    );

    lblVez.setForeground(
            Color.ORANGE
    );

    // BANCO EM PARALELO
    new Thread(() -> {

        boolean sucesso =
                dao.jogarPecaNaMesa(
                        codigoSala,
                        idPeca,
                        direita
                );

        SwingUtilities.invokeLater(() -> {

            if (sucesso) {

                lblVez.setText(
                        "⏳ Próximo jogador"
                );

                lblVez.setForeground(
                        Color.YELLOW
                );

            } else {

                painelMao.atualizarMao();

                atualizarTela();

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao jogar!"
                );
            }

        });

    }).start();
}



    private void atualizarTela() {

    if (atualizando) return;

    atualizando = true;

    new Thread(() -> {

        int jogadorAtual = dao.getIndiceJogadorAtual(codigoSala);
        int qtdJogadores = dao.getQuantidadeJogadores(codigoSala);
        java.util.ArrayList<Peca> pecasMesa = dao.buscarMesa(codigoSala);

        boolean minhaVez = jogadorAtual == minhaOrdem;

        SwingUtilities.invokeLater(() -> {

            lblInfo.setText(
                    "Sala: " + codigoSala +
                            " | Você: Jogador " + (minhaOrdem + 1) +
                            " | Nome: " + nomeJogador +
                            " | Jogadores: " + qtdJogadores
            );

            if (minhaVez) {

                        lblVez.setText(
                            "✅ É sua vez!"
                                            );

                lblVez.setForeground(
                    new Color(
                            0,
                            220,
                            0
                                )
                                            );

                                    }
                 else {
                lblVez.setText("⏳ Aguardando Jogador " + (jogadorAtual + 1));
                lblVez.setForeground(Color.YELLOW);
            }

            if (!pecasMesa.isEmpty()) {

            painelMesa.atualizarCache(
            qtdJogadores,
            jogadorAtual,
            pecasMesa
    );

}

            atualizando = false;
        });

    }).start();
}

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setPreferredSize(new Dimension(145, 52));
        botao.setBackground(new Color(220, 0, 35));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 40, 70), 3),
                        BorderFactory.createEmptyBorder(8, 20, 8, 20)
                )
        );
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(255, 40, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(220, 0, 35));
            }
        });

        return botao;
    }

    private void ajustarCamadas(
            JLayeredPane camada,
            JPanel painelTopo,
            JPanel painelSul
    ) {
        int largura = camada.getWidth();
        int altura = camada.getHeight();

        painelMesa.setBounds(0, 0, largura, altura);

        painelTopo.setBounds(
                0,
                18,
                largura,
                70
        );

        painelMao.setBounds(
        0,
        altura - 260,
        largura,
        220
);

        painelSul.setBounds(
        0,
        altura - 105,
        largura,
        95
);

        painelSul.revalidate();
        painelSul.repaint();
    }
}