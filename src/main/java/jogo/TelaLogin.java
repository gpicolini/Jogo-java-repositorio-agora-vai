package jogo;

import javax.swing.*;
import java.awt.event.*;

public class TelaLogin {

    private Jogo m_parent;

    public TelaLogin(Jogo jogo) {
        m_parent = jogo;
    }

    public void Show() {

        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Usuário
        JLabel labelUser = new JLabel("Usuário:");
        labelUser.setBounds(30, 30, 80, 25);
        frame.add(labelUser);

        JTextField campoUser = new JTextField();
        campoUser.setBounds(100, 30, 150, 25);
        frame.add(campoUser);

        // Senha
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(30, 70, 80, 25);
        frame.add(labelSenha);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setBounds(100, 70, 150, 25);
        frame.add(campoSenha);

        // Botão
        JButton botaoLogin = new JButton("Entrar");
        botaoLogin.setBounds(100, 110, 100, 25);
        frame.add(botaoLogin);

        // Ação
        botaoLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String usuario = campoUser.getText();
                String senha = new String(campoSenha.getPassword());

                String nome = BancoDados.autenticar(m_parent.conn, usuario, senha);

                if (nome != null) {

                    JOptionPane.showMessageDialog(null,
                            "Seja muito bem-vindo, " + nome + "!");

                    frame.dispose();

                    
                    m_parent.abrirJogo(nome);

                } else {

                    JOptionPane.showMessageDialog(null,
                            "Usuário ou senha incorretos!");
                }
            }
        });

        frame.setVisible(true);
    }
}