package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class TelaCRUDTituloDivida extends JFrame {

    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getMediatorTituloDivida();

    private boolean modoAlteracao = false;

    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtTaxaJuros;
    private JTextField txtDataValidade;

    private JPanel painelCampos;
    private JButton btnSalvar;
    private JButton btnExcluirFormulario;
    private JButton btnBuscarFormulario;

    private Font montserrat;

    private JLabel lblIdentificador;
    private JLabel lblNome;
    private JLabel lblDataValidade;
    private JLabel lblTaxaJuros;

    public TelaCRUDTituloDivida() {
        try {
            montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("src/br/com/cesarschool/poo/titulos/telas/resources/static/Montserrat-Regular.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            montserrat = new Font("Arial", Font.PLAIN, 14); // Fallback para Arial
        }

        setTitle("CRUD Título Dívida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Tamanho da janela
        setLocationRelativeTo(null);

        // Layout principal - painel principal para agrupar campos e botões
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título da página
        JLabel lblTituloPagina = new JLabel("Gerenciamento de Títulos Dívidas", JLabel.CENTER);
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
        
        lblTaxaJuros = new JLabel("Taxa de Juros:*");
        lblTaxaJuros.setForeground(new Color(30, 61, 88));
        lblTaxaJuros.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        txtTaxaJuros = new JTextField(20);

        lblDataValidade = new JLabel("Data de Validade:*");
        lblDataValidade.setForeground(new Color(30, 61, 88));
        lblDataValidade.setFont(montserrat.deriveFont(Font.BOLD, 16f));
        txtDataValidade = new JTextField(20);

        addPlaceholder(txtIdentificador, "Digite o identificador");
        addPlaceholder(txtNome, "Digite o nome da ação");
        addPlaceholder(txtTaxaJuros, "Digite a taxa de juros");
        addPlaceholder(txtDataValidade, "yyyy-mm-dd");

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
        painelCampos.add(lblTaxaJuros, gbc);
        gbc.gridx = 1;
        painelCampos.add(txtTaxaJuros, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCampos.add(lblDataValidade, gbc);
        gbc.gridx = 1;
        painelCampos.add(txtDataValidade, gbc);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarTitulo());

        btnExcluirFormulario = new JButton("Excluir");
        btnExcluirFormulario.addActionListener(e -> excluirTituloDivida());
        btnExcluirFormulario.setVisible(false); // Inicialmente oculto

        btnBuscarFormulario = new JButton("Buscar");
        btnBuscarFormulario.addActionListener(e -> buscarTituloDivida());
        btnBuscarFormulario.setVisible(false); // Inicialmente oculto

        // Configuração do layout dos botões no painel de campos
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        painelCampos.add(btnSalvar, gbc);

        // Adicionando os novos botões ao painel de campos
        gbc.gridy = 6;
        painelCampos.add(btnExcluirFormulario, gbc);

        gbc.gridy = 7;
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

    private void salvarTitulo() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());

            TituloDivida titulo = mediatorTituloDivida.buscar(identificador);
            if (modoAlteracao) {
                // Se for modo de alteração, verifique se o título existe
                if (titulo == null) {
                    JOptionPane.showMessageDialog(this, "Erro: o título não existe. Não é possível alterar.", "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Se o título existir, altere-o
                    alterarTituloDivida();
                }
            } else {
                // Se for modo de inclusão, inclua o título
                if (titulo == null) {
                    incluirTituloDivida();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: o título já existe. Não é possível incluir.", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar título: " + ex.getMessage());
        }
    }

    private void exibirFormularioParaInclusao() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, true, true, true);
        limparCampos();
        btnSalvar.setVisible(true);
        btnExcluirFormulario.setVisible(false);
        btnBuscarFormulario.setVisible(false);
        modoAlteracao = false; // Modo de inclusão
    }

    private void exibirFormularioParaAlteracao() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, true, true, true);
        limparCampos();
        btnSalvar.setVisible(true);
        btnExcluirFormulario.setVisible(false);
        btnBuscarFormulario.setVisible(false);
        modoAlteracao = true; // Modo de alteração
    }

    private void exibirFormularioParaExclusao() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, false, false, false); // Apenas Identificador visível
        limparCampos();
        btnSalvar.setVisible(false);
        btnExcluirFormulario.setVisible(true);
        btnBuscarFormulario.setVisible(false);
    }

    private void exibirFormularioParaBusca() {
        painelCampos.setVisible(true);
        configurarVisibilidadeCampos(true, false, false, false); // Apenas Identificador visível
        limparCampos();
        btnSalvar.setVisible(false);
        btnExcluirFormulario.setVisible(false);
        btnBuscarFormulario.setVisible(true);
    }

    private void configurarVisibilidadeCampos(boolean identificadorVisivel, boolean nomeVisivel, boolean valorUnitarioVisivel, boolean dataValidadeVisivel) {
        lblIdentificador.setVisible(identificadorVisivel);
        txtIdentificador.setVisible(identificadorVisivel);
        txtIdentificador.setEnabled(identificadorVisivel);

        lblNome.setVisible(nomeVisivel);
        txtNome.setVisible(nomeVisivel);
        txtNome.setEnabled(nomeVisivel);

        lblTaxaJuros.setVisible(valorUnitarioVisivel);
        txtTaxaJuros.setVisible(valorUnitarioVisivel);
        txtTaxaJuros.setEnabled(valorUnitarioVisivel);

        lblDataValidade.setVisible(dataValidadeVisivel);
        txtDataValidade.setVisible(dataValidadeVisivel);
        txtDataValidade.setEnabled(dataValidadeVisivel);
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
    }

    private void restaurarEstiloPadraoPopups() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
    }

    private void limparCampos() {
        setPlaceholder(txtIdentificador, "Digite o identificador");
        setPlaceholder(txtNome, "Digite o nome da ação");
        setPlaceholder(txtTaxaJuros, "Digite a taxa de juros");
        setPlaceholder(txtDataValidade, "yyyy-mm-dd");
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
                JOptionPane.showMessageDialog(this, "Título incluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao incluir título: " + resultado, "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(this, "Título alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar título: " + resultado, "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Título inexistente.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para buscar um título de dívida
    private void buscarTituloDivida() {
        configurarPopups();
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            TituloDivida titulo = mediatorTituloDivida.buscar(identificador);

            if (titulo != null) {
                String mensagem = "ID " + titulo.getIdentificador() + "\n" +
                        "Nome: " + titulo.getNome() + "\n" +
                        "Taxa de Juros: " + titulo.getTaxaJuros() + "\n" +
                        "Data de Validade: " + titulo.getDataValidade();
                JOptionPane.showMessageDialog(this, mensagem, "Informações do Título Dívida", JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText(titulo.getNome());
                txtTaxaJuros.setText(String.valueOf(titulo.getTaxaJuros()));
                txtDataValidade.setText(titulo.getDataValidade().toString());
            } else {
                JOptionPane.showMessageDialog(this, "Título inexistente.", "Erro", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar título: " + ex.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
        } finally {
            restaurarEstiloPadraoPopups();
        }
    }

    // Método para excluir um título de dívida
    private void excluirTituloDivida() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());

            String resultado = mediatorTituloDivida.excluir(identificador);
            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Título excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir título: " + resultado, "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir título: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            restaurarEstiloPadraoPopups();
        }
    }

    public static void main(String[] args) {
        new TelaCRUDTituloDivida();
    }
}
