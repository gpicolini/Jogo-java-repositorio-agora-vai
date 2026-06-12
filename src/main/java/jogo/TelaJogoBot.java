package jogo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaJogoBot extends JFrame {

    private String nomeJogador;
    private Dificuldade dificuldade;

    private ArrayList<Peca> maoJogador = new ArrayList<>();
    private ArrayList<Peca> maoBot = new ArrayList<>();
    private ArrayList<Peca> monte = new ArrayList<>();
    private ArrayList<Peca> mesa = new ArrayList<>();

    private javax.sound.sampled.Clip  musicaFundo;
    private boolean musicaLigada = true;

    private float volumeMusica = 0.5f;
    private float volumeEfeitos = 0.7f;

    private PainelMesaBot painelMesa;
    private PainelMaoBot painelMao;

    private JLabel lblInfo;
    private JLabel lblVez;

    public TelaJogoBot(String nomeJogador, Dificuldade dificuldade) {

        this.nomeJogador = nomeJogador;
        this.dificuldade = dificuldade;

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        iniciarPartida();

        painelMesa = new PainelMesaBot();
        painelMao = new PainelMaoBot();

        painelMao.carregarPecas(maoJogador);

        JLayeredPane camada = new JLayeredPane();

        camada.add(painelMesa, Integer.valueOf(0));
        camada.add(painelMao, Integer.valueOf(1));

        JPanel painelTopo = new JPanel(new GridLayout(2, 1));
        painelTopo.setOpaque(false);

        lblInfo = new JLabel("", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblInfo.setForeground(Color.CYAN);

        lblVez = new JLabel("✅ Sua vez!", SwingConstants.CENTER);
        lblVez.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblVez.setForeground(new Color(0, 220, 0));

        painelTopo.add(lblInfo);
        painelTopo.add(lblVez);

        camada.add(painelTopo, Integer.valueOf(2));

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

        camada.add(painelSul, Integer.valueOf(3));

        add(camada, BorderLayout.CENTER);

        camada.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                
                
                
                ajustarCamadas(camada, painelTopo, painelSul);
            }
        });

        btnEsquerda.addActionListener(e -> jogarJogador(false));
        btnDireita.addActionListener(e -> jogarJogador(true));
        btnComprar.addActionListener(e -> comprarJogador());

        btnSair.addActionListener(e -> {

    if (musicaFundo != null) {
        musicaFundo.stop();
    }

    dispose();
    new TelaMenu(nomeJogador);
});

        getRootPane().registerKeyboardAction(
                e -> {
                    dispose();
                    new TelaMenu(nomeJogador);
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        atualizarTela();
        
        tocarMusica("/jogo/sounds/music.wav/fundo.wav");

        setVisible(true);

        SwingUtilities.invokeLater(() ->
                ajustarCamadas(camada, painelTopo, painelSul)
        );
    }

    private void iniciarPartida() {

        Monte m = new Monte(dificuldade);

        for (int i = 0; i < 7; i++) {

            Peca pJogador = m.comprarPeca();
            if (pJogador != null) {
                maoJogador.add(pJogador);
            }

            Peca pBot = m.comprarPeca();
            if (pBot != null) {
                maoBot.add(pBot);
            }
        }

        Peca p;

        while ((p = m.comprarPeca()) != null) {
            monte.add(p);
        }
    }

    private void jogarJogador(boolean direita) {

        Peca selecionada = painelMao.getPecaSelecionada();
        int indice = painelMao.getIndiceSelecionado();

        if (selecionada == null || indice == -1) {
            MensagemUI.mostrar(this, "Atenção", "Selecione uma peça!");
            return;
        }

        Peca ajustada;

        if (direita) {
            ajustada = ajustarPecaParaDireita(selecionada);
        } else {
            ajustada = ajustarPecaParaEsquerda(selecionada);
        }

        if (ajustada == null) {
            tocarSom("/jogo/sounds/effects.wav/erro.wav");
            MensagemUI.mostrar(this, "Jogada inválida", "Essa peça não encaixa!");
            return;
        }
        
        tocarSom("/jogo/sounds/effects.wav/acerto.wav"); 

        if (direita) {
            mesa.add(ajustada);
        } else {
            mesa.add(0, ajustada);
        }

        maoJogador.remove(indice);

        painelMao.carregarPecas(maoJogador);

        verificarFim();

        atualizarTela();

        lblVez.setText("🤖 Vez do Bot...");
        lblVez.setForeground(Color.YELLOW);

        Timer t = new Timer(1000, e -> jogarBot());
        t.setRepeats(false);
        t.start();
    }

    private void comprarJogador() {

        if (monte.isEmpty()) {
            MensagemUI.mostrar(this, "Monte vazio", "Não há mais peças no monte!");
            return;
        }

        Peca p = monte.remove(0);

        if (p != null) {
            maoJogador.add(p);
            tocarSom("/jogo/sounds/effects.wav/compra.wav");
        }

        painelMao.carregarPecas(maoJogador);

        atualizarTela();
    }

 private void jogarBot() {

    for (int i = 0; i < maoBot.size(); i++) {

        Peca p = maoBot.get(i);

        Peca direita = ajustarPecaParaDireita(p);

        if (direita != null) {

            mesa.add(direita);
            maoBot.remove(i);

            lblVez.setText("✅ Sua vez!");
            lblVez.setForeground(new Color(0, 220, 0));

            verificarFim();
            atualizarTela();
            return;
        }

        Peca esquerda = ajustarPecaParaEsquerda(p);

        if (esquerda != null) {

            mesa.add(0, esquerda);
            maoBot.remove(i);

            lblVez.setText("✅ Sua vez!");
            lblVez.setForeground(new Color(0, 220, 0));

            verificarFim();
            atualizarTela();
            return;
        }
    }

    if (!monte.isEmpty()) {

    Peca comprada = monte.remove(0);

    if (comprada != null) {
        maoBot.add(comprada);
    }

    lblVez.setText("🤖 Bot comprou uma peça");

    Timer tentarJogar = new Timer(600, e -> jogarBot());
    tentarJogar.setRepeats(false);
    tentarJogar.start();

    atualizarTela();
    return;

} else {

    lblVez.setText("🤖 Bot passou a vez");
}

    lblVez.setForeground(Color.YELLOW);

    Timer t = new Timer(900, e -> {
        lblVez.setText("✅ Sua vez!");
        lblVez.setForeground(new Color(0, 220, 0));
        atualizarTela();
    });

    t.setRepeats(false);
    t.start();

    atualizarTela();
}

private Peca ajustarPecaParaDireita(Peca p) {

    if (p == null) return null;

    if (mesa.isEmpty()) return p;

    Peca ultima = mesa.get(mesa.size() - 1);

    if (ultima == null) return null;

    String ponta = ultima.getLadoDireito();

    if (combinam(ponta, p.getLadoEsquerdo())) {
        return p;
    }

    if (combinam(ponta, p.getLadoDireito())) {
        return new Peca(p.getLadoDireito(), p.getLadoEsquerdo());
    }

    return null;
}

private Peca ajustarPecaParaEsquerda(Peca p) {

    if (p == null) return null;

    if (mesa.isEmpty()) return p;

    Peca primeira = mesa.get(0);

    if (primeira == null) return null;

    String ponta = primeira.getLadoEsquerdo();

    if (combinam(ponta, p.getLadoDireito())) {
        return p;
    }

    if (combinam(ponta, p.getLadoEsquerdo())) {
        return new Peca(p.getLadoDireito(), p.getLadoEsquerdo());
    }

    return null;
}

private boolean combinam(String a, String b) {

    if (a == null || b == null) return false;

    a = a.trim();
    b = b.trim();

    if (a.equalsIgnoreCase("Coringa") || b.equalsIgnoreCase("Coringa")) {
        return true;
    }

    if (a.equalsIgnoreCase(b)) return true;

    String funcaoA = getFuncao(a);
    String funcaoB = getFuncao(b);

    if (funcaoA.isEmpty() || funcaoB.isEmpty()) {
        return false;
    }

    return funcaoA.equalsIgnoreCase(funcaoB);
}
private String getFuncao(String valor) {

    if (valor == null) return "";

    valor = valor.trim();

    if (valor.equalsIgnoreCase("Ácido")) return "Ácido";
    if (valor.equalsIgnoreCase("Base")) return "Base";
    if (valor.equalsIgnoreCase("Óxido")) return "Óxido";
    if (valor.equalsIgnoreCase("Sal")) return "Sal";

    if (
            valor.equalsIgnoreCase("HCl") ||
            valor.equalsIgnoreCase("H2SO4") ||
            valor.equalsIgnoreCase("HNO3") ||
            valor.equalsIgnoreCase("H3PO4")
    ) {
        return "Ácido";
    }

    if (
            valor.equalsIgnoreCase("NaOH") ||
            valor.equalsIgnoreCase("KOH") ||
            valor.equalsIgnoreCase("LiOH") ||
            valor.equalsIgnoreCase("Ca(OH)2")
    ) {
        return "Base";
    }

    if (
            valor.equalsIgnoreCase("CO2") ||
            valor.equalsIgnoreCase("SO3") ||
            valor.equalsIgnoreCase("CaO") ||
            valor.equalsIgnoreCase("MgO")
    ) {
        return "Óxido";
    }

    if (
            valor.equalsIgnoreCase("NaCl") ||
            valor.equalsIgnoreCase("KBr") ||
            valor.equalsIgnoreCase("CaCO3") ||
            valor.equalsIgnoreCase("Na2SO4")
    ) {
        return "Sal";
    }

    return valor;
}

 
    private void verificarFim() {

    if (maoJogador.isEmpty()) {

        if (musicaFundo != null) {
            musicaFundo.stop();
        }

        tocarSom("/jogo/sounds/vitoria.wav/musicavitoria.wav");

        MensagemUI.mostrar(this, "Vitória!", nomeJogador + " venceu!");

        dispose();
        new TelaMenu(nomeJogador);
        return;
    }

    if (maoBot.isEmpty()) {

        if (musicaFundo != null) {
            musicaFundo.stop();
        }

        tocarSom("/jogo/sounds/vitoria.wav/musicavitoria.wav");

        MensagemUI.mostrar(this, "Fim de jogo", "Bot venceu!");

        dispose();
        new TelaMenu(nomeJogador);
    }
}

private void atualizarTela() {

    lblInfo.setText(
            "Modo Bot | Jogador: " + nomeJogador +
                    " | Bot: " + maoBot.size() +
                    " peças | Monte: " + monte.size()
    );

    painelMesa.atualizarMesa(
            mesa,
            maoBot.size(),
            monte.size()
    );

    painelMao.repaint();
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
                altura - 215,
                largura,
                130
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

    private void tocarSom(String caminho) {
    new Thread(() -> {
        try {
            java.io.InputStream audioSrc = getClass().getResourceAsStream(caminho);
            if (audioSrc == null) return;

            javax.sound.sampled.AudioInputStream audioIn =
                    javax.sound.sampled.AudioSystem.getAudioInputStream(audioSrc);

            javax.sound.sampled.Clip clip =
                    javax.sound.sampled.AudioSystem.getClip();

            clip.open(audioIn);

            float dB = (float) (Math.log10(Math.max(volumeEfeitos, 0.0001)) * 20);

            ((javax.sound.sampled.FloatControl)
                    clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN))
                    .setValue(dB);

            clip.start();

        } catch (Exception e) {
            System.out.println("Erro som: " + e.getMessage());
        }
    }).start();
}

private void tocarMusica(String caminho) {

    try {
        java.io.InputStream audioSrc = getClass().getResourceAsStream(caminho);
        if (audioSrc == null) return;

        javax.sound.sampled.AudioInputStream audioIn =
                javax.sound.sampled.AudioSystem.getAudioInputStream(audioSrc);

        musicaFundo = javax.sound.sampled.AudioSystem.getClip();
        musicaFundo.open(audioIn);

        ajustarVolumeMusica();
        musicaFundo.loop(javax.sound.sampled.Clip.LOOP_CONTINUOUSLY);
        musicaFundo.start();

    } catch (Exception e) {
        System.out.println("Erro música: " + e.getMessage());
    }
}

private void ajustarVolumeMusica() {

    if (musicaFundo == null) return;

    try {
        javax.sound.sampled.FloatControl gain =
                (javax.sound.sampled.FloatControl)
                        musicaFundo.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);

        float volume = Math.max(volumeMusica, 0.0001f);
        float dB = (float) (Math.log10(volume) * 20);

        gain.setValue(dB);

    } catch (Exception e) {
        System.out.println("Erro volume música");
    }
}

}