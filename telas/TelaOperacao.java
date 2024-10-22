package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;

public class TelaOperacao extends JFrame {

    private final MediatorAcao mediatorAcao = MediatorAcao.getMediatorAcao();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getMediatorTituloDivida();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getMediatorEntidadeOperadora();

    private JComboBox<String> cbTipoOperacao;
    private JComboBox<Object> cbAcaoOuTitulo;
    private JComboBox<Object> cbEntidadeCredito;
    private JComboBox<Object> cbEntidadeDebito;
    private JTextField txtValorOperacao;
    private JButton btnRealizarOperacao;
    private JButton btnEnviar;
    private JButton btnExtrato;
    private JPanel painelFormulario;

    public TelaOperacao() {
        setTitle("Operação de Transações");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botão "Realizar Operação"
        btnRealizarOperacao = new JButton("Realizar Operação");
        btnRealizarOperacao.addActionListener(e -> mostrarFormularioOperacao());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelPrincipal.add(btnRealizarOperacao, gbc);

        // Botão "Extrato"
        btnExtrato = new JButton("Extrato");
//        btnExtrato.addActionListener(e -> gerarExtrato());
        gbc.gridy = 1;
        painelPrincipal.add(btnExtrato, gbc);

        // Painel do Formulário (oculto inicialmente)
        painelFormulario = criarPainelFormulario();
        painelFormulario.setVisible(false);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        painelPrincipal.add(painelFormulario, gbc);

        add(painelPrincipal);
        setVisible(true);
    }

    private JPanel criarPainelFormulario() {
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de Operação
        JLabel lblTipoOperacao = new JLabel("Tipo de Operação:");
        cbTipoOperacao = new JComboBox<>(new String[]{"Ação", "Título"});
        cbTipoOperacao.addActionListener(e -> atualizarListaAcaoOuTitulo());
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(lblTipoOperacao, gbc);
        gbc.gridx = 1;
        painelFormulario.add(cbTipoOperacao, gbc);

        // Ação ou Título
        JLabel lblAcaoOuTitulo = new JLabel("Selecione:");
        cbAcaoOuTitulo = new JComboBox<>();
        atualizarListaAcaoOuTitulo(); // Inicializar a lista
        gbc.gridy = 1;
        gbc.gridx = 0;
        painelFormulario.add(lblAcaoOuTitulo, gbc);
        gbc.gridx = 1;
        painelFormulario.add(cbAcaoOuTitulo, gbc);

        // Entidade Crédito
        JLabel lblEntidadeCredito = new JLabel("Entidade Crédito:");
        cbEntidadeCredito = new JComboBox<>(listarEntidades().toArray());
        gbc.gridy = 2;
        gbc.gridx = 0;
        painelFormulario.add(lblEntidadeCredito, gbc);
        gbc.gridx = 1;
        painelFormulario.add(cbEntidadeCredito, gbc);

        // Entidade Débito
        JLabel lblEntidadeDebito = new JLabel("Entidade Débito:");
        cbEntidadeDebito = new JComboBox<>(listarEntidades().toArray());
        gbc.gridy = 3;
        gbc.gridx = 0;
        painelFormulario.add(lblEntidadeDebito, gbc);
        gbc.gridx = 1;
        painelFormulario.add(cbEntidadeDebito, gbc);

        // Valor da Operação
        JLabel lblValorOperacao = new JLabel("Valor:");
        txtValorOperacao = new JTextField(20);
        gbc.gridy = 4;
        gbc.gridx = 0;
        painelFormulario.add(lblValorOperacao, gbc);
        gbc.gridx = 1;
        painelFormulario.add(txtValorOperacao, gbc);

        // Botão "Enviar" para confirmar a operação
        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e -> realizarOperacao());
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        painelFormulario.add(btnEnviar, gbc);

        return painelFormulario;
    }

    private void mostrarFormularioOperacao() {
        painelFormulario.setVisible(true);
    }

    private void atualizarListaAcaoOuTitulo() {
        cbAcaoOuTitulo.removeAllItems();
        if (cbTipoOperacao.getSelectedItem().equals("Ação")) {
            List<Acao> acoes = List.of(mediatorAcao.listar());
            for (Acao acao : acoes) {
                cbAcaoOuTitulo.addItem("" + acao.getIdentificador() + " - " + acao.getNome());
            }
        } else {
            List<TituloDivida> titulos = List.of(mediatorTituloDivida.listar());
            for (TituloDivida titulo : titulos) {
                cbAcaoOuTitulo.addItem(titulo);
            }
        }
    }

    private List<String> listarEntidades() {
        return Arrays.stream(mediatorEntidadeOperadora.listar())
                .map(entidade -> entidade.getIdentificador() + " - " + entidade.getNome())
                .collect(Collectors.toList());
    }

    private void realizarOperacao() {
        // Lógica para realizar a operação
    }

//    private void gerarExtrato() {
//        String idStr = JOptionPane.showInputDialog(this, "Informe o ID para gerar o extrato:");
//        try {
//            int id = Integer.parseInt(idStr);
//            String extrato = MediatorOperacao.getMediatorOperacao().gerarExtrato(id);
//            JOptionPane.showMessageDialog(this, extrato, "Extrato", JOptionPane.INFORMATION_MESSAGE);
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "ID inválido", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    public static void main(String[] args) {
        new TelaOperacao();
    }
}
