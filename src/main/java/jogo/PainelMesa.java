package jogo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelMesa extends JPanel {

    private Partida partida;

    public PainelMesa(Partida partida) {
        this.partida = partida;
        setBackground(new Color(35, 35, 35));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // mesa
        g2.setColor(new Color(0, 120, 0));
        g2.fillRoundRect(
                20,
                20,
                getWidth() - 40,
                getHeight() - 40,
                30,
                30
        );

        // ==========================
        // DESENHAR PEÇAS DA MESA
        // ==========================

        if (partida == null ||
            partida.getTabuleiro() == null ||
            partida.getTabuleiro().getPecas().isEmpty()) {
            return;
        }

        List<Peca> pecas =
                partida.getTabuleiro().getPecas();

        int larguraPeca = 140;
        int alturaPeca = 70;

        int xCentro = getWidth() / 2;
        int y = getHeight() / 2;

        int inicioX =
                xCentro - ((pecas.size() * larguraPeca) / 2);

        for (int i = 0; i < pecas.size(); i++) {

            Peca p = pecas.get(i);

            int x = inicioX + (i * larguraPeca);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(
                    x,
                    y,
                    larguraPeca,
                    alturaPeca,
                    10,
                    10
            );

            g2.setColor(Color.BLACK);
            g2.drawRoundRect(
                    x,
                    y,
                    larguraPeca,
                    alturaPeca,
                    10,
                    10
            );

            g2.drawLine(
                    x + larguraPeca / 2,
                    y,
                    x + larguraPeca / 2,
                    y + alturaPeca
            );

            g2.setFont(new Font("Arial", Font.BOLD, 12));

            
            String esquerda = p.getLadoEsquerdo();
    String direita = p.getLadoDireito();

    FontMetrics fm = g2.getFontMetrics();

// lado esquerdo
    int textoEsqX =
        x + (larguraPeca / 2 - fm.stringWidth(esquerda)) / 2;

    int textoY =
        y + ((alturaPeca - fm.getHeight()) / 2)
        + fm.getAscent();

    g2.drawString(esquerda, textoEsqX, textoY);

// lado direito
    int textoDirX =
        x + larguraPeca / 2
        + (larguraPeca / 2 - fm.stringWidth(direita)) / 2;

    g2.drawString(direita, textoDirX, textoY);
        }
    }
}