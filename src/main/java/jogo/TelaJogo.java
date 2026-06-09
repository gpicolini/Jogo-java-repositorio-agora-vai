package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaJogo extends JFrame {

    private Partida partida;

    private JTextArea areaTabuleiro;

    private DefaultListModel<String> modeloLista;

    private JList<String> listaPecas;

    private JLabel lblPontuacao;

    public TelaJogo(String nomeJogador) {

    partida = new Partida(nomeJogador);

    setTitle("🧪 Dominó Químico");

    setSize(1200, 800);

    setLocationRelativeTo(null);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setLayout(new BorderLayout());

    getContentPane().setBackground(
            new Color(35, 35, 35)
    );

    JLabel titulo = new JLabel(
            "🧪 DOMINÓ QUÍMICO"
    );

    titulo.setFont(
            new Font("Arial",
                    Font.BOLD,
                    28)
    );

    titulo.setHorizontalAlignment(
            SwingConstants.CENTER
    );

    add(titulo, BorderLayout.NORTH);

    areaTabuleiro = new JTextArea();

    areaTabuleiro.setEditable(false);

    areaTabuleiro.setFont(
            new Font("Consolas",
                    Font.BOLD,
                    24)
    );

    areaTabuleiro.setBackground(
            new Color(45, 45, 45)
    );

    areaTabuleiro.setForeground(
            Color.WHITE
    );

    add(
            new JScrollPane(areaTabuleiro),
            BorderLayout.CENTER
    );

    modeloLista = new DefaultListModel<>();

    listaPecas = new JList<>(modeloLista);

    listaPecas.setFont(
        new Font("Arial",
                Font.BOLD,
                16)
    );

    listaPecas.setFixedCellHeight(35);

    listaPecas.setBackground(
        new Color(50,50,50)
    );

    listaPecas.setForeground(
        Color.WHITE
    );

    listaPecas.setSelectionBackground(
        new Color(70,130,180)
    );

    add(
            new JScrollPane(listaPecas),
            BorderLayout.WEST
    );

    JPanel painelSul = new JPanel();

    JButton btnEsquerda =
            new JButton("Jogar Esquerda");

    JButton btnDireita =
            new JButton("Jogar Direita");

    JButton btnComprar =
            new JButton("Comprar");

    lblPontuacao =
            new JLabel("Pontuação: 0");

    painelSul.add(btnEsquerda);
    painelSul.add(btnDireita);
    painelSul.add(btnComprar);
    painelSul.add(lblPontuacao);

    add(painelSul, BorderLayout.SOUTH);

    btnEsquerda.addActionListener(
            e -> jogar(false)
    );

    btnDireita.addActionListener(
            e -> jogar(true)
    );

    btnComprar.addActionListener(e -> {

        partida.comprarPeca();

        atualizarTela();
    });

    atualizarTela();

    setVisible(true);
}
    

    private void jogar(boolean direita) {

        int indice =
                listaPecas.getSelectedIndex();

        if(indice == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma peça!"
            );

            return;
        }

        boolean sucesso =
                partida.jogarPeca(indice,
                        direita);

        if(!sucesso) {

            JOptionPane.showMessageDialog(
                    this,
                    "Peça incompatível!"
            );
        }

        atualizarTela();

        verificarFim();
    }

    private void verificarFim() {

        if(partida.terminou()) {

            partida.finalizar();

            JOptionPane.showMessageDialog(
                    this,
                    "VOCÊ VENCEU!\n\nPontuação: "
                    + partida.getPontuacao()
                    .getPontuacaoTotal()
            );
        }
    }

  private void atualizarTela() {

    modeloLista.clear();

    for(Peca p : partida.getJogador().getMao()) {

        modeloLista.addElement(
                "🧪 "
                + p.getSubstancia()
                + " ("
                + p.getFuncao()
                + ")"
        );
    }

    StringBuilder texto =
            new StringBuilder();

    for(Peca p :
            partida.getTabuleiro().getPecas()) {

        texto.append("🧪 [")
                .append(p.getSubstancia())
                .append("] ");
    }

    areaTabuleiro.setText(
            texto.toString()
    );

    lblPontuacao.setText(
            "Pontuação: "
            + partida.getPontuacao()
            .getPontuacaoTotal()
    );
    } 
}