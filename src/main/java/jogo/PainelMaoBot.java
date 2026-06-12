package jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PainelMaoBot extends JPanel {

    private ArrayList<Peca> pecas = new ArrayList<>();

    private int indiceSelecionado = -1;

    public PainelMaoBot() {

        setPreferredSize(new Dimension(0, 130));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int largura = 120;
                int altura = 62;
                int espaco = 28;

                int total =
                        pecas.size() * largura
                                +
                                Math.max(0, pecas.size() - 1) * espaco;

                int x =
                        (getWidth() - total) / 2;

                int yBase =
                        25;

                for (int i = 0; i < pecas.size(); i++) {

                    int y = yBase;

                    if (i == indiceSelecionado) {
                        y -= 16;
                    }

                    Rectangle r =
                            new Rectangle(
                                    x,
                                    y,
                                    largura,
                                    altura
                            );

                    if (r.contains(e.getPoint())) {
                        indiceSelecionado = i;
                        repaint();
                        return;
                    }

                    x += largura + espaco;
                }
            }
        });
    }

    public void carregarPecas(ArrayList<Peca> novasPecas) {

        pecas.clear();

        if (novasPecas != null) {
            pecas.addAll(novasPecas);
        }

        indiceSelecionado = -1;

        repaint();
    }

    public Peca getPecaSelecionada() {

        if (
                indiceSelecionado < 0
                        ||
                        indiceSelecionado >= pecas.size()
        ) {
            return null;
        }

        return pecas.get(indiceSelecionado);
    }

    public int getIndiceSelecionado() {

        return indiceSelecionado;
    }

    public void limparSelecao() {

        indiceSelecionado = -1;

        repaint();
    }

    public void removerPecaSelecionadaLocal() {

        if (
                indiceSelecionado >= 0
                        &&
                        indiceSelecionado < pecas.size()
        ) {

            pecas.remove(indiceSelecionado);

            indiceSelecionado = -1;

            repaint();
        }
    }

    public void adicionarPecaLocal(Peca p) {

        if (p != null) {
            pecas.add(p);
        }

        repaint();
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

        int largura = 120;
        int altura = 62;
        int espaco = 28;

        int total =
                pecas.size() * largura
                        +
                        Math.max(0, pecas.size() - 1) * espaco;

        int x =
                (getWidth() - total) / 2;

        int yBase =
                25;

        for (int i = 0; i < pecas.size(); i++) {

            Peca p =
                    pecas.get(i);

            if (p == null) {
                continue;
            }

            int y =
                    yBase;

            if (i == indiceSelecionado) {
                y -= 16;
            }

            g2.setColor(
                    new Color(
                            0,
                            0,
                            0,
                            110
                    )
            );

            g2.fillRoundRect(
                    x + 5,
                    y + 6,
                    largura,
                    altura,
                    16,
                    16
            );

            if (i == indiceSelecionado) {

                g2.setColor(
                        new Color(
                                255,
                                232,
                                130
                        )
                );

            } else {

                g2.setColor(
                        new Color(
                                245,
                                225,
                                178
                        )
                );
            }

            g2.fillRoundRect(
                    x,
                    y,
                    largura,
                    altura,
                    16,
                    16
            );

            g2.setColor(
                    new Color(
                            75,
                            45,
                            22
                    )
            );

            g2.setStroke(
                    new BasicStroke(2)
            );

            g2.drawRoundRect(
                    x,
                    y,
                    largura,
                    altura,
                    16,
                    16
            );

            g2.drawLine(
                    x + largura / 2,
                    y + 7,
                    x + largura / 2,
                    y + altura - 7
            );

            g2.setColor(Color.BLACK);

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                    )
            );

            desenharTextoCentro(
                    g2,
                    p.getLadoEsquerdo(),
                    x,
                    y,
                    largura / 2,
                    altura
            );

            desenharTextoCentro(
                    g2,
                    p.getLadoDireito(),
                    x + largura / 2,
                    y,
                    largura / 2,
                    altura
            );

            x += largura + espaco;
        }
    }

    private void desenharTextoCentro(
            Graphics2D g2,
            String texto,
            int x,
            int y,
            int largura,
            int altura
    ) {

        FontMetrics fm =
                g2.getFontMetrics();

        int textoX =
                x + (largura - fm.stringWidth(texto)) / 2;

        int textoY =
                y + ((altura - fm.getHeight()) / 2) + fm.getAscent();

        g2.drawString(
                texto,
                textoX,
                textoY
        );
    }

    private String textoCoringa(String texto) {

    if (texto == null) {
        return "";
    }

    if (texto.equalsIgnoreCase("Coringa")) {
        return "";
    }

    return texto;
}

}