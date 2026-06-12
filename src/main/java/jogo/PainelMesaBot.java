package jogo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PainelMesaBot extends JPanel {

    private ArrayList<Peca> pecasMesa = new ArrayList<>();

    private int qtdPecasBot = 7;
    private int qtdMonte = 0;

    private Image fundoLab;

    public PainelMesaBot() {

        setBackground(new Color(30, 30, 30));
        setOpaque(true);

        try {
            java.net.URL url =
                    getClass().getResource("/jogo/images/lab.png");

            if (url != null) {
                fundoLab = new ImageIcon(url).getImage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarMesa(
            ArrayList<Peca> mesa,
            int qtdPecasBot,
            int qtdMonte
    ) {

        this.pecasMesa.clear();

        if (mesa != null) {
            this.pecasMesa.addAll(mesa);
        }

        this.qtdPecasBot = qtdPecasBot;
        this.qtdMonte = qtdMonte;

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

            g2.setColor(new Color(30, 30, 30));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        g2.setColor(new Color(0, 0, 0, 105));
        g2.fillRect(0, 0, getWidth(), getHeight());

        

        int larguraMesa = 1350;
        int alturaMesa = 700;

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

        desenharMonte(g2, xMesa, yMesa);

        desenharBot(g2, xMesa, yMesa, larguraMesa);

        desenharPecasDaMesa(g2);

        
    }

    private void desenharMonte(
        Graphics2D g2,
        int xMesa,
        int yMesa
) {

    int x = xMesa + 65;
    int y = yMesa + 120;

    int largura = 65;
    int altura = 110;

    g2.setColor(new Color(0, 0, 0, 120));
    g2.fillRoundRect(
            x + 6,
            y + 7,
            largura,
            altura,
            16,
            16
    );

    g2.setColor(new Color(245, 225, 178));
    g2.fillRoundRect(
            x,
            y,
            largura,
            altura,
            16,
            16
    );

    g2.setColor(new Color(75, 45, 22));
    g2.setStroke(new BasicStroke(3));
    g2.drawRoundRect(
            x,
            y,
            largura,
            altura,
            16,
            16
    );

    g2.drawLine(
            x + 8,
            y + altura / 2,
            x + largura - 8,
            y + altura / 2
    );

    g2.setColor(Color.BLACK);
    g2.setFont(new Font("Segoe UI", Font.BOLD, 13));

    desenharTextoCentro(
            g2,
            "MONTE",
            x,
            y,
            largura,
            altura / 2
    );

    g2.setFont(new Font("Segoe UI", Font.BOLD, 22));

    desenharTextoCentro(
            g2,
            String.valueOf(qtdMonte),
            x,
            y + altura / 2,
            largura,
            altura / 2
    );
}
    private void desenharBot(
            Graphics2D g2,
            int xMesa,
            int yMesa,
            int larguraMesa
    ) {

        int x =
                xMesa + larguraMesa / 2;

        int y =
                yMesa + 35;

        g2.setColor(
                new Color(
                        255,
                        230,
                        120
                )
        );

        g2.fillOval(
                x - 25,
                y - 25,
                50,
                50
        );

        g2.setColor(Color.DARK_GRAY);

        g2.setStroke(
                new BasicStroke(2)
        );

        g2.drawOval(
                x - 25,
                y - 25,
                50,
                50
        );

        g2.setColor(Color.BLACK);

        g2.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        String txt = "B";

        FontMetrics fm =
                g2.getFontMetrics();

        g2.drawString(
                txt,
                x - fm.stringWidth(txt) / 2,
                y + fm.getAscent() / 2 - 2
        );

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        g2.drawString(
                "Bot: " + qtdPecasBot + " peças",
                x - 55,
                y - 35
        );
    }

   
    private void desenharPecaNormal(
       
        Graphics2D g2,
        Peca p,
        int x,
        int y
) {

    int largura = 120;
    int altura = 60;

    boolean coringa =
            p.getLadoEsquerdo().equalsIgnoreCase("Coringa")
                    ||
                    p.getLadoDireito().equalsIgnoreCase("Coringa");

    g2.setColor(new Color(0, 0, 0, 100));

    g2.fillRoundRect(
            x + 5,
            y + 6,
            largura,
            altura,
            15,
            15
    );

    if (coringa) {
        g2.setColor(Color.WHITE);
    } else {
        g2.setColor(new Color(245, 225, 178));
    }

    g2.fillRoundRect(
            x,
            y,
            largura,
            altura,
            15,
            15
    );

    g2.setColor(new Color(75, 45, 22));

    g2.setStroke(new BasicStroke(2));

    g2.drawRoundRect(
            x,
            y,
            largura,
            altura,
            15,
            15
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

 }
    
private void desenharPecasDaMesa(Graphics2D g2) {

    int larguraNormal = 95;
    int alturaNormal = 48;

    int larguraDupla = 52;
    int alturaDupla = 95;

    int espaco = 12;

    int larguraMesa = 1250;
    int xMesa = (getWidth() - larguraMesa) / 2;

    int xInicial = xMesa + 210;
    int xFinal = xMesa + larguraMesa - 70;

    int yInicial = getHeight() / 2 - 195;
    int y = yInicial;
    int x = xInicial;

    for (Peca p : pecasMesa) {

        if (p == null) {
            continue;
        }

        boolean dupla =
                p.getLadoEsquerdo().equalsIgnoreCase(
                        p.getLadoDireito()
                );

        int larguraPeca =
                dupla ? larguraDupla : larguraNormal;

        if (x + larguraPeca > xFinal) {
            x = xInicial;
            y += 88;
        }

        if (dupla) {

            desenharPecaDupla(
                    g2,
                    p,
                    x,
                    y - 23,
                    larguraDupla,
                    alturaDupla
            );

        } else {

            desenharPecaNormal(
                    g2,
                    p,
                    x,
                    y,
                    larguraNormal,
                    alturaNormal
            );
        }

        x += larguraPeca + espaco;
    }
}

private void desenharPecaNormal(
        Graphics2D g2,
        Peca p,
        int x,
        int y,
        int largura,
        int altura
) {

    int metade = largura / 2;

    String ladoEsq = textoCoringa(p.getLadoEsquerdo());
    String ladoDir = textoCoringa(p.getLadoDireito());

    boolean esquerdaCoringa =
            p.getLadoEsquerdo().equalsIgnoreCase("Coringa");

    boolean direitaCoringa =
            p.getLadoDireito().equalsIgnoreCase("Coringa");

    g2.setColor(new Color(0, 0, 0, 100));
    g2.fillRoundRect(x + 4, y + 5, largura, altura, 12, 12);

    g2.setColor(new Color(245, 225, 178));
    g2.fillRoundRect(x, y, largura, altura, 12, 12);

    
    

    g2.setColor(new Color(75, 45, 22));
    g2.setStroke(new BasicStroke(2));
    g2.drawRoundRect(x, y, largura, altura, 12, 12);

    g2.drawLine(
            x + metade,
            y + 6,
            x + metade,
            y + altura - 6
    );

   g2.setColor(Color.BLACK);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));

        desenharTextoCentro(
        g2,
        ladoEsq,
        x,
        y,
        metade,
        altura
);

        desenharTextoCentro(
        g2,
        ladoDir,
        x + metade,
        y,
        metade,
        altura
);

}

private void desenharPecaDupla(
        Graphics2D g2,
        Peca p,
        int x,
        int y,
        int largura,
        int altura
) {

    int metade = altura / 2;

    boolean cimaCoringa =
            p.getLadoEsquerdo().equalsIgnoreCase("Coringa");

    boolean baixoCoringa =
            p.getLadoDireito().equalsIgnoreCase("Coringa");

    g2.setColor(new Color(0, 0, 0, 100));
    g2.fillRoundRect(x + 4, y + 5, largura, altura, 12, 12);

    g2.setColor(new Color(245, 225, 178));
    g2.fillRoundRect(x, y, largura, altura, 12, 12);

    if (cimaCoringa) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, largura, metade, 12, 12);
    }

    if (baixoCoringa) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y + metade, largura, metade, 12, 12);
    }

    g2.setColor(new Color(75, 45, 22));
    g2.setStroke(new BasicStroke(2));
    g2.drawRoundRect(x, y, largura, altura, 12, 12);

    g2.drawLine(
            x + 6,
            y + metade,
            x + largura - 6,
            y + metade
    );

    g2.setColor(Color.BLACK);
    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));

    desenharTextoCentro(
            g2,
            textoCoringa(p.getLadoEsquerdo()),
            x,
            y,
            largura,
            metade
    );

    desenharTextoCentro(
            g2,
            textoCoringa(p.getLadoDireito()),
            x,
            y + metade,
            largura,
            metade
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

private void desenharPecaDupla(
        Graphics2D g2,
        Peca p,
        int x,
        int y
) {

    int largura = 65;
    int altura = 120;

    boolean coringa =
            p.getLadoEsquerdo().equalsIgnoreCase("Coringa")
                    ||
                    p.getLadoDireito().equalsIgnoreCase("Coringa");

    g2.setColor(new Color(0, 0, 0, 100));

    g2.fillRoundRect(
            x + 5,
            y + 6,
            largura,
            altura,
            15,
            15
    );

    if (coringa) {
        g2.setColor(Color.WHITE);
    } else {
        g2.setColor(new Color(245, 225, 178));
    }

    g2.fillRoundRect(
            x,
            y,
            largura,
            altura,
            15,
            15
    );

    g2.setColor(new Color(75, 45, 22));

    g2.setStroke(new BasicStroke(2));

    g2.drawRoundRect(
            x,
            y,
            largura,
            altura,
            15,
            15
    );

    g2.drawLine(
            x + 7,
            y + altura / 2,
            x + largura - 7,
            y + altura / 2
    );

    g2.setColor(Color.BLACK);

    g2.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    12
            )
    );

    desenharTextoCentro(
            g2,
            textoCoringa(p.getLadoEsquerdo()),
            x,
            y,
            largura,
            altura / 2
    );

    desenharTextoCentro(
            g2,
            textoCoringa(p.getLadoDireito()),
            x,
            y + altura / 2,
            largura,
            altura / 2
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
}