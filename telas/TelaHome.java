package br.com.cesarschool.poo.titulos.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class TelaHome extends JFrame {

    private Font montserrat;

    public TelaHome() {
        try {
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("src/br/com/cesarschool/poo/titulos/telas/resources/static/Montserrat-Regular.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            montserrat = new Font("Arial", Font.PLAIN, 14); // Fallback para Arial
        }

        // Configurações básicas da janela
        setTitle("Sistema Bancário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Alterando a cor de fundo para Azul Petróleo
        getContentPane().setBackground(new Color(30, 61, 88)); // Azul Petróleo (#1E3D58)

        // Painel superior com o título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(30, 61, 88)); // Azul Petróleo (#1E3D58)
        panelTitulo.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Sistema Bancário", JLabel.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(224, 224, 224)); // Branco Fumê (#E0E0E0)

        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // Painel central para os botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(new Color(30, 61, 88)); // Azul Petróleo (#1E3D58)
        panelBotoes.setLayout(new BoxLayout(panelBotoes, BoxLayout.Y_AXIS)); // Layout em coluna

        // Criando um painel para centralizar o conteúdo dos botões
        JPanel botoesCentralizados = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Centralizando sem expandir
        botoesCentralizados.setBackground(new Color(30, 61, 88)); // Azul Petróleo (#1E3D58)

        // Criando os botões
        JButton btnAbrirCrudAcao = new JButton("Ações");
        JButton btnAbrirCrudTituloDivida = new JButton("Título Dívida");
        JButton btnEntidadesOperadoras = new JButton("Entidades Operadoras");
        JButton btnTransacoes = new JButton("Transações");

        // Estilizando os botões
        estilizarBotao(btnAbrirCrudAcao);
        estilizarBotao(btnAbrirCrudTituloDivida);
        estilizarBotao(btnEntidadesOperadoras);
        estilizarBotao(btnTransacoes);

        // Adicionando os botões ao painel centralizado
        botoesCentralizados.add(btnAbrirCrudAcao);
        botoesCentralizados.add(btnAbrirCrudTituloDivida);
        botoesCentralizados.add(btnEntidadesOperadoras);
        botoesCentralizados.add(btnTransacoes);

        // Ajustando o espaçamento superior e inferior
        panelBotoes.add(Box.createVerticalStrut(50)); // Espaçamento entre o título e os primeiros botões
        panelBotoes.add(botoesCentralizados);
        panelBotoes.add(Box.createVerticalStrut(10)); // Espaçamento entre os últimos botões e o rodapé

        // Adicionando o painelBotoes à tela
        add(panelTitulo, BorderLayout.NORTH);
        add(panelBotoes, BorderLayout.CENTER);

        // Listeners dos botões
        btnAbrirCrudAcao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCRUDAcaoSwing(); // Abre a tela CRUD de ações
                dispose();
            }
        });

        btnAbrirCrudTituloDivida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCRUDTituloDivida(); // Abre a tela CRUD de Títulos Dívida
                dispose();
            }
        });

        btnEntidadesOperadoras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Entidades Operadoras
                new TelaCRUDEntidadesOperadoras();
                dispose();
            }
        });

        btnTransacoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaOperacao();
                dispose();
            }
        });

        // Painel do rodapé
        JPanel panelRodape = new JPanel();
        panelRodape.setBackground(new Color(176, 196, 214)); // Cinza Azulado Claro (#B0C4D6)
        JLabel lblRodape = new JLabel("Desenvolvido por Arthur Capistrano e Gheyson Melo");
        lblRodape.setForeground(new Color(30, 61, 88));
        panelRodape.add(lblRodape);
        panelRodape.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(panelRodape, BorderLayout.SOUTH);

        // Exibindo a tela
        setVisible(true);
    }

    // Ajuste para layout e bordas abauladas
    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.BOLD, 12)); // Tamanho de fonte reduzido
        botao.setForeground(new Color(248, 248, 255)); // Branco Neve (#F8F8FF)
        botao.setBackground(new Color(74, 106, 134)); // Azul Acinzentado (#4A6A86)
        botao.setFocusPainted(false); // Remove o foco padrão (bordas ao clicar)

        // Definindo um tamanho fixo e compacto para os botões
        botao.setPreferredSize(new Dimension(400, 60)); // Tamanho fixo dos botões

        // Centralizando o botão
        botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza no layout do painel

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mão

        // Aplicando bordas arredondadas com raio bem acentuado
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(63, 77, 89), 2, true), // Cinza Aço (#3F4D59)
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Efeito hover
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(42, 83, 112)); // Azul Cobalto (#2A5370) ao passar o mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(74, 106, 134)); // Azul Acinzentado (#4A6A86)
            }
        });
    }

    public static void main(String[] args) {
        new TelaHome(); // Executa a tela inicial
    }
}
