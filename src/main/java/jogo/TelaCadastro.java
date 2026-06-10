package jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastro {

    private Jogo m_parent;

    public TelaCadastro(Jogo jogo) {
        m_parent = jogo;
    }

    public void Show() {

        JFrame frame = new JFrame("Cadastro");
        frame.setSize(760, 430);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(160, 170, 180));

        JPanel painel = new JPanel(null);
        painel.setBounds(70, 50, 620, 300);
        painel.setBackground(new Color(245, 245, 245));
        frame.add(painel);

        JPanel faixaVermelha = new JPanel();
        faixaVermelha.setBounds(0, 0, 620, 55);
        faixaVermelha.setBackground(new Color(170, 0, 0));
        painel.add(faixaVermelha);

        JTextField campoNome = criarCampo("Nome");
        campoNome.setBounds(30, 70, 300, 45);
        painel.add(campoNome);

        JTextField campoUser = criarCampo("Usuário");
        campoUser.setBounds(30, 125, 300, 45);
        painel.add(campoUser);

        JPasswordField campoSenha = criarCampoSenha();
        campoSenha.setBounds(30, 180, 300, 45);
        painel.add(campoSenha);

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"ALUNO", "PROFESSOR"});
        comboTipo.setBounds(30, 235, 300, 40);
        comboTipo.setFont(new Font("Arial", Font.BOLD, 16));
        comboTipo.setBackground(Color.WHITE);
        painel.add(comboTipo);

        JButton botaoCadastrar = criarBotao("Adicionar Cadastro");
        botaoCadastrar.setBounds(410, 85, 160, 40);
        painel.add(botaoCadastrar);

        JButton botaoLogin = criarBotao("Já tem Cadastro?");
        botaoLogin.setBounds(410, 155, 160, 40);
        painel.add(botaoLogin);

        JButton botaoSair = criarBotao("Sair");
        botaoSair.setBounds(410, 225, 160, 40);
        painel.add(botaoSair);

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nome = campoNome.getText();
                String usuario = campoUser.getText();
                String senha = new String(campoSenha.getPassword());
                String tipo = comboTipo.getSelectedItem().toString();

                if (nome.equals("Nome")) nome = "";
                if (usuario.equals("Usuário")) usuario = "";
                if (senha.equals("Senha")) senha = "";

                if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }

                boolean sucesso = BancoDados.cadastrar(
                        m_parent.conn,
                        nome,
                        usuario,
                        senha,
                        tipo
                );

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    frame.dispose();
                    new TelaLogin(m_parent).Show();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao realizar o cadastro.");
                }
            }
        });

        botaoLogin.addActionListener(e -> {
            frame.dispose();
            new TelaLogin(m_parent).Show();
        });

        botaoSair.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private JTextField criarCampo(String texto) {

        JTextField campo = new JTextField(texto);
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setForeground(Color.GRAY);
        campo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(texto);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }

    private JPasswordField criarCampoSenha() {

        JPasswordField campo = new JPasswordField("Senha");
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setForeground(Color.GRAY);
        campo.setEchoChar((char) 0);
        campo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(campo.getPassword()).equals("Senha")) {
                    campo.setText("");
                    campo.setEchoChar('•');
                    campo.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (campo.getPassword().length == 0) {
                    campo.setText("Senha");
                    campo.setEchoChar((char) 0);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(180, 195, 240));
        botao.setFocusPainted(false);
        return botao;
    }
}