package br.com.cesarschool.poo.titulos.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class MainTransacoes {
    public static void main(String[] args) {
        // Criar um repositório de transações
        RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

        // Criar entidades
        EntidadeOperadora entidadeCredito = new EntidadeOperadora(1, "Banco A", true);
        EntidadeOperadora entidadeDebito = new EntidadeOperadora(2, "Banco B", true);

        // Criar uma ação
        Acao acao = new Acao(1, "Compra de Ações", LocalDate.now().plusDays(30), 10.0);

        // Criar um título de dívida
        TituloDivida tituloDivida = new TituloDivida(1, "Título de Dívida", LocalDate.now().plusMonths(6), 5.0);

        // Criar uma transação
        Transacao transacao = new Transacao(
                entidadeCredito,
                entidadeDebito,
                acao,
                tituloDivida,
                100.0, // valor da operação
                LocalDateTime.now() // data e hora da operação
        );

        // Incluir a transação no repositório
        repositorioTransacao.incluir(transacao);
        System.out.println("Transação incluída com sucesso!");

        // Buscar transações por entidade credora
        Transacao[] transacoesCredora = repositorioTransacao.buscarPorEntidadeCredora(1);
        System.out.println("Transações por entidade credora:");
        for (Transacao t : transacoesCredora) {
            if (t != null) {
                System.out.println(t);
            }
        }

        // Buscar transações por entidade de débito
        Transacao[] transacoesDebito = repositorioTransacao.buscarPorEntidadeDebito(2);
        System.out.println("Transações por entidade de débito:");
        for (Transacao t : transacoesDebito) {
            if (t != null) {
                System.out.println(t);
            }
        }
    }
}
