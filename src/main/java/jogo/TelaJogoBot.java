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

            Peca pJogador = m.comprarPeca();

            if (pJogador != null) {
                maoJogador.add(pJogador);
            }

            Peca pBot = m.comprarPeca();

            if (pBot != null) {
                maoBot.add(pBot);
            }
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

        Peca direita = ajustarPecaParaDireita(p);
        Peca esquerda = ajustarPecaParaEsquerda(p);

        if (direita != null) {

            mesa.add(direita);
            maoJogador.remove(index);

        } else if (esquerda != null) {

            mesa.add(0, esquerda);
            maoJogador.remove(index);

        } else {

            JOptionPane.showMessageDialog(this, "Essa peça não encaixa!");
            return;
        }

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

        Peca p = monte.remove(0);

        if (p != null) {
            maoJogador.add(p);
        }

        atualizarTela();
    }

    private void jogarBot() {

        for (int i = 0; i < maoBot.size(); i++) {

            Peca p = maoBot.get(i);

            if (p == null) {
                continue;
            }

            Peca direita = ajustarPecaParaDireita(p);

            if (direita != null) {

                mesa.add(direita);
                maoBot.remove(i);

                verificarFim();
                atualizarTela();
                return;
            }

            Peca esquerda = ajustarPecaParaEsquerda(p);

            if (esquerda != null) {

                mesa.add(0, esquerda);
                maoBot.remove(i);

                verificarFim();
                atualizarTela();
                return;
            }
        }

        if (!monte.isEmpty()) {

            Peca comprada = monte.remove(0);

            if (comprada != null) {
                maoBot.add(comprada);
            }
        }

        atualizarTela();
    }

    private Peca ajustarPecaParaDireita(Peca p) {

        if (p == null) return null;

        if (mesa.isEmpty()) return p;

        Peca ultima = mesa.get(mesa.size() - 1);

        if (ultima == null) return null;

        String ponta = ultima.getLadoDireito();

        if (ponta.equalsIgnoreCase(p.getLadoEsquerdo())) {
            return p;
        }

        if (ponta.equalsIgnoreCase(p.getLadoDireito())) {
            return new Peca(p.getLadoDireito(), p.getLadoEsquerdo());
        }

        return null;
    }

    private Peca ajustarPecaParaEsquerda(Peca p) {

        if (p == null) return null;

        if (mesa.isEmpty()) return p;

        Peca primeira = mesa.get(0);

        if (primeira == null) return null;

        String ponta = primeira.getLadoEsquerdo();

        if (ponta.equalsIgnoreCase(p.getLadoDireito())) {
            return p;
        }

        if (ponta.equalsIgnoreCase(p.getLadoEsquerdo())) {
            return new Peca(p.getLadoDireito(), p.getLadoEsquerdo());
        }

        return null;
    }

    private void verificarFim() {

        if (maoJogador.isEmpty()) {
            JOptionPane.showMessageDialog(this, nomeJogador + " venceu!");
            dispose();
            return;
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

            if (p == null) continue;

            areaJogo.append(
                    "[ " +
                            p.getLadoEsquerdo() +
                            " | " +
                            p.getLadoDireito() +
                            " ] "
            );
        }

        areaJogo.append("\n\nMonte: " + monte.size() + " peças");
        areaJogo.append("\nPeças do Bot: " + maoBot.size());

        modeloMao.clear();

        for (Peca p : maoJogador) {

            if (p == null) continue;

            modeloMao.addElement(
                    p.getLadoEsquerdo() +
                            " | " +
                            p.getLadoDireito()
            );
        }
    }
}