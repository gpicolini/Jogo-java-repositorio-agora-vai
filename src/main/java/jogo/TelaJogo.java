package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaJogo extends JFrame {

    private Partida partida;

    private JTextArea areaTabuleiro;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPecas;

    private JLabel lblPontuacao;
    private JLabel lblMensagem;

    // ÁUDIO
    private javax.sound.sampled.Clip musicaFundo;
    private boolean musicaLigada = true;

    private float volumeMusica = 0.5f;
    private float volumeEfeitos = 0.7f;

    private JSlider sliderVolume;

    // PROGRESSÃO
    private int xp = 0;
    private int xpParaProximoNivel = 3;

    private Dificuldade dificuldade;

    // ===================== CONSTRUTOR =====================
    public TelaJogo(String nomeJogador, Dificuldade dificuldade) {

        this.dificuldade = dificuldade;
        this.partida = new Partida(nomeJogador, dificuldade);

        setTitle("🧪 Dominó Químico");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(35, 35, 35));

        // TOPO
        JLabel titulo = new JLabel("🧪 DOMINÓ QUÍMICO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 18));
        lblMensagem.setForeground(Color.WHITE);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(35, 35, 35));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painelTopo.add(lblMensagem, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);

        // TABULEIRO
        areaTabuleiro = new JTextArea();
        areaTabuleiro.setEditable(false);
        areaTabuleiro.setFont(new Font("Consolas", Font.BOLD, 24));
        areaTabuleiro.setBackground(new Color(45, 45, 45));
        areaTabuleiro.setForeground(Color.WHITE);

        add(new JScrollPane(areaTabuleiro), BorderLayout.CENTER);

        // MÃO
        modeloLista = new DefaultListModel<>();
        listaPecas = new JList<>(modeloLista);

        listaPecas.setFont(new Font("Arial", Font.BOLD, 16));
        listaPecas.setFixedCellHeight(35);
        listaPecas.setBackground(new Color(50, 50, 50));
        listaPecas.setForeground(Color.WHITE);

        add(new JScrollPane(listaPecas), BorderLayout.WEST);

        // CONTROLES
        JPanel painelSul = new JPanel();

        JButton btnEsquerda = new JButton("Esquerda");
        JButton btnDireita = new JButton("Direita");
        JButton btnComprar = new JButton("Comprar");
        JButton btnMute = new JButton("🔊");

        lblPontuacao = new JLabel("Pontuação: 0");
        sliderVolume = new JSlider(0, 100, 70);

        painelSul.add(btnEsquerda);
        painelSul.add(btnDireita);
        painelSul.add(btnComprar);
        painelSul.add(lblPontuacao);
        painelSul.add(sliderVolume);
        painelSul.add(btnMute);

        add(painelSul, BorderLayout.SOUTH);

        // EVENTOS
        btnEsquerda.addActionListener(e -> jogar(false));
        btnDireita.addActionListener(e -> jogar(true));

        btnComprar.addActionListener(e -> {
            partida.comprarPeca();
            tocarSom("/jogo/sounds/effects.wav/compra.wav");
            atualizarTela();
        });

        btnMute.addActionListener(e -> {
            musicaLigada = !musicaLigada;

            if (musicaFundo != null) {
                if (musicaLigada) musicaFundo.start();
                else musicaFundo.stop();
            }
        });

        sliderVolume.addChangeListener(e -> {
            volumeEfeitos = sliderVolume.getValue() / 100f;
            volumeMusica = volumeEfeitos;
            ajustarVolumeMusica();
        });

        atualizarTela();
        tocarMusica("/jogo/sounds/music.wav/fundo.wav");

        setVisible(true);
    }

    // ===================== JOGAR =====================
    private void jogar(boolean direita) {

    int indice = listaPecas.getSelectedIndex();

    if (indice == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma peça!");
        return;
    }

    boolean sucesso = partida.jogarPeca(indice, direita);

    if (sucesso) {

        lblMensagem.setText("✅ Jogada correta!");
        lblMensagem.setForeground(new Color(0, 180, 0));

        tocarSom("/jogo/sounds/effects.wav/acerto.wav");

        if (dificuldade == Dificuldade.FACIL) xp += 2;
        else xp += 1;

        if (xp >= xpParaProximoNivel) {
            xp = 0;
            xpParaProximoNivel += 2;

            lblMensagem.setText("🔥 LEVEL UP!");
            lblMensagem.setForeground(Color.YELLOW);
        }

    } else {

        lblMensagem.setText("❌ Jogada inválida!");
        lblMensagem.setForeground(Color.RED);

        tocarSom("/jogo/sounds/effects.wav/erro.wav");
    }

    // verifica mudança de fase
    boolean mudouFase = partida.atualizarFaseSeNecessario();

    if (mudouFase) {

        JOptionPane.showMessageDialog(
                this,
                "🎉 Fase " + partida.getFase() + " iniciada!"
        );

        lblMensagem.setText("🚀 Fase " + partida.getFase());
        lblMensagem.setForeground(Color.CYAN);
    }

    atualizarTela();
    verificarFim();
}
    // ===================== FIM =====================
    private void verificarFim() {

        if (partida.terminouJogo()) {

            partida.finalizar();

            if (musicaFundo != null) musicaFundo.stop();

            tocarSom("/jogo/sounds/vitoria.wav/musicavitoria.wav");

            JOptionPane.showMessageDialog(
                    this,
                    "🏆 FIM DE JOGO!\nPontuação: " +
                    partida.getPontuacao().getPontuacaoTotal()
            );
        }
    }

    // ===================== ATUALIZAR =====================
    private void atualizarTela() {

    modeloLista.clear();

    for (Peca p : partida.getJogador().getMao()) {

        if (partida.getFase() == 1) {

            modeloLista.addElement(
                    p.getSubstancia() + " - " + p.getFuncao()
            );

        } else {

            modeloLista.addElement(
                    p.getSubstancia()
            );
        }
    }

    StringBuilder sb = new StringBuilder();

    for (Peca p : partida.getTabuleiro().getPecas()) {
        sb.append("[")
          .append(p.getSubstancia())
          .append("] ");
    }

    areaTabuleiro.setText(sb.toString());

    lblPontuacao.setText(
            "Pontuação: " +
            partida.getPontuacao().getPontuacaoTotal()
    );
}

    // ===================== SOM =====================
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

                float dB = (float)(Math.log10(Math.max(volumeEfeitos, 0.0001)) * 20);

                ((javax.sound.sampled.FloatControl)
                        clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN))
                        .setValue(dB);

                clip.start();

            } catch (Exception e) {
                System.out.println("Erro som: " + e.getMessage());
            }
        }).start();
    }

    // ===================== MÚSICA =====================
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
            float dB = (float)(Math.log10(volume) * 20);

            gain.setValue(dB);

        } catch (Exception e) {
            System.out.println("Erro volume música");
        }
    }
}