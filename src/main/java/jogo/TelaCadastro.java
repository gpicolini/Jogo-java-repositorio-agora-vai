package jogo;

import javax.swing.*;
import java.awt.event.*;;

public class TelaCadastro {
    
    private Jogo m_parent;

    public TelaCadastro (Jogo jogo) 
    {
        m_parent = jogo;
    }

    public void Show () {
        

        JFrame frame = new JFrame("Cadastro");
        frame.setSize(300, 250);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        
        // NOME

        JLabel labelNome = new JLabel("Nome: ");
        labelNome.setBounds(30, 30, 80, 25);
        frame.add (labelNome);

        JTextField campoNome = new JTextField();
        campoNome.setBounds(100, 30, 150, 25);
        frame.add(campoNome);


        // USUÁRIO 

        JLabel labelUser = new JLabel("Usuário: ");
        labelUser.setBounds(30, 70, 80, 25);
        frame.add (labelUser);

        JTextField campoUser = new JTextField();
        campoUser.setBounds(100, 70, 150, 25);
        frame.add(campoUser);

        // SENHA 

        JLabel labelSenha = new JLabel("Senha: ");
        labelSenha.setBounds (30, 110, 80, 25);
        frame.add(labelSenha);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setBounds (100, 110, 150, 25);
        frame.add(campoSenha);

        // BOTÃO 

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(90, 160, 120, 25);
        frame.add (botaoCadastrar);

        // AÇÃO 

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ) { 

            String nome = campoNome.getText();
            String usuario = campoUser.getText();
            String senha = new String(campoSenha.getPassword());
            
        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty())  {
            JOptionPane.showMessageDialog(null, "Prencha todos os Campos!");
            return;

        }

        boolean sucesso = BancoDados.cadastrar(m_parent.conn, nome, usuario, senha);

        if (sucesso) {

            JOptionPane.showMessageDialog(null, "Cadastro realizado com Extremo Sucesso!");
            frame.dispose();

            new TelaLogin(m_parent).Show();} 
            
            else {
            JOptionPane.showMessageDialog(null, "Erro ao realizar o Cadastro");
        
        }
     }

        });

        frame.setVisible(true);
            
        



    }

}



