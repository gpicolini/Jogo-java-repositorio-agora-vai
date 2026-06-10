package jogo;

import java.awt.*;
import javax.swing.JPanel;

public class PainelMao extends JPanel {

    private Partida partida;
    private int indiceSelecionado = -1;

    public PainelMao(Partida partida) {

        this.partida = partida;

        setPreferredSize(new Dimension(0, 140));
        setBackground(new Color(35, 35, 35));

        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int x = 30;
                int y = 20;

                for (int i = 0; i < partida.getJogadorAtual().getMao().size(); i++) {

                    Rectangle r = new Rectangle(x, y, 140, 70);

                    if (r.contains(e.getPoint())) {
                        indiceSelecionado = i;
                        repaint();
                        break;
                    }

                    x += 160;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int x = 30;
        int yBase = 20;

        for (int i = 0; i < partida.getJogadorAtual().getMao().size(); i++) {

            Peca p = partida.getJogadorAtual().getMao().get(i);

            int y = yBase;

            if (i == indiceSelecionado) {
                y -= 15;
                g2.setColor(new Color(255, 255, 150));
            } else {
                g2.setColor(Color.WHITE);
            }

            g2.fillRoundRect(x, y, 140, 70, 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, y, 140, 70, 10, 10);

            g2.drawLine(
                    x + 70,
                    y,
                    x + 70,
                    y + 70
            );

            String esquerda = p.getLadoEsquerdo();
            String direita = p.getLadoDireito();

            FontMetrics fm = g2.getFontMetrics();

            int textoY =
                    y + ((70 - fm.getHeight()) / 2)
                    + fm.getAscent();

            int textoEsqX =
                    x + (70 - fm.stringWidth(esquerda)) / 2;

            int textoDirX =
                    x + 70
                    + (70 - fm.stringWidth(direita)) / 2;

            g2.drawString(esquerda, textoEsqX, textoY);
            g2.drawString(direita, textoDirX, textoY);

            x += 160;
        }
    }

    public int getIndiceSelecionado() {
        return indiceSelecionado;
    }

    public void limparSelecao() {
        indiceSelecionado = -1;
        repaint();
    }
}