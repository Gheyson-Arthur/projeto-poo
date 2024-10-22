package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class TelaCRUDTituloDivida extends JFrame {

    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getMediatorTituloDivida();

    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtTaxaJuros;
    private JTextField txtDataValidade;

    public TelaCRUDTituloDivida() {
        setTitle("CRUD Título Dívida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Tamanho da janela
        setLocationRelativeTo(null);


        // Layout principal
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título da página
        JLabel lblTituloPagina = new JLabel("Gerenciamento de Títulos de Dívida", JLabel.CENTER);
        lblTituloPagina.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloPagina.setForeground(new Color(30, 45, 90)); // Cor do texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        add(lblTituloPagina, gbc);

        // Campos de texto e labels
        JLabel lblIdentificador = new JLabel("Identificador:");
        txtIdentificador = new JTextField(20);

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);

        JLabel lblTaxaJuros = new JLabel("Taxa de Juros:");
        txtTaxaJuros = new JTextField(20);

        JLabel lblDataValidade = new JLabel("Data de Validade (yyyy-MM-dd):");
        txtDataValidade = new JTextField(20);

        // Botões
        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnVoltar = new JButton("Voltar");

        // Adicionando componentes ao painel
        gbc.gridwidth = 1; // Reseta para uma coluna
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblIdentificador, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtIdentificador, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblTaxaJuros, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(txtTaxaJuros, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblDataValidade, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(txtDataValidade, gbc);

        // Adicionando botões
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(btnIncluir, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(btnAlterar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(btnExcluir, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(btnBuscar, gbc);

        // Posicionando o botão "Voltar" abaixo dos outros botões
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnVoltar, gbc);

        // Ações dos botões
        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incluirTituloDivida();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarTituloDivida();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirTituloDivida();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTituloDivida();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaHome(); // Volta para a tela inicial
                dispose();
            }
        });

        setVisible(true);
    }

    // Método para incluir um título de dívida
    private void incluirTituloDivida() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText());

            TituloDivida titulo = new TituloDivida(identificador, nome, dataValidade, taxaJuros);
            String resultado = mediatorTituloDivida.incluir(titulo);

            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Título incluído com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao incluir título: " + resultado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage());
        }
    }

    // Método para alterar um título de dívida
    private void alterarTituloDivida() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText());

            TituloDivida titulo = mediatorTituloDivida.buscar(identificador);
            if (titulo != null) {
                titulo.setNome(nome);
                titulo.setTaxaJuros(taxaJuros);
                titulo.setDataValidade(dataValidade);

                String resultado = mediatorTituloDivida.alterar(titulo);
                if (resultado == null) {
                    JOptionPane.showMessageDialog(this, "Título alterado com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar título: " + resultado);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Título inexistente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage());
        }
    }

    // Método para excluir um título de dívida
    private void excluirTituloDivida() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());

            String resultado = mediatorTituloDivida.excluir(identificador);
            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Título excluído com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir título: " + resultado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir título: " + ex.getMessage());
        }
    }

    // Método para buscar um título de dívida
    private void buscarTituloDivida() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            TituloDivida titulo = mediatorTituloDivida.buscar(identificador);

            if (titulo != null) {
                txtNome.setText(titulo.getNome());
                txtTaxaJuros.setText(String.valueOf(titulo.getTaxaJuros()));
                txtDataValidade.setText(titulo.getDataValidade().toString());
            } else {
                JOptionPane.showMessageDialog(this, "Título inexistente.");
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar título: " + ex.getMessage());
        }
    }

    // Método para limpar os campos da tela
    private void limparCampos() {
        txtIdentificador.setText("");
        txtNome.setText("");
        txtTaxaJuros.setText("");
        txtDataValidade.setText("");
    }

    public static void main(String[] args) {
        new TelaCRUDTituloDivida();
    }
}
