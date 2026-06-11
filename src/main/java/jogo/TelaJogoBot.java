package jogo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaJogoBot extends JFrame {

    private String nomeJogador;
    private Dificuldade dificuldade;

    private ArrayList<Peca> maoJogador = new ArrayList<>();
    private ArrayList<Peca> maoBot = new ArrayList<>();
    private ArrayList<Peca> monte = new ArrayList<>();
    private ArrayList<Peca> mesa = new ArrayList<>();

    private JTextArea areaJogo;
    private DefaultListModel<String> modeloMao;
    private JList<String> listaMao;

    public TelaJogoBot(String nomeJogador, Dificuldade dificuldade) {

        this.nomeJogador = nomeJogador;
        this.dificuldade = dificuldade;

        setTitle("Dominó Químico - Contra Bot");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        iniciarPartida();

        areaJogo = new JTextArea();
        areaJogo.setEditable(false);
        areaJogo.setFont(new Font("Consolas", Font.BOLD, 16));

        modeloMao = new DefaultListModel<>();
        listaMao = new JList<>(modeloMao);

        JButton btnJogar = new JButton("Jogar Peça");
        JButton btnComprar = new JButton("Comprar");
        JButton btnSair = new JButton("Sair");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnJogar);
        painelBotoes.add(btnComprar);
        painelBotoes.add(btnSair);

        add(new JScrollPane(areaJogo), BorderLayout.CENTER);
        add(new JScrollPane(listaMao), BorderLayout.EAST);
        add(painelBotoes, BorderLayout.SOUTH);

        btnJogar.addActionListener(e -> jogarJogador());
        btnComprar.addActionListener(e -> comprarJogador());
        btnSair.addActionListener(e -> dispose());

        atualizarTela();

        setVisible(true);
    }

    private void iniciarPartida() {

        Monte m = new Monte(dificuldade);

        for (int i = 0; i < 7; i++) {
            maoJogador.add(m.comprarPeca());
            maoBot.add(m.comprarPeca());
        }

        Peca p;

        while ((p = m.comprarPeca()) != null) {
            monte.add(p);
        }
    }

    private void jogarJogador() {

        int index = listaMao.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma peça!");
            return;
        }

        Peca p = maoJogador.get(index);

        mesa.add(p);
        maoJogador.remove(index);

        verificarFim();

        atualizarTela();

        Timer t = new Timer(1000, e -> jogarBot());
        t.setRepeats(false);
        t.start();
    }

    private void comprarJogador() {

        if (monte.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Monte vazio!");
            return;
        }

        maoJogador.add(monte.remove(0));

        atualizarTela();
    }

    private void jogarBot() {

        if (maoBot.isEmpty()) {
            verificarFim();
            return;
        }

        Peca p = maoBot.remove(0);
        mesa.add(p);

        verificarFim();

        atualizarTela();
    }

    private void verificarFim() {

        if (maoJogador.isEmpty()) {
            JOptionPane.showMessageDialog(this, nomeJogador + " venceu!");
            dispose();
        }

        if (maoBot.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bot venceu!");
            dispose();
        }
    }

    private void atualizarTela() {

        areaJogo.setText("");

        areaJogo.append("Mesa:\n\n");

        for (Peca p : mesa) {
            areaJogo.append("[ " + p.getLadoEsquerdo() + " | " + p.getLadoDireito() + " ] ");
        }

        areaJogo.append("\n\nMonte: " + monte.size() + " peças");
        areaJogo.append("\nPeças do Bot: " + maoBot.size());

        modeloMao.clear();

        for (Peca p : maoJogador) {
            modeloMao.addElement(
                    p.getLadoEsquerdo() + " | " + p.getLadoDireito()
            );
        }
    }
}