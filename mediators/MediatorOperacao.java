package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class MediatorOperacao {

    private final MediatorAcao mediatorAcao = MediatorAcao.getMediatorAcao();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getMediatorTituloDivida();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getMediatorEntidadeOperadora();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    private static MediatorOperacao mediatorOperacao;

    private MediatorOperacao() {};

    public static MediatorOperacao getMediatorOperacao() {
        if (mediatorOperacao == null) {
            mediatorOperacao = new MediatorOperacao();
        }

        return mediatorOperacao;
    }

    public String realizarOperacao(boolean ehAcao, int entidadeCredito, int idEntidadeDebito, int idAcaoOuTitulo, double valor) {
        // 1 - Validar o valor, que deve ser maior que zero.
        if(valor <= 0) {
            return "Valor inválido";
        }

        // 2 - Buscar a entidade de crédito no mediator de entidade operadora.
        EntidadeOperadora credito = mediatorEntidadeOperadora.buscar(entidadeCredito);

        // Se não encontrar, retornar a mensagem "Entidade crédito inexistente".
        if(credito == null) {
            return "Entidade crédito inexistente";
        }

        // 3 - Buscar a entidade de débito no mediator de entidade operadora
        EntidadeOperadora debito = mediatorEntidadeOperadora.buscar(idEntidadeDebito);

        // Se não encontrar, retornar a mensagem "Entidade débito inexistente".
        if (debito == null) {
            return "Entidade débito inexistente";
        }

        // 4 - Se ehAcao for true e a entidade de crédito não for autorizada para ação,
        // retornar a mensagem "Entidade de crédito não autorizada para ação"
        // 5 - Se ehAcao for true e a entidade de débito não for autorizada para ação,
        // retornar a mensagem "Entidade de débito não autorizada para ação
        if (ehAcao && (!credito.getAutorizadoAcao() || !debito.getAutorizadoAcao())) {
            return credito.getAutorizadoAcao() ? "Entidade de débito não autorizada para ação" : "Entidade de crédito não autorizada para ação";
        }

        // 6 - Buscar a ação (se ehAcao for true) ou o título (se ehAcao for false) no mediator de ação ou de título.
        Acao acao = ehAcao ? mediatorAcao.buscar(idAcaoOuTitulo) : null;
        TituloDivida titulo = ehAcao ? null : mediatorTituloDivida.buscar(idAcaoOuTitulo);

        // 7- A depender de ehAcao, validar o saldoAcao ou o saldoTituloDivida da entidade de débito.
        // O saldo deve ser maior ou igual a valor. Se não for, retornar a mensagem
        // "Saldo da entidade débito insuficiente".
        if (ehAcao) {
            if (debito.getSaldoAcao() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
        } else {
            if (debito.getSaldoTituloDivida() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
        }

        // 8- Se ehAcao for true, verificar se valorUnitario da ação é maior do que valor. Se for, retornar a mensagem
        // "Valor da operação e menor do que o valor unitário da ação"
        if (ehAcao) {
            if (acao.getValorUnitario() > valor) {
                return "Valor da operação e menor do que o valor unitário da ação";
            }
        }

        // 9- Calcular o valor da operação. Se ehAcao for true, o valor da operação é igual a valor.
        // Se ehAcao for false, o valor da operação é igual ao retorno do método calcularPrecoTransacao,
        //  invocado a partir do título da dívida buscado.
        //  valor deve ser passado como parâmetro na invocação deste método.
        double valorOperacao = ehAcao ? valor : titulo.calcularPrecoTransacao(valor);

        //   10- Invocar o método creditarSaldoAcao ou creditarSaldoTituloDivida de entidade de crédito,
        //    passando o valor da operação.
        if (ehAcao) {
            credito.creditarSaldoAcao(valorOperacao);
        } else {
            credito.creditarSaldoTituloDivida(valorOperacao);
        }

        //    11- Invocar o método debitarSaldoAcao ou debitarSaldoTituloDivida da entidade de débito,
        //     passando o valor da operação.
        if (ehAcao) {
            debito.debitarSaldoAcao(valorOperacao);
        } else {
            debito.debitarSaldoTituloDivida(valorOperacao);
        }

        //     12- Alterar entidade de crédito no mediator de entidade operadora.
        //     Se o retorno do alterar for uma mensagem diferente de null, retornar a mensagem.
        String altecaoCredito = mediatorEntidadeOperadora.alterar(credito);

        if (altecaoCredito != null) {
            return altecaoCredito;
        }

        //     13- Alterar entidade de débito no mediator de entidade operadora.
        //     Se o retorno do alterar for uma mensagem diferente de null, retornar a mensagem.
        String alteracaoDebito = mediatorEntidadeOperadora.alterar(debito);

        if (alteracaoDebito != null) {
            return alteracaoDebito;
        }

        //     14- Criar um objeto do tipo Transacao, com os seguintes valores de atributos:
        //     entidadeCredito - a entidade de crédito buscada
        //     entidadeDebito - a entidade de débito buscada
        //     acao - se ehAcao for true, a ação buscada, caso contrário, null
        //     tituloDivida - se ehAcao for false, o título buscado, caso contrário, null
        //     valorOperacao - o valor da operação calculado no item 9
        //     dataHoraOperacao - a data e a hora atuais

        Transacao transacao = new Transacao(credito, debito, acao, titulo, valorOperacao, LocalDateTime.now());

        //     15- Incluir a transação criada no repositório de transação.
        repositorioTransacao.incluir(transacao);

        return "Operação realizada com sucesso";
    }

    public Transacao[] gerarExtrato(int entidade) {

        Transacao [] transacaoCredito = repositorioTransacao.buscarPorEntidadeCredora(entidade);
        Transacao [] transacaoDebito = repositorioTransacao.buscarPorEntidadeDebito(entidade);

       Transacao[] transacoes = new Transacao[transacaoCredito.length + transacaoDebito.length];

       // Copia o primeiro array para o novo array
       System.arraycopy(transacaoCredito, 0, transacoes, 0, transacaoCredito.length);

       // Copia o segundo array para o novo array logo após o primeiro
       System.arraycopy(transacaoDebito, 0, transacoes, transacaoCredito.length, transacaoDebito.length);

       Arrays.sort(transacoes, new Comparator<Transacao>() {
           @Override
           public int compare(Transacao t1, Transacao t2) {
               // Comparação decrescente: o mais recente vem primeiro
               return t2.getDataHoraOperacao().compareTo(t1.getDataHoraOperacao());
           }
       });

        return transacoes;
    }
}
