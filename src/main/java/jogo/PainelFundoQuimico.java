package jogo;

import javax.swing.*;
import java.awt.*;

public class PainelFundoQuimico extends JPanel {

    public PainelFundoQuimico() {
        setLayout(null);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        GradientPaint gradiente = new GradientPaint(
                0, 0,
                new Color(25, 25, 25),
                getWidth(), getHeight(),
                new Color(120, 0, 25)
        );

        g2.setPaint(gradiente);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}