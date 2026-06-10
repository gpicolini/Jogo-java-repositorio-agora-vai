package jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin {

    private Jogo m_parent;

    public TelaLogin(Jogo jogo) {
        m_parent = jogo;
    }

    public void Show() {

        JFrame frame = new JFrame("Login");
        frame.setSize(760, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(160, 170, 180));

        JPanel painel = new JPanel(null);
        painel.setBounds(130, 65, 500, 260);
        painel.setBackground(new Color(245, 245, 245));
        frame.add(painel);

        JPanel faixaVermelha = new JPanel();
        faixaVermelha.setBounds(0, 0, 500, 55);
        faixaVermelha.setBackground(new Color(170, 0, 0));
        painel.add(faixaVermelha);

        JLabel titulo = new JLabel("LOGIN", SwingConstants.CENTER);
        titulo.setBounds(0, 12, 500, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        faixaVermelha.add(titulo);

        JTextField campoUser = criarCampo("Usuário");
        campoUser.setBounds(70, 85, 360, 50);
        painel.add(campoUser);

        JPasswordField campoSenha = criarCampoSenha();
        campoSenha.setBounds(70, 150, 360, 50);
        painel.add(campoSenha);

        JButton botaoLogin = criarBotao("Entrar");
        botaoLogin.setBounds(185, 215, 130, 35);
        painel.add(botaoLogin);

        botaoLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String usuario = campoUser.getText();
                String senha = new String(campoSenha.getPassword());

                if (usuario.equals("Usuário")) usuario = "";
                if (senha.equals("Senha")) senha = "";

                String nome = BancoDados.autenticar(
                        m_parent.conn,
                        usuario,
                        senha
                );

                if (nome != null) {

                    String tipo = BancoDados.buscarTipo(
                            m_parent.conn,
                            usuario
                    );

                    JOptionPane.showMessageDialog(
                            null,
                            "Seja muito bem-vindo, " + nome + "!"
                    );

                    frame.dispose();

                    if (tipo.equalsIgnoreCase("PROFESSOR")) {
                        new TelaRelatorioProfessor(m_parent.conn);
                    } else {
                        new TelaMenu(nome);
                    }

                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuário ou senha incorretos!"
                    );
                }
            }
        });

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