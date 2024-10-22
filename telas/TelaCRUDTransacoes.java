package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class TelaCRUDTransacoes extends JFrame {

    private final MediatorAcao mediatorAcao = MediatorAcao.getMediatorAcao();

    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtValorUnitario;
    private JTextField txtDataValidade;

    public TelaCRUDTransacoes() {
        setTitle("CRUD Ação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Tamanho ajustado
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Espaçamento maior entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título da página
        JLabel lblTituloPagina = new JLabel("Gerenciamento de Ações", JLabel.CENTER);
        lblTituloPagina.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloPagina.setForeground(new Color(30, 45, 90)); // Cor do texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        add(lblTituloPagina, gbc);

        // Campos da tela
        JLabel lblIdentificador = new JLabel("Identificador:");
        txtIdentificador = new JTextField(20); // Ajuste do tamanho dos campos

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);

        JLabel lblValorUnitario = new JLabel("Valor Unitário:");
        txtValorUnitario = new JTextField(20);

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
        add(lblValorUnitario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(txtValorUnitario, gbc);

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

        // Listeners para botões
        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incluirAcao();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarAcao();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirAcao();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAcao();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para voltar, abrir a TelaHome ou outra tela
                new TelaHome();
                dispose(); // Fecha a tela atual
            }
        });

        setVisible(true);
    }

    private void incluirAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            double valorUnitario = Double.parseDouble(txtValorUnitario.getText());
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText());

            Acao novaAcao = new Acao(identificador, nome, dataValidade, valorUnitario);
            String resultado = mediatorAcao.incluir(novaAcao);

            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Ação incluída com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao incluir ação: " + resultado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage());
        }
    }

    private void alterarAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            double valorUnitario = Double.parseDouble(txtValorUnitario.getText());
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText());

            Acao acao = mediatorAcao.buscar(identificador);
            if (acao != null) {
                acao.setNome(nome);
                acao.setValorUnitario(valorUnitario);
                acao.setDataValidade(dataValidade);

                String resultado = mediatorAcao.alterar(acao);
                if (resultado == null) {
                    JOptionPane.showMessageDialog(this, "Ação alterada com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar ação: " + resultado);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ação inexistente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage());
        }
    }

    private void excluirAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());

            String resultado = mediatorAcao.excluir(identificador);
            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Ação excluída com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir ação: " + resultado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir ação: " + ex.getMessage());
        }
    }

    private void buscarAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            Acao acao = mediatorAcao.buscar(identificador);

            if (acao != null) {
                txtNome.setText(acao.getNome());
                txtValorUnitario.setText(String.valueOf(acao.getValorUnitario()));
                txtDataValidade.setText(acao.getDataValidade().toString());
            } else {
                JOptionPane.showMessageDialog(this, "Ação inexistente.");
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar ação: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtIdentificador.setText("");
        txtNome.setText("");
        txtValorUnitario.setText("");
        txtDataValidade.setText("");
    }

    public static void main(String[] args) {
        new TelaCRUDAcaoSwing();
    }
}
