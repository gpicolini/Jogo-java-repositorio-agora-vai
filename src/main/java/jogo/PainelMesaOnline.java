package jogo;

import java.awt.*;
import javax.swing.*;

public class PainelMesaOnline extends JPanel {

    private PartidaOnlineDAO dao;
    private String codigoSala;
    private int minhaOrdem;
    private String nomeJogador;

    private int qtdJogadoresCache = 0;
    private int jogadorAtualCache = 0;

    private java.util.ArrayList<Peca> pecasMesaCache =
            new java.util.ArrayList<>();

    private Image fundoLab;

    private final Font fontePeca =
            new Font(
                    "Arial",
                    Font.BOLD,
                    12
            );

    public PainelMesaOnline(
            PartidaOnlineDAO dao,
            String codigoSala,
            int minhaOrdem,
            String nomeJogador
    ) {

        this.dao = dao;
        this.codigoSala = codigoSala;
        this.minhaOrdem = minhaOrdem;
        this.nomeJogador = nomeJogador;

        setBackground(
                new Color(
                        30,
                        30,
                        30
                )
        );

        try {

            java.net.URL url =
                    getClass().getResource(
                            "/jogo/images/lab.png"
                    );

            if (url != null) {

                fundoLab =
                        new ImageIcon(url)
                                .getImage();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (fundoLab != null) {

            g2.drawImage(
                    fundoLab,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );

        } else {

            g2.setColor(
                    new Color(
                            30,
                            30,
                            30
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );
        }

        int larguraMesa = 1120;
        int alturaMesa = 620;

        int xMesa =
                (getWidth() - larguraMesa) / 2;

        int yMesa =
                (getHeight() - alturaMesa) / 2;

        g2.setColor(
                new Color(
                        110,
                        0,
                        0,
                        220
                )
        );

        g2.fillRoundRect(
                xMesa,
                yMesa,
                larguraMesa,
                alturaMesa,
                45,
                45
        );

        g2.setColor(
                new Color(
                        90,
                        150,
                        25,
                        235
                )
        );

        g2.fillRoundRect(
                xMesa + 35,
                yMesa + 45,
                larguraMesa - 70,
                alturaMesa - 90,
                35,
                35
        );

        desenharMonte(
                g2,
                xMesa,
                yMesa
        );

        desenharJogadores(
                g2,
                xMesa,
                yMesa,
                larguraMesa,
                alturaMesa
        );

        
    }

    private void desenharMonte(Graphics2D g2, int xMesa, int yMesa) {

        int x = xMesa + 65;
        int y = yMesa + 100;

        for (int i = 0; i < 4; i++) {
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x + (i * 4), y - (i * 4), 55, 75, 8, 8);

            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x + (i * 4), y - (i * 4), 55, 75, 8, 8);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Monte", x - 2, y + 95);
    }

    public void atualizarCache(
        int qtdJogadores,
        int jogadorAtual,
        java.util.ArrayList<Peca> pecasMesa)     
    {

    this.qtdJogadoresCache = qtdJogadores;
    this.jogadorAtualCache = jogadorAtual;
    if (pecasMesa != null) {

    this.pecasMesaCache.clear();

    this.pecasMesaCache.addAll(
            pecasMesa
    );

}

repaint();
    }

    private void desenharJogadores(
            Graphics2D g2,
            int xMesa,
            int yMesa,
            int larguraMesa,
            int alturaMesa
    ) {

        int qtd = qtdJogadoresCache;
        int jogadorAtual = jogadorAtualCache;

        int[][] posicoes = {
                {xMesa + larguraMesa / 2, yMesa + 35},
                {xMesa + larguraMesa - 70, yMesa + 90},
                {xMesa + larguraMesa / 2, yMesa + alturaMesa - 35},
                {xMesa + 70, yMesa + 90}
        };

        for (int i = 0; i < qtd; i++) {

            int x = posicoes[i][0];
            int y = posicoes[i][1];

            if (i == jogadorAtual) {
                g2.setColor(new Color(255, 230, 120));
            } else if (i == minhaOrdem) {
                g2.setColor(new Color(120, 220, 255));
            } else {
                g2.setColor(new Color(230, 230, 230));
            }

            g2.fillOval(x - 25, y - 25, 50, 50);

            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x - 25, y - 25, 50, 50);

            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(Color.BLACK);

            String txt = String.valueOf(i + 1);
            FontMetrics fm = g2.getFontMetrics();

            g2.drawString(
                    txt,
                    x - fm.stringWidth(txt) / 2,
                    y + fm.getAscent() / 2 - 2
            );
        }
    }

    private void desenharPecaCentro(Graphics2D g2) {

        int largura = 130;
        int altura = 70;

        int x = (getWidth() - largura) / 2;
        int y = getHeight() / 2 - 35;

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, largura, altura, 10, 10);

        g2.setColor(Color.BLACK);
        g2.drawRoundRect(x, y, largura, altura, 10, 10);

        g2.drawLine(
                x + largura / 2,
                y,
                x + largura / 2,
                y + altura
        );
    }



private void desenharTextoCentro(
        Graphics2D g2,
        String texto,
        int x,
        int y,
        int largura,
        int altura
) {
    FontMetrics fm = g2.getFontMetrics();

    int textoX = x + (largura - fm.stringWidth(texto)) / 2;
    int textoY = y + ((altura - fm.getHeight()) / 2) + fm.getAscent();

    g2.drawString(texto, textoX, textoY);
}

public void adicionarPecaLocal(Peca p) {

    pecasMesaCache.add(p);

    revalidate();

    repaint();
}

}