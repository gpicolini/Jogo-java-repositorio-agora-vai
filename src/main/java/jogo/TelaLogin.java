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

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JPanel fundo = new JPanel(null) {
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
        };

        frame.add(fundo);

        JLabel icone = new JLabel("🧪", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 44));
        fundo.add(icone);

        JLabel titulo = new JLabel("DOMINÓ QUÍMICO", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);
        fundo.add(titulo);

        JLabel subtitulo = new JLabel("Acesse sua conta para continuar", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subtitulo.setForeground(new Color(230, 230, 230));
        fundo.add(subtitulo);

        JPanel painel = new JPanel(null);
        painel.setBackground(new Color(255, 255, 255, 235));
        painel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2));
        fundo.add(painel);

        JLabel lblLogin = new JLabel("LOGIN", SwingConstants.CENTER);
        lblLogin.setBounds(0, 15, 400, 30);
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogin.setForeground(new Color(125, 15, 35));
        painel.add(lblLogin);

        JTextField campoUser = criarCampo("Usuário");
        campoUser.setBounds(45, 60, 310, 42);
        painel.add(campoUser);

        JPasswordField campoSenha = criarCampoSenha();
        campoSenha.setBounds(45, 112, 310, 42);
        painel.add(campoSenha);

        JButton botaoLogin = criarBotao("Entrar");
        botaoLogin.setBounds(125, 165, 150, 38);
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

        frame.getRootPane().registerKeyboardAction(
                e -> {
                    frame.dispose();
                    new TelaCadastro(m_parent).Show();
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> {

            int largura = frame.getWidth();
            int altura = frame.getHeight();

            fundo.setBounds(0, 0, largura, altura);

            icone.setBounds(0, altura / 2 - 235, largura, 55);
            titulo.setBounds(0, altura / 2 - 180, largura, 50);
            subtitulo.setBounds(0, altura / 2 - 130, largura, 28);

            painel.setBounds(
                    (largura - 400) / 2,
                    (altura - 210) / 2 + 55,
                    400,
                    210
            );

            fundo.revalidate();
            fundo.repaint();
        });
    }

    private JTextField criarCampo(String texto) {

        JTextField campo = new JTextField(texto);

        campo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        campo.setForeground(Color.GRAY);
        campo.setBackground(Color.WHITE);
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                        BorderFactory.createEmptyBorder(5, 12, 5, 12)
                )
        );

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

        campo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        campo.setForeground(Color.GRAY);
        campo.setBackground(Color.WHITE);
        campo.setEchoChar((char) 0);
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                        BorderFactory.createEmptyBorder(5, 12, 5, 12)
                )
        );

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

        Color vermelho = new Color(255, 50, 70);
        Color hover = new Color(255, 75, 95);

        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setBackground(vermelho);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 135), 2));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                botao.setBackground(vermelho);
            }
        });

        return botao;
    }
}