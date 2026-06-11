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

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        fundo.setBounds(0, 0, 1920, 1080);

        JLabel icone = new JLabel("🧪", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 44));
        fundo.add(icone);

        JLabel titulo = new JLabel("DOMINÓ QUÍMICO", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);
        fundo.add(titulo);

        JLabel subtitulo = new JLabel("Crie sua conta para começar", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subtitulo.setForeground(new Color(230, 230, 230));
        fundo.add(subtitulo);

        JPanel painel = new JPanel(null);
        painel.setBackground(new Color(255, 255, 255, 235));
        painel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2));
        fundo.add(painel);

        JLabel lblCadastro = new JLabel("CADASTRO", SwingConstants.CENTER);
        lblCadastro.setBounds(0, 15, 490, 30);
        lblCadastro.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblCadastro.setForeground(new Color(125, 15, 35));
        painel.add(lblCadastro);

        JTextField campoNome = criarCampo("Nome");
        campoNome.setBounds(35, 60, 270, 38);
        painel.add(campoNome);

        JTextField campoUser = criarCampo("Usuário");
        campoUser.setBounds(35, 105, 270, 38);
        painel.add(campoUser);

        JPasswordField campoSenha = criarCampoSenha();
        campoSenha.setBounds(35, 150, 270, 38);
        painel.add(campoSenha);

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"ALUNO", "PROFESSOR"});
        comboTipo.setBounds(35, 195, 270, 38);
        comboTipo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        comboTipo.setBackground(Color.WHITE);
        comboTipo.setForeground(new Color(40, 40, 40));
        painel.add(comboTipo);

        JButton botaoCadastrar = criarBotao("Cadastrar");
        botaoCadastrar.setBounds(325, 70, 125, 38);
        painel.add(botaoCadastrar);

        JButton botaoLogin = criarBotao("Login");
        botaoLogin.setBounds(325, 125, 125, 38);
        painel.add(botaoLogin);

        JButton botaoSair = criarBotao("Sair");
        botaoSair.setBounds(325, 180, 125, 38);
        painel.add(botaoSair);

        fundo.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                int largura = fundo.getWidth();
                int altura = fundo.getHeight();

                fundo.setBounds(0, 0, largura, altura);

                icone.setBounds(0, altura / 2 - 260, largura, 55);
                titulo.setBounds(0, altura / 2 - 205, largura, 50);
                subtitulo.setBounds(0, altura / 2 - 155, largura, 28);

                painel.setBounds(
                        (largura - 490) / 2,
                        (altura - 285) / 2 + 45,
                        490,
                        285
                );
            }
        });

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

        frame.getRootPane().registerKeyboardAction(
                e -> {
                    frame.dispose();
                    new TelaLogin(m_parent).Show();
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        Dimension tela =
        Toolkit.getDefaultToolkit().getScreenSize();

        fundo.setBounds(
        0,
        0,
        tela.width,
        tela.height
        );
        
        frame.setVisible(true);

        

        SwingUtilities.invokeLater(() -> {

        int largura = frame.getWidth();
        int altura = frame.getHeight();

     fundo.setBounds(0, 0, largura, altura);

        icone.setBounds(0, altura / 2 - 260, largura, 55);
        titulo.setBounds(0, altura / 2 - 205, largura, 50);
        subtitulo.setBounds(0, altura / 2 - 155, largura, 28);

        painel.setBounds(
            (largura - 490) / 2,
            (altura - 285) / 2 + 45,
            490,
            285
    );

    fundo.revalidate();
    fundo.repaint();
});
    }

    private JTextField criarCampo(String texto) {

        JTextField campo = new JTextField(texto);
        campo.setFont(new Font("Segoe UI", Font.BOLD, 14));
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
        campo.setFont(new Font("Segoe UI", Font.BOLD, 14));
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

        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
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