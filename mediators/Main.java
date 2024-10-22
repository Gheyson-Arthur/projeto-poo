package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Testando MediatorAcao
        MediatorAcao mediatorAcao = MediatorAcao.getMediatorAcao();
        Acao acao = new Acao(1, "Ação Teste", LocalDate.now().plusDays(31), 10.0);
        String resultadoIncluirAcao = mediatorAcao.incluir(acao);
        System.out.println("Incluir Ação: " + (resultadoIncluirAcao == null ? "Sucesso" : resultadoIncluirAcao));

        // Testando MediatorEntidadeOperadora
        MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getMediatorEntidadeOperadora();
        EntidadeOperadora entidadeOperadora = new EntidadeOperadora(1, "Operadora Teste", true);
        String resultadoIncluirEntidade = mediatorEntidadeOperadora.incluir(entidadeOperadora);
        System.out.println("Incluir Entidade Operadora: " + (resultadoIncluirEntidade == null ? "Sucesso" : resultadoIncluirEntidade));

        // Testando MediatorTituloDivida
        MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getMediatorTituloDivida();
        TituloDivida tituloDivida = new TituloDivida(1, "Título Teste", LocalDate.now().plusDays(181), 5.0);
        String resultadoIncluirTitulo = mediatorTituloDivida.incluir(tituloDivida);
        System.out.println("Incluir Título de Dívida: " + (resultadoIncluirTitulo == null ? "Sucesso" : resultadoIncluirTitulo));

        // Testando MediatorOperacao
        MediatorOperacao mediatorOperacao = MediatorOperacao.getMediatorOperacao();
        String resultadoOperacao = mediatorOperacao.realizarOperacao(true, 1, 1, 1, 10.0);
        System.out.println("Realizar Operação: " + resultadoOperacao);

        // Buscando as entidades
        Acao acaoBuscada = mediatorAcao.buscar(1);
        EntidadeOperadora entidadeBuscada = mediatorEntidadeOperadora.buscar(1);
        TituloDivida tituloBuscado = mediatorTituloDivida.buscar(1);

        System.out.println("Ação Buscada: " + (acaoBuscada != null ? acaoBuscada.getNome() : "Não encontrada"));
        System.out.println("Entidade Operadora Buscada: " + (entidadeBuscada != null ? entidadeBuscada.getNome() : "Não encontrada"));
        System.out.println("Título de Dívida Buscado: " + (tituloBuscado != null ? tituloBuscado.getNome() : "Não encontrado"));
    }
}
