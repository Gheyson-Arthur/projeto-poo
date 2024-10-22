package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class TelaCRUDEntidadesOperadoras extends JFrame {

    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getMediatorEntidadeOperadora();

    private boolean modoAlteracao = false;

    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JCheckBox chkAutorizadoAcao;
    private JTextField txtSaldoAcao;
    private JTextField txtSaldoTituloDivida;

    private JPanel painelCampos;
    private JButton btnSalvar;
    private JButton btnExcluirFormulario;
    private JButton btnBuscarFormulario;

    private Font montserrat;

    private JLabel lblIdentificador;
    private JLabel lblNome;
    private JLabel lblAutorizadoAcao;
    private JLabel lblSaldoAcao;
    private JLabel lblSaldoTituloDivida;

    public TelaCRUDEntidadesOperadoras() {
        try {
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("src/br/com/cesarschool/poo/titulos/telas/resources/static/Montserrat-Regular.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            montserrat = new Font("Arial", Font.PLAIN, 14); // Fallback para Arial
        }

        setTitle("CRUD Entidade Operadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Tamanho ajustado
        setLocationRelativeTo(null);

        // Layout principal - painel principal para agrupar campos e botões
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título da página
        JLabel lblTituloPagina = new JLabel("Gerenciamento de Entidades Operadoras", JLabel.CENTER);
        lblTituloPagina.setFont(montserrat.deriveFont(Font.BOLD, 24f));
        lblTituloPagina.setForeground(new Color(30, 61, 88));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelPrincipal.add(lblTituloPagina, gbc);

        // Painel para campos, inicialmente oculto
        painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBackground(Color.WHITE);
        painelCampos.setVisible(false); // Iniciar oculto

        lblIdentificador = new JLabel("Identificador:*");
        lblIdentificador.setForeground(new Color(30, 61, 88));
        lblIdentificador.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        txtIdentificador = new JTextField(20);

        lblNome = new JLabel("Nome:*");
        lblNome.setForeground(new Color(30, 61, 88));
        lblNome.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        txtNome = new JTextField(20);
        
        lblAutorizadoAcao = new JLabel("Autorizado a Ação:*");
        lblAutorizadoAcao.setForeground(new Color(30, 61, 88));
        lblAutorizadoAcao.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        chkAutorizadoAcao = new JCheckBox();
        
        lblSaldoAcao = new JLabel("Creditar no Saldo da Ação:");
        lblSaldoAcao.setForeground(new Color(30, 61, 88));
        lblSaldoAcao.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        txtSaldoAcao = new JTextField(20);

        lblSaldoTituloDivida = new JLabel("Creditar no Saldo do Título de Dívida:");
        lblSaldoTituloDivida.setForeground(new Color(30, 61, 88));
        lblSaldoTituloDivida.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        txtSaldoTituloDivida = new JTextField(20);

        addPlaceholder(txtIdentificador, "Digite o identificador");
        addPlaceholder(txtNome, "Digite o nome da ação");
        chkAutorizadoAcao.setSelected(false);
        addPlaceholder(txtSaldoAcao, "Digite o saldo a ser creditado");
        addPlaceholder(txtSaldoTituloDivida, "Digite o saldo a ser creditado");

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(lblIdentificador, gbc);
        gbc.gridx = 1;
        painelCampos.add(txtIdentificador, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(lblNome, gbc);
        gbc.gridx = 1;
        painelCampos.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCampos.add(lblAutorizadoAcao, gbc);
        gbc.gridx = 1;
        painelCampos.add(chkAutorizadoAcao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCampos.add(lblSaldoAcao, gbc);
        gbc.gridx = 1;
        painelCampos.add(txtSaldoAcao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        painelCampos.add(lblSaldoTituloDivida, gbc);
        gbc.gridx = 1;
        painelCampos.add(txtSaldoTituloDivida, gbc);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarEntidadeOperadora());

        btnExcluirFormulario = new JButton("Excluir");
        btnExcluirFormulario.addActionListener(e -> excluirEntidadeOperadora());
        btnExcluirFormulario.setVisible(false); // Inicialmente oculto

        btnBuscarFormulario = new JButton("Buscar");
        btnBuscarFormulario.addActionListener(e -> buscarEntidadeOperadora());
        btnBuscarFormulario.setVisible(false); // Inicialmente oculto

        // Configuração do layout dos botões no painel de campos
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        painelCampos.add(btnSalvar, gbc);

        // Adicionando os novos botões ao painel de campos
        gbc.gridy = 7;
        painelCampos.add(btnExcluirFormulario, gbc);

        gbc.gridy = 8;
        painelCampos.add(btnBuscarFormulario, gbc);

        painelPrincipal.add(painelCampos, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(Color.WHITE);

        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnVoltar = new JButton("Voltar");

        configurarBotao(btnIncluir, "src/br/com/cesarschool/poo/titulos/telas/resources/plus.png");
        configurarBotao(btnAlterar, "src/br/com/cesarschool/poo/titulos/telas/resources/pencil.png");
        configurarBotao(btnExcluir, "src/br/com/cesarschool/poo/titulos/telas/resources/trash.png");
        configurarBotao(btnBuscar, "src/br/com/cesarschool/poo/titulos/telas/resources/search.png");
        configurarBotao(btnVoltar, "src/br/com/cesarschool/poo/titulos/telas/resources/back.png");
        configurarBotao(btnBuscarFormulario, "src/br/com/cesarschool/poo/titulos/telas/resources/search.png");
        configurarBotao(btnExcluirFormulario, "src/br/com/cesarschool/poo/titulos/telas/resources/trash.png");
        configurarBotao(btnSalvar, "src/br/com/cesarschool/poo/titulos/telas/resources/checkmark.png");

        painelBotoes.add(btnIncluir);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnBuscar);

        gbc.gridy = 2;
        painelPrincipal.add(painelBotoes, gbc);

        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelVoltar.setBackground(Color.WHITE);
        painelVoltar.add(btnVoltar);

        gbc.gridy = 3;
        painelPrincipal.add(painelVoltar, gbc);

        add(painelPrincipal);

        // Listeners para botões
        btnIncluir.addActionListener(e -> exibirFormularioParaInclusao());
        btnAlterar.addActionListener(e -> exibirFormularioParaAlteracao());
        btnExcluir.addActionListener(e -> exibirFormularioParaExclusao());
        btnBuscar.addActionListener(e -> exibirFormularioParaBusca());
        btnVoltar.addActionListener(e -> {
            new TelaHome();
            dispose();
        });

        setVisible(true);
    }

    private void configurarBotao(JButton botao, String caminhoIcone) {
        botao.setBackground(new Color(30, 61, 88));
        botao.setForeground(new Color(248, 248, 255));
        botao.setFont(montserrat);
        botao.setIcon(new ImageIcon(caminhoIcone)); // Adiciona o ícone
        botao.setFocusable(false); // Remove o foco visual
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(50, 90, 120));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(30, 61, 88));
            }
        });
    }

    private void salvarEntidadeOperadora() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());

            EntidadeOperadora entidadeOperadora = mediatorEntidadeOperadora.buscar(identificador);
            if (modoAlteracao) {
                if (entidadeOperadora == null) {
                    JOptionPane.showMessageDialog(this, "Erro: a entidade operadora não existe. Não é possível alterar.", "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    alterarEntidadeOperadora();
                }
            } else {
                if (entidadeOperadora == null) {
                    incluirEntidadeOperadora();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: a entidade operadora já existe. Não é possível incluir.", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar entidade operadora: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirFormularioParaInclusao() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, true, true, false, false);
        limparCampos();
        btnSalvar.setVisible(true);
        btnExcluirFormulario.setVisible(false);
        btnBuscarFormulario.setVisible(false);
        modoAlteracao = false;
    }

    private void exibirFormularioParaAlteracao() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, true, true, true, true);
        limparCampos();
        btnSalvar.setVisible(true);
        btnExcluirFormulario.setVisible(false);
        btnBuscarFormulario.setVisible(false);
        modoAlteracao = true;
    }

    private void exibirFormularioParaExclusao() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, false, false, false, false); // Apenas Identificador visível
        limparCampos();
        btnSalvar.setVisible(false);
        btnExcluirFormulario.setVisible(true);
        btnBuscarFormulario.setVisible(false);
    }

    private void exibirFormularioParaBusca() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, false, false, false, false); // Apenas Identificador visível
        limparCampos();
        btnSalvar.setVisible(false);
        btnExcluirFormulario.setVisible(false);
        btnBuscarFormulario.setVisible(true);
    }

    private void configurarVisibilidadeCampos(boolean identificadorVisivel, boolean nomeVisivel, boolean autorizadoAcao, boolean saldoAcao, boolean saldoTituloDivida) {
        lblIdentificador.setVisible(identificadorVisivel);
        txtIdentificador.setVisible(identificadorVisivel);
        txtIdentificador.setEnabled(identificadorVisivel);

        lblNome.setVisible(nomeVisivel);
        txtNome.setVisible(nomeVisivel);
        txtNome.setEnabled(nomeVisivel);
        
        lblAutorizadoAcao.setVisible(autorizadoAcao);
        chkAutorizadoAcao.setVisible(autorizadoAcao);
        chkAutorizadoAcao.setEnabled(autorizadoAcao);

        lblSaldoAcao.setVisible(saldoAcao);
        txtSaldoAcao.setVisible(saldoAcao);
        txtSaldoAcao.setEnabled(saldoAcao);

        lblSaldoTituloDivida.setVisible(saldoTituloDivida);
        txtSaldoTituloDivida.setVisible(saldoTituloDivida);
        txtSaldoTituloDivida.setEnabled(saldoTituloDivida);
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new Color(30, 61, 88)); // Cor ao digitar
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY); // Cor do placeholder
                    field.setText(placeholder);
                }
            }
        });

        // Configurações para que o texto do placeholder suma ao digitar
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (field.getForeground().equals(Color.GRAY)) {
                    field.setText("");
                    field.setForeground(new Color(30, 61, 88));
                }
            }
        });
    }

    private void configurarPopups() {
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", new Color(30, 61, 88));
        UIManager.put("OptionPane.messageFont", montserrat.deriveFont(Font.BOLD, 14f));
        UIManager.put("Button.background", new Color(30, 61, 88));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", montserrat.deriveFont(Font.BOLD, 14f));
    }

    private void restaurarEstiloPadraoPopups() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.font", null);
    }

    private void limparCampos() {
        setPlaceholder(txtIdentificador, "Digite o identificador");
        setPlaceholder(txtNome, "Digite o nome da ação");
        chkAutorizadoAcao.setSelected(false);
        setPlaceholder(txtSaldoAcao, "Digite o saldo a ser creditado");
        setPlaceholder(txtSaldoTituloDivida, "Digite o saldo a ser creditado");
    }

    private void setPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new Color(30, 61, 88)); // Cor ao digitar
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY); // Cor do placeholder
                    field.setText(placeholder);
                }
            }
        });

        field.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (field.getForeground().equals(Color.GRAY)) {
                    field.setText("");
                    field.setForeground(new Color(30, 61, 88));
                }
            }
        });
    }

    private void incluirEntidadeOperadora() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = chkAutorizadoAcao.isSelected();

            EntidadeOperadora entidadeOperadora = new EntidadeOperadora(identificador, nome, autorizadoAcao);
            String resultado = mediatorEntidadeOperadora.incluir(entidadeOperadora);

            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Entidade Operadora incluída com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao incluir a Entidade Operadora: " + resultado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao incluir entidade operadora: " + ex.getMessage());
        }
    }

    private void alterarEntidadeOperadora() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = chkAutorizadoAcao.isSelected();
            double saldoAcao = Double.parseDouble(txtSaldoAcao.getText());
            double saldoTituloDivida = Double.parseDouble(txtSaldoTituloDivida.getText());

            EntidadeOperadora entidadeOperadora = mediatorEntidadeOperadora.buscar(identificador);
            if (entidadeOperadora != null) {
                entidadeOperadora.setNome(nome);
                entidadeOperadora.setAutorizadoAcao(autorizadoAcao);
                entidadeOperadora.creditarSaldoAcao(saldoAcao);
                entidadeOperadora.creditarSaldoTituloDivida(saldoTituloDivida);
                String resultado = mediatorEntidadeOperadora.alterar(entidadeOperadora);

                if (resultado == null) {
                    JOptionPane.showMessageDialog(this, "Entidade Operadora alterada com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar a Entidade Operadora: " + resultado);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Entidade Operadora não encontrada!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar entidade operadora: " + ex.getMessage());
        }
    }

    private void buscarEntidadeOperadora() {
        configurarPopups();
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            EntidadeOperadora entidadeOperadora = mediatorEntidadeOperadora.buscar(identificador);

            if (entidadeOperadora != null) {
                String mensagem = "ID: " + entidadeOperadora.getIdentificador() + "\n" +
                        "Nome: " + entidadeOperadora.getNome() + "\n" +
                        "Autorizado a Ação: " + entidadeOperadora.getAutorizadoAcao() + "\n" +
                        "Saldo Ação: " + entidadeOperadora.getSaldoAcao() + "\n" +
                        "Saldo Título Dívida: " + entidadeOperadora.getSaldoTituloDivida();

                JOptionPane.showMessageDialog(this, mensagem, "Informações da Entidade Operadora", JOptionPane.INFORMATION_MESSAGE);
                txtIdentificador.setText(String.valueOf(entidadeOperadora.getIdentificador()));
                txtNome.setText(entidadeOperadora.getNome());
                chkAutorizadoAcao.setEnabled(false);
                txtSaldoAcao.setText(String.valueOf(entidadeOperadora.getSaldoAcao()));
                txtSaldoTituloDivida.setText(String.valueOf(entidadeOperadora.getSaldoTituloDivida()));
            } else {
                JOptionPane.showMessageDialog(this, "Entidade Operadora não encontrada!");
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar entidade operadora: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            restaurarEstiloPadraoPopups();
        }
    }

    private void excluirEntidadeOperadora() {
        configurarPopups();
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String mensagem = mediatorEntidadeOperadora.excluir(identificador);
            
            if (mensagem == null) {
                JOptionPane.showMessageDialog(this, "Entidade Operadora excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir Entidade Operadora: " + mensagem, "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir entidade operadora: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            restaurarEstiloPadraoPopups();
        }
    }

    public static void main(String[] args) {
        new TelaCRUDAcaoSwing();
    }
}