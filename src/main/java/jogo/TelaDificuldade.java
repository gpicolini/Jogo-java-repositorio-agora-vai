package jogo;

import javax.swing.*;
import java.awt.*;


public class TelaDificuldade extends JFrame {

    public TelaDificuldade() {

        setTitle("Escolha a dificuldade");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton btnFacil = new JButton("Fácil");
        JButton btnMedio = new JButton("Médio");
        JButton btnDificil = new JButton("Difícil");

        add(btnFacil);
        add(btnMedio);
        add(btnDificil);

        btnFacil.addActionListener(e -> iniciar(Dificuldade.FACIL));
        btnMedio.addActionListener(e -> iniciar(Dificuldade.MEDIO));
        btnDificil.addActionListener(e -> iniciar(Dificuldade.DIFICIL));

        setVisible(true);
    }

    private void iniciar(Dificuldade dificuldade) {
        new TelaJogo("Jogador", dificuldade);
        dispose();
    }
}