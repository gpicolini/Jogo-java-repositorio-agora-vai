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

    private javax.sound.sampled.Clip musicaFundo;

   
    private float volumeMusica = 0.5f;
    private float volumeEfeitos = 0.7f;
    private boolean musicaLigada = true;

    public TelaJogo(String nomeJogador) {

        partida = new Partida(nomeJogador);

        setTitle("🧪 Dominó Químico");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(35, 35, 35));

        // ===================== TÍTULO =====================
        JLabel titulo = new JLabel("🧪 DOMINÓ QUÍMICO");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // ===================== MENSAGEM =====================
        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 18));
        lblMensagem.setForeground(Color.WHITE);

        // ===================== TOPO =====================
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BorderLayout());
        painelTopo.setBackground(new Color(35, 35, 35));

        painelTopo.add(titulo, BorderLayout.NORTH);
        painelTopo.add(lblMensagem, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);

        // ===================== TABULEIRO =====================
        areaTabuleiro = new JTextArea();
        areaTabuleiro.setEditable(false);
        areaTabuleiro.setFont(new Font("Consolas", Font.BOLD, 24));
        areaTabuleiro.setBackground(new Color(45, 45, 45));
        areaTabuleiro.setForeground(Color.WHITE);

        add(new JScrollPane(areaTabuleiro), BorderLayout.CENTER);

        // ===================== LISTA PEÇAS =====================
        modeloLista = new DefaultListModel<>();
        listaPecas = new JList<>(modeloLista);

        listaPecas.setFont(new Font("Arial", Font.BOLD, 16));
        listaPecas.setFixedCellHeight(35);
        listaPecas.setBackground(new Color(50, 50, 50));
        listaPecas.setForeground(Color.WHITE);
        listaPecas.setSelectionBackground(new Color(70, 130, 180));

        add(new JScrollPane(listaPecas), BorderLayout.WEST);

        // ===================== PAINEL SUL =====================
        JPanel painelSul = new JPanel();

        JButton btnEsquerda = new JButton("Jogar Esquerda");
        JButton btnDireita = new JButton("Jogar Direita");
        JButton btnComprar = new JButton("Comprar");

        JButton btnMute = new JButton("🔊");

        JSlider sliderVolume = new JSlider(0, 100, 70);
        sliderVolume.setBackground(new Color(50, 50, 50));

        lblPontuacao = new JLabel("Pontuação: 0");

        painelSul.add(btnEsquerda);
        painelSul.add(btnDireita);
        painelSul.add(btnComprar);
        painelSul.add(lblPontuacao);
        painelSul.add(sliderVolume);
        painelSul.add(btnMute);

        add(painelSul, BorderLayout.SOUTH);

        // ===================== SLIDER =====================
        sliderVolume.addChangeListener(e -> {
            volumeEfeitos = sliderVolume.getValue() / 100f;
            volumeMusica = volumeEfeitos;
            ajustarVolumeMusica();
        });

        // ===================== MUTE =====================
        btnMute.addActionListener(e -> {
            musicaLigada = !musicaLigada;

            if (musicaLigada) {
                btnMute.setText("🔊");
                if (musicaFundo != null) musicaFundo.start();
            } else {
                btnMute.setText("🔇");
                if (musicaFundo != null) musicaFundo.stop();
            }
        });

        // ===================== AÇÕES =====================
        btnEsquerda.addActionListener(e -> jogar(false));
        btnDireita.addActionListener(e -> jogar(true));

        btnComprar.addActionListener(e -> {
            partida.comprarPeca();
            
            tocarSom("/jogo/sounds/effects.wav/compra.wav");
            atualizarTela();
        });

        atualizarTela();

       
        tocarMusicaLoop("/jogo/sounds/music.wav/fundo.wav");

        setVisible(true);
    }

    // ===================== MÚSICA LOOP =====================
    private void tocarMusicaLoop(String caminho) {
        try {
            java.io.InputStream audioSrc = getClass().getResourceAsStream(caminho);

            
            if (audioSrc == null) {
                System.out.println("Aviso: Música não encontrada no caminho -> " + caminho);
                return;
            }

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

    // ===================== VOLUME MÚSICA =====================
    private void ajustarVolumeMusica() {
        if (musicaFundo == null) return;

        try {
            javax.sound.sampled.FloatControl gain =
                    (javax.sound.sampled.FloatControl)
                            musicaFundo.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);

            float volume = musicaLigada ? volumeMusica : 0.0001f;

            float dB = (float) (Math.log10(Math.max(volume, 0.0001)) * 20);
            gain.setValue(dB);

        } catch (Exception e) {
            System.out.println("Erro volume música");
        }
    }

    // ===================== SOM EFEITOS =====================
    private void tocarSom(String caminho) {
        new Thread(() -> {
            try {
                java.io.InputStream audioSrc = getClass().getResourceAsStream(caminho);

                
                if (audioSrc == null) {
                    System.out.println("Aviso: Efeito sonoro não encontrado -> " + caminho);
                    return;
                }

                javax.sound.sampled.AudioInputStream audioIn =
                        javax.sound.sampled.AudioSystem.getAudioInputStream(audioSrc);

                javax.sound.sampled.Clip clip =
                        javax.sound.sampled.AudioSystem.getClip();

                clip.open(audioIn);

                javax.sound.sampled.FloatControl gain =
                        (javax.sound.sampled.FloatControl)
                                clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);

                float dB = (float) (Math.log10(Math.max(volumeEfeitos, 0.0001)) * 20);
                gain.setValue(dB);

                clip.start();

            } catch (Exception e) {
                System.out.println("Erro som: " + e.getMessage());
            }
        }).start();
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

        } else {
            lblMensagem.setText("❌ Peça incompatível!");
            lblMensagem.setForeground(new Color(220, 50, 50));

            
            tocarSom("/jogo/sounds/effects.wav/erro.wav");
        }

        atualizarTela();
        verificarFim();
    }

    // ===================== FIM =====================
    private void verificarFim() {

        if (partida.terminou()) {

            partida.finalizar();

            if (musicaFundo != null && musicaFundo.isRunning()) {
                musicaFundo.stop();
            }

         
            tocarSom("/jogo/sounds/vitoria.wav/musicavitoria.wav");

            JOptionPane.showMessageDialog(
                    this,
                    "VOCÊ VENCEU!\n\nPontuação: "
                            + partida.getPontuacao().getPontuacaoTotal()
            );
        }
    }

    // ===================== ATUALIZAR =====================
   private void atualizarTela() {

        modeloLista.clear();

        for (Peca p : partida.getJogador().getMao()) {
            modeloLista.addElement(
                    "🧪 " + p.getSubstancia() + " (" + p.getFuncao() + ")"
            );
        }

        StringBuilder texto = new StringBuilder();

        for (Peca p : partida.getTabuleiro().getPecas()) {
            texto.append("🧪 [")
                    .append(p.getSubstancia())
                    .append("] ");
        }

        areaTabuleiro.setText(texto.toString());

        lblPontuacao.setText(
                "Pontuação: " + partida.getPontuacao().getPontuacaoTotal()
        );
    }
}