package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaJogo extends JFrame {

    private Partida partida;

    private PainelMesa painelMesa;
    private PainelMao painelMao;

    private JLabel lblPontuacao;
    private JLabel lblMensagem;
    private JLabel lblVez;

    private javax.sound.sampled.Clip musicaFundo;
    private boolean musicaLigada = true;

    private float volumeMusica = 0.5f;
    private float volumeEfeitos = 0.7f;

    private JSlider sliderVolume;

    private int xp = 0;
    private int xpParaProximoNivel = 3;

    private Dificuldade dificuldade;

    public TelaJogo(String nomeJogador, Dificuldade dificuldade) {

        this.dificuldade = dificuldade;
        this.partida = new Partida(nomeJogador, dificuldade);

        setTitle("🧪 Dominó Químico");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(35, 35, 35));

        JLabel titulo = new JLabel("🧪 DOMINÓ QUÍMICO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        lblVez = new JLabel("", SwingConstants.CENTER);
        lblVez.setFont(new Font("Arial", Font.BOLD, 18));
        lblVez.setForeground(Color.CYAN);

        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 18));
        lblMensagem.setForeground(Color.WHITE);

        JPanel painelTopo = new JPanel(new GridLayout(3, 1));
        painelTopo.setBackground(new Color(35, 35, 35));
        painelTopo.add(titulo);
        painelTopo.add(lblVez);
        painelTopo.add(lblMensagem);

        add(painelTopo, BorderLayout.NORTH);

        painelMesa = new PainelMesa(partida);
        add(painelMesa, BorderLayout.CENTER);

        painelMao = new PainelMao(partida);

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

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(painelMao, BorderLayout.CENTER);
        painelInferior.add(painelSul, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        btnEsquerda.addActionListener(e -> jogar(false));
        btnDireita.addActionListener(e -> jogar(true));

        btnComprar.addActionListener(e -> {
            boolean comprou = partida.comprarPeca();

            if (comprou) {
                painelMao.limparSelecao();
                tocarSom("/jogo/sounds/effects.wav/compra.wav");
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "❌ Sem compras restantes!"
                );
            }

            atualizarTela();
        });

        btnMute.addActionListener(e -> {
            musicaLigada = !musicaLigada;

            if (musicaFundo != null) {
                if (musicaLigada) {
                    musicaFundo.start();
                    btnMute.setText("🔊");
                } else {
                    musicaFundo.stop();
                    btnMute.setText("🔇");
                }
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

    private void jogar(boolean direita) {

        int indice = painelMao.getIndiceSelecionado();

        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma peça!");
            return;
        }

        boolean sucesso = partida.jogarPeca(indice, direita);
        painelMao.limparSelecao();

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

        boolean mudouFase = partida.atualizarFaseSeNecessario();

        if (mudouFase) {
            JOptionPane.showMessageDialog(
                    this,
                    "🎉 " + partida.getUltimoVencedorFase() +
                            " venceu a fase!\n\n" +
                            "Fase " + partida.getFase() + " iniciada!"
            );

            lblMensagem.setText("🚀 Fase " + partida.getFase());
            lblMensagem.setForeground(Color.CYAN);
        }

        atualizarTela();
        verificarFim();
    }

    private void verificarFim() {

        if (partida.terminouJogo()) {

            partida.finalizar();

            if (musicaFundo != null) musicaFundo.stop();

            tocarSom("/jogo/sounds/vitoria.wav/musicavitoria.wav");

            StringBuilder sb = new StringBuilder();

            sb.append("🏆 FIM DE JOGO!\n\n");
            sb.append("Vencedor: ").append(partida.getNomeVencedor()).append("\n\n");

            for (int i = 0; i < partida.getJogadores().size(); i++) {
                sb.append(partida.getJogadores().get(i).getNome())
                  .append(": ")
                  .append(partida.getPontosJogador(i))
                  .append(" pontos\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    private void atualizarTela() {

        painelMao.repaint();
        painelMesa.repaint();

        lblVez.setText(
                "🎮 Vez de: " +
                        partida.getJogadorAtual().getNome()
        );

        StringBuilder placar = new StringBuilder();

        for (int i = 0; i < partida.getJogadores().size(); i++) {
            placar.append(partida.getJogadores().get(i).getNome())
                  .append(": ")
                  .append(partida.getPontosJogador(i));

            if (i < partida.getJogadores().size() - 1) {
                placar.append(" | ");
            }
        }

        placar.append(" | Compras: ")
              .append(partida.getComprasRestantes());

        lblPontuacao.setText(placar.toString());
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